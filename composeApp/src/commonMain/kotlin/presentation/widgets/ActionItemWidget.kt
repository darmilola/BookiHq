package presentation.widgets

import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import presentations.components.ImageComponent
import presentations.components.TextComponent


@Composable
fun ActionItemComponent(modifier: Modifier, buttonText: String, colors: ButtonColors, textColor: Color, fontSize: Int, style: TextStyle, iconRes: String, onClick: (() -> Unit)? = null, isDestructiveAction: Boolean = false) {
    val rowModifier = Modifier
        .fillMaxWidth().wrapContentHeight()

    Button(
        onClick = {
            if (onClick != null) {
                onClick()
            }
        },
        modifier = modifier,
        colors = colors,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {

            val iconModifier = Modifier
                .padding(top = 5.dp)
                .size(24.dp)

            val iconTextBoxModifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.90f)

            val iconNavModifier = Modifier
                .padding(top = 5.dp)
                .fillMaxSize()

            val textModifier = Modifier
                .padding(end = 5.dp, top = 5.dp, start = 15.dp)
                .fillMaxHeight().wrapContentHeight()
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = iconTextBoxModifier
            ) {
                AttachItemImage(iconRes, isDestructiveAction, textColor)
                TextComponent(
                    text = buttonText,
                    fontSize = 15,
                    textStyle = style,
                    textColor = textColor,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textModifier = textModifier,
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = iconNavModifier
            ) {
                ImageComponent(imageModifier = iconModifier, imageRes = "drawable/chevron_right.png", colorFilter = ColorFilter.tint(color = textColor))
            }

        }
    }
}


@Composable
fun AttachItemImage(iconRes: String, isDestructiveAction: Boolean = false, textColor: Color) {
    Box(
        Modifier
            .size(25.dp),
        contentAlignment = Alignment.Center
    ) {
        val modifier = Modifier
            .size(16.dp)
        ImageComponent(imageModifier = modifier, imageRes = iconRes, colorFilter = ColorFilter.tint(color = textColor))
    }

}

