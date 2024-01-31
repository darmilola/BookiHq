package utils

expect object PlatformDateTime {
    fun getFormattedDate(
        timestamp: String,
    ): String

    fun getDay(): Int
    fun getMonth(): Int
    fun getYear(): Int
}