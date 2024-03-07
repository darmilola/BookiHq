package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import kotlinx.coroutines.flow.StateFlow

class AuthenticationViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var pageIndex = 0

    //private val _authenticationScreenData = mutableStateOf(createAuthenticationScreenData())

    private var _currentScreen =  savedStateHandle.getStateFlow("currentScreen", 1)
   /* val authenticationScreenData: AuthenticationScreenData?
        get() = _authenticationScreenData.value*/

    val currentScreenValue: StateFlow<Int>
        get() = _currentScreen


    fun switchScreen(currentScreen: Int) {
        savedStateHandle["currentScreen"] = currentScreen
    }

  /*  private val authScreenOrder: List<AuthenticationScreenEnum> = listOf(
        AuthenticationScreenEnum.LOGIN_SCREEN,
        AuthenticationScreenEnum.SIGNUP_SCREEN
    )
*/
 /*   private fun createAuthenticationScreenData(): AuthenticationScreenData {
        return AuthenticationScreenData(
            screenType = pageIndex
        )
    }*/

/*    fun changeScreen(newPageIndex: Int) {
        pageIndex = newPageIndex
        _authenticationScreenData.value = createAuthenticationScreenData()
    }*/

}

/*
enum class AuthenticationScreenEnum {
    LOGIN_SCREEN,
    SIGNUP_SCREEN
}


data class AuthenticationScreenData(
    val screenType: Int
)*/
