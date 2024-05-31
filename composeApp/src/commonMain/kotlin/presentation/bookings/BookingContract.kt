package presentation.bookings

import androidx.compose.runtime.snapshots.SnapshotStateList
import domain.Models.ServiceTime
import domain.Models.ServiceTypeSpecialist
import domain.Models.UnsavedAppointment
import domain.Models.User
import domain.Models.Vendor
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.UIStates

class BookingContract {
    interface View {
        fun showLce(uiState: UIStates, message: String = "")
        fun showBookingLce(uiState: AsyncUIStates, message: String = "")
        fun showTherapists(serviceSpecialists: List<ServiceTypeSpecialist>)
        fun showUnsavedAppointment()
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUnSavedAppointment()
        abstract fun getServiceTherapists(serviceTypeId: Int, day: Int, month: Int, year: Int)
        abstract fun createAppointment(unsavedAppointments: ArrayList<UnsavedAppointment>, currentUser: User, currentVendor: Vendor)
    }
}