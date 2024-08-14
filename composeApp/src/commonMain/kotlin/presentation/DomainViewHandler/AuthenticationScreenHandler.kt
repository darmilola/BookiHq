package presentation.DomainViewHandler

import dev.jordond.compass.Place
import domain.Models.User
import presentation.authentication.AuthenticationContract
import presentation.authentication.AuthenticationPresenter

class AuthenticationScreenHandler(
    private val authenticationPresenter: AuthenticationPresenter,
    private val onUserLocationReady: (Place) -> Unit,
    private val enterPlatform: (User,vendorPhone: String?) -> Unit,
    private val completeProfile: (userEmail: String, userPhone: String) -> Unit,
    private val connectVendorOnProfileCompleted: (country: String, city: String, profileId: Long, apiKey: String) -> Unit,
    private val connectVendor: (User) -> Unit,
    private val onVerificationStarted: () -> Unit,
    private val onVerificationEnded: () -> Unit,
    private val onCompleteStarted: () -> Unit,
    private val onCompleteEnded: (Boolean) -> Unit,
    private val onUpdateStarted: () -> Unit,
    private val onUpdateEnded: (Boolean) -> Unit
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

    override fun onProfileUpdateStarted() {
        onUpdateStarted()
    }

    override fun onProfileUpdateEnded(isSuccessful: Boolean) {
        onUpdateEnded(isSuccessful)
    }

    override fun goToMainScreen(user: User, vendorPhone: String?) {
        enterPlatform(user, vendorPhone)
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

    override fun onCompleteProfileDone(country: String, city: String, profileId: Long, apiKey: String) {
            onCompleteEnded(true)
            connectVendorOnProfileCompleted(country,city, profileId, apiKey)
    }
    override fun goToConnectVendor(user: User) {
        connectVendor(user)
    }

    override fun onCompleteProfileError() {
        onCompleteEnded(false)
    }

    override fun showUserProfile(user: User) {}
}
