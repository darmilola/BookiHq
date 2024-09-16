package presentation.DomainViewHandler

import domain.Models.ServiceTypeTherapists
import presentation.appointmentBookings.BookingContract
import presentation.appointmentBookings.BookingPresenter
import UIStates.AppUIStates
import domain.Models.AppointmentReview
import presentation.viewmodels.BookingViewModel
import domain.Models.PlatformTime
import domain.Models.UserAppointment
import domain.Models.VendorTime
import presentation.viewmodels.PerformedActionUIStateViewModel

class BookingScreenHandler(
    private val bookingViewModel: BookingViewModel,
    private val loadPendingActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val deleteActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val createAppointmentActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val getTherapistActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val getTimesActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val bookingPresenter: BookingPresenter,
    private val onShowUnsavedAppointment: () -> Unit) : BookingContract.View {
    fun init() {
        bookingPresenter.registerUIContract(this)
    }

    override fun showLoadPendingAppointmentLce(uiState: AppUIStates, message: String) {
        uiState.let {
            when{
                it.isLoading -> {
                    loadPendingActionUIStateViewModel.switchActionLoadPendingAppointmentUiState(AppUIStates(isLoading = true, loadingMessage = it.loadingMessage))
                }

                it.isSuccess -> {
                    loadPendingActionUIStateViewModel.switchActionLoadPendingAppointmentUiState(AppUIStates(isSuccess = true))
                }

                it.isFailed -> {
                    loadPendingActionUIStateViewModel.switchActionLoadPendingAppointmentUiState(AppUIStates(isFailed = true, errorMessage = it.errorMessage))
                }
            }
        }
    }

    override fun showDeleteActionLce(uiState: AppUIStates, message: String) {
        uiState.let {
            when {
                it.isLoading -> {
                    deleteActionUIStateViewModel.switchDeletePendingAppointmentUiState(AppUIStates(isLoading = true, loadingMessage = it.loadingMessage))
                }
                it.isSuccess -> {
                    deleteActionUIStateViewModel.switchDeletePendingAppointmentUiState(AppUIStates(isSuccess = true))
                }

                it.isFailed -> {
                    deleteActionUIStateViewModel.switchDeletePendingAppointmentUiState(AppUIStates(isFailed = true, errorMessage = it.errorMessage))
                }

            }
        }
    }

    override fun showCreateAppointmentActionLce(uiState: AppUIStates, message: String) {
        uiState.let {
            when {
                it.isLoading -> {
                    createAppointmentActionUIStateViewModel.switchCreateAppointmentUiState(AppUIStates(isLoading = true, loadingMessage = it.loadingMessage))
                }
                it.isSuccess -> {
                    createAppointmentActionUIStateViewModel.switchCreateAppointmentUiState(AppUIStates(isSuccess = true))
                }

                it.isFailed -> {
                    createAppointmentActionUIStateViewModel.switchCreateAppointmentUiState(AppUIStates(isFailed = true, errorMessage = it.errorMessage))
                }
            }
        }
    }

    override fun getTherapistActionLce(uiState: AppUIStates, message: String) {
        uiState.let {
            when {
                it.isLoading -> {
                    getTherapistActionUIStateViewModel.switchGetTherapistUiState(AppUIStates(isLoading = true, loadingMessage = it.loadingMessage))
                }
                it.isSuccess -> {
                    getTherapistActionUIStateViewModel.switchGetTherapistUiState(AppUIStates(isSuccess = true))
                }

                it.isFailed -> {
                    getTherapistActionUIStateViewModel.switchGetTherapistUiState(AppUIStates(isFailed = true, errorMessage = it.errorMessage))
                }

            }
        }
    }

    override fun getTimesActionLce(uiState: AppUIStates, message: String) {
        uiState.let {
            when {
                it.isLoading -> {
                    getTimesActionUIStateViewModel.switchTimeActionUiState(AppUIStates(isLoading = true, loadingMessage = it.loadingMessage))
                }
                it.isSuccess -> {
                    getTimesActionUIStateViewModel.switchTimeActionUiState(AppUIStates(isSuccess = true))
                }

                it.isFailed -> {
                    getTimesActionUIStateViewModel.switchTimeActionUiState(AppUIStates(isFailed = true, errorMessage = it.errorMessage))
                }

            }
        }
    }

    override fun showTherapists(serviceTherapists: List<ServiceTypeTherapists>, platformTime: List<PlatformTime>, vendorTime: List<VendorTime>) {
        bookingViewModel.setTherapists(serviceTherapists)
        bookingViewModel.setVendorTimes(vendorTime)
        bookingViewModel.setPlatformTimes(platformTime)
    }

    override fun showTimes(platformTime: List<PlatformTime>, vendorTime: List<VendorTime>) {
        println(platformTime)
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

