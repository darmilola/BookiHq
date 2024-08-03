
import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
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
                    mainViewModel = mainViewModel!!
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
            val welcomeScreen =  WelcomeScreen(platformNavigator, googleAuthEmail = googleAuthEmail)
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
            if (mainViewModel == null) {
                mainViewModel = kmpViewModel(
                    factory = viewModelFactory {
                        MainViewModel(savedStateHandle = createSavedStateHandle())
                    },
                )
            }
            val mainScreen =  MainScreen(platformNavigator)
            mainScreen.setMainViewModel(mainViewModel!!)
            Navigator(mainScreen) { navigator ->
                SlideTransition(navigator)

            }
        }
    }
}




