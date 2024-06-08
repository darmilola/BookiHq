package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.Appointment
import domain.Models.AvailableTime
import domain.Models.SpecialistReviews
import domain.Models.TimeOffs
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

class TherapistViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _therapistAvailableTimes = savedStateHandle.getStateFlow("therapistAvailableTimes", arrayListOf<AvailableTime>())
    private var _therapistTimeOffs = savedStateHandle.getStateFlow("therapistTimeOffs", arrayListOf<TimeOffs>())
    private var _therapistReviews = savedStateHandle.getStateFlow("therapistReviews", arrayListOf<SpecialistReviews>())
    private var _therapistAvailabilityViewUIState = savedStateHandle.getStateFlow("therapistAvailabilityViewUIState", ActionUIStates())
    private var _newSelectedTime = savedStateHandle.getStateFlow("newSelectedTime", AvailableTime())
    private var _addedTimeOffs = savedStateHandle.getStateFlow("addedTimeOffs", arrayListOf<AvailableTime>())
    private var _day =  savedStateHandle.getStateFlow("day", -1)
    private var _month =  savedStateHandle.getStateFlow("month", -1)
    private var _year =  savedStateHandle.getStateFlow("year", -1)


    val therapistAvailableTimes: StateFlow<List<AvailableTime>>
        get() = _therapistAvailableTimes


    val therapistTimeOffs: StateFlow<List<TimeOffs>>
        get() = _therapistTimeOffs

    val therapistReviews: StateFlow<List<SpecialistReviews>>
        get() = _therapistReviews

    val therapistAvailabilityViewUIState: StateFlow<ActionUIStates>
        get() = _therapistAvailabilityViewUIState

    val addedTimeOffs: StateFlow<ArrayList<AvailableTime>>
        get() = _addedTimeOffs

    val day: StateFlow<Int>
        get() = _day

    val month: StateFlow<Int>
        get() = _month

    val year: StateFlow<Int>
        get() = _year
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
    fun setTherapistAvailabilityViewUIState(actionUIStates: ActionUIStates) {
        savedStateHandle["therapistAvailabilityViewUIState"] = actionUIStates
    }

    fun setNewSelectedDate(localDate: LocalDate) {
        savedStateHandle["newSelectedDate"] = localDate
    }

    fun clearServiceTimes() {
        savedStateHandle["therapistAvailableTimes"] = arrayListOf<AvailableTime>()
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

    fun setNewSelectedTime(availableTime: AvailableTime) {
        savedStateHandle["newSelectedTime"] = availableTime
    }

}