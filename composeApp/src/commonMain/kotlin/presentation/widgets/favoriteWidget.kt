package presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import presentations.components.ImageComponent
import theme.styles.Colors

@Composable
fun FavoriteProductWidget(iconRes: String = "drawable/fav_icon.png", onFavClicked:(Boolean) -> Unit) {
    val imageBgColor = Color.White
    val imageTintColor = Colors.primaryColor
    Box(
        Modifier
            .clip(CircleShape)
            .size(40.dp)
            .background(color = imageBgColor),
        contentAlignment = Alignment.Center
    ) {
        val modifier = Modifier
            .clickable {
                if (iconRes == "drawable/fav_icon.png") {
                    onFavClicked(true)
                }
                else{
                    onFavClicked(false)
                }
            }
            .size(24.dp)
        ImageComponent(imageModifier = modifier, imageRes = iconRes, colorFilter = ColorFilter.tint(color = imageTintColor))
    }
}