package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Enums.PaymentMethod
import domain.Models.Appointment
import domain.Models.PlatformTime
import domain.Models.ServiceImages
import domain.Models.ServiceTypeItem
import domain.Models.ServiceTypeTherapists
import domain.Models.UserAppointment
import domain.Models.VendorTime
import kotlinx.coroutines.flow.StateFlow

class BookingViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _therapists =  savedStateHandle.getStateFlow("therapists", arrayListOf<ServiceTypeTherapists>())
    private var _pendingAppointments =  savedStateHandle.getStateFlow("pendingAppointments", listOf<UserAppointment>())
    private var _selectedServiceType =  savedStateHandle.getStateFlow("selectedServiceType", ServiceTypeItem())
    private var _serviceTypeList =  savedStateHandle.getStateFlow("serviceTypeList", listOf<ServiceTypeItem>())
    private var _serviceimages =  savedStateHandle.getStateFlow("serviceImages", listOf<ServiceImages>())
    private var _day =  savedStateHandle.getStateFlow("day", -1)
    private var _month =  savedStateHandle.getStateFlow("month", -1)
    private var _year =  savedStateHandle.getStateFlow("year", -1)
    private var _isMobileService =  savedStateHandle.getStateFlow("isMobileService", false)
    private var _currentAppointmentBooking =  savedStateHandle.getStateFlow("currentAppointmentBooking", Appointment())
    private var _vendorTimes = savedStateHandle.getStateFlow("vendorTimes", listOf<VendorTime>())
    private var _platformTimes = savedStateHandle.getStateFlow("platformTimes", listOf<PlatformTime>())
    private var _paymentMethod = savedStateHandle.getStateFlow("paymentMethod", PaymentMethod.CARD_PAYMENT.toPath())


    val serviceTherapists: StateFlow<List<ServiceTypeTherapists>>
        get() = _therapists

    val serviceTypeList: StateFlow<List<ServiceTypeItem>>
        get() = _serviceTypeList

    val serviceImages: StateFlow<List<ServiceImages>>
        get() = _serviceimages
    val vendorTimes: StateFlow<List<VendorTime>>
        get() = _vendorTimes

    val platformTimes: StateFlow<List<PlatformTime>>
        get() = _platformTimes

    val pendingAppointments: StateFlow<List<UserAppointment>>
        get() = _pendingAppointments

    val selectedServiceType: StateFlow<ServiceTypeItem>
        get() = _selectedServiceType
    val paymentMethod: StateFlow<String>
        get() = _paymentMethod

    val day: StateFlow<Int>
        get() = _day

    val month: StateFlow<Int>
        get() = _month

    val year: StateFlow<Int>
        get() = _year
    fun setTherapists(serviceTherapists: List<ServiceTypeTherapists>) {
        savedStateHandle["therapists"] = serviceTherapists
    }
    fun setServiceTypeList(serviceTypeList: List<ServiceTypeItem>) {
        savedStateHandle["serviceTypeList"] = serviceTypeList
    }
    fun setServiceImages(serviceImages: List<ServiceImages>) {
        savedStateHandle["serviceImages"] = serviceImages
    }
    fun setVendorTimes(vendorTimes: List<VendorTime>) {
        savedStateHandle["vendorTimes"] = vendorTimes
    }
    fun setPlatformTimes(platformTimes: List<PlatformTime>) {
        savedStateHandle["platformTimes"] = platformTimes
    }
    fun setIsMobileService(isMobileService: Boolean) {
        savedStateHandle["isMobileService"] = isMobileService
    }

    fun setPendingAppointments(pendingAppointments: List<UserAppointment>) {
        savedStateHandle["pendingAppointments"] = pendingAppointments
    }

    fun setPaymentMethod(paymentMethod: String) {
        savedStateHandle["paymentMethod"] = paymentMethod
    }


    val isMobileService: StateFlow<Boolean>
        get() = _isMobileService

    val currentAppointmentBooking: StateFlow<Appointment>
        get() = _currentAppointmentBooking

    fun setSelectedServiceType(selectedServiceType: ServiceTypeItem) {
        savedStateHandle["selectedServiceType"] = selectedServiceType
    }

    fun setSelectedDay(day: Int) {
        savedStateHandle["day"] = day
    }

    fun setSelectedMonth(month: Int) {
        savedStateHandle["month"] = month
    }

    fun setSelectedMonthName(monthName: String) {
        savedStateHandle["monthName"] = monthName
    }

    fun setSelectedYear(year: Int) {
        savedStateHandle["year"] = year
    }

    fun setCurrentAppointmentBooking(currentAppointmentBooking: Appointment) {
        savedStateHandle["currentAppointmentBooking"] = currentAppointmentBooking
    }

    fun undoSelectedServiceType() {
        savedStateHandle["selectedServiceType"] = ServiceTypeItem()
        savedStateHandle["therapists"] = arrayListOf<ServiceTypeTherapists>()
    }

    fun undoTherapists() {
        savedStateHandle["therapists"] = arrayListOf<ServiceTypeTherapists>()
    }


}