package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.Auth0ConnectionResponse
import domain.Models.AuthenticationResponse
import domain.Models.User
import kotlinx.coroutines.flow.StateFlow

class AuthenticationViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _authenticatedEmail = savedStateHandle.getStateFlow("userEmail", "")

    private var _userData = savedStateHandle.getStateFlow("user", User())

    val authenticatedEmail: StateFlow<String>
        get() = _authenticatedEmail
    val userData: StateFlow<User>
        get() = _userData

    fun setAuthenticatedEmail(userEmail: String) {
        savedStateHandle["userEmail"] = userEmail
    }

    fun setUserData(user: User) {
        savedStateHandle["user"] = user
    }

}