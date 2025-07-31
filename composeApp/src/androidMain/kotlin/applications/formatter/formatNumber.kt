package applications.formatter

import java.text.DecimalFormat

actual fun formatNumber(number: Long): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(number)
}