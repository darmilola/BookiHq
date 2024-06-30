package presentation.DomainViewHandler

import com.russhwolf.settings.Settings
import dev.jordond.compass.Place
import domain.Models.User
import presentation.authentication.AuthenticationContract
import presentation.authentication.AuthenticationPresenter
import UIStates.ActionUIStates
import presentation.viewmodels.AuthenticationViewModel
import UIStates.ScreenUIStates

class AuthenticationScreenHandler(
    private val authenticationViewModel: AuthenticationViewModel,
    private val authenticationPresenter: AuthenticationPresenter,
    private val onUserLocationReady: (Place) -> Unit,
    private val preferenceSettings: Settings,
    private val onPageLoading: () -> Unit,
    private val onContentVisible: () -> Unit,
    private val onErrorVisible: () -> Unit,
    private val enterPlatform: (userEmail: String) -> Unit,
    private val completeProfile: (userEmail: String) -> Unit,
    private val connectVendor: (userEmail: String) -> Unit,
    private val isLoading:() -> Unit,
    private val isSuccess:() -> Unit,
    private val isFailed:() -> Unit
) : AuthenticationContract.View {
    fun init() {
        authenticationPresenter.registerUIContract(this)
    }

    override fun showLce(uiState: ScreenUIStates, message: String) {
        uiState.let {
            when{
                it.loadingVisible -> {
                    onPageLoading()
                }

                it.contentVisible -> {
                    onContentVisible()
                }

                it.errorOccurred -> {
                    onErrorVisible()
                }
            }
        }
    }

    override fun showAsyncLce(uiState: ActionUIStates, message: String) {
        uiState.let {
            when{
                it.isLoading -> {
                    isLoading()
                }

                it.isSuccess -> {
                    isSuccess()
                }

                it.isFailed -> {
                    isFailed()
                }
            }
        }
    }


    override fun onAuth0Started() {
        preferenceSettings.clear()
        authenticationViewModel.setAuth0Started(true)
    }

    override fun onAuth0Ended() {
        authenticationViewModel.setAuth0Ended(true)
    }

    override fun goToMainScreen(userEmail: String) {
        enterPlatform(userEmail)
    }

    override fun goToCompleteProfile(userEmail: String) {
        completeProfile(userEmail)
    }

    override fun showUserLocation(place: Place) {
        onUserLocationReady(place)
    }

    override fun goToConnectVendor(userEmail: String) {
        connectVendor(userEmail)
    }
    override fun showUserProfile(user: User) {
        TODO("Not yet implemented")
    }
}
