package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.PlatformTime
import domain.Models.ServiceTypeItem
import domain.Models.ServiceTypeTherapists
import domain.Models.CurrentAppointmentBooking
import domain.Models.VendorTime
import kotlinx.coroutines.flow.StateFlow

class BookingViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _therapists =  savedStateHandle.getStateFlow("therapists", arrayListOf<ServiceTypeTherapists>())
    private var _selectedServiceType =  savedStateHandle.getStateFlow("selectedServiceType", ServiceTypeItem())
    private var _currentBookingKey =  savedStateHandle.getStateFlow("currentBookingId", -1)
    private var _day =  savedStateHandle.getStateFlow("day", -1)
    private var _month =  savedStateHandle.getStateFlow("month", -1)
    private var _year =  savedStateHandle.getStateFlow("year", -1)
    private var _isMobileService =  savedStateHandle.getStateFlow("isMobileService", false)
    private var _currentAppointmentBooking =  savedStateHandle.getStateFlow("currentAppointmentBooking", CurrentAppointmentBooking(bookingKey = -1))
    private var _vendorTimes = savedStateHandle.getStateFlow("vendorTimes", listOf<VendorTime>())
    private var _platformTimes = savedStateHandle.getStateFlow("platformTimes", listOf<PlatformTime>())


    val serviceTherapists: StateFlow<List<ServiceTypeTherapists>>
        get() = _therapists
    val vendorTimes: StateFlow<List<VendorTime>>
        get() = _vendorTimes

    val platformTimes: StateFlow<List<PlatformTime>>
        get() = _platformTimes
    val selectedServiceType: StateFlow<ServiceTypeItem>
        get() = _selectedServiceType

    val day: StateFlow<Int>
        get() = _day

    val month: StateFlow<Int>
        get() = _month

    val year: StateFlow<Int>
        get() = _year

    val currentBookingKey: StateFlow<Int>
        get() = _currentBookingKey

    fun setTherapists(serviceTherapists: List<ServiceTypeTherapists>) {
        savedStateHandle["therapists"] = serviceTherapists
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

    val isMobileService: StateFlow<Boolean>
        get() = _isMobileService

    val currentAppointmentBooking: StateFlow<CurrentAppointmentBooking>
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

    fun setSelectedYear(year: Int) {
        savedStateHandle["year"] = year
    }

    fun setCurrentBooking(currentAppointmentBooking: CurrentAppointmentBooking) {
        savedStateHandle["currentAppointmentBooking"] = currentAppointmentBooking
    }

    fun setCurrentBookingKey(bookingId: Int) {
        savedStateHandle["currentBookingId"] = bookingId
    }

    fun undoSelectedServiceType() {
        savedStateHandle["selectedServiceType"] = ServiceTypeItem()
        savedStateHandle["therapists"] = arrayListOf<ServiceTypeTherapists>()
    }

    fun undoTherapists() {
        savedStateHandle["therapists"] = arrayListOf<ServiceTypeTherapists>()
    }

    fun clearCurrentBooking(){
        savedStateHandle["selectedServiceType"] = ServiceTypeItem()
        savedStateHandle["currentAppointmentBooking"] = CurrentAppointmentBooking(bookingKey = -1)
        savedStateHandle["therapists"] = arrayListOf<ServiceTypeTherapists>()
    }

}