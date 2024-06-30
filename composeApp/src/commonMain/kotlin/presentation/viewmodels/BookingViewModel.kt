package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.AvailableTime
import domain.Models.ServiceTypeItem
import domain.Models.ServiceTypeTherapists
import domain.Models.UnsavedAppointment
import kotlinx.coroutines.flow.StateFlow

class BookingViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _therapists =  savedStateHandle.getStateFlow("therapists", arrayListOf<ServiceTypeTherapists>())
    private var _availableTimes =  savedStateHandle.getStateFlow("serviceTimes", arrayListOf<AvailableTime>())
    private var _selectedServiceType =  savedStateHandle.getStateFlow("selectedServiceType", ServiceTypeItem())
    private var _currentBookingId =  savedStateHandle.getStateFlow("currentBookingId", -1)
    private var _day =  savedStateHandle.getStateFlow("day", -1)
    private var _month =  savedStateHandle.getStateFlow("month", -1)
    private var _year =  savedStateHandle.getStateFlow("year", -1)
    private var _currentAppointmentBooking =  savedStateHandle.getStateFlow("currentAppointmentBooking", UnsavedAppointment(bookingId = -1))

    val serviceTherapists: StateFlow<List<ServiceTypeTherapists>>
        get() = _therapists
    val availableTime: StateFlow<List<AvailableTime>>
        get() = _availableTimes
    val selectedServiceType: StateFlow<ServiceTypeItem>
        get() = _selectedServiceType

    val day: StateFlow<Int>
        get() = _day

    val month: StateFlow<Int>
        get() = _month

    val year: StateFlow<Int>
        get() = _year

    val currentBookingId: StateFlow<Int>
        get() = _currentBookingId

    fun setTherapists(serviceTherapists: List<ServiceTypeTherapists>) {
        savedStateHandle["therapists"] = serviceTherapists
    }

    fun setServiceTimes(availableTime: List<AvailableTime>) {
        savedStateHandle["serviceTimes"] = availableTime
    }

    val currentAppointmentBooking: StateFlow<UnsavedAppointment>
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

    fun setCurrentBooking(unsavedAppointment: UnsavedAppointment) {
        savedStateHandle["currentAppointmentBooking"] = unsavedAppointment
    }

    fun setCurrentBookingId(bookingId: Int) {
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
        savedStateHandle["currentAppointmentBooking"] = UnsavedAppointment(-1)
        savedStateHandle["therapists"] = arrayListOf<ServiceTypeTherapists>()
    }

}