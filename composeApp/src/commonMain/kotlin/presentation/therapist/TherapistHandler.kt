package presentation.therapist

import domain.Models.AvailableTime
import domain.Models.SpecialistReviews
import domain.Models.TimeOffs
import presentation.viewmodels.ActionUIStates
import presentation.viewmodels.ScreenUIStates

class TherapistHandler(
    private val therapistPresenter: TherapistPresenter,
    private val onPageLoading: () -> Unit,
    private val onAsyncLoading: () -> Unit,
    private val onAsyncSuccess: () -> Unit,
    private val onAsyncFailed: () -> Unit,
    private val onContentVisible: () -> Unit,
    private val onReviewsReady: (List<SpecialistReviews>) -> Unit,
    private val onErrorVisible: () -> Unit,
    private val onTherapistAvailabilityReady: (availableTimes: List<AvailableTime>, timeOffs: List<TimeOffs>) -> Unit) : TherapistContract.View {
    fun init() {
        therapistPresenter.registerUIContract(this)
    }

    override fun showLce(uiState: ScreenUIStates, message: String) {
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

    override fun showActionLce(uiState: ActionUIStates, message: String) {
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
        availableTimes: List<AvailableTime>,
        timeOffs: List<TimeOffs>
    ) {
        onTherapistAvailabilityReady(availableTimes, timeOffs)
    }


}