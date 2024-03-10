
package presentation.dataModeller
import applications.date.getDay
import applications.date.getMonth
import applications.date.getYear
import kotlinx.datetime.LocalDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus
import domain.Models.CalendarUiModel
import domain.Models.Date
import domain.Models.DateRange


class CalendarDataSource {



    val today: LocalDate = LocalDate(getYear(), getMonth(), getDay())
       

    fun getData(startDate: LocalDate = today, lastSelectedDate: LocalDate): CalendarUiModel {
        val endDayOfWeek = today.plus(30, DateTimeUnit.DAY)
        val visibleDates = getDatesBetween(today, endDayOfWeek)
        return toUiModel(visibleDates, lastSelectedDate)
    }

    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val dateList = mutableListOf<LocalDate>()
        val range = DateRange(startDate, endDate, 1)
            for (dates in range) {
                dateList.add(dates)
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

    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = Date(
        isSelected = isSelectedDate,
        isToday = date == today,
        date = date,
    )
}
