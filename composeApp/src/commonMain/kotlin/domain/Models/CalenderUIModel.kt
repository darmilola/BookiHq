package domain.Models

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

data class CalendarUiModel(
    val selectedPlatformDate: PlatformDate, // the date selected by the User. by default is Today.
    val visiblePlatformDates: List<PlatformDate> // the dates shown on the screen
) {

    val startPlatformDate: PlatformDate = visiblePlatformDates.first() // the first of the visible dates
    val endPlatformDate: PlatformDate = visiblePlatformDates.last() // the last of the visible dates
}

data class PlatformDate(
    val date: LocalDate,
    val isSelected: Boolean
) {
    val day: DayOfWeek = date.dayOfWeek   // get the day by formatting the date
}