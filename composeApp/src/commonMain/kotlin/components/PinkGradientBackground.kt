package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.Dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
public class PinkGradientBackground {
    @Composable
   public fun ShowPinkGradientBackground() {
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
                            Color(color = 0xFFFA4375),
                            Color(color = 0xFFFE8467)
                        )
                    )
                )
        ) {
        }
    }
}