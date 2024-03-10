package applications.date

expect fun getFormattedDate(
        timestamp: String,
    ): String

expect   fun getDay(): Int
expect  fun getMonth(): Int
expect   fun getYear(): Int
