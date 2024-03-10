package presentation.UserProfile.ConnectVendor

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
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import presentation.Bookings.BookingScreen
import presentation.Products.CartScreen
import presentation.main.MainTab
import presentation.viewmodels.MainViewModel
import presentation.widgets.PageBackNavWidget
import presentation.widgets.TitleWidget



@Composable
fun SwitchVendorHeader(mainViewModel: MainViewModel? = null){
    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
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
        .padding(top = 55.dp, end = 0.dp)
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
                leftTopBarItem(mainViewModel)
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
fun SwitchBusinessInfoTitle(mainViewModel: MainViewModel?){
    val rowModifier = Modifier
        .fillMaxWidth()
        .height(40.dp)

    val colModifier = Modifier
        .padding(top = 55.dp, end = 0.dp)
        .fillMaxWidth()
        .height(70.dp)

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
                InfoPageLeftTopBarItem(mainViewModel)
            }

            Box(modifier =  Modifier.weight(3.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                TitleWidget(title = "Details", textColor = Colors.primaryColor)

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
fun leftTopBarItem(mainViewModel: MainViewModel?) {
    val navigator = LocalTabNavigator.current
    PageBackNavWidget {
        if (mainViewModel != null){
            navigator.current = MainTab(mainViewModel)
        }
    }
}


@Composable
fun InfoPageLeftTopBarItem(mainViewModel: MainViewModel?) {
    val navigator = LocalTabNavigator.current
    PageBackNavWidget {
        if (mainViewModel != null && mainViewModel.fromId.value == 6){
            navigator.current = ConnectPageTab(mainViewModel)
        }
        else if (mainViewModel != null && mainViewModel.fromId.value == 1){
            navigator.current = BookingScreen(mainViewModel)
        }
        else if (mainViewModel != null && mainViewModel.fromId.value == 3){
            navigator.current = CartScreen(mainViewModel)
        }
    }
}


@Composable
fun SwitchTitle(){
    TitleWidget(title = "Switch Vendor", textColor = Colors.primaryColor)
}
