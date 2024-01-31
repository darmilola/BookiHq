package presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import components.ImageComponent

class BusinessStatusImageWidget() {
    @Composable
    fun GetStatusImage(imageRes: String) {
        val imageModifier =
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        Card(
            modifier = Modifier
                .padding(top = 10.dp)
                .background(color = Color.White)
                .height(250.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = null
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.BottomStart
            ) {
                ImageComponent(imageModifier = imageModifier, imageRes = imageRes, contentScale = ContentScale.Crop)
            }
        }
    }
}