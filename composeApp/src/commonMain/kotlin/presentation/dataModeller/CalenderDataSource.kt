
package presentation.dataModeller
import kotlinx.datetime.LocalDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus
import domain.Models.CalendarUiModel
import domain.Models.PlatformDate
import domain.Models.DateRange
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


class CalendarDataSource {

    private val currentMoment: Instant = Clock.System.now()
    val today: LocalDate = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault()).date

    fun getDate(startDate: LocalDate = today, lastSelectedDate: LocalDate): CalendarUiModel {
        val endDayOfWeek = startDate.plus(30, DateTimeUnit.DAY)
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
            selectedPlatformDate = toItemUiModel(lastSelectedDate, false),
            visiblePlatformDates = dateList.map {
                toItemUiModel(it, false)
            },
        )
    }

    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = PlatformDate(
        isSelected = isSelectedDate,
        date = date
    )
}
