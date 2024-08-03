package presentation.Screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Models.PlatformNavigator
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.authentication.AuthenticationPresenter
import presentation.authentication.CompleteProfile
import presentation.profile.ProfilePresenter
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PlatformViewModel
import utils.ParcelableScreen

@Parcelize
data class CompleteProfileScreen(val platformNavigator: PlatformNavigator, val authEmail: String, val authPhone: String) : ParcelableScreen, KoinComponent {

    private var platformViewModel: PlatformViewModel? = null
    @Transient
    private var mainViewModel: MainViewModel? = null

    fun setMainViewModel(mainViewModel: MainViewModel) {
        this.mainViewModel = mainViewModel
    }

    @Composable
    override fun Content() {

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()

        if (onBackPressed.value){
            platformNavigator.exitApp()
        }

        if (platformViewModel == null) {
            platformViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PlatformViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        val authenticationPresenter : AuthenticationPresenter by inject()
        val profilePresenter : ProfilePresenter by inject()


        CompleteProfile(authenticationPresenter,authEmail = authEmail, authPhone = authPhone, platformNavigator = platformNavigator!!, platformViewModel!!,
            profilePresenter, mainViewModel = mainViewModel!!)
    }

}