package presentation.bookings

import domain.Models.ServiceTypeTherapists
import UIStates.ActionUIStates
import UIStates.ScreenUIStates
import com.badoo.reaktive.single.Single
import domain.Models.PendingAppointmentResponse
import domain.Models.PlatformNavigator
import domain.Models.PlatformTime
import domain.Models.ServerResponse
import domain.Models.ServiceTherapistsResponse
import domain.Models.ServiceTypeItem
import domain.Models.User
import domain.Models.UserAppointmentsData
import domain.Models.Vendor
import domain.Models.VendorTime

class BookingContract {
    interface View {
        fun showScreenLce(uiState: ScreenUIStates, message: String = "")
        fun showActionLce(uiState: ActionUIStates, message: String = "")
        fun showCreateAppointmentActionLce(uiState: ActionUIStates, message: String = "")
        fun showTherapists(serviceTherapists: List<ServiceTypeTherapists>, platformTime: List<PlatformTime>, vendorTime: List<VendorTime>)
        fun showPendingAppointment(pendingAppointments: List<UserAppointmentsData>)
        fun showUnsavedAppointment()
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUnSavedAppointment()
        abstract fun getServiceTherapists(serviceTypeId: Int, vendorId: Long)
        abstract fun createAppointment(userId: Long, vendorId: Long, paymentAmount: Double, paymentMethod: String, bookingStatus: String, day: Int, month: Int, year: Int)
        abstract fun getPendingAppointment(userId: Long)
        abstract fun deletePendingAppointment(pendingAppointmentId: Long)
        abstract fun silentDelete(pendingAppointmentId: Long)
        abstract fun createPendingAppointment(userId: Long, vendorId: Long, serviceId: Int, serviceTypeId: Int, therapistId: Int,
                                              appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String,
                                              serviceStatus: String, appointmentType: String,
                                              paymentAmount: Double, paymentMethod: String, bookingStatus: String)
    }
}