package presentation.Screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import com.assignment.moniepointtest.ui.theme.AppTheme
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
import presentation.viewmodels.StatesViewModel
import utils.ParcelableScreen

@Parcelize
data class CompleteProfileScreen(val platformNavigator: PlatformNavigator, val authEmail: String, val authPhone: String) : ParcelableScreen, KoinComponent {

    private var statesViewModel: StatesViewModel? = null
    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    fun setMainViewModel(mainViewModel: MainViewModel) {
        this.mainViewModel = mainViewModel
    }
    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()

        if (onBackPressed.value) {
            platformNavigator.exitApp()
        }

        if (statesViewModel == null) {
            statesViewModel = kmpViewModel(
                factory = viewModelFactory {
                    StatesViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        val authenticationPresenter: AuthenticationPresenter by inject()
        val profilePresenter: ProfilePresenter by inject()


        AppTheme {

            CompleteProfile(
                authenticationPresenter,
                authEmail = authEmail,
                authPhone = authPhone,
                platformNavigator = platformNavigator,
                statesViewModel!!,
                profilePresenter,
                mainViewModel = mainViewModel!!,
                databaseBuilder = databaseBuilder
            )
        }
    }

}