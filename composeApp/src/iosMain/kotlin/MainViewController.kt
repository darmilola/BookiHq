
import androidx.compose.runtime.remember
import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import applications.room.getAppDatabase
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import domain.Models.Auth0ConnectionResponse
import domain.Models.PlatformNavigator
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.create
import platform.UIKit.UIViewController
import presentation.Screens.SplashScreen
import presentation.Screens.MainScreen
import presentation.Screens.PhoneInputScreen
import presentation.Screens.WelcomeScreen
import presentation.viewmodels.MainViewModel


class MainViewController {

    private var mainViewModel: MainViewModel? = null
    fun appStartUiView(platformNavigator: PlatformNavigator): UIViewController {
        return ComposeUIViewController(configure = {
            onFocusBehavior = OnFocusBehavior.DoNothing
        }) {
            val database = remember { getAppDatabase() }
            if (mainViewModel == null) {
                mainViewModel = kmpViewModel(
                    factory = viewModelFactory {
                        MainViewModel(savedStateHandle = createSavedStateHandle())
                    },
                )
            }
            Navigator(
                SplashScreen(
                    platformNavigator,
                    mainViewModel = mainViewModel!!,
                    databaseBuilder = database
                )
            ) { navigator ->
                SlideTransition(navigator)

            }
        }
    }

    fun welcomePageUiView(platformNavigator: PlatformNavigator, googleAuthEmail: String): UIViewController {
        return ComposeUIViewController(configure = {
            onFocusBehavior = OnFocusBehavior.DoNothing
        }) {
            val database = remember { getAppDatabase() }
            val welcomeScreen =  WelcomeScreen(platformNavigator, googleAuthEmail = googleAuthEmail)
            welcomeScreen.setDatabaseBuilder(database)
            welcomeScreen.setMainViewModel(mainViewModel!!)
            Navigator(welcomeScreen) { navigator ->
                SlideTransition(navigator)

            }
        }
    }


    fun mainScreenUiView(platformNavigator: PlatformNavigator): UIViewController {
        return ComposeUIViewController(configure = {
            onFocusBehavior = OnFocusBehavior.DoNothing
        }) {
            val database = remember { getAppDatabase() }
            if (mainViewModel == null) {
                mainViewModel = kmpViewModel(
                    factory = viewModelFactory {
                        MainViewModel(savedStateHandle = createSavedStateHandle())
                    },
                )
            }
            val mainScreen =  MainScreen(platformNavigator)
            mainScreen.setDatabaseBuilder(database)
            mainScreen.setMainViewModel(mainViewModel!!)
            Navigator(mainScreen) { navigator ->
                SlideTransition(navigator)

            }
        }
    }
}




