package presentation.authentication

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.russhwolf.settings.Settings
import domain.Models.PlatformNavigator
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import utils.ParcelableScreen

@Parcelize
data class CompleteProfileScreen(val platformNavigator: PlatformNavigator, val authEmail: String, val authPhone: String) : ParcelableScreen, KoinComponent {
    @Composable
    override fun Content() {

        val authenticationPresenter : AuthenticationPresenter by inject()
        CompleteProfile(authenticationPresenter,authEmail, authPhone = authPhone, platformNavigator = platformNavigator!!)
    }

}