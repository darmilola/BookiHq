package presentation.Bookings

import domain.Models.HomePageResponse
import domain.Models.ServiceTypeSpecialist
import presentation.viewmodels.UIStates

class BookingContract {
    interface View {
        fun showLce(uiState: UIStates, message: String = "")
        fun showTherapists(serviceSpecialists: List<ServiceTypeSpecialist>)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getServiceTherapists(serviceTypeId: Int, selectedDate: String)

    }
}