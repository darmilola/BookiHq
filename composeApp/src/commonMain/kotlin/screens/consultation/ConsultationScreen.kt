package screens.consultation

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ButtonComponent
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import screens.Bookings.BookingPayment
import screens.Bookings.BookingScreenTopBar
import screens.Bookings.BookingSelectServices
import screens.Bookings.BookingSelectSpecialist
import screens.main.MainViewModel


class ConsultationScreen(private val mainViewModel: MainViewModel) : Screen {

    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {

        val pagerState = rememberPagerState(pageCount = {
            3
        })


        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
            val topLayoutModifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color(0xFFF3F3F3))
            Column(modifier = topLayoutModifier) {

                ConsultationScreenTopBar(mainViewModel, pagerState)

                val bgStyle = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFF3F3F3))
                    .fillMaxHeight()
                MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {

                    Box(contentAlignment = Alignment.TopCenter, modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = Color.White)
                    ) {

                        Column(
                            modifier = bgStyle
                        ) {
                            AttachBookingPages(pagerState)
                            AttachActionButtons(pagerState)
                        }
                    }

                }

            }
        }


    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    @Composable
    fun AttachActionButtons(pagerState: PagerState){

        var btnFraction by remember { mutableStateOf(0f) }

        val currentPage = pagerState.currentPage

        btnFraction = if (pagerState.currentPage == 1){
            0.5f
        } else {
            0f
        }

        val bgStyle = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight()

        val coroutineScope = rememberCoroutineScope()

        val buttonStyle2 = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .height(50.dp)


        Row (modifier = bgStyle,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,) {
            ButtonComponent(modifier = buttonStyle2, buttonText = "Continue", colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFF43569)), fontSize = 18, shape = RoundedCornerShape(10.dp), textColor = Color(color = 0xFFFFFFFF), style = MaterialTheme.typography.h4, borderStroke = null){
                coroutineScope.launch {
                    if(currentPage == 0){
                        pagerState.animateScrollToPage(1)
                    }
                    else if(currentPage == 1){
                        pagerState.animateScrollToPage(2)
                    }
                }
            }
        }

    }


    @OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
    @Composable
    fun AttachBookingPages(pagerState: PagerState){

        val  boxModifier =
            Modifier
                .padding(top = 15.dp)
                .background(color = Color(0xFFF3F3F3))
                .fillMaxHeight(0.85f)
                .fillMaxWidth()

        // AnimationEffect
        Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> BookConsultation()
                    1 -> ConsultationDate()
                    2 -> ConsultationPayment()
                }
            }

        }

    }
}