package presentation.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.Enums.Screens
import presentation.viewmodels.MainViewModel
import presentation.widgets.PageBackNavWidget

@Composable
fun JoinASpaTopBar(onBackPressed: () -> Unit) {

    val rowModifier = Modifier
        .fillMaxWidth()
        .padding(top = 40.dp)
        .height(60.dp)
    Row(modifier = rowModifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically) {

        Box(modifier =  Modifier.weight(1.0f)
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 10.dp),
            contentAlignment = Alignment.CenterStart) {
            leftTopBarItem(onBackPressed = {
                onBackPressed()
            })
        }

        Box(modifier =  Modifier.weight(3.0f)
            .fillMaxWidth()
            .fillMaxHeight(),
            contentAlignment = Alignment.Center) {}

        Box(modifier =  Modifier.weight(1.0f)
            .fillMaxWidth(0.20f)
            .fillMaxHeight(),
            contentAlignment = Alignment.Center) {
        }

    }
}

@Composable
fun leftTopBarItem(onBackPressed: () -> Unit) {
    PageBackNavWidget(){
        onBackPressed()
    }
}