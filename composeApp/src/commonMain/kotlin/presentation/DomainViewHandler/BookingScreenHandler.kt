package presentation.DomainViewHandler

import domain.Models.ServiceTypeTherapists
import presentation.bookings.BookingContract
import presentation.bookings.BookingPresenter
import UIStates.ActionUIStates
import presentation.viewmodels.BookingViewModel
import UIStates.ScreenUIStates
import domain.Models.PlatformTime
import domain.Models.UserAppointment
import domain.Models.VendorTime

class BookingScreenHandler(
    private val bookingViewModel: BookingViewModel,
    private val bookingPresenter: BookingPresenter,
    private val onPageLoading: () -> Unit,
    private val onShowUnsavedAppointment: () -> Unit,
    private val onContentVisible: () -> Unit,
    private val onErrorVisible: () -> Unit,
    private val onCreateAppointmentStarted: () -> Unit,
    private val onCreateAppointmentSuccess: () -> Unit,
    private val onCreateAppointmentFailed: () -> Unit,
    private val onActionStarted: () -> Unit,
    private val onActionSuccess: () -> Unit,
    private val onActionFailed: () -> Unit,
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
                    onActionStarted()
                }
                it.isSuccess -> {
                    onActionSuccess()
                }

                it.isFailed -> {
                    onActionFailed()
                }

            }
        }
    }

    override fun showCreateAppointmentActionLce(uiState: ActionUIStates, message: String) {
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

    override fun showTherapists(serviceTherapists: List<ServiceTypeTherapists>, platformTime: List<PlatformTime>, vendorTime: List<VendorTime>) {
        bookingViewModel.setTherapists(serviceTherapists)
        bookingViewModel.setVendorTimes(vendorTime)
        bookingViewModel.setPlatformTimes(platformTime)
    }

    override fun showPendingBookingAppointment(pendingAppointments: List<UserAppointment>) {
        bookingViewModel.setPendingAppointments(pendingAppointments)
    }


    override fun showUnsavedAppointment() {
        onShowUnsavedAppointment()
    }


}

