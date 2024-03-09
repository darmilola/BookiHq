package presentation.Products

import GGSansBold
import GGSansRegular
import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import presentation.components.ButtonComponent
import presentations.components.ImageComponent
import presentations.components.TextComponent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductItem(onProductClickListener: () -> Unit) {
    val columnModifier = Modifier
        .padding(start = 5.dp, top = 5.dp, bottom = 10.dp)
        .clickable {
            onProductClickListener()
        }
        .height(280.dp)
        Column(modifier = columnModifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ProductImage()
                ProductNameAndPrice()
            }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewProductItem(onProductClickListener: () -> Unit) {
    val columnModifier = Modifier
        .padding(start = 5.dp, top = 5.dp, bottom = 10.dp)
        .clickable {
            onProductClickListener()
        }
        .height(220.dp)
        Row(modifier = columnModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            NewProductImage()
            NewProductDescription(onProductClickListener)
        }
    }


    @Composable
    fun ProductImage() {
        val imageModifier =
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        Card(
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                .background(color = Color.White)
                .height(200.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = null
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.TopStart
            ) {
                ImageComponent(
                    imageModifier = imageModifier,
                    imageRes = "oil.jpg",
                    contentScale = ContentScale.Crop
                )
                DiscountText()
            }
        }
    }


@Composable
fun NewProductImage() {
    val imageModifier =
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    Card(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 5.dp)
            .background(color = Color.White)
            .height(220.dp)
            .fillMaxWidth(0.45f),
        shape = RoundedCornerShape(8.dp),
        border = null
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.TopStart
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
fun NewProductDescription(onProductClickListener: () -> Unit){
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
                lineHeight = 30,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2)
            NewProductDescriptionText()
            ViewPopularProduct(onProductClickListener)
        }
}

@Composable
fun ViewPopularProduct(onProductClickListener: () -> Unit){
    val buttonStyle = Modifier
        .padding(top = 10.dp)
        .fillMaxWidth()
        .background(color = Color.Transparent)
        .height(45.dp)
    ButtonComponent(modifier = buttonStyle, buttonText = "View Product", borderStroke = BorderStroke((1).dp, color = Colors.primaryColor), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 16, shape = RoundedCornerShape(10.dp), textColor =  Colors.primaryColor, style = TextStyle(fontFamily = GGSansRegular)){
        onProductClickListener()
    }
}



@Composable
fun NewProductDescriptionText() {
    Column(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.Start,
    ) {
        val modifier = Modifier
            .fillMaxWidth()
        TextComponent(
            text = "Lorem ipsum dolor sit amet consectetuer adipiscing Aenean commodo ligula adipiscing Aenean commodo ligula adipiscing Aenean commodo ligula",
            fontSize = 17,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Color.Gray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            lineHeight = 25,
            textModifier = modifier,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }

}


@Composable
fun ProductNameAndPrice(){
    val columnModifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
        .clickable {}
        .fillMaxHeight()
        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment  = Alignment.CenterHorizontally,
        ) {
            val modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .wrapContentHeight()

            TextComponent(
                text = "Bloom Rose Oil And Argan Oil",
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                textStyle = TextStyle(),
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 20,
                maxLines = 2,
                textModifier = modifier
            )

            TextComponent(
                text = "$67,000",
                fontSize = 16,
                fontFamily = GGSansBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Black,
                lineHeight = 30,
                textModifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            )

        }
    }


@Composable
fun DiscountText() {

    val indicatorModifier = Modifier
        .padding(end = 15.dp, bottom = 20.dp)
        .background(color = Color.Transparent)
        .width(50.dp)
        .height(25.dp)
        .background(color =  Colors.darkPrimary)

        Box(modifier = indicatorModifier,
            contentAlignment = Alignment.Center){
            TextComponent(
                text = "-15%",
                fontSize = 15,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
        }
}
