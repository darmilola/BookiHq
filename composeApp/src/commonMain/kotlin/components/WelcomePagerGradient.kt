package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun WelcomePagerGradient() {
    val bgStyle = Modifier
        .padding(bottom = 200.dp)
        .fillMaxWidth()
        .height(200.dp)

    Row(verticalAlignment = Alignment.CenterVertically, modifier = bgStyle) {
        welcomeGradientBlock()
    }
}

@Composable
fun welcomeGradientBlock() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(color = 0x4D000000),
                        Color(color = 0x66000000),
                        Color(color = 0x8C000000),
                        Color(color = 0xff000000)
                    )
                )
            )
    ) {
    }
}


@Composable
fun welcomeLineGradientBlock() {
    Box(
        modifier = Modifier
            .padding(top = 7.dp)
            .width(200.dp)
            .height(3.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(color = 0xFFF43569),
                        Color(color = 0xFFFF823E)
                    )
                )
            )
    ) {
    }
}

@Composable
fun StraightLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = Color.LightGray)
    ) {
    }
}
