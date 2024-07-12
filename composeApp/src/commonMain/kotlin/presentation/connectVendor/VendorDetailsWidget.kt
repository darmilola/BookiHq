package presentation.connectVendor

import theme.styles.Colors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.Enums.Screens
import presentation.viewmodels.MainViewModel
import presentation.widgets.PageBackNavWidget
import presentation.widgets.TitleWidget



@Composable
fun ConnectVendorHeader(mainViewModel: MainViewModel? = null){
    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        ConnectBusinessTitle(mainViewModel)
        ConnectBusinessDescription()
    }
}

@Composable
fun ConnectBusinessTitle(mainViewModel: MainViewModel?){
    val rowModifier = Modifier
        .fillMaxWidth()
        .height(40.dp)

    val colModifier = Modifier
        .padding(top = 20.dp, end = 0.dp)
        .fillMaxWidth()
        .height(40.dp)

    Column(modifier = colModifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = rowModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart) {
                leftTopBarItem(mainViewModel!!)
            }

            Box(modifier =  Modifier.weight(3.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                SwitchTitle()
            }

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth(0.20f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
            }
        }
    }
}

@Composable
fun BusinessInfoTitle(mainViewModel: MainViewModel?){
    val rowModifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp)
        .height(40.dp)

        Row(modifier = rowModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart) {
                InfoPageLeftTopBarItem(mainViewModel)
            }

            Box(modifier =  Modifier.weight(3.0f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                TitleWidget(title = "Vendor Details", textColor = Colors.primaryColor)

            }

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
            }
        }
}


@Composable
fun leftTopBarItem(mainViewModel: MainViewModel) {
    PageBackNavWidget {
        mainViewModel.setScreenNav(Pair(Screens.CONNECT_VENDOR_TAB.toPath(), Screens.MAIN_TAB.toPath()))
    }
}


@Composable
fun InfoPageLeftTopBarItem(mainViewModel: MainViewModel?) {
    PageBackNavWidget {
        when (mainViewModel?.screenNav?.value?.first) {
            Screens.MAIN_TAB.toPath() -> {
                mainViewModel.setScreenNav(Pair(Screens.VENDOR_INFO.toPath(), Screens.MAIN_TAB.toPath()))
            }
            Screens.BOOKING.toPath() -> {
                mainViewModel.setScreenNav(Pair(Screens.VENDOR_INFO.toPath(), Screens.BOOKING.toPath()))
            }
            Screens.CART.toPath() -> {
                mainViewModel.setScreenNav(Pair(Screens.VENDOR_INFO.toPath(), Screens.CART.toPath()))
            }
            Screens.JOIN_SPA.toPath() -> {
                mainViewModel.setScreenNav(Pair(Screens.VENDOR_INFO.toPath(), Screens.JOIN_SPA.toPath()))
            }
            Screens.CONNECT_VENDOR_TAB.toPath() -> {
                mainViewModel.setScreenNav(Pair(Screens.VENDOR_INFO.toPath(), Screens.CONNECT_VENDOR_TAB.toPath()))
            }
            else -> {
                // navigator.current = MainTab(mainViewModel)
            }
        }
    }
}


@Composable
fun SwitchTitle(){
    TitleWidget(title = "Switch Vendor", textColor = Colors.primaryColor)
}
