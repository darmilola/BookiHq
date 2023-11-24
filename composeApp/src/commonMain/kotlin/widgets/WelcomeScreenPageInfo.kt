package widgets

import AppTheme.AppColors
import AppTheme.AppBoldTypography
import AppTheme.AppSemiBoldTypography
import GGSansBold
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
import androidx.compose.ui.text.font.FontFamily
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

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        // AnimationEffect
        Box(modifier = modifier, contentAlignment = Alignment.CenterStart) {
            welcomeGradientBlock()
            welcomeScreenTextContent(textStyle = MaterialTheme.typography.h6, fontFamily = GGSansBold)

        }
    }
}

@Composable
fun welcomeScreenTextContent(textStyle: TextStyle, fontFamily: FontFamily? = null) {
    val columnModifier = Modifier
        .padding(start = 30.dp)
        .fillMaxWidth()
    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
        val modifier = Modifier
            .fillMaxWidth()
        TextComponent(
            textModifier = modifier, text = "Lorem ipsum dolor sit\namet consectetuer adipiscing\nAenean commodo ligula", fontSize = 23, fontFamily = fontFamily, textStyle = textStyle, textColor = Color.White, textAlign = TextAlign.Left,
            fontWeight = FontWeight.ExtraBold, lineHeight = 30)
        welcomeLineGradientBlock()
    }
}

@Composable
fun WelcomeScreenImageTextWidget(imageRes: String) {
    val  imageModifier =
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()

    val  boxModifier =
        Modifier
            .fillMaxHeight(0.80f)
            .fillMaxWidth()

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        // AnimationEffect
        Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
            ImageComponent(imageModifier = imageModifier, imageRes = imageRes)
            WelcomeScreenTextWidget()
        }
    }
}

@Composable
fun WelcomeScreenPagerContent(imageRes: String) {
    val  imageTextModifier =
        Modifier
            .fillMaxWidth()

    val  boxModifier =
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()

    MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
        // AnimationEffect
        Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
            val modifier = Modifier
                .padding(start = 30.dp, top = 20.dp, end = 10.dp, bottom = 20.dp)
                .fillMaxWidth()
                .height(270.dp)

            WelcomeScreenImageTextWidget(imageRes)
            TextComponent(
                textModifier = modifier, text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.  consectetuer adipiscing", fontSize = 16, textStyle = MaterialTheme.typography.subtitle2, textColor = Color(0xFF4D4C4C), textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal, lineHeight = 25)
        }
    }
}
