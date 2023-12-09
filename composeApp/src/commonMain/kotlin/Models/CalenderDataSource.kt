
package Models
import kotlinx.datetime.LocalDate
import Models.DateRange
import PlatformDateTime
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.plus


class CalendarDataSource {


    private val dateTime = PlatformDateTime
    val today: LocalDate = LocalDate(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay())
       

    fun getData(startDate: LocalDate = today, lastSelectedDate: LocalDate): CalendarUiModel {
        val endDayOfWeek = today.plus(30, DateTimeUnit.DAY)
        val visibleDates = getDatesBetween(today, endDayOfWeek)
        return toUiModel(visibleDates, lastSelectedDate)
    }

    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val dateList = mutableListOf<LocalDate>()
        val range = DateRange(startDate, endDate, 1)
            for (cc in range) {
                dateList.add(cc)
            }
        return dateList
    }

    private fun toUiModel(
        dateList: List<LocalDate>,
        lastSelectedDate: LocalDate
    ): CalendarUiModel {
        return CalendarUiModel(
            selectedDate = toItemUiModel(lastSelectedDate, true),
            visibleDates = dateList.map {
                toItemUiModel(it, it == lastSelectedDate)
            },
        )
    }

    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = CalendarUiModel.Date(
        isSelected = isSelectedDate,
        isToday = date == today,
        date = date,
    )
}
