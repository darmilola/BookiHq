package presentation.viewmodels

import UIStates.AppUIStates
import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import kotlinx.coroutines.flow.StateFlow



// To be used for initializing the page been loaded, displaying loader with empty screen
class LoadingScreenUIStateViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _uiState = savedStateHandle.getStateFlow("loadingScreenUiState", AppUIStates())
    val uiStateInfo: StateFlow<AppUIStates>
        get() = _uiState

    fun switchScreenUIState(appUIStates: AppUIStates) {
        savedStateHandle["loadingScreenUiState"] = appUIStates
    }
}


// To be used for performing action, displaying dialog for respective action been performed
class PerformedActionUIStateViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _uiState = savedStateHandle.getStateFlow("actionUiState", AppUIStates())
    private var _paymentUiState = savedStateHandle.getStateFlow("paymentUiState", AppUIStates())
    private var _deleteUiState = savedStateHandle.getStateFlow("deleteActionUiState", AppUIStates())
    private var _availabilityUiState = savedStateHandle.getStateFlow("availabilityUiState", AppUIStates())
    private var _loadHomepageUiState = savedStateHandle.getStateFlow("loadHomepageUiState", AppUIStates())
    private var _switchVendorUiState = savedStateHandle.getStateFlow("switchVendorUiState", AppUIStates())
    private var _getTherapistUiState = savedStateHandle.getStateFlow("getTherapistUiState", AppUIStates())
    private var _getTimesActionUiState = savedStateHandle.getStateFlow("getTimesActionUiState", AppUIStates())
    private var _addAppointmentReviewUiState = savedStateHandle.getStateFlow("addAppointmentReviewUiState", AppUIStates())
    private var _addProductReviewUiState = savedStateHandle.getStateFlow("addProductReviewUiState", AppUIStates())
    private var _refreshAppointmentUiState = savedStateHandle.getStateFlow("refreshAppointmentUiState", AppUIStates())
    private var _loadPendingAppointmentUiState = savedStateHandle.getStateFlow("loadPendingAppointmentUiState", AppUIStates())
    private var _deletePendingAppointmentUiState = savedStateHandle.getStateFlow("deletePendingAppointmentUiState", AppUIStates())
    private var _postponeAppointmentUiState = savedStateHandle.getStateFlow("postponeAppointmentUiState", AppUIStates())
    private var _createAppointmentUiState = savedStateHandle.getStateFlow("createAppointmentUiState", AppUIStates())
    private var _joinMeetingUiState = savedStateHandle.getStateFlow("joinMeetingUiState", AppUIStates())
    private var _therapistDashboardUiState = savedStateHandle.getStateFlow("therapistDashboardUiState", AppUIStates())

    val deleteUIStateInfo: StateFlow<AppUIStates>
        get() = _deleteUiState
    val loadPendingAppointmentUiState: StateFlow<AppUIStates>
        get() = _loadPendingAppointmentUiState
    val createAppointmentUiState: StateFlow<AppUIStates>
        get() = _createAppointmentUiState
    val deletePendingAppointmentUiState: StateFlow<AppUIStates>
        get() = _deletePendingAppointmentUiState

    val postponeAppointmentUiState: StateFlow<AppUIStates>
        get() = _postponeAppointmentUiState

    val therapistDashboardUiState: StateFlow<AppUIStates>
        get() = _therapistDashboardUiState

    val timesActionUiState: StateFlow<AppUIStates>
        get() = _getTimesActionUiState

    val refreshAppointmentActionUiState: StateFlow<AppUIStates>
        get() = _refreshAppointmentUiState

    val addAppointmentReviewUiState: StateFlow<AppUIStates>
        get() = _addAppointmentReviewUiState

    val addProductReviewUiState: StateFlow<AppUIStates>
        get() = _addProductReviewUiState

    val loadHomepageUiState: StateFlow<AppUIStates>
        get() = _loadHomepageUiState

    val getTherapistUiState: StateFlow<AppUIStates>
        get() = _getTherapistUiState

    val availabilityStateInfo: StateFlow<AppUIStates>
        get() = _availabilityUiState

    val switchVendorUiState: StateFlow<AppUIStates>
        get() = _switchVendorUiState

    val uiStateInfo: StateFlow<AppUIStates>
        get() = _uiState

    val paymentUiStateInfo: StateFlow<AppUIStates>
        get() = _paymentUiState

    val joinMeetingStateInfo: StateFlow<AppUIStates>
        get() = _joinMeetingUiState

    fun switchPostPostponeAppointmentUiState(appUIStates: AppUIStates) {
        savedStateHandle["postponeAppointmentUiState"] = appUIStates
    }
    fun switchRefreshAppointmentUiState(appUIStates: AppUIStates) {
        savedStateHandle["refreshAppointmentUiState"] = appUIStates
    }
    fun switchActionDeleteUIState(appUIStates: AppUIStates) {
        savedStateHandle["deleteActionUiState"] = appUIStates
    }
    fun switchDeletePendingAppointmentUiState(appUIStates: AppUIStates) {
        savedStateHandle["deletePendingAppointmentUiState"] = appUIStates
    }
    fun switchCreateAppointmentUiState(appUIStates: AppUIStates) {
        savedStateHandle["createAppointmentUiState"] = appUIStates
    }

    fun switchActionLoadPendingAppointmentUiState(appUIStates: AppUIStates) {
        savedStateHandle["loadPendingAppointmentUiState"] = appUIStates
    }
    fun switchActionLoadHomepageUiState(appUIStates: AppUIStates) {
        savedStateHandle["loadHomepageUiState"] = appUIStates
    }

    fun switchActionAvailabilityUIState(appUIStates: AppUIStates) {
        savedStateHandle["availabilityUiState"] = appUIStates
    }

    fun switchAddAppointmentReviewUiState(appUIStates: AppUIStates) {
        savedStateHandle["addAppointmentReviewUiState"] = appUIStates
    }

    fun switchAddProductReviewUiState(appUIStates: AppUIStates) {
        savedStateHandle["addProductReviewUiState"] = appUIStates
    }

    fun switchTimeActionUiState(appUIStates: AppUIStates) {
        savedStateHandle["getTimesActionUiState"] = appUIStates
    }

    fun switchActionMeetingUIState(appUIStates: AppUIStates) {
        savedStateHandle["joinMeetingUiState"] = appUIStates
    }
    fun switchGetTherapistUiState(appUIStates: AppUIStates) {
        savedStateHandle["getTherapistUiState"] = appUIStates
    }
    fun switchActionTherapistDashboardUIState(appUIStates: AppUIStates) {
        savedStateHandle["therapistDashboardUiState"] = appUIStates
    }

    fun switchVendorActionUIState(appUIStates: AppUIStates) {
        savedStateHandle["switchVendorUiState"] = appUIStates
    }
    fun switchActionUIState(appUIStates: AppUIStates) {
        savedStateHandle["actionUiState"] = appUIStates
    }

    fun switchPaymentActionUIState(appUIStates: AppUIStates) {
        savedStateHandle["paymentUiState"] = appUIStates
    }


}