package presentation.therapist

import domain.Models.AppointmentResourceListEnvelope
import domain.Models.AvailableTime
import domain.Models.SpecialistReviews
import domain.Models.TimeOffs
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.ActionUIStates
import presentation.viewmodels.AppointmentResourceListEnvelopeViewModel
import presentation.viewmodels.ScreenUIStateViewModel
import presentation.viewmodels.ScreenUIStates

class TherapistHandler(
    private val therapistPresenter: TherapistPresenter,
    private val screenUiStateViewModel: ScreenUIStateViewModel,
    private val actionUIStateViewModel: ActionUIStateViewModel,
    private val onReviewsReady: (List<SpecialistReviews>) -> Unit,
    private val onMeetingTokenReady: (meetingToken: String) -> Unit,
    private val appointmentResourceListEnvelopeViewModel: AppointmentResourceListEnvelopeViewModel,
    private val onTherapistAvailabilityReady: (availableTimes: List<AvailableTime>, timeOffs: List<TimeOffs>) -> Unit) : TherapistContract.View {
    fun init() {
        therapistPresenter.registerUIContract(this)
    }

    override fun showScreenLce(screenUIStates: ScreenUIStates) {
        screenUiStateViewModel.switchScreenUIState(screenUIStates)
    }

    override fun showActionLce(actionUiState: ActionUIStates) {
         actionUIStateViewModel.switchActionPostponeUIState(actionUiState)
    }

    override fun showReviews(reviews: List<SpecialistReviews>) {
        onReviewsReady(reviews)
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
        availableTimes: List<AvailableTime>,
        timeOffs: List<TimeOffs>
    ) {
        onTherapistAvailabilityReady(availableTimes, timeOffs)
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