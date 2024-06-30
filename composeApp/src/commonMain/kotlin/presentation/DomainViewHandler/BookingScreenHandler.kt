package presentation.DomainViewHandler

import domain.Models.ServiceTypeTherapists
import presentation.bookings.BookingContract
import presentation.bookings.BookingPresenter
import UIStates.ActionUIStates
import presentation.viewmodels.BookingViewModel
import UIStates.ScreenUIStates

class BookingScreenHandler(
    private val bookingViewModel: BookingViewModel,
    private val bookingPresenter: BookingPresenter,
    private val onPageLoading: () -> Unit,
    private val onShowUnsavedAppointment: () -> Unit,
    private val onContentVisible: () -> Unit,
    private val onErrorVisible: () -> Unit,
    private val onCreateAppointmentStarted: () -> Unit,
    private val onCreateAppointmentSuccess: () -> Unit,
    private val onCreateAppointmentFailed: () -> Unit
) : BookingContract.View {
    fun init() {
        bookingPresenter.registerUIContract(this)
    }

    override fun showScreenLce(uiState: ScreenUIStates, message: String) {
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
            when {
                it.isLoading -> {
                    onCreateAppointmentStarted()
                }
                it.isSuccess -> {
                    onCreateAppointmentSuccess()
                }

                it.isFailed -> {
                    onCreateAppointmentFailed()
                }

            }
        }
    }

    override fun showTherapists(serviceTherapists: List<ServiceTypeTherapists>) {
        bookingViewModel.setTherapists(serviceTherapists)
    }

    override fun showUnsavedAppointment() {
        onShowUnsavedAppointment()
    }


}

