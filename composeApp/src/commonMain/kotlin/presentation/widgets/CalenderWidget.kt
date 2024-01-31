package presentation.widgets

import GGSansSemiBold
import models.CalendarDataSource
import models.CalendarUiModel
import theme.styles.Colors
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.ImageComponent
import components.TextComponent
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

@Composable
fun Calendar(modifier: Modifier = Modifier.fillMaxSize().padding(start = 10.dp, end = 10.dp, top = 40.dp)) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Column(modifier = modifier) {
        val dataSource = CalendarDataSource()
        // get CalendarUiModel from CalendarDataSource, and the lastSelectedDate is Today.
        val calendarUiModel = dataSource.getData(lastSelectedDate = dataSource.today)

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