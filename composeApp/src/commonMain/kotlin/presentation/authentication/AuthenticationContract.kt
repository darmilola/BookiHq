package presentation.authentication

import domain.Models.User
import presentation.viewmodels.UIStates

class AuthenticationContract {
    interface View {
        fun onAuth0Started()

        fun onAuth0Ended()

        /**
         * Registration successful callback.
         */
        fun goToMainScreen(userEmail: String)

        fun goToCompleteProfile(userEmail: String)

        fun goToConnectVendor(userEmail: String)

        fun onProfileDeleted()

        fun onProfileUpdated()

        fun showUserProfile(user: User)

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
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun startAuth0()
        abstract fun endAuth0()
        abstract fun completeProfile(firstname: String, lastname: String, userEmail: String, address: String,
                                     contactPhone: String, country: String, gender: String, profileImageUrl: String)
        abstract fun updateProfile(firstname: String, lastname: String, userEmail: String, address: String,
                                     contactPhone: String, country: String, gender: String, profileImageUrl: String)
        abstract fun connectVendor(userEmail: String, vendorId: Int)
        abstract fun getUserProfile(userEmail: String)

        abstract fun deleteProfile(userEmail: String)

    }
}