package presentation.viewmodels

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.VendorRecommendation
import domain.Models.Services
import domain.Models.UnsavedAppointment
import domain.Models.User
import domain.Models.Vendor
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _screenTitle = savedStateHandle.getStateFlow("screenTitle","")
    private var _connectedVendor =  savedStateHandle.getStateFlow("connectedVendor", Vendor())
    private var _currentUserInfo =  savedStateHandle.getStateFlow("userInfo", User())
    private var _screenNav =  savedStateHandle.getStateFlow("screenNav", Pair(-1,-1))
    private var _selectedService =  savedStateHandle.getStateFlow("selectedService", Services())
    private var _vendorRecommendation =  savedStateHandle.getStateFlow("vendorRecommendation", VendorRecommendation())
    private var _currentUnsavedAppointments =  savedStateHandle.getStateFlow("currentUnsavedAppointments", SnapshotStateList<UnsavedAppointment>())
    private var _mainUiState =  savedStateHandle.getStateFlow("mainUiState", AsyncUIStates())

    val screenTitle: StateFlow<String>
        get() = _screenTitle

    val selectedService: StateFlow<Services>
        get() = _selectedService

    val unSavedAppointments: StateFlow<SnapshotStateList<UnsavedAppointment>>
        get() = _currentUnsavedAppointments

    val connectedVendor: StateFlow<Vendor>
        get() = _connectedVendor

    val vendorRecommendation: StateFlow<VendorRecommendation>
        get() = _vendorRecommendation

    val currentUserInfo: StateFlow<User>
        get() = _currentUserInfo

    val screenNav: StateFlow<Pair<Int,Int>>
        get() = _screenNav
    val mainUIState: StateFlow<AsyncUIStates>
        get() = _mainUiState

    fun setTitle(newTitle: String) {
        savedStateHandle["screenTitle"] = newTitle
    }

    fun setScreenNav(screenNav: Pair<Int,Int>) {
        savedStateHandle["screenNav"] = screenNav
    }

    fun setConnectedVendor(vendor: Vendor) {
        savedStateHandle["connectedVendor"] = vendor
    }

    fun setUserInfo(user: User) {
        savedStateHandle["userInfo"] = user
    }

    fun setSelectedService(selectedService: Services) {
        savedStateHandle["selectedService"] = selectedService
    }

    fun setVendorRecommendation(vendorRecommendation: VendorRecommendation) {
        savedStateHandle["vendorRecommendation"] = vendorRecommendation
    }

    fun setCurrentUnsavedAppointments(unsavedAppointments: MutableList<UnsavedAppointment>) {
        savedStateHandle["currentUnsavedAppointments"] = unsavedAppointments
    }
    fun clearUnsavedAppointments() {
        savedStateHandle["currentUnsavedAppointments"] = SnapshotStateList<UnsavedAppointment>()
    }
    fun clearVendorRecommendation() {
        savedStateHandle["vendorRecommendation"] = VendorRecommendation()
    }
    fun setMainUIState(asyncUIStates: AsyncUIStates) {
        savedStateHandle["mainUiState"] = asyncUIStates
    }
}