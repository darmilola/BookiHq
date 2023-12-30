package screens.consultation

import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansRegular
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ImageComponent
import components.TextComponent
import screens.Bookings.BookingScreen


object VirtualConsultationRoom : Screen {

    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        Box {
            var attendanceCount by remember { mutableStateOf(2) }
            var userWantsMinimizedView by remember { mutableStateOf(false) }
            ImageComponent(imageModifier = Modifier.fillMaxSize().blur(radius = 35.dp), imageRes = "drawable/smartphone.jpg")
            Scaffold(
                topBar = {
                },
                content = {

                    AttachVideoCallAttendanceView(attendanceCount = attendanceCount, userWantsMinimizedView = userWantsMinimizedView){
                        userWantsMinimizedView = true
                    }

                },
                floatingActionButton  = {
                    MinimizedCallAttendeeView(imageRes = "drawable/man.jpg", userWantsMinimizedView = userWantsMinimizedView, attendanceCount = attendanceCount){
                        userWantsMinimizedView = false
                    }
                },
                floatingActionButtonPosition = FabPosition.End,
                bottomBar = {
                    AttachActionButtons()
                },
                backgroundColor = Color.Transparent,

                )
        }

    }

    @Composable
    fun MinimizedCallAttendeeView(imageRes: String, userWantsMinimizedView: Boolean = false, attendanceCount: Int  = 0, onMaximizeClicked: () -> Unit){
      if (attendanceCount >= 2 && userWantsMinimizedView) {
          Box(
              modifier = Modifier.width(150.dp).height(200.dp)
                  .clickable {
                      onMaximizeClicked()
                  }
                  .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(10.dp))
          ) {
              ImageComponent(
                  imageModifier = Modifier
                      .height(200.dp).width(150.dp).padding(2.dp).clip(RoundedCornerShape(10.dp)),
                  imageRes = imageRes
              )
          }
      }
    }

    @Composable
    fun AttachVideoCallAttendanceView(userWantsMinimizedView: Boolean = false, attendanceCount: Int  = 0, onMinimizeClicked: () -> Unit){
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(bottom = 120.dp, top = 50.dp)) {

            if (attendanceCount == 1 || (attendanceCount == 2 && userWantsMinimizedView)){
                Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                    VideoCallAttendeeView(imageRes = "drawable/woman.jpg", isUserView = false){
                        onMinimizeClicked()
                    }
                }
            }
            else if (attendanceCount >= 2 && !userWantsMinimizedView){

                Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                    VideoCallAttendeeView("Esther", imageRes = "drawable/woman.jpg", isUserView = false, userWantsMinimizedView = userWantsMinimizedView){
                         onMinimizeClicked()
                    }
                }

               Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                VideoCallAttendeeView(imageRes = "drawable/man.jpg", isUserView = true){
                         onMinimizeClicked()
                }
              }

            }
        }
    }

    @Composable
    fun VideoCallAttendeeView(attendeeName: String = "", imageRes: String, isUserView: Boolean = false, userWantsMinimizedView: Boolean = false, onMinimizeClicked: () -> Unit){
        Box(modifier = Modifier.fillMaxWidth()) {
            ImageComponent(imageModifier = Modifier
                .fillMaxSize(), imageRes = imageRes)

        if(isUserView){
            Box(modifier = Modifier.fillMaxWidth().height(100.dp).padding(10.dp),
                contentAlignment = Alignment.TopEnd) {
                MinimizeActionImage(userWantsMinimizedView = userWantsMinimizedView){
                    onMinimizeClicked()
                }
            }
        }

            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                contentAlignment = Alignment.BottomStart) {
                TextComponent(
                    text = attendeeName,
                    fontSize = 20,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.White,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    lineHeight = 25,
                    textModifier = Modifier.wrapContentWidth().padding(all = 10.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }

    @Composable
    fun AttachActionButtons(){
        val navigator = LocalNavigator.currentOrThrow
        Row(modifier = Modifier.fillMaxWidth().height(120.dp).padding(bottom = 20.dp, start = 40.dp, end = 40.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center) {
                VideoCallActionWidget(iconRes = "drawable/mute_voice_icon.png")
            }

            Box(modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center) {
                VideoCallActionWidget(iconRes = "drawable/switch_camera_icon.png")
            }

            Box(modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center) {
                VideoCallEndActionWidget(iconRes = "drawable/end_call_icon.png"){
                    navigator.pop()
                }
            }

        }

     }


    @Composable
    fun VideoCallActionWidget(iconRes: String){
        val columnModifier = Modifier
            .wrapContentSize()
        MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
            Column(
                modifier = columnModifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment  = Alignment.CenterHorizontally,
            ) {
                val modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                AttachConsultActionImages(iconRes)
            }
        }
    }

    @Composable
    fun VideoCallEndActionWidget(iconRes: String, onEndCallClicked: () -> Unit){
        val columnModifier = Modifier
            .wrapContentSize()
            .clickable {
                onEndCallClicked()
            }
        MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
            Column(
                modifier = columnModifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment  = Alignment.CenterHorizontally,
            ) {
                val modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                AttachConsultActionEndImage(iconRes)
            }
        }
    }

    @Composable
    fun AttachConsultActionImages(iconRes: String) {
        Box(
            Modifier
                .clip(CircleShape)
                .size(70.dp)
                .background(color = Color(color = 0x20ffffff)),
            contentAlignment = Alignment.Center
        ) {
            val modifier = Modifier
                .size(35.dp)
            ImageComponent(imageModifier = modifier, imageRes = iconRes, colorFilter = ColorFilter.tint(color = Color.White))
        }
    }

    @Composable
    fun MinimizeActionImage(userWantsMinimizedView: Boolean = false, onMinimizeClicked: () -> Unit) {
        Box(
            Modifier
                .clip(CircleShape)
                .size(40.dp)
                .clickable {
                   onMinimizeClicked()
                }
                .background(color = Color(color = 0x40ffffff)),
            contentAlignment = Alignment.Center
        ) {
            val modifier = Modifier
                .size(20.dp)
            ImageComponent(imageModifier = modifier, imageRes = "drawable/minimize_icon.png", colorFilter = ColorFilter.tint(color = Color.White))
        }
    }

    @Composable
    fun AttachConsultActionEndImage(iconRes: String) {
        Box(
            Modifier
                .clip(CircleShape)
                .size(70.dp)
                .background(color = Color(color = 0xfffa2d65)),
            contentAlignment = Alignment.Center
        ) {
            val modifier = Modifier
                .size(40.dp)
            ImageComponent(imageModifier = modifier, imageRes = iconRes, colorFilter = ColorFilter.tint(color = Color.White))
        }
    }
}

