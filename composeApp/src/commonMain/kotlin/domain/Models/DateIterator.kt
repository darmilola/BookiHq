package domain.Models

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

class DateIterator(val startDate: LocalDate,
                   val endDateInclusive: LocalDate,
                   val stepDays: Long): Iterator<LocalDate> {
    private var currentDate = startDate

    override fun hasNext() = currentDate <= endDateInclusive

    override fun next(): LocalDate {

        val next = currentDate

        currentDate = currentDate.plus(stepDays, DateTimeUnit.DAY)

        return next

    }

    operator fun LocalDate.rangeTo(other: LocalDate) = DateRange(this, other)

}