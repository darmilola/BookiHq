package presentation.therapist

import domain.Models.AppointmentResourceListEnvelope
import domain.Models.AvailableTime
import domain.Models.TherapistReviews
import domain.Models.TimeOffs
import presentation.viewmodels.ActionUIStates
import presentation.viewmodels.ScreenUIStates

interface TherapistContract {
    interface View {
        fun showScreenLce(screenUIStates: ScreenUIStates)
        fun showActionLce(actionUiState: ActionUIStates)
        fun showReviews(reviews: List<TherapistReviews>)
        fun showAppointments(appointments: AppointmentResourceListEnvelope)
        fun onLoadMoreAppointmentStarted()
        fun onLoadMoreAppointmentEnded()
        fun onJoinMeetingTokenReady(meetingToken: String)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getTherapistReviews(therapistId: Int)
        abstract fun getTherapistAppointments(therapistId: Int)
        abstract fun getMoreTherapistAppointments(therapistId: Int, nextPage: Int = 1)
    }
}
