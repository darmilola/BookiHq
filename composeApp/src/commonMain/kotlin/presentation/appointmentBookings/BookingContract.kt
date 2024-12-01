package presentation.appointmentBookings

import domain.Models.ServiceTypeTherapists
import UIStates.AppUIStates
import domain.Models.PlatformNavigator
import domain.Models.PlatformTime
import domain.Models.ServiceImages
import domain.Models.ServiceTypeItem
import domain.Models.UserAppointment
import domain.Models.Vendor
import domain.Models.VendorTime

class BookingContract {
    interface View {
        fun showLoadPendingAppointmentLce(uiState: AppUIStates, message: String = "")
        fun showCreateAppointmentActionLce(uiState: AppUIStates, message: String = "")
        fun getTherapistActionLce(uiState: AppUIStates, message: String = "")
        fun getServiceTypesLce(uiState: AppUIStates, message: String = "")
        fun getTimesActionLce(uiState: AppUIStates, message: String = "")
        fun showTherapists(serviceTherapists: List<ServiceTypeTherapists>, platformTime: List<PlatformTime>, vendorTime: List<VendorTime>)
        fun showTimes(platformTime: List<PlatformTime>, vendorTime: List<VendorTime>)
        fun showServiceTypes(serviceTypes: List<ServiceTypeItem>, serviceImages: List<ServiceImages>)
        fun showPendingBookingAppointment(pendingAppointments: List<UserAppointment>)
        fun showUnsavedAppointment()
    }

    interface CancelContractView {
        fun showCancelActionLce(uiState: AppUIStates, message: String = "")
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun registerCancelUIContract(view: CancelContractView?)
        abstract fun getUnSavedAppointment()
        abstract fun getServiceTherapists(serviceTypeId: Long, vendorId: Long, day: Int, month: Int, year: Int)
        abstract fun getMobileServiceTherapists(serviceTypeId: Long, vendorId: Long, day: Int, month: Int, year: Int)
        abstract fun getServiceData(serviceId: Long)
        abstract fun createAppointment(userId: Long, vendorId: Long, paymentAmount: Int, paymentMethod: String, bookingStatus: String, day: Int, month: Int, year: Int, platformNavigator: PlatformNavigator, vendor: Vendor)
        abstract fun getPendingBookingAppointment(userId: Long, bookingStatus: String)
        abstract fun deletePendingBookingAppointment(pendingAppointmentId: Long)
        abstract fun getTimeAvailability(vendorId: Long)
        abstract fun silentDeletePendingBookingAppointment(pendingAppointmentId: Long)
        abstract fun deleteAllPendingAppointment(userId: Long, bookingStatus: String)
        abstract fun createPendingBookingAppointment(userId: Long, vendorId: Long, serviceId: Long, serviceTypeId: Long, therapistId: Long,
                                                     appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String, bookingStatus: String)
        abstract fun createPendingPackageBookingAppointment(userId: Long, vendorId: Long, packageId: Long, appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String, bookingStatus: String)
    }
}