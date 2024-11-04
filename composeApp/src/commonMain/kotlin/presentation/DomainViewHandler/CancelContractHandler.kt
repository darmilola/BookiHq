package presentation.DomainViewHandler

import UIStates.AppUIStates
import domain.Models.PlatformTime
import domain.Models.ServiceImages
import domain.Models.ServiceTypeItem
import domain.Models.ServiceTypeTherapists
import domain.Models.UserAppointment
import domain.Models.VendorTime
import presentation.appointmentBookings.BookingContract
import presentation.appointmentBookings.BookingPresenter
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel

class CancelContractHandler(
    private val deleteActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val bookingPresenter: BookingPresenter) : BookingContract.CancelContractView {
    fun init() {
        bookingPresenter.registerCancelUIContract(this)
    }

    override fun showCancelActionLce(uiState: AppUIStates, message: String) {
        deleteActionUIStateViewModel.switchDeletePendingAppointmentUiState(uiState)
    }

}

