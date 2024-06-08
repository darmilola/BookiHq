package presentation.therapist

import domain.Models.AppointmentResourceListEnvelope
import domain.Models.AvailableTime
import domain.Models.SpecialistReviews
import domain.Models.TimeOffs
import presentation.viewmodels.ActionUIStates
import presentation.viewmodels.ScreenUIStates

interface TherapistContract {
    interface View {
        fun showScreenLce(screenUIStates: ScreenUIStates)
        fun showActionLce(actionUiState: ActionUIStates)
        fun showReviews(reviews: List<SpecialistReviews>)
        fun showAppointments(appointments: AppointmentResourceListEnvelope)
        fun showTherapistAvailability(availableTimes: List<AvailableTime>, timeOffs: List<TimeOffs>)
        fun onLoadMoreAppointmentStarted()
        fun onLoadMoreAppointmentEnded()
        fun onJoinMeetingTokenReady(meetingToken: String)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getTherapistReviews(specialistId: Int)
        abstract fun getTherapistAvailability(specialistId: Int, day: Int, month: Int, year: Int)
        abstract fun getSpecialistAppointments(specialistId: Int)
        abstract fun getMoreSpecialistAppointments(specialistId: Int, nextPage: Int = 1)
        abstract fun addTimeOff(specialistId: Int, timeId: Int,day: Int,
                                month: Int,
                                year: Int)
        abstract fun removeTimeOff(specialistId: Int, timeId: Int,day: Int,
                                   month: Int,
                                   year: Int)
    }
}
