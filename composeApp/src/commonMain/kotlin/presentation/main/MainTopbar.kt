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

    val topBarHeight = if (userId != -1  && vendorId != -1) 50 else 0
    Column(modifier = Modifier.fillMaxWidth().height(topBarHeight.dp), verticalArrangement = Arrangement.Center,
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
fun leftTopBarItem() {
    val rowModifier = Modifier
        .background(color = Color.Transparent)
        .fillMaxWidth()
        .fillMaxHeight()

     Box(modifier = rowModifier, contentAlignment = Alignment.CenterStart) {
               WelcomeToProfile()
           }
        }

@Composable
fun WelcomeToProfile() {
    val rowModifier = Modifier
        .fillMaxWidth()
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {
                BusinessImage(businessImageUrl = "drawable/sample_logo_icon.png") {}
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
                            textModifier = Modifier.height(25.dp).wrapContentWidth()
                        )
                        TitleWidget(
                            textColor = Colors.primaryColor,
                            title = "Damilola",
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
            ImageComponent(imageModifier = modifier, imageRes = businessImageUrl, isAsync = false)
        }
    }
}



@Composable
fun rightTopBarItem(mainViewModel: MainViewModel, isBottomNavSelected: Boolean = false, onNotificationTabSelected:() -> Unit) {
    val tabNavigator = LocalTabNavigator.current
    val heightRatio = if (mainViewModel.userId.value != -1) 1f else 0f

    val imageRes = if(isBottomNavSelected){
        "drawable/bell_outline.png"
    }else{
        "drawable/bell_filled.png"
    }
    val imageTint: Color = if(isBottomNavSelected){
        Colors.darkPrimary
    }else{
        Colors.primaryColor
    }

    val modifier = Modifier
        .padding(end = 10.dp)
        .background(color = Color.Transparent)
        .fillMaxWidth()
        .fillMaxHeight()

    val indicatorModifier = Modifier
        .padding(start = 8.dp, bottom = 25.dp, end = 4.dp)
        .background(color = Color.Transparent)
        .size(14.dp)
        .background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(color = 0xFFFA2D65),
                    Color(color = 0xFFFA2D65)
                )
            ),
            shape = RoundedCornerShape(7.dp)
        )

    Box(modifier = modifier,
        contentAlignment = Alignment.CenterEnd
        ) {
            ImageComponent(imageModifier = Modifier.size(33.dp).clickable {
                tabNavigator.current = NotificationTab(mainViewModel)
                onNotificationTabSelected()
            }, imageRes = imageRes, colorFilter = ColorFilter.tint(color = imageTint))
        Box(modifier = indicatorModifier){}
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
                   leftTopBarItem()
            }
        }
    }
