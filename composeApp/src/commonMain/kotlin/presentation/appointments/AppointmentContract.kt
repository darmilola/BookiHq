package presentation.appointments

import com.badoo.reaktive.single.Single
import domain.Models.Appointment
import domain.Models.AppointmentResourceListEnvelope
import domain.Models.AvailableTime
import domain.Models.JoinMeetingResponse
import domain.Models.TimeOffs
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.UIStates

interface AppointmentContract {
    interface View {
        fun showLce(uiState: UIStates, message: String = "")
        fun showAsyncLce(uiState: AsyncUIStates, message: String = "")
        fun showAppointments(appointments: AppointmentResourceListEnvelope)
        fun showTherapistAvailability(availableTimes: List<AvailableTime>, bookedAppointment: List<Appointment>, timeOffs: List<TimeOffs>)
        fun onLoadMoreAppointmentStarted(isSuccess: Boolean = false)
        fun onLoadMoreAppointmentEnded(isSuccess: Boolean = false)
        fun  onPostponeAppointmentStarted()
        fun  onPostponeAppointmentSuccess()
        fun  onPostponeAppointmentFailed()
        fun  onDeleteAppointmentStarted()
        fun  onDeleteAppointmentSuccess()
        fun  onDeleteAppointmentFailed()
        fun onJoinMeetingHandlerTokenReady(meetingToken: String)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUserAppointments(userId: Int)
        abstract fun getMoreAppointments(userId: Int, nextPage: Int = 1)
        abstract fun getSpecialistAppointments(specialistId: Int)
        abstract fun getMoreSpecialistAppointments(specialistId: Int, nextPage: Int = 1)
        abstract fun postponeAppointment(appointment: Appointment, newAppointmentTime: Int,  day: Int, month: Int, year: Int)
        abstract fun deleteAppointment(appointmentId: Int)
        abstract fun joinMeeting(customParticipantId: String, presetName: String, meetingId: String)
        abstract fun getTherapistAvailability(specialistId: Int, day: Int, month: Int, year: Int)
    }
}
