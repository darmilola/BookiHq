package screens.authentication

import androidx.compose.runtime.mutableStateOf
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class AuthenticationViewModel: ViewModel() {

    private var pageIndex = 0

    private val _authenticationScreenData = mutableStateOf(createAuthenticationScreenData())
    val authenticationScreenData: AuthenticationScreenData?
        get() = _authenticationScreenData.value



    private val authScreenOrder: List<AuthenticationScreenEnum> = listOf(
        AuthenticationScreenEnum.LOGIN_SCREEN,
        AuthenticationScreenEnum.SIGNUP_SCREEN)

    private fun createAuthenticationScreenData(): AuthenticationScreenData {
        return AuthenticationScreenData(
            screenType = pageIndex
        )
    }

    fun changeScreen(newPageIndex: Int) {
        pageIndex = newPageIndex
        _authenticationScreenData.value = createAuthenticationScreenData()
    }

}

enum class AuthenticationScreenEnum {
    LOGIN_SCREEN,
    SIGNUP_SCREEN
}


data class AuthenticationScreenData(
    val screenType: Int
)