package presentation.authentication

import dev.jordond.compass.Place
import domain.Models.User
import UIStates.ActionUIStates
import UIStates.ScreenUIStates
import presentation.profile.ProfileContract

class AuthenticationContract {
    interface View {
        fun showUserProfile(user: User)
        fun goToMainScreen(userEmail: String)
        fun goToMainScreenWithPhone(phone: String)
        fun goToCompleteProfileWithEmail(userEmail: String)
        fun goToCompleteProfileWithPhone(phone: String)
        fun showUserLocation(place: Place)
        fun goToConnectVendor(userEmail: String)
        fun goToConnectVendorWithPhone(phone: String)
        fun onProfileValidationStarted()
        fun onProfileValidationEnded()

        fun onCompleteProfileStarted()
        fun onCompleteProfileEnded(isSuccessful: Boolean = false)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun validateUserProfile(userEmail: String)
        abstract fun validateEmail(userEmail: String)
        abstract fun validatePhone(phone: String)
        abstract fun getUserLocation(lat: Double, lng: Double)
        abstract fun completeProfile(firstname: String, lastname: String, userEmail: String, authPhone: String,
                                     signupType: String, country: String, city: String, gender: String, profileImageUrl: String)

    }
}