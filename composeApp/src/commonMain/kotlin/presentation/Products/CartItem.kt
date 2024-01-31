package presentation.Products

import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import presentation.components.ImageComponent
import presentation.components.TextComponent
import presentation.widgets.CartIncrementDecrementWidget

@Composable
fun CartItem(onProductClickListener: () -> Unit) {
    val columnModifier = Modifier
        .padding(start = 5.dp, top = 10.dp, bottom = 10.dp)
        .clickable {
            onProductClickListener()
        }
        .height(160.dp)
        Row(modifier = columnModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CartItemImage()
            CartItemDetail {}
        }
    }




@Composable
fun CartItemImage() {
    val imageModifier =
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    Card(
        modifier = Modifier
            .padding(start = 15.dp, end = 5.dp)
            .background(color = Color.White)
            .height(140.dp)
            .width(140.dp),
        shape = RoundedCornerShape(8.dp),
        border = null
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            ImageComponent(
                imageModifier = imageModifier,
                imageRes = "woman2.jpg",
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Composable
fun CartItemDetail(onProductClickListener: () -> Unit){
    val columnModifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
        .fillMaxHeight()
        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment  = Alignment.Start,
        ) {

            val modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .wrapContentHeight()

            TextComponent(
                text = "Bloom Rose Oil And Argan Oil is For Sale",
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                textStyle = TextStyle(),
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Black,
                lineHeight = 25,
                textModifier = modifier,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            CartProductPriceInfoContent()
            CartIncrementDecrementWidget()
        }
    }

@Composable
fun CartProductPriceInfoContent() {
    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxHeight(),
    ) {
        TextComponent(
            text = "$670,000",
            fontSize = 20,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.primaryColor,
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .padding(end = 10.dp)
                .wrapContentSize())

        TextComponent(
            text = "$67,000",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(textDecoration = TextDecoration.LineThrough),
            textColor = Color.LightGray,
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.Medium,
            lineHeight = 30,
            textModifier = Modifier
                .wrapContentSize())

    }
}



