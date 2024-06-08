package presentation.appointments

import domain.Models.Appointment
import domain.Models.AppointmentResourceListEnvelope
import domain.Models.AvailableTime
import domain.Models.TimeOffs
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.AppointmentResourceListEnvelopeViewModel
import presentation.viewmodels.ActionUIStates
import presentation.viewmodels.PostponementViewModel
import presentation.viewmodels.ScreenUIStateViewModel
import presentation.viewmodels.ScreenUIStates


class AppointmentsHandler(
    private val appointmentResourceListEnvelopeViewModel: AppointmentResourceListEnvelopeViewModel,
    private val screenUiStateViewModel: ScreenUIStateViewModel,
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
        screenUiStateViewModel.switchScreenUIState(screenUiState)
    }

    override fun showDeleteActionLce(actionUIStates: ActionUIStates) {
        deleteActionUIStateViewModel.switchActionUIState(actionUIStates)
    }

    override fun showPostponeActionLce(actionUIStates: ActionUIStates) {
        postponementViewModel.setPostponementViewUIState(actionUIStates)
    }

    override fun showJoinMeetingActionLce(actionUIStates: ActionUIStates) {
        joinMeetingActionUIStateViewModel.switchActionUIState(actionUIStates)
    }

    override fun showGetAvailabilityActionLce(actionUIStates: ActionUIStates) {
        getAvailabilityActionUIStateViewModel.switchActionUIState(actionUIStates)
    }

    override fun showAppointments(appointments: AppointmentResourceListEnvelope) {
        if (appointmentResourceListEnvelopeViewModel.resources.value.isNotEmpty()) {
            val appointmentList = appointmentResourceListEnvelopeViewModel.resources.value
            appointmentList.addAll(appointments.resources!!)
            appointmentResourceListEnvelopeViewModel.setResources(appointmentList)
            appointments.prevPageUrl?.let { appointmentResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            appointments.nextPageUrl?.let { appointmentResourceListEnvelopeViewModel.setNextPageUrl(it) }
            appointments.currentPage?.let { appointmentResourceListEnvelopeViewModel.setCurrentPage(it) }
            appointments.totalItemCount?.let { appointmentResourceListEnvelopeViewModel.setTotalItemCount(it) }
            appointments.displayedItemCount?.let { appointmentResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        } else {
            appointmentResourceListEnvelopeViewModel.setResources(appointments.resources)
            appointments?.prevPageUrl?.let { appointmentResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            appointments?.nextPageUrl?.let { appointmentResourceListEnvelopeViewModel.setNextPageUrl(it) }
            appointments?.currentPage?.let { appointmentResourceListEnvelopeViewModel.setCurrentPage(it) }
            appointments?.totalItemCount?.let { appointmentResourceListEnvelopeViewModel.setTotalItemCount(it) }
            appointments?.displayedItemCount?.let { appointmentResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
    }

    override fun showTherapistAvailability(
        availableTimes: List<AvailableTime>,
        bookedAppointment: List<Appointment>,
        timeOffs: List<TimeOffs>
    ) {
        postponementViewModel.setTherapistAvailableTimes(availableTimes)
        postponementViewModel.setTherapistTimeOffs(timeOffs)
        postponementViewModel.setTherapistBookedAppointment(bookedAppointment)
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