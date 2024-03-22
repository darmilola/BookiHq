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
                    mainViewModel.setScreenNav(Pair(Screens.MAIN_TAB.toPath(), Screens.CONSULTATION.toPath()))
                })



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


    @Composable
    fun TherapistText() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 10.dp)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {

                TextComponent(
                    text = "Top Therapist",
                    fontSize = 32,
                    fontFamily = GGSansSemiBold,
                    textStyle = TextStyle(),
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 40
                )
            }

            AttachImageStacks()

        }

    }

    @Composable
    fun GradientLock() {
        Box(
            modifier = Modifier
                .width(200.dp)
                .padding(top = 10.dp)
                .height(3.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Colors.darkPrimary,
                            Colors.primaryColor,
                            Colors.lightPrimaryColor
                        )
                    )
                )
        ) {
        }
    }


 /*   @Composable
    fun AttachActionButtons() {
        val buttonStyle2 = Modifier
            .fillMaxWidth()
            .height(55.dp)
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            ButtonComponent(
                modifier = buttonStyle2,
                buttonText = "Get Started",
                borderStroke = BorderStroke(1.dp, Colors.primaryColor),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                fontSize = 18,
                shape = CircleShape,
                textColor = Colors.primaryColor,
                style = TextStyle()
            ) {
                mainViewModel.setScreenNav(Pair(Screens.MAIN_TAB.toPath(), Screens.CONSULTATION.toPath()))
            }
        }
    }*/



    @Composable
    fun ConsultTherapistImages(){
        Box(
           modifier =  Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ){
            Box(
                Modifier
                    .padding(start = 40.dp, bottom = 25.dp)
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(color = Color(0xFBFBFB), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.CenterStart
            ) {
                val modifier = Modifier
                    .rotate(-25f)
                    .width(120.dp)
                    .clip(shape = RoundedCornerShape(17.dp))
                    .height(130.dp)
                ImageComponent(imageModifier = modifier, imageRes = "drawable/therap1.jpg")
            }

            Box(
                Modifier
                    .padding(bottom = 80.dp)
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(color = Color(0xFBFBFB), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                val modifier = Modifier
                    .width(125.dp)
                    .clip(shape = RoundedCornerShape(17.dp))
                    .height(130.dp)
                ImageComponent(imageModifier = modifier, imageRes = "drawable/doctor.jpg")
            }

            Box(
                Modifier
                    .padding(end = 40.dp)
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(color = Color(0xFBFBFB), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.CenterEnd
            ) {
                val modifier = Modifier
                    .rotate(20f)
                    .width(120.dp)
                    .clip(shape = RoundedCornerShape(17.dp))
                    .height(130.dp)
                ImageComponent(imageModifier = modifier, imageRes = "drawable/1.jpg")
            }
            AttachCircle()
        }

    }
    @Composable
    fun AttachCircle() {
        Box(
            Modifier
                .graphicsLayer {
                    this.transformOrigin = TransformOrigin(0.5f, 0.5f)
                    this.rotationX = 105f
                    this.rotationY = 150f
                    this.rotationZ = 0f
                }
                .padding(start = 40.dp, end = 40.dp)
                .height(210.dp)
                .fillMaxWidth()
                .drawWithContent {
                    clipRect(top = 180f) {
                        this@drawWithContent.drawContent()
                    }
                }
        ) {
            Box(
                Modifier
                    .border(width = (0.5).dp, color = Colors.primaryColor, shape = RoundedCornerShape(50))
                    .height(210.dp)
                    .padding(start = 40.dp, end = 40.dp)
                    .fillMaxWidth()) {}
            }

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