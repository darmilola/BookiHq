package presentation.DomainViewHandler

import domain.Models.Appointment
import domain.Models.AppointmentResourceListEnvelope
import presentation.appointments.AppointmentContract
import presentation.appointments.AppointmentPresenter
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.AppointmentResourceListEnvelopeViewModel
import UIStates.AppUIStates
import presentation.viewmodels.PostponementViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import domain.Models.PlatformTime
import domain.Models.VendorTime


class AppointmentsHandler(
    private val appointmentResourceListEnvelopeViewModel: AppointmentResourceListEnvelopeViewModel,
    private val refreshActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel,
    private val deletePerformedActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val joinMeetingPerformedActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val addTherapistReviewPerformedActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val getTherapistAvailabilityActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val postponeActionPerformedActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val postponementViewModel: PostponementViewModel,
    private val appointmentPresenter: AppointmentPresenter,
    private val onMeetingTokenReady: (meetingToken: String) -> Unit) : AppointmentContract.View {
    fun init() {
        appointmentPresenter.registerUIContract(this)
    }

    override fun showLce(screenUiState: AppUIStates) {
        loadingScreenUiStateViewModel.switchScreenUIState(screenUiState)
    }

    override fun showRefreshing(screenUiState: AppUIStates) {
        refreshActionUIStateViewModel.switchRefreshAppointmentUiState(screenUiState)
    }

    override fun showDeleteActionLce(appUIStates: AppUIStates) {
        deletePerformedActionUIStateViewModel.switchActionDeleteUIState(appUIStates)
    }

    override fun showPostponeActionLce(appUIStates: AppUIStates) {
        postponeActionPerformedActionUIStateViewModel.switchPostPostponeAppointmentUiState(appUIStates)
    }

    override fun showReviewsActionLce(appUIStates: AppUIStates) {
        addTherapistReviewPerformedActionUIStateViewModel.switchAddAppointmentReviewUiState(appUIStates)
    }

    override fun showJoinMeetingActionLce(appUIStates: AppUIStates) {
        joinMeetingPerformedActionUIStateViewModel.switchActionMeetingUIState(appUIStates)
    }

    override fun showGetAvailabilityActionLce(appUIStates: AppUIStates) {
        getTherapistAvailabilityActionUIStateViewModel.switchActionAvailabilityUIState(appUIStates)
    }

    override fun showAppointments(appointments: AppointmentResourceListEnvelope, isRefresh: Boolean) {
        if (isRefresh || appointmentResourceListEnvelopeViewModel.resources.value.isEmpty()){
            appointmentResourceListEnvelopeViewModel.setResources(appointments.data)
            appointments.prevPageUrl?.let { appointmentResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            appointments.nextPageUrl?.let { appointmentResourceListEnvelopeViewModel.setNextPageUrl(it) }
            appointments.currentPage?.let { appointmentResourceListEnvelopeViewModel.setCurrentPage(it) }
            appointments.totalItemCount?.let { appointmentResourceListEnvelopeViewModel.setTotalItemCount(it) }
            appointments.displayedItemCount?.let { appointmentResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
        else {
            val appointmentList = appointmentResourceListEnvelopeViewModel.resources.value
            appointmentList.addAll(appointments.data!!)
            appointmentResourceListEnvelopeViewModel.setResources(appointmentList)
            appointments.prevPageUrl?.let { appointmentResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            appointments.nextPageUrl?.let { appointmentResourceListEnvelopeViewModel.setNextPageUrl(it) }
            appointments.currentPage?.let { appointmentResourceListEnvelopeViewModel.setCurrentPage(it) }
            appointments.totalItemCount?.let { appointmentResourceListEnvelopeViewModel.setTotalItemCount(it) }
            appointments.displayedItemCount?.let { appointmentResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
    }

    override fun showTherapistAvailability(
        bookedAppointment: List<Appointment>, platformTime: List<PlatformTime>,
        vendorTime: List<VendorTime>){
        postponementViewModel.setTherapistBookedAppointment(bookedAppointment)
        postponementViewModel.setPlatformTimes(platformTime)
        postponementViewModel.setVendorTimes(vendorTimes = vendorTime)
    }

    override fun onLoadMoreAppointmentStarted() {
        appointmentResourceListEnvelopeViewModel.setLoadingMore(true)
    }

    override fun onLoadMoreAppointmentEnded() {
        appointmentResourceListEnvelopeViewModel.setLoadingMore(false)
    }

    override fun onJoinMeetingTokenReady(meetingToken: String) {
         onMeetingTokenReady(meetingToken)
    }

}