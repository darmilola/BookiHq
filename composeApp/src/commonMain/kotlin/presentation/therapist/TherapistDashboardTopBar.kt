package presentation.therapist

import androidx.compose.foundation.ExperimentalFoundationApi
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
import presentation.widgets.TitleWidget
import theme.styles.Colors

@Composable
fun TherapistDashboardTopBar(mainViewModel: MainViewModel) {

    val rowModifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp)
        .height(60.dp)
        Row(modifier = rowModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 10.dp),
                contentAlignment = Alignment.CenterStart) {
                leftTopBarItem(mainViewModel)
            }

            Box(modifier =  Modifier.weight(3.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                DashboardTitle()
            }

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth(0.20f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
            }

        }
    }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun leftTopBarItem(mainViewModel: MainViewModel) {
    PageBackNavWidget(){
           mainViewModel.setScreenNav(Pair(Screens.THERAPIST_DASHBOARD.toPath(), Screens.MAIN_TAB.toPath()))
                 }
            }

@Composable
fun DashboardTitle(){
    TitleWidget(textColor = Colors.primaryColor, title = "Your Dashboard")
}
