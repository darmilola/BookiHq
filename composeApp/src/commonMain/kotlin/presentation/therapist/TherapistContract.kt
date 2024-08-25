package presentation.therapist

import domain.Models.TherapistReviews
import UIStates.AppUIStates
import com.badoo.reaktive.single.Single
import domain.Models.ServerResponse
import domain.Models.TherapistAppointmentResourceListEnvelope

interface TherapistContract {
    interface View {
        fun showScreenLce(actionUiState: AppUIStates)
        fun showActionLce(actionUiState: AppUIStates)
        fun showReviews(reviews: List<TherapistReviews>)
        fun showAppointments(appointments: TherapistAppointmentResourceListEnvelope)
        fun onLoadMoreAppointmentStarted()
        fun onLoadMoreAppointmentEnded()
        fun onJoinMeetingTokenReady(meetingToken: String)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getTherapistReviews(therapistId: Long)
        abstract fun getTherapistAppointments(therapistId: Long)
        abstract fun getMoreTherapistAppointments(therapistId: Long, nextPage: Int = 1)
        abstract fun archiveAppointment(appointmentId: Long)
        abstract fun doneAppointment(appointmentId: Long)
        abstract fun updateAvailability(therapistId: Long, isMobileServiceAvailable: Boolean, isAvailable: Boolean)
    }
}
