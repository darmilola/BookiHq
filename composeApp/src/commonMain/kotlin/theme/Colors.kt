package theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
public fun AppColors(): Colors {
    val lightThemeColors = lightColors(
        primary = Color(0xFF855446),
        primaryVariant = Color(0xFF9C684B),
        secondary = Color(0xFF03DAC5),
        secondaryVariant = Color(0xFF0AC9F0),
        background = Color(0xFFFBFBFB),
        surface = Color(0xFFFBFBFB),
        error = Color(0xFFB00020),
        onPrimary = Color(0xFFFBFBFB),
        onSecondary = Color(0xFFFBFBFB),
        onBackground = Color.Black,
        onSurface = Color.Black,
        onError = Color.White
    )

    val darkThemeColors = darkColors(
        primary = Color(0xFF1F1F1F),
        primaryVariant = Color(0xFF3E2723),
        secondary = Color(0xFF03DAC5),
        background = Color(0xFF121212),
        surface = Color.White,
        error = Color(0xFFCF6679),
        onPrimary = Color.White,
        onSecondary = Color.White,
        onBackground = Color.White,
        onSurface = Color.White,
        onError = Color.Black
    )

    return darkThemeColors

}