package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.ServiceTypeItem
import domain.Models.ServiceTypeSpecialist
import domain.Models.UnsavedAppointment
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

class BookingViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _specialists =  savedStateHandle.getStateFlow("specialists", arrayListOf<ServiceTypeSpecialist>())
    private var _selectedServiceType =  savedStateHandle.getStateFlow("selectedServiceType", ServiceTypeItem())
    private var _currentBookingId =  savedStateHandle.getStateFlow("currentBookingId", -1)
    private var _selectedDate =  savedStateHandle.getStateFlow("selectedDate", LocalDate.parse("2024-03-30"))
    private var _currentAppointmentBooking =  savedStateHandle.getStateFlow("currentAppointmentBooking", UnsavedAppointment(-1))

    val serviceSpecialists: StateFlow<List<ServiceTypeSpecialist>>
        get() = _specialists
    val selectedServiceType: StateFlow<ServiceTypeItem>
        get() = _selectedServiceType

    val selectedDate: StateFlow<LocalDate>
        get() = _selectedDate

    val currentBookingId: StateFlow<Int>
        get() = _currentBookingId

    fun setSpecialists(serviceSpecialists: List<ServiceTypeSpecialist>) {
        savedStateHandle["specialists"] = serviceSpecialists
    }

    val currentAppointmentBooking: StateFlow<UnsavedAppointment>
        get() = _currentAppointmentBooking

    fun setSelectedServiceType(selectedServiceType: ServiceTypeItem) {
        savedStateHandle["selectedServiceType"] = selectedServiceType
    }

    fun setSelectedDate(localDate: LocalDate) {
        savedStateHandle["selectedDate"] = localDate
    }
    fun setCurrentBooking(unsavedAppointment: UnsavedAppointment) {
        savedStateHandle["currentAppointmentBooking"] = unsavedAppointment
    }

    fun setCurrentBookingId(bookingId: Int) {
        savedStateHandle["currentBookingId"] = bookingId
    }
    fun clearCurrentBooking(){
        savedStateHandle["selectedServiceType"] = ServiceTypeItem()
        savedStateHandle["currentAppointmentBooking"] = UnsavedAppointment(-1)
        savedStateHandle["selectedDate"] = LocalDate.parse("2024-03-30")
        savedStateHandle["specialists"] = arrayListOf<ServiceTypeSpecialist>()
    }

}