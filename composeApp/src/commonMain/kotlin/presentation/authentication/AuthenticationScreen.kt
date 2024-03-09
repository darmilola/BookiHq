package presentation.authentication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import domain.Models.PlatformNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import domain.Models.Auth0ConnectionResponse
import domain.Models.AuthSSOScreenNav
import domain.Models.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.UserProfile.SwitchVendor.ConnectPage
import presentation.dialogs.LoadingDialog
import presentation.main.MainScreen
import presentation.viewmodels.AuthenticationViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.viewmodels.UIStates

open class AuthenticationScreen(private var currentPosition: Int = AuthSSOScreenNav.AUTH_LOGIN.toPath(),
                                val  platformNavigator: PlatformNavigator? = null,
                                private val auth0ConnectionResponse: Auth0ConnectionResponse? = null) : Screen, KoinComponent {

    private val preferenceSettings: Settings = Settings()
    private val authenticationPresenter : AuthenticationPresenter by inject()
    private var uiStateViewModel: UIStateViewModel? = null
    private var userNavigation = false
    private var userNavigationPosition = AuthSSOScreenNav.AUTH_LOGIN.toPath()
    private var authenticationViewModel: AuthenticationViewModel? = null
    private var auth0UserEmail = ""

    @Composable
    override fun Content() {

        val contentVisible = remember { mutableStateOf(false) }
        val contentLoading = remember { mutableStateOf(false) }
        val errorVisible = remember { mutableStateOf(false) }
        val imageUploading = remember { mutableStateOf(false) }
        val isAuthEmailAssigned = remember { mutableStateOf(false) }

        // for Ios Auth0
        preferenceSettings as ObservableSettings
        preferenceSettings.addStringListener("auth0Email","") {
                value: String -> auth0UserEmail = value
                isAuthEmailAssigned.value = true
        }

        preferenceSettings.addBooleanListener("imageUploadProcessing",false) {
                value: Boolean -> imageUploading.value = value
        }

        if (auth0ConnectionResponse != null && currentPosition == AuthSSOScreenNav.AUTH_LOGIN.toPath()) {
            // for Android Auth0
            auth0UserEmail = auth0ConnectionResponse.email
            isAuthEmailAssigned.value = true
        }

        uiStateViewModel = kmpViewModel(
            factory = viewModelFactory {
                UIStateViewModel(savedStateHandle = createSavedStateHandle())
            },
        )

        authenticationViewModel = kmpViewModel(
            factory = viewModelFactory {
                AuthenticationViewModel(savedStateHandle = createSavedStateHandle())
            },
        )

        // View Contract Handler Initialisation
        val handler = AuthenticationScreenHandler(authenticationViewModel!!,authenticationPresenter,
            onPageLoading = {
                contentLoading.value = true
            },
            onContentVisible = {
                preferenceSettings.clear()
                contentLoading.value = false
                contentVisible.value = true
                isAuthEmailAssigned.value = false
            },
            onErrorVisible = {
                preferenceSettings.clear()
                errorVisible.value = true
                contentLoading.value = false
                isAuthEmailAssigned.value = false
            },
            enterPlatform = {
                preferenceSettings.clear()
                userNavigation = true
                isAuthEmailAssigned.value = false
                userNavigationPosition = AuthSSOScreenNav.MAIN.toPath()
            },
            completeProfile = {
                preferenceSettings.clear()
                userNavigation = true
                isAuthEmailAssigned.value = false
                userNavigationPosition = AuthSSOScreenNav.COMPLETE_PROFILE.toPath()
            },
            connectVendor = {
                preferenceSettings.clear()
                preferenceSettings["userEmail"] = it
                preferenceSettings["isVendorConnected"] = false
                isAuthEmailAssigned.value = false
                userNavigation = true
                userNavigationPosition = AuthSSOScreenNav.CONNECT_VENDOR.toPath()

            })
        handler.init()

        //Main Service Content Arena
        if (contentLoading.value && currentPosition != AuthSSOScreenNav.COMPLETE_PROFILE.toPath()) {
            Box(modifier = Modifier.fillMaxWidth(0.80f)) {
                LoadingDialog("Loading...")
            }
        }
        else if (contentLoading.value && currentPosition == AuthSSOScreenNav.COMPLETE_PROFILE.toPath()) {
            Box(modifier = Modifier.fillMaxWidth(0.80f)) {
                LoadingDialog("Completing Profile...")
            }
        }

        else if(imageUploading.value) {
            Box(modifier = Modifier.fillMaxWidth(0.80f)) {
                LoadingDialog("Uploading...")
            }
        }

        if (isAuthEmailAssigned.value && auth0UserEmail.isNotEmpty() && currentPosition == AuthSSOScreenNav.AUTH_LOGIN.toPath()) {
            authenticationPresenter.getUserProfile(auth0UserEmail)
        }

        if(!userNavigation) {
            AuthenticationScreenCompose(currentPosition = currentPosition)
        }
        else if(userNavigation && userNavigationPosition == AuthSSOScreenNav.COMPLETE_PROFILE.toPath()){
            currentPosition =  AuthSSOScreenNav.COMPLETE_PROFILE.toPath()
            AuthenticationScreenCompose(currentPosition = userNavigationPosition, userEmail = auth0UserEmail)
        }
        else if(userNavigation && userNavigationPosition == AuthSSOScreenNav.CONNECT_VENDOR.toPath()){
            currentPosition =  AuthSSOScreenNav.CONNECT_VENDOR.toPath()
            val navigator = LocalNavigator.current
            navigator?.replaceAll(ConnectPage())
        }
        else if(userNavigation && userNavigationPosition == AuthSSOScreenNav.MAIN.toPath()){
            currentPosition =  AuthSSOScreenNav.MAIN.toPath()
            val navigator = LocalNavigator.current
            navigator?.replaceAll(MainScreen())
        }

    }

    open fun setLoginAuthResponse(auth0ConnectionResponse: Auth0ConnectionResponse) {
        preferenceSettings["connectionType"] = auth0ConnectionResponse.connectionType
        preferenceSettings["auth0Email"] = auth0ConnectionResponse.email
        preferenceSettings["authAction"] = auth0ConnectionResponse.action
    }

    open fun setSignupAuthResponse(auth0ConnectionResponse: Auth0ConnectionResponse) {
        preferenceSettings["connectionType"] = auth0ConnectionResponse.connectionType
        preferenceSettings["auth0Email"] = auth0ConnectionResponse.email
        preferenceSettings["authAction"] = auth0ConnectionResponse.action
    }

    open fun setImageUploadResponse(imageUrl: String) {
        preferenceSettings["imageUrl"] = imageUrl
    }

    open fun setImageUploadProcessing(isDone: Boolean = false) {
        preferenceSettings["imageUploadProcessing"] = isDone
    }

    @Composable
    open fun AuthenticationScreenCompose(currentPosition: Int = AuthSSOScreenNav.AUTH_LOGIN.toPath(), userEmail: String = "") {
              when (currentPosition) {
                    AuthSSOScreenNav.AUTH_LOGIN.toPath() -> {
                        SignUpLogin(platformNavigator = platformNavigator!!)
                    }

                AuthSSOScreenNav.AUTH_SIGNUP.toPath() -> {
                        SignUpLogin(platformNavigator!!)
                    }

                AuthSSOScreenNav.COMPLETE_PROFILE.toPath() -> {
                        CompleteProfile(authenticationPresenter,userEmail,platformNavigator = platformNavigator!!)
                    }

                }
         }
}

