package utils

import androidx.compose.ui.input.key.Key.Companion.Calendar
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateTime {
    fun getFormattedReviewDate(
        iso8601Timestamp: String,
    ): String {
        val localDateTime = iso8601TimestampToLocalDateTime(iso8601Timestamp)
        val date = localDateTime.date
        val day = date.dayOfMonth
        val month = date.month.name
        val year = date.year

        return "${month.lowercase()} $day"
    }
    private fun iso8601TimestampToLocalDateTime(timestamp: String): LocalDateTime {
        return Instant.parse(timestamp).toLocalDateTime(TimeZone.currentSystemDefault())
    }
}