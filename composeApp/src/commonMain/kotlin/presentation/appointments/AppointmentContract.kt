package presentation.appointments

import domain.Models.Appointment
import domain.Models.ResourceListEnvelope
import domain.Models.ServiceTime
import domain.Models.TimeOffs
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.UIStates

interface AppointmentContract {
    interface View {
        fun showLce(uiState: UIStates, message: String = "")
        fun showAsyncLce(uiState: AsyncUIStates, message: String = "")
        fun showAppointments(appointments: ResourceListEnvelope<Appointment>?)
        fun showTherapistAvailability(availableTimes: List<ServiceTime>, bookedAppointment: List<Appointment>, timeOffs: List<TimeOffs>)
        fun onLoadMoreAppointmentStarted(isSuccess: Boolean = false)
        fun onLoadMoreAppointmentEnded(isSuccess: Boolean = false)
        fun  onPostponeAppointmentStarted()
        fun  onPostponeAppointmentSuccess()
        fun  onPostponeAppointmentFailed()
        fun  onDeleteAppointmentStarted()
        fun  onDeleteAppointmentSuccess()
        fun  onDeleteAppointmentFailed()
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUserAppointments(userId: Int)
        abstract fun getMoreAppointments(userId: Int, nextPage: Int = 1)
        abstract fun getSpecialistAppointments(specialistId: Int)
        abstract fun getMoreSpecialistAppointments(specialistId: Int, nextPage: Int = 1)
        abstract fun postponeAppointment(appointment: Appointment, newAppointmentTime: Int, newAppointmentDate: String)
        abstract fun deleteAppointment(appointmentId: Int)
        abstract fun getTherapistAvailability(specialistId: Int, selectedDate: String)
    }
}
