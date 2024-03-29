package presentation.components

import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun SplashScreenBackground() {
   val bgStyle = Modifier
            .fillMaxWidth()
            .fillMaxHeight()

        Row(verticalAlignment = Alignment.CenterVertically, modifier = bgStyle) {
            gradientBlock()
        }
    }

    @Composable
    fun gradientBlock() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            Color.White
                        )
                    )
                )
        ) {
        }
    }
