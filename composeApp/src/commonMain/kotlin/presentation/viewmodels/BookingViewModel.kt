package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.ServiceCategoryItem
import domain.Models.ServiceTypeSpecialist
import domain.Models.Services
import domain.Models.Vendor
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

class BookingViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _specialists =  savedStateHandle.getStateFlow("specialists", arrayListOf<ServiceTypeSpecialist>())
    private var _selectedServiceType =  savedStateHandle.getStateFlow("selectedServiceType", ServiceCategoryItem())
    private var _selectedDate =  savedStateHandle.getStateFlow("selectedDate", LocalDate.parse("2024-03-30"))

    val serviceSpecialists: StateFlow<List<ServiceTypeSpecialist>>
        get() = _specialists
    val selectedServiceType: StateFlow<ServiceCategoryItem>
        get() = _selectedServiceType

    val selectedDate: StateFlow<LocalDate>
        get() = _selectedDate

    fun setSpecialists(serviceSpecialists: List<ServiceTypeSpecialist>) {
        savedStateHandle["specialists"] = serviceSpecialists
    }

    fun setSelectedServiceType(selectedServiceType: ServiceCategoryItem) {
        savedStateHandle["selectedServiceType"] = selectedServiceType
    }

    fun setSelectedDate(localDate: LocalDate) {
        savedStateHandle["selectedDate"] = localDate
    }

}