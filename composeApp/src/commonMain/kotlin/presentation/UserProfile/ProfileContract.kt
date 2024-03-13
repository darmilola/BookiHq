package presentation.UserProfile

import domain.Models.User
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.UIStates

class ProfileContract {
    interface View {
        fun onProfileDeleted()

        fun onProfileUpdated()

        fun showLce(asyncUIStates: AsyncUIStates, message: String = "")
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun updateProfile(firstname: String, lastname: String, userEmail: String, address: String,
                                   contactPhone: String,   countryId: Int,
                                   cityId: Int, gender: String, profileImageUrl: String)

        abstract fun deleteProfile(userEmail: String)

    }
}