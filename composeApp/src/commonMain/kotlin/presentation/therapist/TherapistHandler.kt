package presentation.therapist

import domain.Models.Appointment
import domain.Models.SpecialistReviews
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
    private val onContentVisible: () -> Unit,
    private val onReviewsReady: (List<SpecialistReviews>) -> Unit,
    private val onErrorVisible: () -> Unit) : TherapistContract.View {
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

    override fun showAsyncLce(uiState: AsyncUIStates, message: String) {}

    override fun showReviews(reviews: List<SpecialistReviews>) {
        onReviewsReady(reviews)
    }


}