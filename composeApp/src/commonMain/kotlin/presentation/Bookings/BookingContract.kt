package presentation.Bookings

import androidx.compose.runtime.snapshots.SnapshotStateList
import domain.Models.HomePageResponse
import domain.Models.ServiceTypeSpecialist
import domain.Models.UnsavedAppointment
import presentation.viewmodels.UIStates

class BookingContract {
    interface View {
        fun showLce(uiState: UIStates, message: String = "")
        fun showTherapists(serviceSpecialists: List<ServiceTypeSpecialist>)
        fun showUnsavedAppointment()
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUnSavedAppointment()
        abstract fun getServiceTherapists(serviceTypeId: Int, selectedDate: String)

    }
}