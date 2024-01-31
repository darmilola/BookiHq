package presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import presentation.components.ImageComponent
import presentation.components.TextComponent
class BusinessStatusItemWidget {
    @Composable
    fun GetStatusWidget(imageRes: String) {
        val imageModifier =
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        Card(
            modifier = Modifier
                .padding(top = 10.dp)
                .background(color = Color.White)
                .fillMaxSize(),
            shape = RoundedCornerShape(8.dp),
            border = null
        ) {
            Box(
                modifier = Modifier
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
                    TextGradientBlock()
                }

            }
        }
    }

    @Composable
    fun TextGradientBlock() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(color = 0x00000000),
                            Color(color = 0x50000000),
                            Color(color = 0x90000000),
                            Color.Black

                        )
                    )
                )
        ) {
            TextComponent(
                textModifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .padding(top = 10.dp, bottom = 20.dp, start = 15.dp, end = 15.dp),
                text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
                fontSize = 16,
                textStyle = TextStyle(),
                textColor = Color.White,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
                maxLines = 3,
                lineHeight = 20
            )
        }
    }

}

