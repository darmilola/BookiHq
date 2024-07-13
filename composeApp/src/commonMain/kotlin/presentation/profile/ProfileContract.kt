package presentation.profile

import dev.jordond.compass.Place
import domain.Models.VendorTime
import UIStates.ActionUIStates
import UIStates.ScreenUIStates
import com.badoo.reaktive.single.Single
import domain.Models.PlatformTime
import domain.Models.ServerResponse

class ProfileContract {
    interface View {
        fun onProfileDeleted()
        fun onProfileUpdated()
        fun showUserLocation(place: Place)
        fun showActionLce(actionUIStates: ActionUIStates)
    }

    interface MeetingViewContract {
        fun showAvailability(vendorTimes: List<VendorTime>, platformTimes: List<PlatformTime>)
        fun showLce(screenUIStates: ScreenUIStates, message: String = "")
        fun showActionLce(actionUIStates: ActionUIStates, message: String = "")

    }

    interface SwitchVendorContract {
        fun showActionLce(actionUIStates: ActionUIStates, message: String = "")

    }

    interface PlatformContract {
        fun showPlatformCities(cities: ArrayList<String>)

    }


    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun registerTalkWithTherapistContract(view: MeetingViewContract?)
        abstract fun registerSwitchVendorContract(view: SwitchVendorContract?)
        abstract fun updateProfile(firstname: String, lastname: String, userEmail: String, address: String,
                                   contactPhone: String,   countryId: Int,
                                   cityId: Int, gender: String, profileImageUrl: String)
        abstract fun deleteProfile(userEmail: String)
        abstract fun registerPlatformContract(view: PlatformContract?)
        abstract fun getPlatformCities(country: String)
        abstract fun getVendorAvailability(vendorId: Long)
       abstract fun switchVendor(userId: Long, vendorId: Long, action: String,
                         exitReason: String)
        abstract fun getUserLocation(lat: Double, lng: Double)
        abstract fun createMeeting(meetingTitle: String,userId: Long, vendorId: Long, serviceStatus: String, appointmentType: String,
                                   appointmentTime: Int, day: Int, month: Int, year: Int, meetingDescription: String)

    }
}