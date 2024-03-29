package presentation.authentication

import com.badoo.reaktive.single.Single
import dev.jordond.compass.Place
import domain.Models.User
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.UIStates

class AuthenticationContract {
    interface View {
        fun onAuth0Started()

        fun onAuth0Ended()
        fun showUserProfile(user: User)


        /**
         * Registration successful callback.
         */
        fun goToMainScreen(userEmail: String)

        fun goToCompleteProfile(userEmail: String)

        fun showUserLocation(place: Place)

        fun goToConnectVendor(userEmail: String)

        /**
         * Registration failure callback.
         *
         * @param errorText Text to be displayed.
         */

        /**
         * Might be useful if wanted to show user that action is in progress with some funny animation
         * or just simple progressbar.
         */
        abstract fun lockUser()

        /**
         * Unlocks user's action. Now user should be able to re-execute request.
         */
        abstract fun unlockUser()

        /**
         * Shows Allowed Char Tooltip for Password View
         */
        abstract fun showPasswordAllowedCharTooltip()

        fun showLce(uiState: UIStates, message: String = "")
        fun showAsyncLce(uiState: AsyncUIStates, message: String = "")
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