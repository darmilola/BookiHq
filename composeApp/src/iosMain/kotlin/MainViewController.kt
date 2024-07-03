
import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.russhwolf.settings.Settings
import domain.Models.Auth0ConnectionResponse
import domain.Enums.AuthSSOScreenNav
import domain.Enums.AuthenticationAction
import domain.Enums.AuthenticationStatus
import domain.Models.PlatformNavigator
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.create
import platform.UIKit.UIViewController
import presentation.Splashscreen.SplashScreen
import presentation.authentication.AuthenticationScreen
import presentation.main.MainScreen


class MainViewController: PlatformNavigator {

    private var onLoginEvent: ((connectionType: String) -> Unit)? = null
    private var onSignupEvent: ((connectionType: String) -> Unit)? = null
    private var onLogoutEvent: ((connectionType: String) -> Unit)? = null
    private var onLocationEvent: (() -> Unit)? = null
    private var onUploadImageEvent: ((data: NSData) -> Unit)? = null
    private val preferenceSettings: Settings = Settings()
    //Handles All Screens Used For Authentication
    private val authScreen = AuthenticationScreen(currentPosition = AuthSSOScreenNav.AUTH_LOGIN.toPath(), platformNavigator = this)
    //Handles All Other Screens in the System
    private val mainScreen = MainScreen(platformNavigator = this)
    fun MainViewController(onLoginEvent:(connectionType: String) -> Unit,
                           onLogoutEvent:(connectionType: String) -> Unit,
                           onSignupEvent: ((connectionType: String) -> Unit)?,
                           onUploadImageEvent: (data: NSData) -> Unit,
                           onLocationEvent: () -> Unit): UIViewController {

            val view = ComposeUIViewController(configure = {
                onFocusBehavior = OnFocusBehavior. DoNothing }) {
                Navigator(SplashScreen(this)) { navigator ->
                    SlideTransition(navigator)

                 }
            }
            this.onLoginEvent = onLoginEvent
            this.onLogoutEvent = onLogoutEvent
            this.onSignupEvent = onSignupEvent
            this.onUploadImageEvent = onUploadImageEvent
            this.onLocationEvent = onLocationEvent
            return view
    }

    fun setAuthResponse(response: Auth0ConnectionResponse) {
        when (response.action) {
            AuthenticationAction.SIGNUP.toPath() -> {
                onSignupAuthResponse(response)
            }
            AuthenticationAction.LOGIN.toPath() -> {
                onLoginAuthResponse(response)
            }
            AuthenticationAction.LOGOUT.toPath() -> {
                onLogoutAuthResponse(response)
            }
        }
    }

    private fun onLoginAuthResponse(response: Auth0ConnectionResponse) {
        val status = response.status
        if (status == AuthenticationStatus.SUCCESS.toPath()) {
            authScreen.setLoginAuthResponse(response)
        }
    }

   fun onImageUploadResponse(imageUrl: String) {
        authScreen.setImageUploadResponse(imageUrl)
        mainScreen.setImageUploadResponse(imageUrl)
    }

    fun onLocationResponse(latitude: Double, longitude: Double) {
        mainScreen.setLocationResponse(latitude, longitude)
    }

    fun onLocationRequestAllowed(isAllowed: Boolean) {
        mainScreen.setLocationRequestAllowed(isAllowed)
    }


    fun onImageUploadProcessing(isDone: Boolean) {
        authScreen.setImageUploadProcessing(isDone)
        mainScreen.setImageUploadProcessing(isDone)
    }


    private fun onLogoutAuthResponse(response: Auth0ConnectionResponse) {
        preferenceSettings.clear()
    }

    private fun onSignupAuthResponse(response: Auth0ConnectionResponse) {
        val status = response.status
        if (status == AuthenticationStatus.SUCCESS.toPath()) {
            authScreen.setSignupAuthResponse(response)
        }
    }

    override fun startAuth0Login(connectionType: String) {
        onLoginEvent?.let {
            it(connectionType)
        }
    }

    override fun startAuth0Signup(connectionType: String) {
        onSignupEvent?.let {
            it(connectionType)
        }
    }

    override fun startAuth0Logout(connectionType: String) {
        onLogoutEvent?.let {
            it(connectionType)
        }
    }

    override fun startVideoCall(authToken: String) {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun startImageUpload(imageByteArray: ByteArray) {
        val data = imageByteArray.usePinned {
            NSData.create(
                bytes = it.addressOf(0),
                length = imageByteArray.size.toULong()
            )
        }
        onUploadImageEvent?.let {
            it(data)
        }
    }


    override fun getUserLocation() {
       onLocationEvent?.let {
           it()
       }
    }

    override fun startGoogleSSO() {
        TODO("Not yet implemented")
    }
}



