package presentation.DomainViewHandler

import domain.Models.TherapistReviews
import presentation.therapist.TherapistContract
import presentation.therapist.TherapistPresenter
import presentation.viewmodels.PerformedActionUIStateViewModel
import UIStates.AppUIStates
import domain.Models.AppointmentReview
import presentation.viewmodels.LoadingScreenUIStateViewModel
import domain.Models.TherapistAppointmentResourceListEnvelope
import presentation.viewmodels.TherapistAppointmentResourceListEnvelopeViewModel

class TherapistHandler(
    private val therapistPresenter: TherapistPresenter,
    private val loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel,
    private val performedActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val appointmentResourceListEnvelopeViewModel: TherapistAppointmentResourceListEnvelopeViewModel? = null) : TherapistContract.View {
    fun init() {
        therapistPresenter.registerUIContract(this)
    }

    override fun showScreenLce(actionUiState: AppUIStates) {
        loadingScreenUiStateViewModel.switchScreenUIState(actionUiState)
    }

    override fun showActionLce(actionUiState: AppUIStates) {
         performedActionUIStateViewModel.switchActionTherapistDashboardUIState(actionUiState)
    }

    override fun showAppointments(appointments: TherapistAppointmentResourceListEnvelope) {
        if (appointmentResourceListEnvelopeViewModel!!.resources.value.isNotEmpty()) {
            val appointmentList = appointmentResourceListEnvelopeViewModel.resources.value
            appointmentList.addAll(appointments.data!!)
            appointmentResourceListEnvelopeViewModel.setResources(appointmentList)
            appointments.prevPageUrl?.let { appointmentResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            appointments.nextPageUrl?.let { appointmentResourceListEnvelopeViewModel.setNextPageUrl(it) }
            appointments.currentPage?.let { appointmentResourceListEnvelopeViewModel.setCurrentPage(it) }
            appointments.totalItemCount?.let { appointmentResourceListEnvelopeViewModel.setTotalItemCount(it) }
            appointments.displayedItemCount?.let { appointmentResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        } else {
            appointmentResourceListEnvelopeViewModel.setResources(appointments.data)
            appointments.prevPageUrl?.let { appointmentResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            appointments.nextPageUrl?.let { appointmentResourceListEnvelopeViewModel.setNextPageUrl(it) }
            appointments.currentPage?.let { appointmentResourceListEnvelopeViewModel.setCurrentPage(it) }
            appointments.totalItemCount?.let { appointmentResourceListEnvelopeViewModel.setTotalItemCount(it) }
            appointments.displayedItemCount?.let { appointmentResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
    }

    override fun onLoadMoreAppointmentStarted() {
        appointmentResourceListEnvelopeViewModel!!.setLoadingMore(true)
    }

    override fun onLoadMoreAppointmentEnded() {
        appointmentResourceListEnvelopeViewModel!!.setLoadingMore(false)
    }


}

class TherapistProfileHandler(
    private val therapistPresenter: TherapistPresenter,
    private val performedActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel,
    private val onReviewsReady: (List<AppointmentReview>) -> Unit,) : TherapistContract.TherapistDashboardView {
    fun init() {
        therapistPresenter.registerTherapistDashboardUIContract(this)
    }

    override fun showUpdateScreenLce(actionUiState: AppUIStates) {
        performedActionUIStateViewModel.switchActionUIState(actionUiState)
    }

    override fun showReviews(reviews: List<AppointmentReview>) {
        onReviewsReady(reviews)
    }

    override fun showScreenLce(actionUiState: AppUIStates) {
        loadingScreenUiStateViewModel.switchScreenUIState(actionUiState)
    }


}
