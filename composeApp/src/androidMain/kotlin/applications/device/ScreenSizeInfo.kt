package applications.device

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import domain.Models.ScreenSizeInfo

@Composable
actual fun ScreenSizeInfo(): ScreenSizeInfo{
    val density = LocalDensity.current
    val config = LocalConfiguration.current
    val heightDp = config.screenHeightDp.dp
    val widthDp = config.screenWidthDp.dp

    return remember(density, config) {
        ScreenSizeInfo(
            heightPx = with(density) { heightDp.roundToPx() },
            widthPx = with(density) { widthDp.roundToPx() })
    }

}