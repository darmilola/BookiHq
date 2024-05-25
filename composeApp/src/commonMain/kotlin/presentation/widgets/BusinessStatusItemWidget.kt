package presentation.widgets

import GGSansRegular
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import applications.videoplayer.VideoPlayer
import presentations.components.ImageComponent
import presentations.components.TextComponent
import presentations.widgets.InputWidget
import theme.styles.Colors

class BusinessStatusItemWidget {
    @Composable
    fun getImageStatusWidget(imageUrl: String) {
        Column(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.70f)) {
                ImageComponent(
                    imageModifier = Modifier.fillMaxSize(),
                    imageRes = imageUrl,
                    contentScale = ContentScale.Crop,
                    isAsync = true
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                StatusText()
            }
            ReplyWidget(
                iconRes = "drawable/send_icon.png",
                placeholderText = "Reply",
                iconSize = 28
            ) {

            }
        }
    }


    @Composable
    fun getVideoStatusWidget(videoUrl: String) {
        Column(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.85f)) {
                // Video Playback
                VideoPlayer(modifier = Modifier.fillMaxSize(), url = videoUrl)
            }
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                StatusText()
            }
        }
    }


    @Composable
    fun StatusText() {
        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            TextComponent(
                textModifier = Modifier.fillMaxWidth().padding(top = 5.dp, bottom = 10.dp),
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et" +
                        " dolore magna aliqua. Ut enim ad minim",
                fontSize = 17, fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 23,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }

    }
}



