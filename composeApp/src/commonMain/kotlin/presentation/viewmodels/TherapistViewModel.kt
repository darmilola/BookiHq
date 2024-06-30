package presentation.viewmodels

import UIStates.ActionUIStates
import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.Appointment
import domain.Models.AvailableTime
import domain.Models.TherapistReviews
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

class TherapistViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _therapistAvailableTimes = savedStateHandle.getStateFlow("therapistAvailableTimes", arrayListOf<AvailableTime>())
    private var _therapistReviews = savedStateHandle.getStateFlow("therapistReviews", arrayListOf<TherapistReviews>())
    private var _therapistAvailabilityViewUIState = savedStateHandle.getStateFlow("therapistAvailabilityViewUIState", ActionUIStates())
    private var _newSelectedTime = savedStateHandle.getStateFlow("newSelectedTime", AvailableTime())
    private var _day =  savedStateHandle.getStateFlow("day", -1)
    private var _month =  savedStateHandle.getStateFlow("month", -1)
    private var _year =  savedStateHandle.getStateFlow("year", -1)


    val therapistAvailableTimes: StateFlow<List<AvailableTime>>
        get() = _therapistAvailableTimes

    val therapistReviews: StateFlow<List<TherapistReviews>>
        get() = _therapistReviews

    val therapistAvailabilityViewUIState: StateFlow<ActionUIStates>
        get() = _therapistAvailabilityViewUIState

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

    fun setTherapistReviews(therapistReviews: List<TherapistReviews>) {
        savedStateHandle["therapistReviews"] = therapistReviews
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