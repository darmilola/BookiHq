package presentation.consultation

import theme.styles.Colors
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import presentation.components.ButtonComponent
import kotlinx.coroutines.launch
import presentation.viewmodels.MainViewModel


class ConsultationScreen(private val mainViewModel: MainViewModel) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Consultation"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title
                )
            }
        }
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {

        val pagerState = rememberPagerState(pageCount = {
            3
        })

            val layoutModifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.White)
            Column(modifier = layoutModifier) {

                ConsultationScreenTopBar(pagerState, mainViewModel)

                val bgStyle = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .fillMaxHeight()

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

    @OptIn(ExperimentalFoundationApi::class)
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
            ButtonComponent(modifier = buttonStyle2, buttonText = "Continue", colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor), fontSize = 18, shape = RoundedCornerShape(10.dp), textColor = Color(color = 0xFFFFFFFF), style = TextStyle(), borderStroke = null){
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


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun AttachBookingPages(pagerState: PagerState){

        val  boxModifier =
            Modifier
                .background(color = Color.White)
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