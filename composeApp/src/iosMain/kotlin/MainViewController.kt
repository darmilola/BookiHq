
import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.russhwolf.settings.Settings
import domain.Models.Auth0ConnectionResponse
import domain.Models.AuthSSOScreen
import domain.Models.AuthenticationAction
import domain.Models.AuthenticationStatus
import domain.Models.PlatformNavigator
import platform.UIKit.UIViewController
import presentation.SplashScreen
import presentation.authentication.AuthenticationScreen


class MainViewController: PlatformNavigator {

    private var onLoginEvent: ((connectionType: String) -> Unit)? = null
    private var onSignupEvent: ((connectionType: String) -> Unit)? = null
    private var onLogoutEvent: ((connectionType: String) -> Unit)? = null
    private val preferenceSettings: Settings = Settings()
    private val authScreen = AuthenticationScreen(currentPosition = AuthSSOScreen.AUTH_LOGIN.toPath(), platformNavigator = this)

    fun MainViewController(onLoginEvent:(connectionType: String) -> Unit,
                           onLogoutEvent:(connectionType: String) -> Unit,
                           onSignupEvent: ((connectionType: String) -> Unit)?): UIViewController {

            val view = ComposeUIViewController {
                Navigator(SplashScreen(this)) { navigator ->
                    SlideTransition(navigator)

                 }
            }
            this.onLoginEvent = onLoginEvent
            this.onLogoutEvent = onLogoutEvent
            this.onSignupEvent = onSignupEvent
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
}