package presentation.widgets

import GGSansRegular
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import chaintech.videoplayer.model.PlayerConfig
import chaintech.videoplayer.ui.VideoPlayerView
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors

class BusinessStatusItemWidget {
    @Composable
    fun getImageStatusWidget(imageUrl: String) {
              Column (
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center
            ) {
              Box(modifier = Modifier.fillMaxSize()) {
                  ImageComponent(
                      imageModifier = Modifier.fillMaxSize(),
                      imageRes = imageUrl,
                      contentScale = ContentScale.Crop,
                      isAsync = true
                  )
                  Box(
                      modifier = Modifier
                          .padding(10.dp)
                          .size(50.dp).background(color = Color.White, shape = CircleShape),
                      contentAlignment = Alignment.Center
                  ) {
                      ImageComponent(imageModifier = Modifier.size(28.dp), imageRes = "drawable/chat_icon.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
                  }
                  Box(modifier = Modifier
                              .fillMaxWidth()
                              .fillMaxHeight(),
                      contentAlignment = Alignment.BottomCenter
                  ) {

                 Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()
                          .background(color = Color(0x90ffffff)),
                      contentAlignment = Alignment.BottomCenter
                  ) {
                      StatusText()
                  }
                 }

              }
           }
        }


    @Composable
    fun getVideoStatusWidget(videoUrl: String) {
        Column (
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Video Playback
                VideoPlayerView(
                    modifier = Modifier.fillMaxSize(),
                    url = videoUrl,
                    playerConfig = PlayerConfig(showDuration = false, autoHideControl = true)
                )
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(50.dp).background(color = Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    ImageComponent(imageModifier = Modifier.size(28.dp), imageRes = "drawable/chat_icon.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        .background(color = Color(0x90ffffff)),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        StatusText()
                    }
                }

            }
        }
    }



    @Composable
    fun StatusText() {
        Box(
            modifier = Modifier
                .padding(top = 15.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.BottomCenter
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
                overflow = TextOverflow.Ellipsis)

        }

    }

}

