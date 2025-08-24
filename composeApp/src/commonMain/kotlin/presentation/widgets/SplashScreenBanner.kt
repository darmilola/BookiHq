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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.Colors

@Composable
fun SplashScreenWidget() {
    val bgStyle = Modifier
        .fillMaxWidth()
        .fillMaxHeight()

    Column(modifier = bgStyle, verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
        attachCompanyLogo()
    }
}

@Composable
fun attachCompanyLogo() {
    val modifier = Modifier
        .size(400.dp)
    ImageComponent(imageModifier = modifier, imageRes = "drawable/bookilogo.png", ColorFilter.tint(color = Colors.darkPrimary), contentScale = ContentScale.Inside)
}