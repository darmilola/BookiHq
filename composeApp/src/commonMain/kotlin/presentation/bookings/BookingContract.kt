package presentation.bookings

import domain.Models.ServiceTypeTherapists
import UIStates.AppUIStates
import domain.Models.PlatformTime
import domain.Models.UserAppointment
import domain.Models.VendorTime

class BookingContract {
    interface View {
        fun showLoadPendingAppointmentLce(uiState: AppUIStates, message: String = "")
        fun showDeleteActionLce(uiState: AppUIStates, message: String = "")
        fun showCreateAppointmentActionLce(uiState: AppUIStates, message: String = "")
        fun getTherapistActionLce(uiState: AppUIStates, message: String = "")
        fun showTherapists(serviceTherapists: List<ServiceTypeTherapists>, platformTime: List<PlatformTime>, vendorTime: List<VendorTime>)
        fun showPendingBookingAppointment(pendingAppointments: List<UserAppointment>)
        fun showUnsavedAppointment()
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUnSavedAppointment()
        abstract fun getServiceTherapists(serviceTypeId: Int, vendorId: Long)
        abstract fun createAppointment(userId: Long, vendorId: Long, paymentAmount: Double, paymentMethod: String, bookingStatus: String, day: Int, month: Int, year: Int)
        abstract fun getPendingBookingAppointment(userId: Long, bookingStatus: String)
        abstract fun deletePendingBookingAppointment(pendingAppointmentId: Long)
        abstract fun silentDeletePendingBookingAppointment(pendingAppointmentId: Long)
        abstract fun createPendingBookingAppointment(userId: Long, vendorId: Long, serviceId: Int, serviceTypeId: Int, therapistId: Int,
                                                     appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String,
                                                     serviceStatus: String, paymentAmount: Double, paymentMethod: String, bookingStatus: String)
    }
}