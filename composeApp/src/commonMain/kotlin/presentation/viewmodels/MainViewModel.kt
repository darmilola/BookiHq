package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Enums.MainTabEnum
import domain.Models.OrderItem
import domain.Models.VendorRecommendation
import domain.Models.Services
import domain.Models.CurrentAppointmentBooking
import domain.Models.User
import domain.Models.Vendor
import kotlinx.coroutines.flow.StateFlow
import domain.Models.ServiceTypeItem


class MainViewModel(val savedStateHandle: SavedStateHandle): ViewModel(){

    private var _screenTitle = savedStateHandle.getStateFlow("screenTitle","Home")
    private var _connectedVendor =  savedStateHandle.getStateFlow("connectedVendor", Vendor())
    private var _currentUserInfo =  savedStateHandle.getStateFlow("userInfo", User())
    private var _currentVendorId =  savedStateHandle.getStateFlow("currentVendorId", -1L)
    private var _screenNav =  savedStateHandle.getStateFlow("screenNav", Pair(-1,-1))
    private var _selectedService =  savedStateHandle.getStateFlow("selectedService", Services())
    private var _selectedServiceType =  savedStateHandle.getStateFlow("selectedServiceType", ServiceTypeItem())
    private var _currentCurrentAppointmentBooking =  savedStateHandle.getStateFlow("currentUnsavedAppointment", arrayListOf<CurrentAppointmentBooking>())
    private var _currentUnsavedOrders =  savedStateHandle.getStateFlow("currentUnsavedOrders", ArrayList<OrderItem>())
    private var _currentUnsavedOrderSize =  savedStateHandle.getStateFlow("currentUnsavedOrderSize", 0)
    private var _currentMainDisplayTab =  savedStateHandle.getStateFlow("displayedTab", MainTabEnum.HOME.toPath())
    private var _isSearchProductState =  savedStateHandle.getStateFlow("isSearchProduct", false)

    val screenTitle: StateFlow<String>
        get() = _screenTitle

    val selectedService: StateFlow<Services>
        get() = _selectedService

    val unSavedAppointments: StateFlow<ArrayList<CurrentAppointmentBooking>>
        get() = _currentCurrentAppointmentBooking

    val unSavedOrders: StateFlow<ArrayList<OrderItem>>
        get() = _currentUnsavedOrders

    val isSearchProduct: StateFlow<Boolean>
        get() = _isSearchProductState

    val displayedTab: StateFlow<String>
        get() = _currentMainDisplayTab

    val unSavedOrderSize: StateFlow<Int>
        get() = _currentUnsavedOrderSize
    val connectedVendor: StateFlow<Vendor>
        get() = _connectedVendor

    val vendorId: StateFlow<Long>
        get() = _currentVendorId

    val currentUserInfo: StateFlow<User>
        get() = _currentUserInfo

    val selectedServiceType: StateFlow<ServiceTypeItem>
        get() = _selectedServiceType


    val screenNav: StateFlow<Pair<Int,Int>>
        get() = _screenNav

    fun setTitle(newTitle: String) {
        savedStateHandle["screenTitle"] = newTitle
    }

    fun setIsSearchProduct(isSearching: Boolean) {
        savedStateHandle["isSearchProduct"] = isSearching
    }

    fun setDisplayedTab(displayedTab: String) {
        savedStateHandle["displayedTab"] = displayedTab
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

    fun setRecommendationServiceType(serviceTypeItem: ServiceTypeItem) {
        savedStateHandle["selectedServiceType"] = serviceTypeItem
    }

    fun setUserId(userId: Long) {
        savedStateHandle["currentUserId"] = userId
    }

    fun setVendorId(vendorId: Long) {
        savedStateHandle["currentVendorId"] = vendorId
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

    fun setSelectedService(selectedService: Services) {
        savedStateHandle["selectedService"] = selectedService
    }

    fun setCurrentUnsavedAppointment(currentAppointmentBookingList: ArrayList<CurrentAppointmentBooking>) {
        savedStateHandle["currentUnsavedAppointment"] = currentAppointmentBookingList
    }

    fun setCurrentUnsavedOrders(orderItems: ArrayList<OrderItem>) {
        savedStateHandle["currentUnsavedOrders"] = orderItems
    }

    fun clearUnsavedAppointments() {
        savedStateHandle["currentUnsavedAppointments"] = ArrayList<CurrentAppointmentBooking>()
    }

    fun clearCurrentOrderReference() {
        savedStateHandle["currentOrderReference"] = -1
    }

    fun clearUnsavedOrders() {
        savedStateHandle["currentUnsavedOrders"] = ArrayList<OrderItem>()
    }

    fun removeLastAppointment() {
        val currentAppointmentBookings: ArrayList<CurrentAppointmentBooking> = savedStateHandle["currentUnsavedAppointment"]!!
        currentAppointmentBookings.removeLastOrNull()
        savedStateHandle["currentUnsavedAppointments"] = currentAppointmentBookings
    }

    fun clearVendorRecommendation() {
        savedStateHandle["vendorRecommendation"] = VendorRecommendation()
    }
}