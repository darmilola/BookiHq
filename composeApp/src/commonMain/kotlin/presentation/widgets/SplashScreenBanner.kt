package presentation.widgets


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashScreenWidget(textStyle: TextStyle) {
    val bgStyle = Modifier
        .fillMaxWidth()
        .fillMaxHeight()

    Column(modifier = bgStyle, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        attachCompanyLogo()
    }
}

@Composable
fun attachCompanyLogo() {
    val modifier = Modifier
        .size(250.dp)
    ImageComponent(imageModifier = modifier, imageRes = "0.png")
}

@Composable
fun attachCompanyName(textStyle: TextStyle) {
    val modifier = Modifier
        .fillMaxWidth()
    TextComponent(
        textModifier = modifier, text = "Company Name", fontSize = 40, textStyle = textStyle, textColor = Color.White, textAlign = TextAlign.Center,
        fontWeight = FontWeight.ExtraBold)
}
