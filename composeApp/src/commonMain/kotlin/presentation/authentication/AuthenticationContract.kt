package presentation.authentication

import dev.jordond.compass.Place
import domain.Models.User
import UIStates.ActionUIStates
import UIStates.ScreenUIStates

class AuthenticationContract {
    interface View {
        fun onAuth0Started()

        fun onAuth0Ended()
        fun showUserProfile(user: User)
        fun goToMainScreen(userEmail: String)

        fun goToCompleteProfile(userEmail: String)

        fun showUserLocation(place: Place)

        fun goToConnectVendor(userEmail: String)

        fun showLce(uiState: ScreenUIStates, message: String = "")
        fun showAsyncLce(uiState: ActionUIStates, message: String = "")
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun startAuth0()
        abstract fun endAuth0()
        abstract fun validateUserProfile(userEmail: String)
        abstract fun getUserLocation(lat: Double, lng: Double)
        abstract fun completeProfile(firstname: String, lastname: String, userEmail: String, address: String,
                                     contactPhone: String,   countryId: Int,
                                     cityId: Int, gender: String, profileImageUrl: String)

    }
}