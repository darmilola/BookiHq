package widgets

import Styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ImageComponent

@Composable
fun PageBackNavWidget(onBackPressed: (() -> Unit)) {
    val boxModifier = Modifier
            .border(border = BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(15.dp))
            .background(color = Color.Transparent)
            .size(50.dp)
    Box(modifier = boxModifier,
            contentAlignment = Alignment.Center
        ) {
            ImageComponent(imageModifier = Modifier.size(25.dp).clickable {
                onBackPressed()
            }, imageRes = "drawable/nav_back_icon.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
        }
}