package presentation.DomainViewHandler

import domain.Models.ServiceTypeTherapists
import presentation.bookings.BookingContract
import presentation.bookings.BookingPresenter
import UIStates.ActionUIStates
import presentation.viewmodels.BookingViewModel
import domain.Models.PlatformTime
import domain.Models.UserAppointment
import domain.Models.VendorTime
import presentation.viewmodels.ActionUIStateViewModel

class BookingScreenHandler(
    private val bookingViewModel: BookingViewModel,
    private val actionUIStateViewModel: ActionUIStateViewModel,
    private val bookingPresenter: BookingPresenter,
    private val onShowUnsavedAppointment: () -> Unit) : BookingContract.View {
    fun init() {
        bookingPresenter.registerUIContract(this)
    }

    override fun showLoadPendingAppointmentLce(uiState: ActionUIStates, message: String) {
        uiState.let {
            when{
                it.isLoading -> {
                    actionUIStateViewModel.switchActionLoadPendingAppointmentUiState(ActionUIStates(isLoading = true))
                }

                it.isSuccess -> {
                    actionUIStateViewModel.switchActionLoadPendingAppointmentUiState(ActionUIStates(isSuccess = true))
                }

                it.isFailed -> {
                    actionUIStateViewModel.switchActionLoadPendingAppointmentUiState(ActionUIStates(isFailed = true))
                }
            }
        }
    }

    override fun showDeleteActionLce(uiState: ActionUIStates, message: String) {
        uiState.let {
            when {
                it.isLoading -> {
                    actionUIStateViewModel.switchDeletePendingAppointmentUiState(ActionUIStates(isLoading = true))
                }
                it.isSuccess -> {
                    actionUIStateViewModel.switchDeletePendingAppointmentUiState(ActionUIStates(isSuccess = true))
                }

                it.isFailed -> {
                    actionUIStateViewModel.switchDeletePendingAppointmentUiState(ActionUIStates(isFailed = true))
                }

            }
        }
    }

    override fun showCreateAppointmentActionLce(uiState: ActionUIStates, message: String) {
        uiState.let {
            when {
                it.isLoading -> {
                    actionUIStateViewModel.switchCreateAppointmentUiState(ActionUIStates(isLoading = true))
                }
                it.isSuccess -> {
                    actionUIStateViewModel.switchCreateAppointmentUiState(ActionUIStates(isSuccess = true))
                }

                it.isFailed -> {
                    actionUIStateViewModel.switchCreateAppointmentUiState(ActionUIStates(isFailed = true))
                }

            }
        }
    }

    override fun getTherapistActionLce(uiState: ActionUIStates, message: String) {
        uiState.let {
            when {
                it.isLoading -> {
                    actionUIStateViewModel.switchGetTherapistUiState(ActionUIStates(isLoading = true))
                }
                it.isSuccess -> {
                    actionUIStateViewModel.switchGetTherapistUiState(ActionUIStates(isSuccess = true))
                }

                it.isFailed -> {
                    actionUIStateViewModel.switchGetTherapistUiState(ActionUIStates(isFailed = true))
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

