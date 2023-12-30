package screens.Bookings

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansRegular
import GGSansSemiBold
import Models.AppointmentItemListModel
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import components.ImageComponent
import components.TextComponent
import features.consultations.WaitingRoomBottomSheet
import screens.Products.BottomSheet
import screens.main.MainViewModel

@Composable
fun BookingItemCard(viewType: Int = 0, contentSize: Int = 0, itemCount: Int = 0) {
    var itemColor: Long = 0L
    var imageRes: String = ""
    var headerFooterHeight: Int = 180



    when (viewType) {
        0 -> {
            itemColor = 0xFFFF799D
            imageRes = "app_logo_minimal.png"
        }
        1 -> {
            itemColor = 0xFF65B8FA
            imageRes = "task_icon.png"
        }
        2 -> {
            itemColor =  0xFF68EA8C
            imageRes = "schedule_icon.png"
        }
        3 -> {
            itemColor = 0xFFF8CF69
            imageRes = "video_icon.png"
        }
        4 -> {
            itemColor = 0xFFDD6EEA
            imageRes = "purchase_icon.png"
        }
        else -> {
            itemColor = 0xFFFF799D
            imageRes = "app_logo_minimal.png"
        }
    }


    Card(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
            .background(color = Color.Transparent)
            .height((contentSize+headerFooterHeight).dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        border = null
    ) {
        Row(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
         Row(
              modifier = Modifier
                        .background(color = Color(color = itemColor))
                        .fillMaxWidth(0.015f)
                        .fillMaxHeight()) {}


            Column (
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                    .background(color = Color.White)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {

                TextComponent(
                    text = "Friday, 26 Dec, 2023",
                    fontSize = 18,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color(color = 0xFFFF6B94),
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    textModifier = Modifier.padding(top = 10.dp).height(30.dp).fillMaxWidth()
                )

                AttachBusinessName()

                StraightLine()

                LazyColumn(modifier = Modifier
                    .padding(bottom = 10.dp, top = 10.dp)
                    .fillMaxWidth()
                    .height(contentSize.dp)) {
                    items(itemCount) {item ->
                        BookingItem()
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 5.dp).height(50.dp).fillMaxWidth()
                ) {

                    TextComponent(
                        text = "Total",
                        fontSize = 20,
                        fontFamily = GGSansRegular,
                        textStyle = MaterialTheme.typography.h6,
                        textColor = Color.DarkGray,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Black,
                        textModifier = Modifier.padding(top = 5.dp).height(30.dp).fillMaxWidth(0.20f)
                    )

                    TextComponent(
                        text = "$2.300",
                        fontSize = 20,
                        fontFamily = GGSansRegular,
                        textStyle = MaterialTheme.typography.h6,
                        textColor = Color(color = 0xfffa2d65),
                        textAlign = TextAlign.Right,
                        fontWeight = FontWeight.Black,
                        textModifier = Modifier.padding(top = 5.dp).height(30.dp).fillMaxWidth()
                    )

                }
            }
        }
    }
}

@Composable
fun AttachBusinessName(){
    val rowModifier = Modifier
        .padding(top = 10.dp, bottom = 10.dp)
        .fillMaxWidth()
    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            attachLocationIcon()
            TextComponent(
                text = "JonJo, Beauty and Spa Services",
                fontSize = 17,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.Gray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Medium,
                textModifier = Modifier.wrapContentSize().padding(start = 3.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun attachLocationIcon() {
    val modifier = Modifier
        .padding(top = 2.dp)
        .size(17.dp)
    ImageComponent(imageModifier = modifier, imageRes = "location_icon_filled.png", colorFilter = ColorFilter.tint(color = Color.LightGray))
}




@Composable
fun AppointmentItemCard(viewType: Int = 0, contentSize: Int = 0, bookingItems: AppointmentItemListModel, mainViewModel: MainViewModel) {
    var itemColor = 0L
    val headerFooterHeight: Int = 80



    when (viewType) {
        0 -> {
            itemColor = 0xFFFF799D
        }
        1 -> {
            itemColor = 0xFF65B8FA
        }
        2 -> {
            itemColor =  0xFF68EA8C
        }
        3 -> {
            itemColor = 0xFFF8CF69
        }
        4 -> {
            itemColor = 0xFFDD6EEA
        }
        else -> {
            itemColor = 0xFFFF799D
        }
    }


    Card(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 15.dp, bottom = 5.dp)
            .background(color = Color.Transparent)
            .height((contentSize+headerFooterHeight).dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        border = null
    ) {
        Row(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Row(
                modifier = Modifier
                    .background(color = Color(color = itemColor))
                    .fillMaxWidth(0.015f)
                    .fillMaxHeight()) {}


            Column (
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                    .background(color = Color.White)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {

                TextComponent(
                    text = "Friday, 26 Dec, 2023",
                    fontSize = 18,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color(color = 0xFFFF6B94),
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    textModifier = Modifier.padding(top = 10.dp).height(30.dp).fillMaxWidth()
                )

                AttachBusinessName()
                StraightLine()

                LazyColumn(modifier = Modifier
                    .padding(bottom = 10.dp, top = 10.dp)
                    .fillMaxWidth()
                    .height(contentSize.dp), userScrollEnabled = false) {
                    items(bookingItems.appointmentItems) {item ->
                        AppointmentItem(item.appointmentType, mainViewModel = mainViewModel)
                    }
                }

            }
        }
    }
}


@Composable
fun AppointmentItem(appointmentType: Int, mainViewModel: MainViewModel) {
    val rowModifier = Modifier
        .padding(top = 10.dp)
        .fillMaxWidth()
    var showSheet by remember { mutableStateOf(false) }
    if (showSheet) {
        WaitingRoomBottomSheet(mainViewModel = mainViewModel) {
            showSheet = false
        }
    }

    val indicatorModifier = Modifier
        .padding(start = 10.dp, end = 10.dp, top = 5.dp)
        .background(color = Color.Transparent)
        .size(4.dp)
        .background(
            color = Color.Gray,
            shape = RoundedCornerShape(4.dp)
        )

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Column(
            modifier = rowModifier
        ) {
            TextComponent(
                text = if (appointmentType == 1) "Visions of Beauty" else "Consultation",
                fontSize = 22,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 25
            )
            Row (horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically){
                TextComponent(
                    text = if (appointmentType == 1) "Anna McCain" else "Virtual",
                    fontSize = 20,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.Gray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    textModifier = Modifier.padding(top = 5.dp).wrapContentSize(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Box(modifier = indicatorModifier)

                TextComponent(
                    text = "5:30 - 8:00am",
                    fontSize = 18,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Black,
                    textModifier = Modifier.padding(top = 5.dp).wrapContentSize(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp, top = 10.dp).clickable {
                   if (appointmentType == 1) {
                       mainViewModel.setId(7)
                   }
                    else{
                        showSheet = true
                    }
                }
            ) {
                TextComponent(
                    text = if (appointmentType == 1) "View Info" else "Join Session",
                    fontSize = 17,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color(color = 0xfffa2d65),
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    textModifier = Modifier.wrapContentSize().padding(end = 7.dp))

                ImageComponent(imageModifier = Modifier.size(24.dp), imageRes = "drawable/forward_arrow.png", colorFilter = ColorFilter.tint(color = Color(color = 0xfffa2d65)))
            }

            StraightLine()
        }
    }
}



@Composable
fun BookingItem() {
    val rowModifier = Modifier
        .padding(top = 10.dp)
        .fillMaxWidth()

    val indicatorModifier = Modifier
        .padding(start = 10.dp, end = 10.dp, top = 5.dp)
        .background(color = Color.Transparent)
        .size(4.dp)
        .background(
            color = Color.Gray,
            shape = RoundedCornerShape(4.dp)
        )

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Column(
            modifier = rowModifier
        ) {
            TextComponent(
                text = "Visions of Beauty",
                fontSize = 22,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 25
            )
            Row (horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically){
                TextComponent(
                    text = "Anna Mccawin",
                    fontSize = 20,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.Gray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    textModifier = Modifier.padding(top = 5.dp).wrapContentSize(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Box(modifier = indicatorModifier)

                TextComponent(
                    text = "5:30 - 8:00am",
                    fontSize = 18,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Black,
                    textModifier = Modifier.padding(top = 5.dp).wrapContentSize(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            TextComponent(
                text = "$2.300",
                fontSize = 17,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color(color = 0xfffa2d65),
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Black,
                textModifier = Modifier.padding(top = 5.dp, bottom = 30.dp)
            )
            StraightLine()
        }
    }
}

@Composable
fun StraightLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(color = Color(color = 0x50CCCCCC))
    ) {
    }
}