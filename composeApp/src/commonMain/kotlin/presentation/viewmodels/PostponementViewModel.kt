package presentation.viewmodels

import UIStates.ActionUIStates
import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.Appointment
import domain.Models.AvailableTime
import kotlinx.coroutines.flow.StateFlow
import presentation.dataModeller.CalendarDataSource

class PostponementViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _therapistAvailableTimes = savedStateHandle.getStateFlow("therapistAvailableTimes", arrayListOf<AvailableTime>())
    private var _therapistBookedTimes = savedStateHandle.getStateFlow("therapistBookedAppointment", arrayListOf<Appointment>())
    private var _currentAppointment = savedStateHandle.getStateFlow("currentAppointment",Appointment())
    private var _postponementViewUIState = savedStateHandle.getStateFlow("postponementViewUIState", ActionUIStates())
    private var _day =  savedStateHandle.getStateFlow("day", -1)
    private var _month =  savedStateHandle.getStateFlow("month", -1)
    private var _year =  savedStateHandle.getStateFlow("year", -1)
    private var _newSelectedTime = savedStateHandle.getStateFlow("newSelectedTime", AvailableTime())


    val therapistAvailableTimes: StateFlow<List<AvailableTime>>
        get() = _therapistAvailableTimes

    val therapistBookedTimes: StateFlow<List<Appointment>>
        get() = _therapistBookedTimes

    val currentAppointment: StateFlow<Appointment>
        get() = _currentAppointment

    val postponementViewUIState: StateFlow<ActionUIStates>
        get() = _postponementViewUIState

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

    fun setCurrentAppointment(currentAppointment: Appointment) {
        savedStateHandle["currentAppointment"] = currentAppointment
    }

    fun setTherapistBookedAppointment(bookedAppointment: List<Appointment>) {
        savedStateHandle["therapistBookedAppointment"] = bookedAppointment
    }

    fun setPostponementViewUIState(actionUIStates: ActionUIStates) {
        savedStateHandle["postponementViewUIState"] = actionUIStates
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


    fun clearPostponementSelection(){
        savedStateHandle["newSelectedTime"] = AvailableTime()
        savedStateHandle["therapistAvailableTimes"] = arrayListOf<AvailableTime>()
        savedStateHandle["day"] = CalendarDataSource().today.dayOfMonth
        savedStateHandle["month"] = CalendarDataSource().today.monthNumber
        savedStateHandle["year"] = CalendarDataSource().today.year

    }

    fun setNewSelectedTime(availableTime: AvailableTime) {
        savedStateHandle["newSelectedTime"] = availableTime
    }

}