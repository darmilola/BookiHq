package presentation.Bookings

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.badoo.reaktive.single.Single
import domain.Models.Appointment
import domain.Models.HomePageResponse
import domain.Models.ServerResponse
import domain.Models.ServiceTypeSpecialist
import domain.Models.SpecialistAvailabilityResponse
import domain.Models.UnsavedAppointment
import domain.Models.User
import domain.Models.Vendor
import domain.bookings.CreateAppointmentRequest
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
        abstract fun getServiceTherapists(serviceTypeId: Int, selectedDate: String)
        abstract fun createAppointment(unsavedAppointments: SnapshotStateList<UnsavedAppointment>, currentUser: User, currentVendor: Vendor)
    }
}