package presentation.main

import theme.styles.Colors
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import presentation.notification.NotificationTab
import presentation.viewmodels.MainViewModel
import presentation.widgets.TitleWidget
import presentations.components.ImageComponent

@Composable
fun MainTopBar(mainViewModel: MainViewModel, isBottomNavSelected: Boolean = false, onNotificationTabSelected:() -> Unit) {

    val userId = mainViewModel.userId.value
    val vendorId = mainViewModel.vendorId.value

    val topBarHeight = if (userId != -1L  && vendorId != -1L) 50 else 0
    Column(modifier = Modifier.fillMaxWidth().height(50.dp), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
           Box(
               modifier = Modifier
                   .padding(end = 0.dp)
                   .fillMaxHeight()
                   .background(color = Color.White)
           ) {
               centerTopBarItem(mainViewModel)
               rightTopBarItem(mainViewModel, isBottomNavSelected = isBottomNavSelected) {
                   onNotificationTabSelected()
               }
           }
       }
}


@Composable
fun leftTopBarItem(mainViewModel: MainViewModel) {
    val columnModifier = Modifier
        .background(color = Color.Transparent)
        .fillMaxWidth()
        .fillMaxHeight()

     Column(modifier = columnModifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
               WelcomeToProfile(mainViewModel)
           }
        }

@Composable
fun WelcomeToProfile(mainViewModel: MainViewModel) {
    val userInfo = mainViewModel.currentUserInfo.collectAsState()
    val vendorInfo = mainViewModel.connectedVendor.collectAsState()

    val rowModifier = Modifier
        .fillMaxWidth()
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {
                BusinessImage(businessImageUrl = vendorInfo.value.businessLogo!!) {}
                Box(
                    modifier = Modifier.wrapContentWidth().height(60.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.height(30.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TitleWidget(
                            textColor = Color.DarkGray,
                            title = "Welcome ",
                            fonSize = 16,
                            textModifier = Modifier.height(25.dp).padding(start = 5.dp).wrapContentWidth()
                        )
                        TitleWidget(
                            textColor = Colors.primaryColor,
                            title = userInfo.value.firstname!!,
                            fonSize = 16,
                            textModifier = Modifier.height(25.dp).wrapContentWidth()
                        )
                        TitleWidget(
                            textColor = Color.DarkGray,
                            title = ",",
                            fonSize = 16,
                            textModifier = Modifier.height(25.dp).wrapContentWidth()
                        )
                    }
                }
            }
    }


@Composable
fun BusinessImage(businessImageUrl: String, onBusinessImageClicked: () -> Unit) {
    Box(Modifier.size(40.dp), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .size(35.dp)
                .clip(CircleShape)
                .clickable {
                    onBusinessImageClicked()
                }
                .border(
                    width = (0.6).dp,
                    color = Colors.primaryColor,
                    shape = CircleShape
                )
                .background(color = Color.Transparent)
        ) {
            val modifier = Modifier
                .padding(2.dp)
                .clip(CircleShape)
                .border(
                    width = 0.2.dp,
                    color = Color.White,
                    shape = CircleShape)
                .fillMaxSize()
            ImageComponent(imageModifier = modifier, imageRes = businessImageUrl, isAsync = true)
        }
    }
}

@Composable
fun UserImage(userImageUrl: String, onUserImageClicked: () -> Unit) {
    Box(Modifier.size(40.dp), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .size(35.dp)
                .clip(CircleShape)
                .clickable {
                    onUserImageClicked()
                }
                .border(
                    width = (0.6).dp,
                    color = Colors.pinkColor,
                    shape = CircleShape
                )
                .background(color = Color.Transparent)
        ) {
            val modifier = Modifier
                .padding(2.dp)
                .clip(CircleShape)
                .border(
                    width = 0.2.dp,
                    color = Color.White,
                    shape = CircleShape)
                .fillMaxSize()
            ImageComponent(imageModifier = modifier, imageRes = userImageUrl, isAsync = true)
        }
    }
}



@Composable
fun rightTopBarItem(mainViewModel: MainViewModel, isBottomNavSelected: Boolean = false, onNotificationTabSelected:() -> Unit) {
    val userInfo = mainViewModel.currentUserInfo.collectAsState()
    val modifier = Modifier
        .padding(end = 10.dp)
        .background(color = Color.Transparent)
        .fillMaxWidth()
        .fillMaxHeight()

    Box(modifier = modifier,
        contentAlignment = Alignment.CenterEnd
        ) {
        UserImage(userImageUrl = userInfo.value.profileImageUrl!!, onUserImageClicked = {})
       }
    }

@Composable
fun centerTopBarItem(mainViewModel: MainViewModel) {
    val rowModifier = Modifier
        .background(color = Color.Transparent)
        .fillMaxWidth()
        .fillMaxHeight()

    val screenTitle =  mainViewModel.screenTitle.collectAsState()

    Box(modifier = rowModifier,
        contentAlignment = Alignment.Center
    ) {
        val isHomePage = screenTitle.value == "Home"
        AnimatedVisibility(
                visible = !isHomePage,
                enter = fadeIn(animationSpec = keyframes {
                    this.durationMillis = 0
                }),
                exit = fadeOut(animationSpec = keyframes {
                    this.durationMillis = 0
                })
            ) {
                TitleWidget(textColor = Colors.primaryColor, title = screenTitle.value)
            }
            AnimatedVisibility(
                visible = isHomePage,
                enter = fadeIn(animationSpec = keyframes {
                    this.durationMillis = 0
                }),
                exit = fadeOut(animationSpec = keyframes {
                    this.durationMillis = 0
                })
            ) {
                   leftTopBarItem(mainViewModel)
            }
        }
    }
