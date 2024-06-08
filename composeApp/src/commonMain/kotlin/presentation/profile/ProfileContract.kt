package presentation.profile

import dev.jordond.compass.Place
import domain.Models.VendorTime
import presentation.viewmodels.ActionUIStates

class ProfileContract {
    interface View {
        fun onProfileDeleted()

        fun onProfileUpdated()
        fun showUserLocation(place: Place)

        fun showLce(actionUIStates: ActionUIStates, message: String = "")
    }

    interface VideoView {
        fun showAvailability(availableTimes: List<VendorTime>)
        fun showLce(actionUIStates: ActionUIStates, message: String = "")

    }


    abstract class Presenter {
        abstract fun registerUIContract(view: View?)

        abstract fun registerTalkWithTherapistContract(view: VideoView?)
        abstract fun updateProfile(firstname: String, lastname: String, userEmail: String, address: String,
                                   contactPhone: String,   countryId: Int,
                                   cityId: Int, gender: String, profileImageUrl: String)
        abstract fun deleteProfile(userEmail: String)
        abstract fun getVendorAvailability(vendorId: Int)
        abstract fun getUserLocation(lat: Double, lng: Double)

    }
}