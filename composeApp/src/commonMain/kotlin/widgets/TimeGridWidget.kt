package widgets

import GGSansSemiBold
import Models.AvailableSlotsUIModel
import Models.WorkingHoursDataSource
import Styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.TextComponent

@Composable
fun TimeGrid() {

    val dataSource = WorkingHoursDataSource()
    // get CalendarUiModel from CalendarDataSource, and the lastSelectedDate is Today.
    val timePair = Pair(Pair("7:00","12:00"), false)
    val workingHours = dataSource.getWorkingHours(lastSelectedSlot = timePair)

    var selectedWorkHourUIModel by remember { mutableStateOf(workingHours) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth().height(220.dp),
        contentPadding = PaddingValues(6.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(selectedWorkHourUIModel.visibleSlots.size) { i ->
            TimeItem(selectedWorkHourUIModel.visibleSlots[i], onWorkHourClickListener = {
                    it -> selectedWorkHourUIModel = selectedWorkHourUIModel.copy(
                selectedSlot = it,
                visibleSlots = selectedWorkHourUIModel.visibleSlots.map { it2 ->
                    it2.copy(
                        isSelected = it2.timeSlot == it.timeSlot
                    )
                }
            )})
        }
    }
}


@Composable
fun TimeItem(availableSlot: AvailableSlotsUIModel.AvailableSlot, onWorkHourClickListener: (AvailableSlotsUIModel.AvailableSlot) -> Unit) {
    val timeStampObject = availableSlot.timeSlot
    val timeRange = timeStampObject.first
    val meridianVal: String = if(timeStampObject.second) "AM" else "PM"
    val color: Color = if(availableSlot.isSelected) Colors.primaryColor else Color.Gray

    Column(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 15.dp)
            .fillMaxWidth()
            .clickable {
                onWorkHourClickListener(availableSlot)
            }
            .border(border = BorderStroke((1.5).dp, color), shape = RoundedCornerShape(3.dp))
            .height(50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextComponent(
            text = timeRange.first+" - "+timeRange.second+ " "+meridianVal,
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = color,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier.fillMaxWidth()
        )
    }
}