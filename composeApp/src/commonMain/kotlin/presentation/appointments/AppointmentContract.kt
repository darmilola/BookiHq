package presentation.appointments

import domain.Models.Appointment
import domain.Models.AppointmentResourceListEnvelope
import domain.Models.AvailableTime
import domain.Models.TimeOffs
import presentation.viewmodels.ActionUIStates
import presentation.viewmodels.ScreenUIStates

interface AppointmentContract {
    interface View {
        fun showLce(screenUiState: ScreenUIStates)
        fun showDeleteActionLce(actionUIStates: ActionUIStates)
        fun showPostponeActionLce(actionUIStates: ActionUIStates)
        fun showJoinMeetingActionLce(actionUIStates: ActionUIStates)
        fun showGetAvailabilityActionLce(actionUIStates: ActionUIStates)
        fun showAppointments(appointments: AppointmentResourceListEnvelope)
        fun showTherapistAvailability(availableTimes: List<AvailableTime>, bookedAppointment: List<Appointment>, timeOffs: List<TimeOffs>)
        fun onLoadMoreAppointmentStarted()
        fun onLoadMoreAppointmentEnded()
        fun onJoinMeetingTokenReady(meetingToken: String)
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
