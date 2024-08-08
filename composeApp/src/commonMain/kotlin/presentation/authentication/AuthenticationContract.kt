package presentation.authentication

import dev.jordond.compass.Place
import domain.Models.User
import UIStates.ActionUIStates
import UIStates.ScreenUIStates
import com.badoo.reaktive.single.Single
import domain.Models.ServerResponse
import domain.Models.Vendor
import presentation.profile.ProfileContract

class AuthenticationContract {
    interface View {
        fun showUserProfile(user: User)
        fun goToMainScreen(user: User, vendorPhone: String?)
        fun goToCompleteProfileWithEmail(userEmail: String)
        fun goToCompleteProfileWithPhone(phone: String)
        fun showUserLocation(place: Place)
        fun goToConnectVendor(user: User)
        fun onCompleteProfileDone(country: String, profileId: Long, apiKey: String)
        fun onCompleteProfileError()
        fun onProfileValidationStarted()
        fun onProfileValidationEnded()
        fun onCompleteProfileStarted()
        fun onProfileUpdateStarted()
        fun onProfileUpdateEnded(isSuccessful: Boolean)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun validateEmail(userEmail: String)
        abstract fun updateFcmToken(userId: Long, fcmToken: String)
        abstract fun validatePhone(phone: String,  requireValidation: Boolean = true)
        abstract fun getUserLocation(lat: Double, lng: Double)
        abstract fun completeProfile(firstname: String, lastname: String, userEmail: String, authPhone: String,
                                     signupType: String, country: String, city: String, gender: String, profileImageUrl: String)
       abstract fun updateProfile(userId: Long, firstname: String, lastname: String, address: String, contactPhone: String,
                                  country: String, city: String, gender: String, profileImageUrl: String)

    }
}