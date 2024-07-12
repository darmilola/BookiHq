package presentation.DomainViewHandler

import domain.Models.Appointment
import domain.Models.AppointmentResourceListEnvelope
import presentation.appointments.AppointmentContract
import presentation.appointments.AppointmentPresenter
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.AppointmentResourceListEnvelopeViewModel
import UIStates.ActionUIStates
import presentation.viewmodels.PostponementViewModel
import presentation.viewmodels.UIStateViewModel
import UIStates.ScreenUIStates
import domain.Models.PlatformTime
import domain.Models.VendorTime


class AppointmentsHandler(
    private val appointmentResourceListEnvelopeViewModel: AppointmentResourceListEnvelopeViewModel,
    private val uiStateViewModel: UIStateViewModel,
    private val deleteActionUIStateViewModel: ActionUIStateViewModel,
    private val joinMeetingActionUIStateViewModel: ActionUIStateViewModel,
    private val getAvailabilityActionUIStateViewModel: ActionUIStateViewModel,
    private val postponementViewModel: PostponementViewModel,
    private val appointmentPresenter: AppointmentPresenter,
    private val onMeetingTokenReady: (meetingToken: String) -> Unit) : AppointmentContract.View {
    fun init() {
        appointmentPresenter.registerUIContract(this)
    }

    override fun showLce(screenUiState: ScreenUIStates) {
        uiStateViewModel.switchScreenUIState(screenUiState)
    }

    override fun showDeleteActionLce(actionUIStates: ActionUIStates) {
        deleteActionUIStateViewModel.switchActionDeleteUIState(actionUIStates)
    }

    override fun showPostponeActionLce(actionUIStates: ActionUIStates) {
        postponementViewModel.setPostponementViewUIState(actionUIStates)
    }

    override fun showJoinMeetingActionLce(actionUIStates: ActionUIStates) {
        joinMeetingActionUIStateViewModel.switchActionMeetingUIState(actionUIStates)
    }

    override fun showGetAvailabilityActionLce(actionUIStates: ActionUIStates) {
        getAvailabilityActionUIStateViewModel.switchActionAvailabilityUIState(actionUIStates)
    }

    override fun showAppointments(appointments: AppointmentResourceListEnvelope) {
        if (appointmentResourceListEnvelopeViewModel.resources.value.isNotEmpty()) {
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
            appointments?.prevPageUrl?.let { appointmentResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            appointments?.nextPageUrl?.let { appointmentResourceListEnvelopeViewModel.setNextPageUrl(it) }
            appointments?.currentPage?.let { appointmentResourceListEnvelopeViewModel.setCurrentPage(it) }
            appointments?.totalItemCount?.let { appointmentResourceListEnvelopeViewModel.setTotalItemCount(it) }
            appointments?.displayedItemCount?.let { appointmentResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
    }

    override fun showTherapistAvailability(
        bookedAppointment: List<Appointment>, platformTime: List<PlatformTime>,
        vendorTimes: List<VendorTime>){
        postponementViewModel.setTherapistBookedAppointment(bookedAppointment)
        postponementViewModel.setPlatformTimes(platformTime)
        postponementViewModel.setVendorTimes(vendorTimes = vendorTimes)
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