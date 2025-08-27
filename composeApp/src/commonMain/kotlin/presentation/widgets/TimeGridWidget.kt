package presentation.widgets

import GGSansSemiBold
import theme.styles.Colors
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
import androidx.compose.foundation.layout.wrapContentHeight
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
import domain.Models.PlatformTime
import domain.Models.PlatformTimeUIModel
import domain.Models.VendorTime
import domain.Models.VendorTimeUIModel
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun TimeGrid(platformTimes: List<PlatformTime>? = arrayListOf(), selectedTime: PlatformTime? = null,  onWorkHourClickListener: (PlatformTime) -> Unit) {

    var workHourUIModel by remember {
        mutableStateOf(
            PlatformTimeUIModel(
                selectedTime = PlatformTime(),
                platformTimes!!
            )
        )
    }
    workHourUIModel = if (selectedTime != null && selectedTime in platformTimes!!) {
        PlatformTimeUIModel(selectedTime, visibleTime = platformTimes)
    } else {
        PlatformTimeUIModel(
            PlatformTime(), platformTimes!!)
    }


    Column(modifier = Modifier.fillMaxWidth()) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth().height(300.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            userScrollEnabled = false,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(workHourUIModel.visibleTime.size) { i ->
                TimeItem(workHourUIModel.visibleTime[i], onWorkHourClickListener = { it ->
                    onWorkHourClickListener(it)
                    workHourUIModel = workHourUIModel.copy(
                        selectedTime = it,
                        visibleTime = workHourUIModel.visibleTime.map { it2 ->
                            it2.copy(
                                isSelected = it2.id == it.id
                            )
                        }

                    )
                })
            }
        }
    }
}


@Composable
fun TimeGridDisplay(platformTimes: Triple<ArrayList<PlatformTime>, ArrayList<PlatformTime>, ArrayList<PlatformTime>>, onWorkHourClickListener: (PlatformTime) -> Unit) {

    var morningHourUIModel by remember {
        mutableStateOf(
            PlatformTimeUIModel(
                selectedTime = PlatformTime(),
                platformTimes.first
            )
        )
    }

    var afternoonHourUIModel by remember {
        mutableStateOf(
            PlatformTimeUIModel(
                selectedTime = PlatformTime(),
                platformTimes.second
            )
        )
    }

    var eveningHourUIModel by remember {
        mutableStateOf(
            PlatformTimeUIModel(
                selectedTime = PlatformTime(),
                platformTimes.third
            )
        )
    }

    Row(modifier = Modifier.fillMaxWidth(0.90f).wrapContentHeight()) {
        Column(modifier = Modifier.weight(1f).wrapContentHeight()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxWidth().height(400.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                userScrollEnabled = false,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(morningHourUIModel.visibleTime.size) { i ->
                    TimeItem(morningHourUIModel.visibleTime[i], onWorkHourClickListener = { it ->
                        onWorkHourClickListener(it)
                        morningHourUIModel = morningHourUIModel.copy(
                            selectedTime = it,
                            visibleTime = morningHourUIModel.visibleTime.map { it2 ->
                                it2.copy(
                                    isSelected = it2.id == it.id
                                )
                            }

                        )
                        afternoonHourUIModel = afternoonHourUIModel.copy(
                            selectedTime = it,
                            visibleTime = afternoonHourUIModel.visibleTime.map { it2 ->
                                it2.copy(
                                    isSelected = it2.id == it.id
                                )
                            }

                        )
                        eveningHourUIModel = eveningHourUIModel.copy(
                            selectedTime = it,
                            visibleTime = eveningHourUIModel.visibleTime.map { it2 ->
                                it2.copy(
                                    isSelected = it2.id == it.id
                                )
                            }

                        )
                    })
                }
            }
        }
        Column(modifier = Modifier.weight(1f).wrapContentHeight()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxWidth().height(400.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                userScrollEnabled = false,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(afternoonHourUIModel.visibleTime.size) { i ->
                    TimeItem(afternoonHourUIModel.visibleTime[i], onWorkHourClickListener = { it ->
                        onWorkHourClickListener(it)
                        morningHourUIModel = morningHourUIModel.copy(
                            selectedTime = it,
                            visibleTime = morningHourUIModel.visibleTime.map { it2 ->
                                it2.copy(
                                    isSelected = it2.id == it.id
                                )
                            }
                        )
                        afternoonHourUIModel = afternoonHourUIModel.copy(
                            selectedTime = it,
                            visibleTime = afternoonHourUIModel.visibleTime.map { it2 ->
                                it2.copy(
                                    isSelected = it2.id == it.id
                                )
                            }

                        )
                        eveningHourUIModel = eveningHourUIModel.copy(
                            selectedTime = it,
                            visibleTime = eveningHourUIModel.visibleTime.map { it2 ->
                                it2.copy(
                                    isSelected = it2.id == it.id
                                )
                            }

                        )
                    })
                }
            }
        }
        Column(modifier = Modifier.weight(1f).wrapContentHeight()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxWidth().height(400.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                userScrollEnabled = false,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(eveningHourUIModel.visibleTime.size) { i ->
                    TimeItem(eveningHourUIModel.visibleTime[i], onWorkHourClickListener = { it ->
                        onWorkHourClickListener(it)
                        morningHourUIModel = morningHourUIModel.copy(
                            selectedTime = it,
                            visibleTime = morningHourUIModel.visibleTime.map { it2 ->
                                it2.copy(
                                    isSelected = it2.id == it.id
                                )
                            }

                        )
                        afternoonHourUIModel = afternoonHourUIModel.copy(
                            selectedTime = it,
                            visibleTime = afternoonHourUIModel.visibleTime.map { it2 ->
                                it2.copy(
                                    isSelected = it2.id == it.id
                                )
                            }

                        )
                        eveningHourUIModel = eveningHourUIModel.copy(
                            selectedTime = it,
                            visibleTime = eveningHourUIModel.visibleTime.map { it2 ->
                                it2.copy(
                                    isSelected = it2.id == it.id
                                )
                            }

                        )
                    })
                }
            }
        }

    }
}

