package presentation.widgets

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


@Composable
fun SwitchVendorHeader(onBackPressed: () -> Unit){
    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        ConnectBusinessTitle(onBackPressed = {
            onBackPressed()
        })
    }
}

@Composable
fun ConnectBusinessTitle(onBackPressed: () -> Unit){
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
                leftTopBarItem(onBackPressed = {
                    onBackPressed()
                })
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
fun VendorDetailsTitle(onBackPressed: () -> Unit){
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
                VendorInfoTopBarItem(onBackPressed = {
                    onBackPressed()
                })
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
fun VendorInfoTopBarItem(onBackPressed: () -> Unit) {
    PageBackNavWidget {
        onBackPressed()
    }
}


@Composable
fun SwitchTitle(){
    TitleWidget(title = "Switch Parlor", textColor = Colors.primaryColor)
}
