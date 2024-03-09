package presentation.Bookings


import GGSansBold
import GGSansRegular
import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun BookingItemCard(viewType: Int = 0, contentSize: Int = 0, itemCount: Int = 0) {
     val itemColor: Long = 0L
     val headerFooterHeight: Int = 180


    Card(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
            .background(color = Color.Transparent)
            .height((contentSize+headerFooterHeight).dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
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
              Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = rowModifier
            ) {
                val modifier = Modifier.padding(start = 3.dp)
                AttachLocationIcon()
                TextComponent(
                    text = "Plash Spa and Beauty Shop",
                    fontSize = 17,
                    fontFamily = GGSansBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30,
                    textModifier = modifier
                )
            }
}


@Composable
fun AttachLocationIcon() {
    val modifier = Modifier
        .padding(top = 2.dp)
        .size(18.dp)
    ImageComponent(imageModifier = modifier, imageRes = "location_icon_filled.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
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

        Column(
            modifier = rowModifier
        ) {
            TextComponent(
                text = "Visions of Beauty",
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                textStyle = TextStyle(),
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 25
            )
            Row (horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically){
                TextComponent(
                    text = "Anna Mccawin",
                    fontSize = 18,
                    fontFamily = GGSansSemiBold,
                    textStyle = TextStyle(),
                    textColor = Color.Gray,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30,
                    textModifier = Modifier.padding(top = 5.dp).wrapContentSize(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Box(modifier = indicatorModifier)

                TextComponent(
                    text = "5:30 - 8:00am",
                    fontSize = 18,
                    fontFamily = GGSansSemiBold,
                    textStyle = TextStyle(),
                    textColor = Color.Gray,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30,
                    textModifier = Modifier.padding(top = 5.dp).wrapContentSize(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            TextComponent(
                text = "$2.300",
                fontFamily = GGSansSemiBold,
                textStyle = TextStyle(),
                textColor = Color.Gray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                lineHeight = 30,
                fontSize = 18,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textModifier = Modifier.padding(top = 5.dp, bottom = 30.dp)
            )
            StraightLine()
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