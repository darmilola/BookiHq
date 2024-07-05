package presentation.authentication

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import domain.Models.PlatformNavigator
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.profile.ProfilePresenter
import presentation.viewmodels.ConnectPageViewModel
import presentation.viewmodels.PlatformViewModel
import utils.ParcelableScreen

@Parcelize
data class CompleteProfileScreen(val platformNavigator: PlatformNavigator, val authEmail: String, val authPhone: String) : ParcelableScreen, KoinComponent {

    private var platformViewModel: PlatformViewModel? = null

    @Composable
    override fun Content() {

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
            profilePresenter!!)
    }

}