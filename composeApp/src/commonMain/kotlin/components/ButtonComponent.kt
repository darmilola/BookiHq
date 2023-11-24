package components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
public fun ButtonComponent(modifier: Modifier, buttonText: String, borderStroke: BorderStroke?, shape: Shape, colors: ButtonColors, textColor: Color, fontSize: Int, style: TextStyle) {
       Button(
             onClick = {

             },
             border = borderStroke,
             shape = shape,
             modifier = modifier,
             colors = colors,
             elevation =  ButtonDefaults.elevation(
               defaultElevation = 0.dp,
               pressedElevation = 0.dp,
               disabledElevation = 0.dp
           )
       ){
           TextComponent(
               text = buttonText, fontSize = fontSize, textStyle = style, textColor = textColor, textAlign = TextAlign.Center,
               fontWeight = FontWeight.SemiBold)
        }
}


@Composable
fun GradientButton(
    modifier: Modifier, buttonText: String, borderStroke: BorderStroke?, shape: Shape, colors: ButtonColors, textColor: Color, fontSize: Int, style: TextStyle, gradient : Brush
) {
    Button(
        onClick = {

        },
        colors = colors,
        elevation =  ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
    )) {
        Box(
            modifier = modifier
                .padding(start = 5.dp, end = 5.dp)
                .clip(shape)
                .background(gradient),
                contentAlignment = Alignment.Center,
        ) {
            TextComponent(
                text = buttonText, fontSize = fontSize, textStyle = style, textColor = textColor, textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold)
        }
    }
}