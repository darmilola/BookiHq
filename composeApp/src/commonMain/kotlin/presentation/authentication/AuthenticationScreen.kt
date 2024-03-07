package presentation.authentication

import domain.Models.PlatformNavigator
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import di.initKoin
import domain.Models.Auth0ConnectionResponse
import domain.Models.AuthSSOScreen

open class AuthenticationScreen(private val currentPosition: Int = AuthSSOScreen.AUTH_LOGIN.toPath(), open val  platformNavigator: PlatformNavigator? = null) : Screen {
    private val preferenceSettings: Settings = Settings()
    private var userEmail: String = ""

    @Composable
    override fun Content() {
        initKoin()
        preferenceSettings as ObservableSettings
        preferenceSettings.addStringListener("email","email") {
            value: String -> userEmail = value
        }

        // authenticationPresenter.startAuth0()

        // val authStatus = preferenceSettings.getInt("authStatus", 0)
        //println("AuthStatus$authStatus")

        //  if(authStatus == 8){
        //    authViewModel!!.switchScreen(8)
        //}
        AuthenticationScreenCompose(currentPosition = currentPosition)

    }

    open fun setLoginAuthResponse(auth0ConnectionResponse: Auth0ConnectionResponse) {
        preferenceSettings["connectionType"] = auth0ConnectionResponse.connectionType
        preferenceSettings["email"] = auth0ConnectionResponse.email
        preferenceSettings["authAction"] = auth0ConnectionResponse.action
    }

    open fun setSignupAuthResponse(auth0ConnectionResponse: Auth0ConnectionResponse) {
        preferenceSettings["connectionType"] = auth0ConnectionResponse.connectionType
        preferenceSettings["email"] = auth0ConnectionResponse.email
        preferenceSettings["authAction"] = auth0ConnectionResponse.action
    }

    @Composable
    open fun AuthenticationScreenCompose(currentPosition: Int = AuthSSOScreen.AUTH_LOGIN.toPath()) {
              when (currentPosition) {
                    AuthSSOScreen.AUTH_LOGIN.toPath() -> {
                        SignUpLogin(platformNavigator = platformNavigator!!)
                    }

                AuthSSOScreen.AUTH_SIGNUP.toPath() -> {
                        SignUpLogin(platformNavigator!!)
                    }

                AuthSSOScreen.COMPLETE_PROFILE.toPath() -> {
                        CompleteProfile()
                    }

                }

      }
}

