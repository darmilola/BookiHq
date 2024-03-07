package presentation.consultation

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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import presentation.widgets.Calendar
import presentation.widgets.ReviewsWidget
import presentation.widgets.TimeGrid
import presentations.components.TextComponent

@Composable
fun ConsultationDate() {

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Calendar(modifier = Modifier.fillMaxSize().padding(start = 10.dp, end = 10.dp, top = 20.dp))
        AvailableTimeContent()
        AttachServiceReviews()
    }
}


@Composable
fun AvailableTimeContent() {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 10.dp, top = 35.dp, bottom = 10.dp)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Available Time",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            textModifier = Modifier.fillMaxWidth()
        )
        TimeGrid()
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AttachServiceReviews() {

    TextComponent(
        text = "Reviews",
        fontSize = 18,
        fontFamily = GGSansSemiBold,
        textStyle = TextStyle(),
        textColor = Colors.darkPrimary,
        textAlign = TextAlign.Left,
        fontWeight = FontWeight.Black,
        textModifier = Modifier.fillMaxWidth().padding(start = 20.dp))

    val pagerState = rememberPagerState(pageCount = {
        5
    })

    val boxModifier =
        Modifier
            .padding(bottom = 20.dp, top = 20.dp, start = 20.dp)
            .fillMaxHeight()
            .fillMaxWidth()

    val boxBgModifier =
        Modifier
            .padding(bottom = 10.dp, top = 10.dp, start = 15.dp)
            .fillMaxHeight()
            .fillMaxWidth()
            .border(
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(topStart = 7.dp, bottomStart = 7.dp)
            )


    Box(modifier = boxBgModifier) {

        Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                ReviewsWidget()
            }
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    var color = Color.LightGray
                    var width = 0
                    if (pagerState.currentPage == iteration) {
                        color = Colors.primaryColor
                        width = 20
                    } else {
                        color = Color.LightGray
                        width = 20
                    }
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .height(3.dp)
                            .width(width.dp)
                    )
                }

            }
        }
    }
}