@Composable
fun TimeItem(platformTime: PlatformTime, onWorkHourClickListener: (PlatformTime) -> Unit) {
    val meridian = if (platformTime.isAm){
        "AM"
    }
    else{
        "PM"
    }
    val color: Color = if(platformTime.isSelected){
        theme.Colors.greenColor
    }
    else if (!platformTime.isEnabled){
        Color.LightGray
    }
    else {
        Color.Gray
    }
    val showSelectIcon = remember { mutableStateOf(false) }

    showSelectIcon.value = platformTime.isSelected

    Row(
        modifier = Modifier
            .padding(start = 3.dp, end = 3.dp, top = 10.dp)
            .fillMaxWidth()
            .clickable {
               if (platformTime.isEnabled){
                   onWorkHourClickListener(platformTime)
               }
            }
            .border(border = BorderStroke((1.5).dp, color), shape = RoundedCornerShape(6.dp))
            .height(40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(showSelectIcon.value){
            ImageComponent(imageModifier = Modifier.size(20.dp), imageRes = "drawable/check_mark_icon.png", colorFilter = ColorFilter.tint(color = theme.Colors.greenColor))
        }
        TextComponent(
            text = platformTime.time!!+" "+meridian,
            fontSize = 15,
            textStyle = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            textColor = color,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 30)

    }
}



@Composable
fun VendorTimeGrid(availableTimes: List<VendorTime>? = arrayListOf(), selectedTime: VendorTime? = null, onWorkHourClickListener: (VendorTime) -> Unit) {
    var workHourUIModel by remember {
        mutableStateOf(
            VendorTimeUIModel(
                selectedTime = VendorTime(),
                availableTimes!!
            )
        )
    }
    workHourUIModel = if (selectedTime != null) {
        VendorTimeUIModel(selectedTime, visibleTime = availableTimes!!)
    } else {
        VendorTimeUIModel(VendorTime(), availableTimes!!)
    }


    Column(modifier = Modifier.fillMaxWidth()) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            userScrollEnabled = false,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(workHourUIModel.visibleTime.size) { i ->
                VendorTimeItem(workHourUIModel.visibleTime[i], onWorkHourClickListener = { it ->
                    onWorkHourClickListener(it)
                    workHourUIModel = workHourUIModel.copy(
                        selectedTime = it,
                        visibleTime = workHourUIModel.visibleTime.map { it2 ->
                            it2.copy(
                                isSelected = it2.id == it.id
                            )
                        }

                    )
                })
            }
        }
    }
}


@Composable
fun VendorTimeItem(vendorTime: VendorTime, onWorkHourClickListener: (VendorTime) -> Unit) {
    val meridian = if (vendorTime.platformTime?.isAm!!){
        "AM"
    }
    else{
        "PM"
    }
    val color: Color = if(vendorTime.isSelected){
        Colors.primaryColor
    } else {
        Color.Gray
    }
    val showSelectIcon = remember { mutableStateOf(false) }

    showSelectIcon.value = vendorTime.isSelected

    Row(
        modifier = Modifier
            .padding(start = 3.dp, end = 3.dp, top = 10.dp)
            .fillMaxWidth()
            .clickable {
                    onWorkHourClickListener(vendorTime)

            }
            .border(border = BorderStroke((1.5).dp, color), shape = RoundedCornerShape(6.dp))
            .height(40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(showSelectIcon.value){
            ImageComponent(imageModifier = Modifier.size(20.dp), imageRes = "drawable/check_mark_icon.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
        }
        TextComponent(
            text = vendorTime.platformTime.time!!+" "+meridian,
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
