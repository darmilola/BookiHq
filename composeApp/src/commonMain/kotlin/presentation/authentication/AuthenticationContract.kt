package presentation.authentication

import dev.jordond.compass.Place
import domain.Models.City
import domain.Models.User

class AuthenticationContract {
    interface View {
        fun showUserProfile(user: User)
        fun goToMainScreen(user: User, vendorPhone: String?)
        fun goToCompleteProfileWithEmail(userEmail: String)
        fun goToCompleteProfileWithPhone(phone: String)
        fun showUserLocation(place: Place)
        fun goToConnectVendor(user: User)
        fun onCompleteProfileDone(country: String,city: String, profileId: Long, apiKey: String)
        fun onCompleteProfileError()
        fun onProfileValidationStarted()
        fun onProfileValidationEnded()
        fun onProfileValidationError()
        fun onCompleteProfileStarted()
        fun onProfileUpdateStarted()
        fun onProfileUpdateEnded(isSuccessful: Boolean)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun validateEmail(userEmail: String)
        abstract fun updateFcmToken(userId: Long, fcmToken: String)
        abstract fun validatePhone(phone: String,  requireValidation: Boolean = true)
        abstract fun completeProfile(firstname: String, lastname: String, userEmail: String, authPhone: String,
                                     address: String, contactPhone: String, country: String, city: String,
                                     signupType: String, gender: String, profileImageUrl: String)
       abstract fun updateProfile(userId: Long, firstname: String, lastname: String,  address: String, contactPhone: String,
                                  country: String, city: String, gender: String, profileImageUrl: String)

    }
}