import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Typeface
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.Image
import org.jetbrains.skia.Typeface


actual val GGSansBold: FontFamily = FontFamily(
    Typeface(loadCustomFont("ggsans_bold"))
)

actual val GGSansSemiBold: FontFamily = FontFamily(
    Typeface(loadCustomFont("ggsans_semibold"))
)

actual val GGSansRegular: FontFamily = FontFamily(
    Typeface(loadCustomFont("ggsans_regular"))
)


private fun loadCustomFont(name: String): Typeface {
    return Typeface.makeFromName(name, FontStyle.NORMAL)
}