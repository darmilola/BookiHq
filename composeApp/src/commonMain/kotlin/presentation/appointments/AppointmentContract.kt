package presentation.appointments

import domain.Models.Appointment
import domain.Models.ResourceListEnvelope
import presentation.viewmodels.UIStates

interface AppointmentContract {
    interface View {
        fun showLce(uiState: UIStates, message: String = "")
        fun showAppointments(appointments: ResourceListEnvelope<Appointment>?)
        fun onLoadMoreAppointmentStarted(isSuccess: Boolean = false)
        fun onLoadMoreAppointmentEnded(isSuccess: Boolean = false)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUserAppointments(userId: Int)
        abstract fun getMoreAppointments(userId: Int, nextPage: Int = 1)
    }
}
