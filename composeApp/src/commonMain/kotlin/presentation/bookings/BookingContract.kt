package presentation.bookings

import domain.Models.ServiceTypeTherapists
import UIStates.ActionUIStates
import UIStates.ScreenUIStates
import domain.Models.PlatformNavigator
import domain.Models.PlatformTime
import domain.Models.ServiceTypeItem
import domain.Models.User
import domain.Models.Vendor
import domain.Models.VendorTime

class BookingContract {
    interface View {
        fun showScreenLce(uiState: ScreenUIStates, message: String = "")
        fun showActionLce(uiState: ActionUIStates, message: String = "")
        fun showTherapists(serviceTherapists: List<ServiceTypeTherapists>, platformTime: List<PlatformTime>, vendorTime: List<VendorTime>)
        fun showUnsavedAppointment()
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUnSavedAppointment()
        abstract fun getServiceTherapists(serviceTypeId: Int, vendorId: Long)
        abstract fun createAppointment(userId: Long, vendorId: Long, service_id: Int, serviceTypeId: Int, therapist_id: Int,
                                       appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String, serviceStatus: String,
                                       appointmentType: String, platformNavigator: PlatformNavigator, user: User, vendor: Vendor,monthName: String,
                                       platformTime: PlatformTime, serviceType: ServiceTypeItem, paymentAmount: Double, paymentMethod: String)
    }
}