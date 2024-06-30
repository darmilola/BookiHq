package presentation.viewmodels

import UIStates.ActionUIStates
import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.OrderItem
import domain.Models.VendorRecommendation
import domain.Models.Services
import domain.Models.TherapistInfo
import domain.Models.UnsavedAppointment
import domain.Models.User
import domain.Models.Vendor
import kotlinx.coroutines.flow.StateFlow
import domain.Models.HomepageInfo
import domain.Models.VendorStatusModel

class MainViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _screenTitle = savedStateHandle.getStateFlow("screenTitle","Home")
    private var _connectedVendor =  savedStateHandle.getStateFlow("connectedVendor", Vendor())
    private var _currentUserInfo =  savedStateHandle.getStateFlow("userInfo", User())
    private var _currentUserId =  savedStateHandle.getStateFlow("currentUserId", -1)
    private var _currentTherapistId =  savedStateHandle.getStateFlow("currentTherapistId", -1)
    private var _currentVendorId =  savedStateHandle.getStateFlow("currentVendorId", -1)
    private var _currentUserFirstname =  savedStateHandle.getStateFlow("currentUserFirstname", "")
    private var _currentUserEmail =  savedStateHandle.getStateFlow("currentUserEmail", "")
    private var _currentVendorEmail =  savedStateHandle.getStateFlow("currentVendorEmail", "")
    private var _currentVendorLogoUrl =  savedStateHandle.getStateFlow("currentVendorLogoUrl", "")
    private var _currentTherapistInfo =  savedStateHandle.getStateFlow("therapistInfo", TherapistInfo())
    private var _screenNav =  savedStateHandle.getStateFlow("screenNav", Pair(-1,-1))
    private var _selectedService =  savedStateHandle.getStateFlow("selectedService", Services())
    private var _vendorRecommendation =  savedStateHandle.getStateFlow("vendorRecommendation", VendorRecommendation())
    private var _currentUnsavedAppointments =  savedStateHandle.getStateFlow("currentUnsavedAppointments", ArrayList<UnsavedAppointment>())
    private var _currentUnsavedOrders =  savedStateHandle.getStateFlow("currentUnsavedOrders", ArrayList<OrderItem>())
    private var _currentOrderReference =  savedStateHandle.getStateFlow("currentOrderReference", -1)
    private var _homePageViewHeight =  savedStateHandle.getStateFlow("homePageViewHeight",0)
    private var _currentUnsavedOrderSize =  savedStateHandle.getStateFlow("currentUnsavedOrderSize", 0)

    val screenTitle: StateFlow<String>
        get() = _screenTitle

    val selectedService: StateFlow<Services>
        get() = _selectedService

    val unSavedAppointments: StateFlow<ArrayList<UnsavedAppointment>>
        get() = _currentUnsavedAppointments

    val unSavedOrders: StateFlow<ArrayList<OrderItem>>
        get() = _currentUnsavedOrders

    val unSavedOrderSize: StateFlow<Int>
        get() = _currentUnsavedOrderSize

    val currentOrderReference: StateFlow<Int>
        get() = _currentOrderReference

    val userFirstname: StateFlow<String>
        get() = _currentUserFirstname

    val connectedVendor: StateFlow<Vendor>
        get() = _connectedVendor

    val homePageViewHeight: StateFlow<Int>
        get() = _homePageViewHeight

    val userEmail: StateFlow<String>
        get() = _currentUserEmail
    val vendorEmail: StateFlow<String>
        get() = _currentVendorEmail
    val vendorLogoUrl: StateFlow<String>
        get() = _currentVendorLogoUrl
    val userId: StateFlow<Int>
        get() = _currentUserId

    val therapistId: StateFlow<Int>
        get() = _currentTherapistId

    val vendorId: StateFlow<Int>
        get() = _currentVendorId

    val vendorRecommendation: StateFlow<VendorRecommendation>
        get() = _vendorRecommendation

    val currentUserInfo: StateFlow<User>
        get() = _currentUserInfo

    val currentTherapistInfo: StateFlow<TherapistInfo>
        get() = _currentTherapistInfo


    val screenNav: StateFlow<Pair<Int,Int>>
        get() = _screenNav

    fun setTitle(newTitle: String) {
        savedStateHandle["screenTitle"] = newTitle
    }

    fun setUnsavedOrderSize(size: Int) {
        savedStateHandle["currentUnsavedOrderSize"] = size
    }

    fun setScreenNav(screenNav: Pair<Int,Int>) {
        savedStateHandle["screenNav"] = screenNav
    }

    fun setConnectedVendor(vendor: Vendor) {
        savedStateHandle["connectedVendor"] = vendor
    }

    fun setUserId(userId: Int) {
        savedStateHandle["currentUserId"] = userId
    }

    fun setVendorId(vendorId: Int) {
        savedStateHandle["currentVendorId"] = vendorId
    }

    fun setTherapistId(therapistId: Int) {
        savedStateHandle["currentTherapistId"] = therapistId
    }

    fun setUserFirstname(userFirstname: String) {
        savedStateHandle["currentUserFirstname"] = userFirstname
    }
    fun setUserEmail(userEmail: String) {
        savedStateHandle["currentUserEmail"] = userEmail
    }
    fun setVendorEmail(vendorEmail: String) {
        savedStateHandle["currentVendorEmail"] = vendorEmail
    }
    fun setVendorBusinessLogoUrl(vendorLogoUrl: String) {
        savedStateHandle["currentVendorLogoUrl"] = vendorLogoUrl
    }

    fun setUserInfo(user: User) {
        savedStateHandle["userInfo"] = user
    }

    fun setTherapistInfo(therapistInfo: TherapistInfo) {
        savedStateHandle["therapistInfo"] = therapistInfo
    }

    fun setSelectedService(selectedService: Services) {
        savedStateHandle["selectedService"] = selectedService
    }

    fun setVendorRecommendation(vendorRecommendation: VendorRecommendation) {
        savedStateHandle["vendorRecommendation"] = vendorRecommendation
    }

    fun setHomePageViewHeight(viewHeight: Int) {
        savedStateHandle["homePageViewHeight"] = viewHeight
    }

    fun setCurrentUnsavedAppointments(unsavedAppointments: ArrayList<UnsavedAppointment>) {
        savedStateHandle["currentUnsavedAppointments"] = unsavedAppointments
    }

    fun setCurrentUnsavedOrders(orderItems: ArrayList<OrderItem>) {
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
        savedStateHandle["currentUnsavedOrders"] = ArrayList<OrderItem>()
    }

    fun setHomePageInfo(homepageInfo: HomepageInfo) {
        savedStateHandle["homePageInfo"] = homepageInfo
    }

    fun setVendorStatus(vendorStatus: ArrayList<VendorStatusModel>) {
        savedStateHandle["vendorStatus"] = vendorStatus
    }

    fun removeLastAppointment() {
        val unsavedAppointments: ArrayList<UnsavedAppointment> = savedStateHandle["currentUnsavedAppointments"]!!
        unsavedAppointments.removeLastOrNull()
        savedStateHandle["currentUnsavedAppointments"] = unsavedAppointments
    }

    fun clearVendorRecommendation() {
        savedStateHandle["vendorRecommendation"] = VendorRecommendation()
    }
    fun setMainUIState(actionUIStates: ActionUIStates) {
        savedStateHandle["mainUiState"] = actionUIStates
    }
}