package presentation.Screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import countryList
import domain.Models.PlatformNavigator
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.authentication.AuthenticationPresenter
import presentation.authentication.CompleteProfile
import presentation.profile.ProfilePresenter
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.CityViewModel
import presentation.widgets.DropDownWidget
import utils.ParcelableScreen

@Parcelize
data class CompleteProfileScreen(val platformNavigator: PlatformNavigator, val authEmail: String, val authPhone: String) : ParcelableScreen, KoinComponent {

    private var cityViewModel: CityViewModel? = null
    @Transient
    private var mainViewModel: MainViewModel? = null

    fun setMainViewModel(mainViewModel: MainViewModel) {
        this.mainViewModel = mainViewModel
    }

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()

        if (onBackPressed.value){
            platformNavigator.exitApp()
        }

        if (cityViewModel == null) {
            cityViewModel = kmpViewModel(
                factory = viewModelFactory {
                    CityViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        val authenticationPresenter : AuthenticationPresenter by inject()
        val profilePresenter : ProfilePresenter by inject()


        CompleteProfile(authenticationPresenter,authEmail = authEmail, authPhone = authPhone, platformNavigator = platformNavigator, cityViewModel!!,
            profilePresenter, mainViewModel = mainViewModel!!)
    }

}