class AuthenticationScreenHandler(
    private val authenticationViewModel: AuthenticationViewModel,
    private val authenticationPresenter: AuthenticationPresenter,
    private val onPageLoading: () -> Unit,
    private val onContentVisible: () -> Unit,
    private val onErrorVisible: () -> Unit,
    private val enterPlatform: (userEmail: String) -> Unit,
    private val completeProfile: (userEmail: String) -> Unit,
    private val connectVendor: (userEmail: String) -> Unit
) : AuthenticationContract.View {
    fun init() {
        authenticationPresenter.registerUIContract(this)
    }

    override fun showLce(uiState: UIStates, message: String) {
        uiState.let {
            when{
                it.loadingVisible -> {
                    onPageLoading()
                }

                it.contentVisible -> {
                    onContentVisible()
                }

                it.errorOccurred -> {
                    onErrorVisible()
                }
            }
        }
    }


    override fun onAuth0Started() {
        authenticationViewModel.setAuth0Started(true)
    }

    override fun onAuth0Ended() {
        authenticationViewModel.setAuth0Ended(true)
    }

    override fun goToMainScreen(userEmail: String) {
        enterPlatform(userEmail)
    }

    override fun goToCompleteProfile(userEmail: String) {
        completeProfile(userEmail)
    }

    override fun goToConnectVendor(userEmail: String) {
        connectVendor(userEmail)
    }

    override fun onProfileDeleted() {
        TODO("Not yet implemented")
    }

    override fun onProfileUpdated() {
        TODO("Not yet implemented")
    }

    override fun showUserProfile(user: User) {
        TODO("Not yet implemented")
    }
    override fun lockUser() {
        TODO("Not yet implemented")
    }

    override fun unlockUser() {
        TODO("Not yet implemented")
    }

    override fun showPasswordAllowedCharTooltip() {
        TODO("Not yet implemented")
    }
}

