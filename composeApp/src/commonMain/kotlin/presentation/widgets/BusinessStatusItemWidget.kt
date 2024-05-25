package presentation.widgets

import GGSansRegular
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.eygraber.compose.placeholder.PlaceholderHighlight
import com.eygraber.compose.placeholder.material3.fade
import com.eygraber.compose.placeholder.material3.placeholder
import domain.Models.VendorStatusModel
import domain.Models.VideoStatusViewMeta
import presentations.components.ImageComponent
import presentations.components.TextComponent
import presentations.widgets.InputWidget
import theme.styles.Colors

class BusinessStatusItemWidget {
    @Composable
    fun getImageStatusWidget(imageUrl: String, vendorStatusModel: VendorStatusModel, onStatusViewChanged: (Boolean) -> Unit) {

        val isStatusExpanded = remember { mutableStateOf(false) }
        val imageRes = if(isStatusExpanded.value) "drawable/collapse_icon.png"  else "drawable/expand_icon.png"

        Column(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(if(isStatusExpanded.value) 0.70f else 0.85f)) {
                ImageComponent(
                    imageModifier = Modifier.fillMaxSize(),
                    imageRes = imageUrl,
                    contentScale = ContentScale.Crop,
                    isAsync = true
                )
                Box(modifier = Modifier.fillMaxWidth().height(80.dp).padding(end = 10.dp, top = 10.dp), contentAlignment = Alignment.TopEnd) {
                    AttachExpandCollapseIcon(imageRes = imageRes) {
                        isStatusExpanded.value = !isStatusExpanded.value
                        onStatusViewChanged(isStatusExpanded.value)
                    }
                }
            }
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(if(isStatusExpanded.value) 0.5f else 1f).padding(top = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    StatusText()
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ReplyWidget(
                        iconRes = "drawable/send_icon.png",
                        placeholderText = "Reply",
                        iconSize = 28
                    ) {

                    }
                }

            }
        }
    }


    @Composable
    fun AttachExpandCollapseIcon(imageRes: String, onClick:() -> Unit) {
        Box(modifier = Modifier.size(40.dp).background(color = Color.White, shape = CircleShape), contentAlignment = Alignment.Center) {
            val modifier = Modifier
                .padding(top = 2.dp)
                .clickable {
                    onClick()
                }
                .size(20.dp)
            ImageComponent(imageModifier = modifier, imageRes = imageRes, colorFilter = ColorFilter.tint(color = Colors.darkPrimary))
        }
    }


    @Composable
    fun getVideoStatusWidget(videoUrl: String, vendorStatusModel: VendorStatusModel,videoStatusViewMeta: VideoStatusViewMeta, onStatusViewChanged: (Boolean) -> Unit) {

        val isStatusExpanded = remember { mutableStateOf(false) }
        val imageRes = if(isStatusExpanded.value) "drawable/collapse_icon.png"  else "drawable/expand_icon.png"

        Column(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(if(isStatusExpanded.value) 0.70f else 0.85f)) {
                // Video Playback
                VideoPlayer(modifier = Modifier.fillMaxSize(), url = videoUrl, videoStatusViewMeta = videoStatusViewMeta)
                Box(modifier = Modifier.fillMaxWidth().height(80.dp).padding(end = 10.dp, top = 10.dp), contentAlignment = Alignment.TopEnd) {
                    AttachExpandCollapseIcon(imageRes = imageRes) {
                        isStatusExpanded.value = !isStatusExpanded.value
                        onStatusViewChanged(isStatusExpanded.value)
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(if(isStatusExpanded.value) 0.5f else 1f).padding(top = 5.dp),
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
    fun StatusText() {
        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            TextComponent(
                textModifier = Modifier.wrapContentSize(),
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod",
                fontSize = 17, fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 23,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }

    }
}



