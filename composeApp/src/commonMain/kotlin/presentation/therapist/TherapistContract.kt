package presentation.therapist

import domain.Models.AppointmentResourceListEnvelope
import domain.Models.TherapistReviews
import UIStates.ActionUIStates
import UIStates.ScreenUIStates
import com.badoo.reaktive.single.Single
import domain.Models.ServerResponse
import domain.Models.TherapistAppointmentResourceListEnvelope

interface TherapistContract {
    interface View {
        fun showScreenLce(screenUIStates: ScreenUIStates)
        fun showActionLce(actionUiState: ActionUIStates)
        fun showReviews(reviews: List<TherapistReviews>)
        fun showAppointments(appointments: TherapistAppointmentResourceListEnvelope)
        fun onLoadMoreAppointmentStarted()
        fun onLoadMoreAppointmentEnded()
        fun onJoinMeetingTokenReady(meetingToken: String)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getTherapistReviews(therapistId: Int)
        abstract fun getTherapistAppointments(therapistId: Long)
        abstract fun getMoreTherapistAppointments(therapistId: Long, nextPage: Int = 1)
        abstract fun archiveAppointment(appointmentId: Long)
        abstract fun doneAppointment(appointmentId: Long)
    }
}
