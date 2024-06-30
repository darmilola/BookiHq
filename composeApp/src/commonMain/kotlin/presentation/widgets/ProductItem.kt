package presentation.widgets

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import domain.Models.Product
import presentation.components.ButtonComponent
import presentations.components.ImageComponent
import presentations.components.TextComponent
import utils.calculateDiscount

@Composable
fun ProductItem(product: Product, onProductClickListener: (Product) -> Unit) {
    val columnModifier = Modifier
        .padding(start = 5.dp, top = 5.dp, bottom = 10.dp)
        .clickable {
            onProductClickListener(product)
        }
        .height(280.dp)
        Column(modifier = columnModifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ProductImage(product)
                ProductNameAndPrice(product)
            }
}



@Composable
fun HomeProductItem(product: Product, onProductClickListener: (Product) -> Unit) {
    val columnModifier = Modifier
        .padding(start = 5.dp, top = 5.dp, bottom = 10.dp)
        .clickable {
            onProductClickListener(product)
        }
        .height(200.dp)
        Row(modifier = columnModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            HomeProductImage(product)
            HomeProductDescription(product, onProductClickListener = {
                onProductClickListener(it)
            })
        }
    }


    @Composable
    fun ProductImage(product: Product) {
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
                    imageRes = product.productImages[0].imageUrl,
                    isAsync = true,
                    contentScale = ContentScale.Crop
                )
                if (product.isDiscounted) {
                    DiscountText(product)
                }
            }
        }
    }


@Composable
fun HomeProductImage(product: Product) {
    val imageModifier =
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    Card(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 5.dp)
            .background(color = Color.White)
            .height(200.dp)
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
                imageRes = product.productImages[0].imageUrl,
                contentScale = ContentScale.Crop,
                isAsync = true
            )
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .size(35.dp).background(color = Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
              ImageComponent(imageModifier = Modifier.size(16.dp), imageRes = "drawable/like_icon.png", colorFilter = ColorFilter.tint(color = Colors.pinkColor))
            }
        }
    }
}


@Composable
fun HomeProductDescription(product: Product,onProductClickListener: (Product) -> Unit){
    val columnModifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
        .fillMaxHeight()
        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment  = Alignment.Start,
        ) {
            TextComponent(
                text = product.productName,
                fontSize = 16,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 20,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2)
            HomeProductProductDescriptionText(product)
            ViewProductFromHome(product, onProductClickListener = {
                onProductClickListener(it)
            })
        }
}

@Composable
fun ViewProductFromHome(product: Product,onProductClickListener: (Product) -> Unit){
    val buttonStyle = Modifier
        .padding(top = 10.dp)
        .fillMaxWidth()
        .background(color = Color.Transparent)
        .height(40.dp)
    ButtonComponent(modifier = buttonStyle, buttonText = "View Product", borderStroke = BorderStroke((1).dp, color = Colors.primaryColor), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 14, shape = RoundedCornerShape(12.dp), textColor =  Colors.primaryColor, style = TextStyle(fontFamily = GGSansRegular)){
        onProductClickListener(product)
    }
}



@Composable
fun HomeProductProductDescriptionText(product: Product) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.Start,
    ) {
        val modifier = Modifier
            .fillMaxWidth()
        TextComponent(
            text = product.productDescription,
            fontSize = 15,
            fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Medium,
            lineHeight = 20,
            textModifier = modifier,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis)

    }

}


@Composable
fun ProductNameAndPrice(product: Product){
    val price = if(product.isDiscounted) product.discount else product.productPrice
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
                text = product.productName,
                fontSize = 16,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                lineHeight = 20,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textModifier = modifier
            )

            TextComponent(
                text = "$$price",
                fontSize = 16,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.primaryColor,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                lineHeight = 30,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textModifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight())
        }
    }


@Composable
fun DiscountText(product: Product) {

    val discount = calculateDiscount(price = product.productPrice, discount = product.discount)
    println(discount)
    val indicatorModifier = Modifier
        .padding(end = 15.dp, bottom = 20.dp)
        .background(color = Color.Transparent)
        .width(50.dp)
        .height(25.dp)
        .background(color =  Colors.darkPrimary)

        Box(modifier = indicatorModifier,
            contentAlignment = Alignment.Center){
            TextComponent(
                text = "-$discount%",
                fontSize = 15,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
        }
}
