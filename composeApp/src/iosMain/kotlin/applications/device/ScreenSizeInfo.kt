package applications.device

import androidx.compose.runtime.Composable
import domain.Models.ScreenSizeInfo

@Composable
actual fun ScreenSizeInfo(): ScreenSizeInfo {

    return ScreenSizeInfo(heightPx = 20, widthPx = 20)
}