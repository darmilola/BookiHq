package presentation.therapist

import domain.Models.TherapistReviews
import UIStates.AppUIStates
import domain.Models.AppointmentReview
import domain.Models.TherapistAppointmentResourceListEnvelope

interface TherapistContract {
    interface View {
        fun showScreenLce(actionUiState: AppUIStates)
        fun showActionLce(actionUiState: AppUIStates)
        fun showAppointments(appointments: TherapistAppointmentResourceListEnvelope)
        fun onLoadMoreAppointmentStarted()
        fun onLoadMoreAppointmentEnded()
    }

    interface TherapistDashboardView {
        fun showUpdateScreenLce(actionUiState: AppUIStates)
        fun showReviews(reviews: List<AppointmentReview>)
        fun showScreenLce(actionUiState: AppUIStates)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun registerTherapistDashboardUIContract(view: TherapistDashboardView?)
        abstract fun getTherapistReviews(therapistId: Long)
        abstract fun getTherapistAppointments(therapistId: Long)
        abstract fun getMoreTherapistAppointments(therapistId: Long, nextPage: Int = 1)
        abstract fun archiveAppointment(appointmentId: Long)
        abstract fun doneAppointment(appointmentId: Long)
        abstract fun updateAvailability(therapistId: Long, isMobileServiceAvailable: Boolean, isAvailable: Boolean)
    }
}
