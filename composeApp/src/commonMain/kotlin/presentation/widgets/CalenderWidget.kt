package presentation.widgets

import GGSansSemiBold
import presentation.dataModeller.CalendarDataSource
import domain.Models.CalendarUiModel
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import domain.Models.PlatformDate
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun BookingCalendar(modifier: Modifier = Modifier.fillMaxSize().padding(start = 10.dp, end = 10.dp, top = 30.dp),onDateSelected: (LocalDate) -> Unit) {
    val dataSource = CalendarDataSource()
    val calendarUiModel = dataSource.getDate(lastSelectedDate = dataSource.today)
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()


    Column(modifier = modifier) {
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
                selectedPlatformDate = it,
                visiblePlatformDates = selectedUIModel.visiblePlatformDates.map { it2 ->
                    it2.copy(
                        isSelected = it2.date == it.date
                    )
                }
            )
            onDateSelected(selectedUIModel.selectedPlatformDate.date)
        }, listState = listState)
    }
}

@Composable
fun CalenderContent(calendarUiModel: CalendarUiModel, onDateClickListener: (PlatformDate) -> Unit, listState: LazyListState) {

    LazyRow(modifier = Modifier.padding( top = 10.dp).fillMaxWidth(), state = listState) {
        items(items = calendarUiModel.visiblePlatformDates) { date ->
            ContentItem(date, onDateClickListener)
        }
    }
}



@Composable
fun ContentItem(platformDate: PlatformDate, onClickListener: (PlatformDate) -> Unit) {
    val textColor: Color = if(platformDate.isSelected){
        Color.White
    }
    else{
        Colors.darkPrimary
    }

    val bgColor: Color = if(platformDate.isSelected){
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
                    onClickListener(platformDate)
                }
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextComponent(
                text = platformDate.date.dayOfMonth.toString(),
                textModifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 23,
                fontFamily = GGSansSemiBold,
                fontWeight = FontWeight.Black,
                textColor = textColor,
                textAlign = TextAlign.Center,
                textStyle = MaterialTheme.typography.h6
            )
            TextComponent(
                text = platformDate.date.dayOfWeek.toString().substring(0,3),
                textModifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 10.dp),
                fontSize = 13,
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
                onPrevClickListener(calendarUiModel.startPlatformDate.date)
            }) {
            ImageComponent(
                imageModifier = imageModifier.rotate(180f),
                imageRes = "drawable/left_arrow.png",
                colorFilter = ColorFilter.tint(color = Colors.darkPrimary)
            )
        }

        Row(modifier = Modifier.weight(2f),horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top) {
            TextComponent(
                text = if (calendarUiModel.selectedPlatformDate.isToday) {
                    "Today"
                }
                else if (calendarUiModel.selectedPlatformDate.isTomorrow){
                    val formattedMonth = calendarUiModel.selectedPlatformDate.date.month.toString()
                        .lowercase()
                        .replaceFirstChar {
                                char -> char.titlecase()
                        }
                    "Tomorrow, " + formattedMonth + " " + calendarUiModel.selectedPlatformDate.date.dayOfMonth.toString()
                }
                else {
                    val formattedMonth = calendarUiModel.selectedPlatformDate.date.month.toString()
                        .lowercase()
                        .replaceFirstChar {
                        char -> char.titlecase()
                    }
                    formattedMonth + ", " + calendarUiModel.selectedPlatformDate.date.dayOfMonth.toString()
                },
                textModifier = Modifier
                    .align(Alignment.CenterVertically),
                fontSize = 16,
                fontFamily = GGSansSemiBold,
                fontWeight = FontWeight.Bold,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Center,
                textStyle = MaterialTheme.typography.h6
            )
        }

        Row(modifier = Modifier
            .weight(1f)
            .clickable {
                onNextClickListener(calendarUiModel.endPlatformDate.date)
            },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top) {
            ImageComponent(
                imageModifier = imageModifier,
                imageRes = "drawable/left_arrow.png",
                colorFilter = ColorFilter.tint(color = Colors.darkPrimary)
            )
        }

    }
}