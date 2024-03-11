package presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import presentations.components.ImageComponent
import presentations.components.TextComponent

class BusinessStatusItemWidget {
    @Composable
    fun GetStatusWidget(imageRes: String) {
        val imageModifier =
            Modifier.aspectRatio(ratio = 1f).fillMaxWidth()
            Box(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.BottomStart
            ) {
                ImageComponent(
                    imageModifier = imageModifier,
                    imageRes = imageRes,
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        .background(color = Color.Transparent).padding(top = 100.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    //TextGradientBlock()
                }

            }
        }




}

