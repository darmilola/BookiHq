package presentation.viewmodels

import UIStates.AppUIStates
import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.Appointment
import domain.Models.AvailableTime
import domain.Models.PlatformTime
import domain.Models.UserAppointment
import domain.Models.VendorTime
import kotlinx.coroutines.flow.StateFlow
import presentation.dataModeller.CalendarDataSource

class PostponementViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _therapistBookedTimes = savedStateHandle.getStateFlow("therapistBookedAppointment", arrayListOf<Appointment>())
    private var _currentAppointment = savedStateHandle.getStateFlow("currentAppointment",UserAppointment())
    private var _platformTimes = savedStateHandle.getStateFlow("platformTimes", listOf<PlatformTime>())
    private var _vendorTimes = savedStateHandle.getStateFlow("vendorTimes", listOf<VendorTime>())
    private var _postponementViewUIState = savedStateHandle.getStateFlow("postponementViewUIState", AppUIStates())
    private var _day =  savedStateHandle.getStateFlow("day", -1)
    private var _month =  savedStateHandle.getStateFlow("month", -1)
    private var _monthName =  savedStateHandle.getStateFlow("monthName", "")
    private var _year =  savedStateHandle.getStateFlow("year", -1)
    private var _newSelectedTime = savedStateHandle.getStateFlow("newSelectedTime", PlatformTime())



    val therapistBookedTimes: StateFlow<List<Appointment>>
        get() = _therapistBookedTimes

    val currentAppointment: StateFlow<UserAppointment>
        get() = _currentAppointment

    val postponementViewUIState: StateFlow<AppUIStates>
        get() = _postponementViewUIState

    val platformTimes: StateFlow<List<PlatformTime>>
        get() = _platformTimes

    val vendorTimes: StateFlow<List<VendorTime>>
        get() = _vendorTimes

    val day: StateFlow<Int>
        get() = _day

    val month: StateFlow<Int>
        get() = _month

    val monthName: StateFlow<String>
        get() = _monthName

    val year: StateFlow<Int>
        get() = _year
    val selectedTime: StateFlow<PlatformTime>
        get() = _newSelectedTime
    fun setCurrentAppointment(userAppointment: UserAppointment) {
        savedStateHandle["currentAppointment"] = userAppointment
    }

    fun setTherapistBookedAppointment(bookedAppointment: List<Appointment>) {
        savedStateHandle["therapistBookedAppointment"] = bookedAppointment
    }

    fun setPostponementViewUIState(appUIStates: AppUIStates) {
        savedStateHandle["postponementViewUIState"] = appUIStates
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

    fun setMonthName(monthName: String) {
        savedStateHandle["monthName"] = monthName
    }


    fun clearPostponementSelection(){
        savedStateHandle["newSelectedTime"] = PlatformTime()
        savedStateHandle["therapistAvailableTimes"] = arrayListOf<AvailableTime>()
        savedStateHandle["day"] = CalendarDataSource().today.dayOfMonth
        savedStateHandle["month"] = CalendarDataSource().today.monthNumber
        savedStateHandle["year"] = CalendarDataSource().today.year

    }

    fun setNewSelectedTime(platformTime: PlatformTime) {
        savedStateHandle["newSelectedTime"] = platformTime
    }

    fun setPlatformTimes(platformTimes: List<PlatformTime>) {
        savedStateHandle["platformTimes"] = platformTimes
    }

    fun setVendorTimes(vendorTimes: List<VendorTime>) {
        savedStateHandle["vendorTimes"] = vendorTimes
    }

}