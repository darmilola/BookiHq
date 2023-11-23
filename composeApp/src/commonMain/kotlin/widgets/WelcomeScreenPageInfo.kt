package widgets

import AppTheme.AppColors
import AppTheme.AppTypography
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.ImageComponent
import components.TextComponent
import components.welcomeGradientBlock
import components.welcomeLineGradientBlock
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun WelcomeScreenTextWidget() {
    val  modifier =
        Modifier
            .height(140.dp)
            .fillMaxWidth()

    MaterialTheme(colors = AppColors(), typography = AppTypography()) {
        // AnimationEffect
        Box(modifier = modifier, contentAlignment = Alignment.CenterStart) {
            welcomeGradientBlock()
            welcomeScreenTextContent(textStyle = MaterialTheme.typography.h3)

        }
    }
}

@Composable
fun welcomeScreenTextContent(textStyle: TextStyle) {
    val columnModifier = Modifier
        .padding(start = 30.dp)
        .fillMaxWidth()
    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
        val modifier = Modifier
            .fillMaxWidth()
        TextComponent(
            textModifier = modifier, text = "Unlock a world of\nbeauty and relaxation\nwith our business", fontSize = 25, textStyle = textStyle, textColor = Color.White, textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black)
        welcomeLineGradientBlock()
    }
}

@Composable
fun WelcomeScreenImageTextWidget() {
    val  imageModifier =
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()

    val  boxModifier =
        Modifier
            .fillMaxHeight(0.80f)
            .fillMaxWidth()

    MaterialTheme(colors = AppColors(), typography = AppTypography()) {
        // AnimationEffect
        Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
            ImageComponent(imageModifier = imageModifier, imageRes = "afro_woman.jpg")
            WelcomeScreenTextWidget()
        }
    }
}

@Composable
fun WelcomeScreenPagerContent() {
    val  imageTextModifier =
        Modifier
            .fillMaxWidth()

    val  boxModifier =
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()

    MaterialTheme(colors = AppColors(), typography = AppTypography()) {
        // AnimationEffect
        Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
            val modifier = Modifier
                .padding(start = 30.dp, top = 20.dp, end = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
                .height(270.dp)

            WelcomeScreenImageTextWidget()
            TextComponent(
                textModifier = modifier, text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.", fontSize = 18, textStyle = MaterialTheme.typography.h4, textColor = Color(0xFF4D4C4C), textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold)
        }
    }
}
