package presentation.therapist

import domain.Models.Appointment
import domain.Models.ServiceTime
import domain.Models.SpecialistReviews
import domain.Models.TimeOffs
import presentation.appointments.AppointmentContract
import presentation.appointments.AppointmentPresenter
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.PostponementViewModel
import presentation.viewmodels.ResourceListEnvelopeViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.viewmodels.UIStates

class TherapistHandler(
    private val therapistPresenter: TherapistPresenter,
    private val onPageLoading: () -> Unit,
    private val onAsyncLoading: () -> Unit,
    private val onAsyncSuccess: () -> Unit,
    private val onAsyncFailed: () -> Unit,
    private val onContentVisible: () -> Unit,
    private val onReviewsReady: (List<SpecialistReviews>) -> Unit,
    private val onErrorVisible: () -> Unit,
    private val onTherapistAvailabilityReady: (availableTimes: List<ServiceTime>, bookedAppointment: List<Appointment>, timeOffs: List<TimeOffs>) -> Unit) : TherapistContract.View {
    fun init() {
        therapistPresenter.registerUIContract(this)
    }

    override fun showLce(uiState: UIStates, message: String) {
        uiState.let {
            when{
                it.loadingVisible -> {
                    onPageLoading()
                }

                it.contentVisible -> {
                    onContentVisible()
                }

                it.errorOccurred -> {
                    onErrorVisible()
                }
            }
        }
    }

    override fun showAsyncLce(uiState: AsyncUIStates, message: String) {
        uiState.let {
            when{
                it.isLoading-> {
                    onAsyncLoading()
                }

                it.isSuccess -> {
                    onAsyncSuccess()
                }

                it.isFailed-> {
                    onAsyncFailed()
                }
                it.isDone -> {
                    onAsyncFailed()
                }
            }
        }
    }

    override fun showReviews(reviews: List<SpecialistReviews>) {
        onReviewsReady(reviews)
    }

    override fun showTherapistAvailability(
        availableTimes: List<ServiceTime>,
        bookedAppointment: List<Appointment>,
        timeOffs: List<TimeOffs>
    ) {
        onTherapistAvailabilityReady(availableTimes, bookedAppointment, timeOffs)
    }


}