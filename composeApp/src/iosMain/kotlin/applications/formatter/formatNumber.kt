package applications.formatter

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle

actual fun formatNumber(number: Long): String {
    val numberFormatter = NSNumberFormatter()
    numberFormatter.numberStyle = NSNumberFormatterDecimalStyle
    return numberFormatter.stringFromNumber(NSNumber(number)) ?: ""
}