package screens.Bookings

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansRegular
import GGSansSemiBold
import Models.CalendarDataSource
import Models.CalendarUiModel
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
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
import components.LocationToggleButton
import components.TextComponent
import components.ToggleButton
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.ExperimentalResourceApi
import widgets.WelcomeScreenImageTextWidget

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
            attachServiceImages()
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

    Column(modifier = modifier.fillMaxSize().padding(start = 15.dp, end = 15.dp, top = 40.dp)) {
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

        LazyRow(modifier = Modifier.padding(start = 10.dp, top = 10.dp).fillMaxWidth(), state = listState) {
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
        Color.DarkGray
    }

    val bgColor: Color = if(date.isSelected){
        Color(color = 0xFFFA2D65)
    }
    else{
        Color.White
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
                colorFilter = ColorFilter.tint(color = Color.DarkGray)
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
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                fontWeight = FontWeight.Black,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Center,
                textStyle = MaterialTheme.typography.h4
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
                colorFilter = ColorFilter.tint(color = Color.Gray)
            )
        }

    }
}


@Composable
fun AttachServiceTypeToggle(){
    var checked by remember { mutableStateOf(false) }

    val tint by animateColorAsState(if (checked) Color.Cyan else Color.Black)
    val textColor = if (checked) Color.White else Color.Cyan

    val background = Brush.horizontalGradient(
        colors = listOf(
            Color(color = 0xFFF43569),
            Color(color = 0xFFFF823E)
        ))


    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 10.dp, top = 35.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "What type of Service do you want?",
            fontSize = 16,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .padding(bottom = 15.dp)
                .fillMaxWidth()
        )
    }

}




@OptIn(ExperimentalResourceApi::class)
@Composable
fun ServiceDropDownWidget(menuItems: List<String>,
                   menuExpandedState: Boolean,
                   selectedIndex : Int,
                   updateMenuExpandStatus : () -> Unit,
                   onDismissMenuView : () -> Unit,
                   onMenuItemclick : (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                onClick = {
                    updateMenuExpandStatus()
                },
            ),
    ) {

        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
            ) {
                val textStyle: TextStyle = TextStyle(
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    fontFamily = GGSansRegular,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold
                )

                TextComponent(
                    text = "What type of service is this?",
                    fontSize = 18,
                    fontFamily = GGSansRegular,
                    textStyle = textStyle,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    textModifier = Modifier.weight(5f).padding(start = 15.dp, end = 5.dp)
                )

                val imageModifier = Modifier
                    .size(25.dp)
                    .weight(1f)
                    .padding(start = 10.dp, top = 3.dp)
                ImageComponent(
                    imageModifier = imageModifier,
                    imageRes = "chevron_down_icon.png",
                    colorFilter = ColorFilter.tint(color = Color.Gray)
                )

            }
        }
    }

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {

        val textStyle: TextStyle = TextStyle(
            fontSize = TextUnit(18f, TextUnitType.Sp),
            fontFamily = GGSansRegular,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Black,
        )

        DropdownMenu(
            expanded = menuExpandedState,
            onDismissRequest = { onDismissMenuView() },
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .background(MaterialTheme.colors.surface)
        ) {
            menuItems.forEachIndexed { index, title ->
                DropdownMenuItem(
                    onClick = {
                        if (index != 0) {
                            onMenuItemclick(index)
                        }
                    }) {
                    TextComponent(
                        text = title,
                        fontSize = 18,
                        fontFamily = GGSansRegular,
                        textStyle = textStyle,
                        textColor = Color.DarkGray,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}





@Composable
fun ServiceLocationToggle(){
    val checked by remember { mutableStateOf(false) }

    val tint by animateColorAsState(if (checked) Color.Cyan else Color.Black)
    val textColor = if (checked) Color.White else Color.Cyan

    val background = Brush.horizontalGradient(
        colors = listOf(
            Color(color = 0xFFF43569),
            Color(color = 0xFFFF823E)
        ))


    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 25.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Where do you want your Service?",
            fontSize = 16,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth()
        )

        ToggleButton(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(10.dp), style = MaterialTheme.typography.h4, onLeftClicked = {

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
            text = "Body Massage",
            fontSize = 25,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Light,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth()
        )
    }

}

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun attachServiceImages(){

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
                var color = Color.White
                var width = 0
                if (pagerState.currentPage == iteration){
                    color =  Color(color = 0xFFF43569)
                    width = 20
                } else{
                    color =  Color(0xFFF3F3F3)
                    width = 20
                }
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .height(5.dp)
                        .width(width.dp)
                )
            }

        }
    }

}
