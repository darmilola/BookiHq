package presentation.authentication

import PlatformNavigator
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import domain.Models.Auth0ConnectionResponse
import domain.Models.AuthSSOScreen

class AuthenticationScreen(private val currentPosition: Int = AuthSSOScreen.AUTH_LOGIN.toPath(), val  platformNavigator: PlatformNavigator? = null) : Screen {
    private val preferenceSettings: Settings = Settings()
    @Composable
    override fun Content() {
      //  val currentScreen = remember { mutableStateOf(1) }

     /*   preferenceSettings as ObservableSettings
        preferenceSettings.addStringListener("page","1") {
            value: String -> currentScreen.value = 5
        }

        println("Current Screen is ${currentScreen.value}")*/
       // authenticationPresenter.startAuth0()

       // val authStatus = preferenceSettings.getInt("authStatus", 0)
        //println("AuthStatus$authStatus")

        //  if(authStatus == 8){
        //    authViewModel!!.switchScreen(8)
        //}
        AuthenticationScreenCompose(currentPosition = currentPosition)

    }

    fun setLoginAuthResponse(auth0ConnectionResponse: Auth0ConnectionResponse) {
        println("response $auth0ConnectionResponse")
        preferenceSettings["connectionType"] = auth0ConnectionResponse.connectionType
        preferenceSettings["email"] = auth0ConnectionResponse.email
        preferenceSettings["authAction"] = auth0ConnectionResponse.action
    }

    fun setSignupAuthResponse(auth0ConnectionResponse: Auth0ConnectionResponse) {
        println("response $auth0ConnectionResponse")
        preferenceSettings["connectionType"] = auth0ConnectionResponse.connectionType
        preferenceSettings["email"] = auth0ConnectionResponse.email
        preferenceSettings["authAction"] = auth0ConnectionResponse.action
    }

    @Composable
    fun AuthenticationScreenCompose(currentPosition: Int = AuthSSOScreen.AUTH_LOGIN.toPath()) {
              when (currentPosition) {
                    AuthSSOScreen.AUTH_LOGIN.toPath() -> {
                        SignUpLogin(AuthSSOScreen.AUTH_LOGIN.toPath(), platformNavigator = platformNavigator!!)
                    }

                AuthSSOScreen.AUTH_SIGNUP.toPath() -> {
                        SignUpLogin(AuthSSOScreen.AUTH_SIGNUP.toPath(), platformNavigator!!)
                    }

                AuthSSOScreen.COMPLETE_PROFILE.toPath() -> {
                        CompleteProfile()
                    }

                }

          }
}

