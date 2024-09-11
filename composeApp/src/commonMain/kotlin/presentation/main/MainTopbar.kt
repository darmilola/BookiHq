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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import domain.Enums.MainTabEnum
import domain.Enums.Screens
import presentation.viewmodels.MainViewModel
import presentation.widgets.TitleWidget
import presentations.components.ImageComponent

@Composable
fun MainTopBar(mainViewModel: MainViewModel) {
    val displayedTab = mainViewModel.displayedTab.collectAsState()
        Column(
            modifier = Modifier.fillMaxWidth().height(60.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(color = Color.White)
            ) {
                appLogoTitleItem(mainViewModel)
                mainTopBarItem(mainViewModel)
            }
        }
}


@Composable
fun appLogoItem(mainViewModel: MainViewModel) {
    val columnModifier = Modifier
        .background(color = Color.Transparent)
        .fillMaxWidth()
        .fillMaxHeight()

     Column(modifier = columnModifier, horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center) {
         appLogo()
           }
        }


@Composable
fun appLogo() {
    Box(Modifier.wrapContentSize(), contentAlignment = Alignment.CenterStart) {
            val modifier = Modifier
                .width(200.dp)
                .height(100.dp)
            ImageComponent(imageModifier = modifier, imageRes = "drawable/app_logo.png")
        }
    }


@Composable
fun VendorLogo(imageUrl: String, onVendorLogoClicked: () -> Unit) {
    Box(Modifier.size(50.dp), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .size(50.dp)
                .clip(CircleShape)
                .clickable {
                    onVendorLogoClicked()
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
                    width = 0.4.dp,
                    color = Color.White,
                    shape = CircleShape)
                .fillMaxSize()
            ImageComponent(imageModifier = modifier, imageRes = imageUrl, isAsync = true)
        }
    }
}



@Composable
fun mainTopBarItem(mainViewModel: MainViewModel) {
    val vendorInfo = mainViewModel.connectedVendor.value
    val displayedTab = mainViewModel.displayedTab.collectAsState()
    val modifier = Modifier
        .padding(end = 10.dp)
        .background(color = Color.Transparent)
        .fillMaxWidth()
        .fillMaxHeight()

    Box(modifier = modifier,
        contentAlignment = Alignment.CenterEnd
        ) {
        when (displayedTab.value) {
            MainTabEnum.HOME.toPath() -> {
                VendorLogo(imageUrl = vendorInfo.businessLogo!!, onVendorLogoClicked = {
                    mainViewModel.setScreenNav(Pair(Screens.MAIN_SCREEN.toPath(), Screens.CONNECTED_VENDOR_DETAILS.toPath()))
                })
            }
            MainTabEnum.PRODUCTS.toPath() -> {
                val iconModifier = Modifier
                    .padding(top = 5.dp)
                    .clickable {
                        mainViewModel.setIsClickedSearchProduct(true)
                    }
                    .size(24.dp)
                ImageComponent(imageModifier = iconModifier, imageRes = "drawable/search_icon.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
            }
            MainTabEnum.APPOINTMENT.toPath() -> {}
            MainTabEnum.MORE.toPath() -> {}

        }
    }

}

@Composable
fun appLogoTitleItem(mainViewModel: MainViewModel) {
    val rowModifier = Modifier
        .background(color = Color.Transparent)
        .fillMaxWidth()
        .fillMaxHeight()

    val screenTitle =  mainViewModel.screenTitle.collectAsState()
    val isHomePage = screenTitle.value == "Home"
    Box(modifier = rowModifier,
        contentAlignment = if (isHomePage) Alignment.CenterStart else Alignment.Center
    ) {

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
                   appLogoItem(mainViewModel)
            }
        }
    }
