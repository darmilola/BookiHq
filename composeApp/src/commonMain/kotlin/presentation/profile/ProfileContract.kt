package presentation.profile

import dev.jordond.compass.Place
import domain.Models.VendorTime
import UIStates.AppUIStates
import domain.Models.PlatformNavigator
import domain.Models.PlatformTime
import domain.Models.User
import domain.Models.Vendor

class ProfileContract {
    interface View {
        fun onProfileDeleted()
        fun onProfileUpdated()
        fun showUserLocation(place: Place)
        fun showVendorInfo(vendor: Vendor)
        fun showActionLce(appUIStates: AppUIStates)
    }

    interface MeetingViewContract {
        fun showAvailability(vendorTimes: List<VendorTime>, platformTimes: List<PlatformTime>)
        fun showScreenLce(appUIStates: AppUIStates, message: String = "")
        fun showActionLce(appUIStates: AppUIStates, message: String = "")

    }

    interface SwitchVendorContract {
        fun showActionLce(appUIStates: AppUIStates, message: String = "")

    }

    interface PlatformContract {
        fun showCities(cities: ArrayList<String>)

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
        abstract fun getCities(country: String)
        abstract fun getVendorAvailability(vendorId: Long)
        abstract fun switchVendor(userId: Long, vendorId: Long, action: String,
                         exitReason: String, vendor: Vendor, platformNavigator: PlatformNavigator)
        abstract fun getVendorAccountInfo(vendorId: Long)
        abstract fun joinSpa(vendorId: Long, therapistId: Long)
        abstract fun getUserLocation(lat: Double, lng: Double)
        abstract fun createMeeting(meetingTitle: String,userId: Long, vendorId: Long, serviceStatus: String,bookingStatus: String,appointmentType: String,
                                   appointmentTime: Int, day: Int, month: Int, year: Int, meetingDescription: String,
                                   user: User, vendor: Vendor, platformTime: PlatformTime, monthName: String, platformNavigator: PlatformNavigator,
                                   paymentAmount: Double, paymentMethod: String)

    }
}