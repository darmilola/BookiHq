package presentation.viewmodels

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.OrderItem
import domain.Models.VendorRecommendation
import domain.Models.Services
import domain.Models.SpecialistInfo
import domain.Models.UnsavedAppointment
import domain.Models.User
import domain.Models.Vendor
import kotlinx.coroutines.flow.StateFlow
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.wrapper.wrap

class MainViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _screenTitle = savedStateHandle.getStateFlow("screenTitle","Home")
    private var _connectedVendor =  savedStateHandle.getStateFlow("connectedVendor", Vendor())
    private var _currentUserInfo =  savedStateHandle.getStateFlow("userInfo", User())
    private var _currentSpecialistInfo =  savedStateHandle.getStateFlow("specialistInfo", SpecialistInfo())
    private var _screenNav =  savedStateHandle.getStateFlow("screenNav", Pair(-1,-1))
    private var _selectedService =  savedStateHandle.getStateFlow("selectedService", Services())
    private var _vendorRecommendation =  savedStateHandle.getStateFlow("vendorRecommendation", VendorRecommendation())
    private var _currentUnsavedAppointments =  savedStateHandle.getStateFlow("currentUnsavedAppointments", ArrayList<UnsavedAppointment>())
    private var _currentUnsavedOrders =  savedStateHandle.getStateFlow("currentUnsavedOrders", mutableListOf<OrderItem>())
    private var _currentOrderReference =  savedStateHandle.getStateFlow("currentOrderReference", -1)
    private var _mainUiState =  savedStateHandle.getStateFlow("mainUiState", AsyncUIStates())

    val screenTitle: StateFlow<String>
        get() = _screenTitle

    val selectedService: StateFlow<Services>
        get() = _selectedService

    val unSavedAppointments: StateFlow<ArrayList<UnsavedAppointment>>
        get() = _currentUnsavedAppointments

    val unSavedOrders: StateFlow<MutableList<OrderItem>>
        get() = _currentUnsavedOrders

    val currentOrderReference: StateFlow<Int>
        get() = _currentOrderReference

    val connectedVendor: StateFlow<Vendor>
        get() = _connectedVendor

    val vendorRecommendation: StateFlow<VendorRecommendation>
        get() = _vendorRecommendation

    val currentUserInfo: StateFlow<User>
        get() = _currentUserInfo

    val currentSpecialistInfo: StateFlow<SpecialistInfo>
        get() = _currentSpecialistInfo


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

    fun setSpecialistInfo(specialistInfo: SpecialistInfo) {
        savedStateHandle["specialistInfo"] = specialistInfo
    }

    fun setSelectedService(selectedService: Services) {
        savedStateHandle["selectedService"] = selectedService
    }

    fun setVendorRecommendation(vendorRecommendation: VendorRecommendation) {
        savedStateHandle["vendorRecommendation"] = vendorRecommendation
    }

    fun setCurrentUnsavedAppointments(unsavedAppointments: ArrayList<UnsavedAppointment>) {
        savedStateHandle["currentUnsavedAppointments"] = unsavedAppointments
    }

    fun setCurrentUnsavedOrders(orderItems: MutableList<OrderItem>) {
        savedStateHandle["currentUnsavedOrders"] = orderItems
    }

    fun setCurrentOrderReference(orderReference: Int) {
        savedStateHandle["currentOrderReference"] = orderReference
    }

    fun clearUnsavedAppointments() {
        savedStateHandle["currentUnsavedAppointments"] = ArrayList<UnsavedAppointment>()
    }

    fun clearCurrentOrderReference() {
        savedStateHandle["currentOrderReference"] = -1
    }

    fun clearUnsavedOrders() {
        savedStateHandle["currentUnsavedOrders"] = SnapshotStateList<OrderItem>()
    }

    fun removeLastAppointment() {
        val unsavedAppointments: ArrayList<UnsavedAppointment> = savedStateHandle["currentUnsavedAppointments"]!!
        unsavedAppointments.removeLastOrNull()
        savedStateHandle["currentUnsavedAppointments"] = unsavedAppointments
    }

    fun clearVendorRecommendation() {
        savedStateHandle["vendorRecommendation"] = VendorRecommendation()
    }
    fun setMainUIState(asyncUIStates: AsyncUIStates) {
        savedStateHandle["mainUiState"] = asyncUIStates
    }
}