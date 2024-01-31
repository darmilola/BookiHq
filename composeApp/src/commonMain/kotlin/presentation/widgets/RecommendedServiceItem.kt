package presentation.widgets

import GGSansBold
import GGSansRegular
import theme.styles.Colors
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import components.ImageComponent
import components.TextComponent

@Composable
fun RecommendedServiceItem(viewType: Int = 0, onSessionClickListener: () -> Unit) {


    val columnModifier = Modifier
        .clickable {
            onSessionClickListener()
        }
        .height(410.dp)
        .fillMaxWidth()
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .height(410.dp)
                .wrapContentWidth()
        ) {
            Row(
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier
                        .background(color = Colors.primaryColor)
                        .fillMaxWidth(0.005f)
                        .fillMaxHeight()
                ) {}
                Column(
                    modifier = columnModifier
                ) {
                    RecommendedServicesImage(viewType = viewType)
                    RecommendedServiceDescription()
                    RecommendedServicePriceAndAction(viewType = viewType)
                }
            }
        }
}


@Composable
fun RecommendedServicePriceAndAction(viewType: Int = 0) {
    val textColor = Colors.primaryColor

    Row(modifier = Modifier.height(50.dp).padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {

        Row(modifier = Modifier.fillMaxWidth(0.4f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            PopularServicePriceContent(textColor = textColor)
        }

        Row(horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                val textStyle: TextStyle = TextStyle(
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    fontFamily = GGSansRegular,
                    fontWeight = FontWeight.Black
                )
                TextComponent(
                    text = if(viewType == 0) "Book Now" else if (viewType == 1) "Let's Discuss" else "Buy Now",
                    fontSize = 20,
                    fontFamily = GGSansRegular,
                    textStyle = textStyle,
                    textColor = textColor,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    textModifier = Modifier.wrapContentSize().padding(end = 7.dp))

                    ImageComponent(imageModifier = Modifier.size(24.dp), imageRes = "drawable/forward_arrow.png", colorFilter = ColorFilter.tint(color = textColor))
            }

        }
    }

}
@Composable
fun PopularServicePriceContent(textColor: Color) {
    val textStyle: TextStyle = TextStyle(
        fontSize = TextUnit(20f, TextUnitType.Sp),
        fontFamily = GGSansRegular,
        fontWeight = FontWeight.Black
    )
     TextComponent(
            text = "$670,000",
            fontSize = 20,
            fontFamily = GGSansRegular,
            textStyle = textStyle,
            textColor = textColor,
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.Bold,
            lineHeight = 30,
            textModifier = Modifier
                .padding(top = 5.dp)
                .wrapContentSize()
        )
    }

@Composable
fun RecommendedServiceDescription() {
    Column(
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.Start,
    ) {
        TextComponent(
            text = "Medium Length Layer Cut with cute Salon stuff",
            fontSize = 20,
            fontFamily = GGSansBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            lineHeight = 25,
            textModifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        TextComponent(
            textModifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
            text = "Lorem ipsum dolor sit amet consectetuer adipiscing Aenean commodo ligula adipiscing Aene ligula",
            fontSize = 18, fontFamily = GGSansRegular,
            textStyle = TextStyle(), textColor = Color.Gray, textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 23, maxLines = 3,
            overflow = TextOverflow.Ellipsis)

         StraightLine()

    }

}

@Composable
fun RecommendedServicesImage(viewType: Int = 0) {
    val imageModifier =
        Modifier
            .height(200.dp)
            .fillMaxWidth()
        Box(
            modifier = imageModifier,
            contentAlignment = Alignment.TopStart
        ) {
            ImageComponent(
                imageModifier = imageModifier,
                imageRes = if(viewType == 0) "drawable/fingernails.jpg" else if(viewType == 1) "drawable/sale4.jpg" else "drawable/olive_oil.jpg",
                contentScale = ContentScale.Crop
            )
        }
}
@Composable
fun StraightLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(color = Color(color = 0x80CCCCCC))
    ) {}
}


