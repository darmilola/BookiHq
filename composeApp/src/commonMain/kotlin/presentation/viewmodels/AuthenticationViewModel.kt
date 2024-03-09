package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.Auth0ConnectionResponse
import domain.Models.AuthenticationResponse
import domain.Models.User
import kotlinx.coroutines.flow.StateFlow

class AuthenticationViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _authenticatedEmail = savedStateHandle.getStateFlow("userEmail", "")

    private var _auth0Response = savedStateHandle.getStateFlow("auth0Response", Auth0ConnectionResponse())

    private var _userData = savedStateHandle.getStateFlow("user", User())

    val authenticatedEmail: StateFlow<String>
        get() = _authenticatedEmail

    val auth0Response: StateFlow<Auth0ConnectionResponse>
        get() = _auth0Response

    val userData: StateFlow<User>
        get() = _userData

    fun setAuthenticatedEmail(userEmail: String) {
        savedStateHandle["userEmail"] = userEmail
    }

    fun setAuth0Started(auth0Started: Boolean) {
        savedStateHandle["auth0Started"] = auth0Started
    }

    fun setAuth0Response(auth0Response: Auth0ConnectionResponse) {
        savedStateHandle["auth0Response"] = auth0Response
    }

    fun setAuth0Ended(auth0Ended: Boolean) {
        savedStateHandle["auth0Ended"] = auth0Ended
    }

    fun setUserData(user: User) {
        savedStateHandle["user"] = user
    }

}