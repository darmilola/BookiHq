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
    private val authenticationPresenter: AuthenticationPresenter,
    private val onUserLocationReady: (Place) -> Unit,
    private val enterPlatform: (userEmail: String, userPhone: String) -> Unit,
    private val completeProfile: (userEmail: String, userPhone: String) -> Unit,
    private val connectVendor: (userEmail: String, userPhone: String) -> Unit,
    private val onVerificationStarted: () -> Unit,
    private val onVerificationEnded: () -> Unit,
    private val onCompleteStarted: () -> Unit,
    private val onCompleteEnded: (Boolean) -> Unit
) : AuthenticationContract.View {
    fun init() {
        authenticationPresenter.registerUIContract(this)
    }

    override fun onProfileValidationStarted() {
        onVerificationStarted()
    }

    override fun onProfileValidationEnded() {
        onVerificationEnded()
    }

    override fun onCompleteProfileStarted() {
        onCompleteStarted()
    }

    override fun onCompleteProfileEnded(isSuccessful: Boolean) {
        onCompleteEnded(isSuccessful)
    }

    override fun goToMainScreen(userEmail: String) {
        enterPlatform(userEmail, "")
    }

    override fun goToMainScreenWithPhone(phone: String) {
        enterPlatform("", phone)
    }

    override fun goToCompleteProfileWithEmail(userEmail: String) {
        completeProfile(userEmail,"")
    }

    override fun goToCompleteProfileWithPhone(phone: String) {
        completeProfile("",phone)
    }

    override fun showUserLocation(place: Place) {
        onUserLocationReady(place)
    }

    override fun goToConnectVendor(userEmail: String) {
        connectVendor(userEmail,"")
    }

    override fun goToConnectVendorWithPhone(userEmail: String) {
        connectVendor("",userEmail)
    }

    override fun showUserProfile(user: User) {}
}
