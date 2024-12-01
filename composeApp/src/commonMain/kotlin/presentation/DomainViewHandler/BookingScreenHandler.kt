package presentation.DomainViewHandler

import domain.Models.ServiceTypeTherapists
import presentation.appointmentBookings.BookingContract
import presentation.appointmentBookings.BookingPresenter
import UIStates.AppUIStates
import domain.Models.AppointmentReview
import presentation.viewmodels.BookingViewModel
import domain.Models.PlatformTime
import domain.Models.ServiceImages
import domain.Models.ServiceTypeItem
import domain.Models.UserAppointment
import domain.Models.VendorTime
import presentation.viewmodels.PerformedActionUIStateViewModel

class BookingScreenHandler(
    private val bookingViewModel: BookingViewModel,
    private val loadPendingActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val createAppointmentActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val getTherapistActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val getServiceTypesActionUIStateViewModel: PerformedActionUIStateViewModel?,
    private val getTimesActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val bookingPresenter: BookingPresenter,
    private val onShowUnsavedAppointment: () -> Unit) : BookingContract.View {
    fun init() {
        bookingPresenter.registerUIContract(this)
    }

    override fun showLoadPendingAppointmentLce(uiState: AppUIStates, message: String) {
        loadPendingActionUIStateViewModel.switchActionLoadPendingAppointmentUiState(uiState)
    }

    override fun showCreateAppointmentActionLce(uiState: AppUIStates, message: String) {
        createAppointmentActionUIStateViewModel.switchCreateAppointmentUiState(uiState)
    }

    override fun getTherapistActionLce(uiState: AppUIStates, message: String) {
        getTherapistActionUIStateViewModel.switchGetTherapistUiState(uiState)
    }

    override fun getServiceTypesLce(uiState: AppUIStates, message: String) {
        getServiceTypesActionUIStateViewModel!!.switchGetServiceTypeUiState(uiState)
    }

    override fun getTimesActionLce(uiState: AppUIStates, message: String) {
        getTimesActionUIStateViewModel.switchTimeActionUiState(uiState)
    }

    override fun showTherapists(serviceTherapists: List<ServiceTypeTherapists>, platformTime: List<PlatformTime>, vendorTime: List<VendorTime>) {
        bookingViewModel.setTherapists(serviceTherapists)
        bookingViewModel.setVendorTimes(vendorTime)
        bookingViewModel.setPlatformTimes(platformTime)
    }

    override fun showTimes(platformTime: List<PlatformTime>, vendorTime: List<VendorTime>) {
        bookingViewModel.setVendorTimes(vendorTime)
        bookingViewModel.setPlatformTimes(platformTime)
    }

    override fun showServiceTypes(serviceTypes: List<ServiceTypeItem>, serviceImages: List<ServiceImages>) {
        bookingViewModel.setServiceTypeList(serviceTypes)
        bookingViewModel.setServiceImages(serviceImages)
    }

    override fun showPendingBookingAppointment(pendingAppointments: List<UserAppointment>) {
        bookingViewModel.setPendingAppointments(pendingAppointments)
    }


    override fun showUnsavedAppointment() {
        onShowUnsavedAppointment()
    }


}

