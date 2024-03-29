package presentation.profile

import dev.jordond.compass.Place
import presentation.viewmodels.AsyncUIStates

class ProfileContract {
    interface View {
        fun onProfileDeleted()

        fun onProfileUpdated()
        fun showUserLocation(place: Place)

        fun showLce(asyncUIStates: AsyncUIStates, message: String = "")
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun updateProfile(firstname: String, lastname: String, userEmail: String, address: String,
                                   contactPhone: String,   countryId: Int,
                                   cityId: Int, gender: String, profileImageUrl: String)

        abstract fun deleteProfile(userEmail: String)
        abstract fun getUserLocation(lat: Double, lng: Double)

    }
}