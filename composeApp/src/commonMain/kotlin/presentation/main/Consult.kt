package presentation.main

import GGSansBold
import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.Models.RecommendationType
import domain.Models.Screens
import domain.Models.VendorRecommendation
import presentation.components.ButtonComponent
import presentation.viewmodels.MainViewModel
import presentation.widgets.AttachImageStacks
import presentation.widgets.ConsultationSchedule
import presentation.widgets.ConsultationWidget
import presentation.widgets.RecommendedServiceItem
import presentations.components.ImageComponent
import presentations.components.TextComponent

class ConsultTab(private val mainViewModel: MainViewModel) : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Consult"
            val icon = painterResource("drawable/video_icon.png")

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon

                )
            }
        }

    @Composable
    override fun Content() {
        val columnModifier = Modifier
            .padding(start = 20.dp, top = 25.dp)
            .fillMaxHeight()
            .fillMaxWidth()

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = columnModifier
        ) {
            ConsultDescYour()
            ConsultDescSchedule()
            ScheduledConsultationList()
            Sessions()
            ConsultationSessionList(onCreateSessionClick = {
                mainViewModel.setScreenNav(
                    Pair(
                        Screens.MAIN_TAB.toPath(),
                        Screens.CONSULTATION.toPath()
                    )
                )
            })


        }
    }
}


    @Composable
    fun ConsultDescYour() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            TextComponent(
                text = "Your",
                fontSize = 35,
                fontFamily = GGSansSemiBold,
                textStyle = TextStyle(),
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Medium,
                lineHeight = 45,
                letterSpacing = 1,
                textModifier = Modifier
                    .fillMaxWidth())

        }

    }

    @Composable
    fun Sessions() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            TextComponent(
                text = "Available Sessions",
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                textStyle = TextStyle(),
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 45,
                textModifier = Modifier
                    .fillMaxWidth())

        }

    }

    @Composable
    fun ConsultDescSchedule() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            TextComponent(
                text = "Schedule",
                fontSize = 35,
                fontFamily = GGSansSemiBold,
                textStyle = TextStyle(),
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Black,
                lineHeight = 45,
                letterSpacing = 1,
                textModifier = Modifier
                    .fillMaxWidth())

        }

    }


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScheduledConsultationList() {
    val pagerState = rememberPagerState(pageCount = { 3 })

    val boxModifier =
        Modifier
            .background(color = Color.White)
            .height(280.dp)
            .fillMaxWidth()

        Box(contentAlignment = Alignment.CenterStart, modifier = boxModifier) {
            HorizontalPager(
                state = pagerState,
                pageSize = PageSize.Fixed(250.dp),
                modifier = Modifier.fillMaxSize(),
                pageSpacing = 10.dp
            ) { page ->
                ConsultationSchedule()
            }

        }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConsultationSessionList(onCreateSessionClick:() -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 3 })

    val boxModifier =
        Modifier
            .background(color = Color.White)
            .height(280.dp)
            .fillMaxWidth()

    Box(contentAlignment = Alignment.CenterStart, modifier = boxModifier) {
        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fixed(350.dp),
            modifier = Modifier.fillMaxSize(),
            pageSpacing = 10.dp
        ) { page ->
            ConsultationWidget(onCreateSessionClick = {
                onCreateSessionClick()
            })
        }

    }
}
