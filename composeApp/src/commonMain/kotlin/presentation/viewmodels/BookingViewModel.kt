package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.ServiceTime
import domain.Models.ServiceTypeItem
import domain.Models.ServiceTypeSpecialist
import domain.Models.UnsavedAppointment
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

class BookingViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _specialists =  savedStateHandle.getStateFlow("specialists", arrayListOf<ServiceTypeSpecialist>())
    private var _serviceTimes =  savedStateHandle.getStateFlow("serviceTimes", arrayListOf<ServiceTime>())
    private var _selectedServiceType =  savedStateHandle.getStateFlow("selectedServiceType", ServiceTypeItem())
    private var _currentBookingId =  savedStateHandle.getStateFlow("currentBookingId", -1)
    private var _day =  savedStateHandle.getStateFlow("day", -1)
    private var _month =  savedStateHandle.getStateFlow("month", -1)
    private var _year =  savedStateHandle.getStateFlow("year", -1)
    private var _currentAppointmentBooking =  savedStateHandle.getStateFlow("currentAppointmentBooking", UnsavedAppointment(bookingId = -1))

    val serviceSpecialists: StateFlow<List<ServiceTypeSpecialist>>
        get() = _specialists
    val serviceTime: StateFlow<List<ServiceTime>>
        get() = _serviceTimes
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

    fun setSpecialists(serviceSpecialists: List<ServiceTypeSpecialist>) {
        savedStateHandle["specialists"] = serviceSpecialists
    }

    fun setServiceTimes(serviceTime: List<ServiceTime>) {
        savedStateHandle["serviceTimes"] = serviceTime
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
        savedStateHandle["specialists"] = arrayListOf<ServiceTypeSpecialist>()
    }

    fun undoSpecialist() {
        savedStateHandle["specialists"] = arrayListOf<ServiceTypeSpecialist>()
    }

    fun clearCurrentBooking(){
        savedStateHandle["selectedServiceType"] = ServiceTypeItem()
        savedStateHandle["currentAppointmentBooking"] = UnsavedAppointment(-1)
        savedStateHandle["specialists"] = arrayListOf<ServiceTypeSpecialist>()
    }

}