package presentation.main

import theme.styles.Colors
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import presentation.viewmodels.MainViewModel
import presentation.widgets.TitleWidget
import presentations.components.ImageComponent

@Composable
fun MainTopBar(mainViewModel: MainViewModel, isBottomNavSelected: Boolean = false, onNotificationTabSelected:() -> Unit) {

    val rowModifier = Modifier
        .padding(top = 55.dp, end = 0.dp)
        .height(45.dp)
        .background(color = Color.Transparent)

        Box(modifier = rowModifier) {
            centerTopBarItem(mainViewModel)
            rightTopBarItem(mainViewModel, isBottomNavSelected = isBottomNavSelected){
                onNotificationTabSelected()
            }
        }
}


@Composable
fun leftTopBarItem(currentScreen: Int = 0) {
    val rowModifier = Modifier
        .background(color = Color.Transparent)
        .fillMaxWidth()
        .fillMaxHeight()

    Box(modifier = rowModifier
    ) {
       WelcomeToProfile()
    }
}

@Composable
fun WelcomeToProfile(){
    val rowModifier = Modifier
        .padding(start = 10.dp, top = 5.dp)
        .fillMaxWidth()
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {
            val modifier = Modifier.padding(start = 5.dp)
            TitleWidget(textColor = Color.DarkGray, title = "Welcome ")
            TitleWidget(textColor = Colors.primaryColor, title = "Damilola")
            TitleWidget(textColor = Color.DarkGray, title = ",")
        }
    }

@Composable
fun rightTopBarItem(mainViewModel: MainViewModel, isBottomNavSelected: Boolean = false, onNotificationTabSelected:() -> Unit) {
    val tabNavigator = LocalTabNavigator.current
    var imageRes = ""

    imageRes = if(isBottomNavSelected){
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

    val screenTitle: State<String> =  mainViewModel.screenTitle.observeAsState()

    Box(modifier = rowModifier,
        contentAlignment = Alignment.Center
    ) {
       val shouldDisplay = screenTitle.value.contentEquals("Home")
            AnimatedVisibility(
                visible = !shouldDisplay,
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
                visible = shouldDisplay,
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
