package presentation.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import presentations.components.ImageComponent
import theme.styles.Colors

@Composable
fun LiveVideoActionWidget() {
    val audioIcon = remember { mutableStateOf("drawable/mic_on.png") }
    val videoIcon = remember { mutableStateOf("drawable/video_icon.png") }
    val modifier =
        Modifier
            .padding(bottom = 5.dp, top = 5.dp, start = 10.dp)
            .height(130.dp)
            .fillMaxWidth()

    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {

        Box(modifier = Modifier.size(60.dp).clip(CircleShape).background(color = Colors.transparentGray), contentAlignment = Alignment.Center) {
            ImageComponent(imageModifier = Modifier
                .size(30.dp).clickable {
                    if (audioIcon.value == "drawable/mic_on.png"){
                        audioIcon.value = "drawable/mic_off.png"
                    }
                    else{
                        audioIcon.value = "drawable/mic_on.png"
                    }
                },
                imageRes = audioIcon.value,
                colorFilter = ColorFilter.tint(color = Color.White)
            )

          }
        Box(modifier = Modifier.width(120.dp), contentAlignment = Alignment.Center) {

            Box(
                modifier = Modifier.size(70.dp).clip(CircleShape).background(color = Color.Red),
                contentAlignment = Alignment.Center
            ) {
                ImageComponent(
                    imageModifier = Modifier
                        .size(30.dp).clickable {},
                    imageRes = "drawable/phone_call.png",
                    colorFilter = ColorFilter.tint(color = Color.White)
                )
            }
        }
        Box(modifier = Modifier.size(60.dp).clip(CircleShape).background(color = Colors.transparentGray), contentAlignment = Alignment.Center) {
            ImageComponent(imageModifier = Modifier
                .size(35.dp).clickable {
                    if (videoIcon.value == "drawable/video_icon.png"){
                        videoIcon.value = "drawable/no_video.png"
                    }
                    else{
                        videoIcon.value = "drawable/video_icon.png"
                    }
                },
                imageRes = videoIcon.value,
                colorFilter = ColorFilter.tint(color = Color.White)
            )
        }
    }
}