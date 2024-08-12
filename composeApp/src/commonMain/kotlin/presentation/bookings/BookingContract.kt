package presentation.bookings

import domain.Models.ServiceTypeTherapists
import UIStates.ActionUIStates
import UIStates.ScreenUIStates
import domain.Models.PlatformTime
import domain.Models.UserAppointment
import domain.Models.VendorTime

class BookingContract {
    interface View {
        fun showScreenLce(uiState: ScreenUIStates, message: String = "")
        fun showActionLce(uiState: ActionUIStates, message: String = "")
        fun showCreateAppointmentActionLce(uiState: ActionUIStates, message: String = "")
        fun showTherapists(serviceTherapists: List<ServiceTypeTherapists>, platformTime: List<PlatformTime>, vendorTime: List<VendorTime>)
        fun showPendingBookingAppointment(pendingAppointments: List<UserAppointment>)
        fun showUnsavedAppointment()
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUnSavedAppointment()
        abstract fun getServiceTherapists(serviceTypeId: Int, vendorId: Long)
        abstract fun createAppointment(userId: Long, vendorId: Long, paymentAmount: Double, paymentMethod: String, bookingStatus: String, day: Int, month: Int, year: Int)
        abstract fun getPendingBookingAppointment(userId: Long)
        abstract fun deletePendingBookingAppointment(pendingAppointmentId: Long)
        abstract fun silentDeletePendingBookingAppointment(pendingAppointmentId: Long)
        abstract fun createPendingBookingAppointment(userId: Long, vendorId: Long, serviceId: Int, serviceTypeId: Int, therapistId: Int,
                                                     appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String,
                                                     serviceStatus: String, appointmentType: String,
                                                     paymentAmount: Double, paymentMethod: String, bookingStatus: String)
    }
}