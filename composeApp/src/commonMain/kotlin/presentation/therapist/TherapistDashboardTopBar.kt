package presentation.therapist

import GGSansRegular
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import domain.Models.Screens
import kotlinx.coroutines.launch
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.widgets.PageBackNavWidget
import presentation.widgets.StepsProgressBar
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors

@Composable
fun TherapistDashboardTopBar(mainViewModel: MainViewModel) {

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
    TextComponent(
        text = "Your Dashboard",
        fontSize = 16,
        fontFamily = GGSansRegular,
        textStyle = MaterialTheme.typography.h6,
        textColor = Color.DarkGray,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
    )
}
