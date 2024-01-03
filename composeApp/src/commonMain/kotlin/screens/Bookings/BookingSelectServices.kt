package screens.Bookings

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansRegular
import GGSansSemiBold
import Models.CalendarDataSource
import Models.CalendarUiModel
import Styles.Colors
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import components.ImageComponent
import components.TextComponent
import components.ToggleButton
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.ExperimentalResourceApi
import widgets.DropDownWidget

@Composable
fun BookingSelectServices() {

    val boxModifier =
        Modifier
            .height(350.dp)
            .fillMaxWidth()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        // AnimationEffect
        Box(contentAlignment = Alignment.TopStart, modifier = boxModifier) {
            AttachServiceImages()
        }
        ServiceTitle()
        ServiceLocationToggle()
        AttachServiceTypeToggle()
        BookingCalendar()

    }


}

@Composable
fun BookingCalendar(modifier: Modifier = Modifier) {

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Column(modifier = modifier.fillMaxSize().padding(start = 10.dp, end = 10.dp, top = 40.dp)) {
        val dataSource = CalendarDataSource()
        // get CalendarUiModel from CalendarDataSource, and the lastSelectedDate is Today.
        var calendarUiModel = dataSource.getData(lastSelectedDate = dataSource.today)

        var selectedUIModel by remember { mutableStateOf(calendarUiModel) }

        var initialVisibleDates by remember { mutableStateOf(5) }


        CalenderHeader(selectedUIModel, onPrevClickListener = { startDate ->
            coroutineScope.launch {
                if (initialVisibleDates > 0) initialVisibleDates--
                listState.animateScrollToItem(index = initialVisibleDates)
            }
        },
            onNextClickListener = { endDate ->
                coroutineScope.launch {
                    if (initialVisibleDates < listState.layoutInfo.totalItemsCount - 5) initialVisibleDates++
                    listState.animateScrollToItem(index = initialVisibleDates)
                }
            })
        CalenderContent(selectedUIModel, onDateClickListener = { it ->
            selectedUIModel = selectedUIModel.copy(
                selectedDate = it,
                visibleDates = selectedUIModel.visibleDates.map { it2 ->
                    it2.copy(
                        isSelected = it2.date == it.date
                    )
                }
            )
        }, listState = listState)
    }
}

    @Composable
    fun CalenderContent(calendarUiModel: CalendarUiModel, onDateClickListener: (CalendarUiModel.Date) -> Unit, listState: LazyListState) {

        LazyRow(modifier = Modifier.padding( top = 10.dp).fillMaxWidth(), state = listState) {
            // pass the visibleDates to the UI
            items(items = calendarUiModel.visibleDates) { date ->
                ContentItem(date, onDateClickListener)
            }
        }
    }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentItem(date: CalendarUiModel.Date, onClickListener: (CalendarUiModel.Date) -> Unit) {
    val textColor: Color = if(date.isSelected){
        Color.White
    }
    else{
        Colors.darkPrimary
    }

    val bgColor: Color = if(date.isSelected){
        Colors.primaryColor
    }
    else{
        Colors.lighterPrimaryColor
    }


    Card(colors = CardDefaults.cardColors(
        containerColor = bgColor
    ),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(
            modifier = Modifier
                .width(70.dp)
                .height(80.dp)
                .clickable {
                    onClickListener(date)
                }
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextComponent(
                text = date.date.dayOfMonth.toString(),
                textModifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 25,
                fontFamily = GGSansSemiBold,
                fontWeight = FontWeight.ExtraBold,
                textColor = textColor,
                textAlign = TextAlign.Center,
                textStyle = MaterialTheme.typography.h4
            )
            TextComponent(
                text = date.date.dayOfWeek.toString().substring(0,3),
                textModifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 16,
                fontFamily = GGSansSemiBold,
                fontWeight = FontWeight.Light,
                textColor = textColor,
                textAlign = TextAlign.Center,
                textStyle = MaterialTheme.typography.h4)
        }
    }
}



@Composable
fun CalenderHeader(calendarUiModel: CalendarUiModel, onPrevClickListener: (LocalDate) -> Unit,
                   onNextClickListener: (LocalDate) -> Unit,) {
    val imageModifier = Modifier
        .size(20.dp)

    Row {
        Row(modifier = Modifier
            .weight(1f)
            .clickable {
                onPrevClickListener(calendarUiModel.startDate.date)
            }) {
            ImageComponent(
                imageModifier = imageModifier.rotate(180f),
                imageRes = "left_arrow.png",
                colorFilter = ColorFilter.tint(color = Colors.darkPrimary)
            )
        }

        Row(modifier = Modifier.weight(2f),horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top) {
            TextComponent(
                text = if (calendarUiModel.selectedDate.isToday) {
                    "Today"
                } else {
                    calendarUiModel.selectedDate.date.month.toString()
                        .lowercase() + ", " + calendarUiModel.selectedDate.date.year.toString()
                },
                textModifier = Modifier
                    .align(Alignment.CenterVertically),
                fontSize = 20,
                fontFamily = GGSansSemiBold,
                fontWeight = FontWeight.Bold,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Center,
                textStyle = TextStyle()
            )
        }

        Row(modifier = Modifier
            .weight(1f)
            .clickable {
                onNextClickListener(calendarUiModel.endDate.date)
            },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top) {
            ImageComponent(
                imageModifier = imageModifier,
                imageRes = "left_arrow.png",
                colorFilter = ColorFilter.tint(color = Colors.darkPrimary)
            )
        }

    }
}


@Composable
fun AttachServiceTypeToggle(){
    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 35.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "What type of Service do you want?",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .padding(bottom = 5.dp)
                .fillMaxWidth()
        )

        AttachDropDownWidget()


    }

}

@Composable
fun AttachDropDownWidget(){
    val serviceList = listOf("Service A ", "Service B ", "Service C ", "Service D ", "Service E ")
    DropDownWidget(menuItems = serviceList,iconRes = "drawable/spa_service.png", placeHolderText = "Select Service Type", iconSize = 40)
}

@Composable
fun ServiceLocationToggle(){
    Column(
        modifier = Modifier
            .padding(top = 25.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Where do you want your Service?",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth().padding(start = 10.dp)
        )

        ToggleButton(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(10.dp), style = TextStyle(), onLeftClicked = {

        }, onRightClicked = {

        }, leftText = "Parlor", rightText = "Home")

    }

}



@Composable
fun ServiceTitle(){
    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Manicure",
            fontSize = 25,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Light,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth()
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AttachServiceImages(){

    val pagerState = rememberPagerState(pageCount = {
        3
    })

    val  boxModifier =
        Modifier
            .padding(bottom = 20.dp)
            .fillMaxHeight()
            .fillMaxWidth()

    // AnimationEffect
    Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            ImageComponent(imageModifier = Modifier.fillMaxWidth().height(350.dp), imageRes = "$page.jpg", contentScale = ContentScale.Crop)
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color: Color
                val width: Int
                if (pagerState.currentPage == iteration){
                    color =  Colors.primaryColor
                    width = 25
                } else{
                    color =  Colors.lightPrimaryColor
                    width = 25
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
