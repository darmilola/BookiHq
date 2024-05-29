package applications.date

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
actual fun getDay(): Int {
    val today: LocalDate = LocalDate.now()
    return today.dayOfMonth
}
@RequiresApi(Build.VERSION_CODES.O)
actual fun getMonth(): Int {
    val today: LocalDate = LocalDate.now()
    return  today.monthValue
}
@RequiresApi(Build.VERSION_CODES.O)
actual fun getYear(): Int {
    val today: LocalDate = LocalDate.now()
    return  today.year
}
actual fun getFormattedDate(timestamp: String): String {
    TODO("Not yet implemented")
}