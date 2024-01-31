package UIStates

import GGSansBold
import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import components.ButtonComponent
import components.ImageComponent
import components.TextComponent

@Composable
fun UIStateViewComponent(/*modifier: Modifier, buttonText: String, colors: ButtonColors, textColor: Color, fontSize: Int, style: TextStyle, iconRes: String, onClick: (() -> Unit)? = null, isDestructiveAction: Boolean = false*/) {
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally) {

          AttachUIImage("drawable/error.png")

          TextComponent(
            text = "Network Not Available",
            fontSize = 23,
            fontFamily = GGSansBold,
            textStyle = TextStyle(),
            textColor = Colors.primaryColor,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 30,
            textModifier = Modifier.fillMaxWidth().padding(top = 30.dp)
        )

        TextComponent(
            text = "Seems there is network issue, you can check your internet connection and retry",
            fontSize = 17,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Color.Gray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            lineHeight = 25,
            textModifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, top = 30.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        RetryButton()

    }

}

@Composable
fun RetryButton(){
    val buttonStyle = Modifier
        .padding(top = 25.dp)
        .fillMaxWidth(0.50f)
        .background(color = Color.Transparent)
        .height(50.dp)

    ButtonComponent(modifier = buttonStyle, buttonText = "Retry", borderStroke = BorderStroke((1.5).dp, color = Colors.primaryColor), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 20, shape = RoundedCornerShape(25.dp), textColor =  Colors.primaryColor, style = TextStyle()){}

}


@Composable
fun AttachUIImage(iconRes: String) {
    val imageTintColor =  Colors.darkPrimary
    val modifier = Modifier
            .size(150.dp)
        ImageComponent(imageModifier = modifier, imageRes = iconRes, colorFilter = ColorFilter.tint(color = imageTintColor))
    }


