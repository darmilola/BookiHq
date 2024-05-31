package presentation.widgets

import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.Models.AvailableTimeUIModel
import domain.Models.ServiceTime
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors


@Composable
fun TherapistAvailabilityTimeGrid(availableTimes: List<ServiceTime>? = arrayListOf(), onWorkHourUnAvailable: (ServiceTime) -> Unit,
                                  onWorkHourAvailable: (ServiceTime) -> Unit) {

    var workHourUIModel by remember {
        mutableStateOf(
            AvailableTimeUIModel(
                selectedTime = ServiceTime(),
                availableTimes!!
            )
        )
    }

    AvailableTimeUIModel(ServiceTime(), availableTimes!!)

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(modifier = Modifier.fillMaxWidth().height(20.dp)) {

            TextComponent(
                text = "Morning",
                fontSize = 15,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.Gray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                lineHeight = 30,
                textModifier = Modifier.weight(1f))

            TextComponent(
                text = "Afternoon",
                fontSize = 15,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.Gray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                lineHeight = 30,
                textModifier = Modifier.weight(1f))

            TextComponent(
                text = "Evening",
                fontSize = 15,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.Gray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                lineHeight = 30,
                textModifier = Modifier.weight(1f))

        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth().height(300.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            userScrollEnabled = false,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(workHourUIModel.visibleTime.size) { i ->
                TherapistTimeItem(workHourUIModel.visibleTime[i], onAvailableWorkHourClickListener = { it ->
                    workHourUIModel = workHourUIModel.copy(
                        selectedTime =  it,
                        visibleTime = workHourUIModel.visibleTime.map { it2 ->
                            val isAvailable =  if(it2.id == it.id){
                                false
                            }
                            else{
                                it2.isAvailable
                            }
                            it2.copy(isAvailable = isAvailable)
                        }
                    )
                    onWorkHourUnAvailable(it)
                }, onUnAvailableWorkHourClickListener = {
                    workHourUIModel = workHourUIModel.copy(
                        selectedTime =  it,
                        visibleTime = workHourUIModel.visibleTime.map { it2 ->
                            val isAvailable =  if(it2.id == it.id){
                                true
                            }
                            else{
                                it2.isAvailable
                            }
                            it2.copy(isAvailable = isAvailable)
                        }
                    )
                    onWorkHourAvailable(it)
                })
            }
        }
    }
}


@Composable
fun TherapistTimeItem(availableTime: ServiceTime, onAvailableWorkHourClickListener: (ServiceTime) -> Unit,
                      onUnAvailableWorkHourClickListener: (ServiceTime) -> Unit) {
    val meridian = if (availableTime.platformTime?.isAm!!){
          "am"
    }
    else{
        "pm"
    }
    val color: Color = if (availableTime.isAvailable){
        Colors.primaryColor
    } else {
        Colors.pinkColor
    }

    val iconRes = if(availableTime.isAvailable){
        "drawable/check_mark_icon.png"
    }
    else{
        "drawable/cancel_icon.png"
    }


    Row(
        modifier = Modifier
            .padding(start = 3.dp, end = 3.dp, top = 10.dp)
            .fillMaxWidth()
            .clickable {
                if (availableTime.isAvailable) {
                    onAvailableWorkHourClickListener(availableTime)
                }
                else{
                    onUnAvailableWorkHourClickListener(availableTime)
                }
            }
            .border(border = BorderStroke((1.5).dp, color), shape = RoundedCornerShape(6.dp))
            .height(40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageComponent(imageModifier = Modifier.size(20.dp), imageRes = iconRes, colorFilter = ColorFilter.tint(color = color))
        TextComponent(
            text = availableTime.platformTime.time!!+" "+meridian,
            fontSize = 15,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = color,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            lineHeight = 30,
            textModifier = Modifier.padding(start = 5.dp))

    }
}