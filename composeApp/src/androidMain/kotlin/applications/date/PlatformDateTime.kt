package applications.date

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale
import java.util.TimeZone

/*
actual object PlatformDateTime {

    actual
    fun getFormattedDate(
        timestamp: String,
    ): String {
        val timestampFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val outputFormat = "MMM dd, yyyy HH:mm:ss"

        val dateFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
        dateFormatter.timeZone = TimeZone.getTimeZone("GMT")

        val parser = SimpleDateFormat(timestampFormat, Locale.getDefault())
        parser.timeZone = TimeZone.getTimeZone("GMT")

        try {
            val date = parser.parse(timestamp)
            if (date != null) {
                dateFormatter.timeZone = TimeZone.getDefault()
                return dateFormatter.format(date)
            }
        } catch (e: Exception) {
            // Handle parsing error
            e.printStackTrace()
        }

        // If parsing fails, return the original timestamp
        return timestamp
    }
    @RequiresApi(Build.VERSION_CODES.O)
    actual
    fun getDay(): Int {
        val today: LocalDate = LocalDate.now()
        return today.getDayOfMonth()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    actual
    fun getMonth(): Int {
        val today: LocalDate = LocalDate.now()
        return  today.getMonthValue()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    actual
    fun getYear(): Int {
        val today: LocalDate = LocalDate.now()
        return  today.getYear()
    }
}*/
