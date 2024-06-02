package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.Appointment
import domain.Models.AvailableTime
import domain.Models.SpecialistReviews
import domain.Models.TimeOffs
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import presentation.dataModeller.CalendarDataSource

class TherapistViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _therapistAvailableTimes = savedStateHandle.getStateFlow("therapistAvailableTimes", arrayListOf<AvailableTime>())
    private var _therapistTimeOffs = savedStateHandle.getStateFlow("therapistTimeOffs", arrayListOf<TimeOffs>())
    private var _therapistReviews = savedStateHandle.getStateFlow("therapistReviews", arrayListOf<SpecialistReviews>())
    private var _therapistAvailabilityViewUIState = savedStateHandle.getStateFlow("therapistAvailabilityViewUIState", AsyncUIStates())
    private var _newSelectedDate = savedStateHandle.getStateFlow("newSelectedDate", CalendarDataSource().today)
    private var _newSelectedTime = savedStateHandle.getStateFlow("newSelectedTime", AvailableTime())
    private var _addedTimeOffs = savedStateHandle.getStateFlow("addedTimeOffs", arrayListOf<AvailableTime>())


    val therapistAvailableTimes: StateFlow<List<AvailableTime>>
        get() = _therapistAvailableTimes


    val therapistTimeOffs: StateFlow<List<TimeOffs>>
        get() = _therapistTimeOffs

    val therapistReviews: StateFlow<List<SpecialistReviews>>
        get() = _therapistReviews

    val therapistAvailabilityViewUIState: StateFlow<AsyncUIStates>
        get() = _therapistAvailabilityViewUIState

    val addedTimeOffs: StateFlow<ArrayList<AvailableTime>>
        get() = _addedTimeOffs

    val selectedDate: StateFlow<LocalDate>
        get() = _newSelectedDate

    val selectedTime: StateFlow<AvailableTime>
        get() = _newSelectedTime

    fun setTherapistAvailableTimes(availableTimes: List<AvailableTime>) {
        savedStateHandle["therapistAvailableTimes"] = availableTimes
    }

    fun setTherapistReviews(specialistReviews: List<SpecialistReviews>) {
        savedStateHandle["therapistReviews"] = specialistReviews
    }

    fun setAddedTimeOffs(availableTimes: List<AvailableTime>) {
        savedStateHandle["addedTimeOffs"] = availableTimes
    }


    fun setTherapistTimeOffs(timeOffs: List<TimeOffs>) {
        savedStateHandle["therapistTimeOffs"] = timeOffs
    }

    fun setCurrentAppointment(currentAppointment: Appointment) {
        savedStateHandle["currentAppointment"] = currentAppointment
    }
    fun setTherapistAvailabilityViewUIState(asyncUIStates: AsyncUIStates) {
        savedStateHandle["therapistAvailabilityViewUIState"] = asyncUIStates
    }

    fun setNewSelectedDate(localDate: LocalDate) {
        savedStateHandle["newSelectedDate"] = localDate
    }

    fun clearServiceTimes() {
        savedStateHandle["therapistAvailableTimes"] = arrayListOf<AvailableTime>()
    }

    fun clearAvailabilityViewSelection(){
        savedStateHandle["newSelectedTime"] = AvailableTime()
        savedStateHandle["therapistAvailableTimes"] = arrayListOf<AvailableTime>()
        savedStateHandle["newSelectedDate"] = CalendarDataSource().today
    }

    fun setNewSelectedTime(availableTime: AvailableTime) {
        savedStateHandle["newSelectedTime"] = availableTime
    }

}