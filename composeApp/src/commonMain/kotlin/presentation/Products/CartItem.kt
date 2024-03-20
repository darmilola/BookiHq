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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.Models.OrderItem
import presentation.widgets.CartIncrementDecrementWidget
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun CartItem(orderItem: OrderItem, onProductClickListener: (OrderItem) -> Unit, onItemCountChanged:(OrderItem) -> Unit, onItemRemovedFromCart: (OrderItem) -> Unit) {
    val columnModifier = Modifier
        .padding(start = 5.dp, top = 10.dp, bottom = 10.dp)
        .clickable {
            onProductClickListener(orderItem)
        }
        .height(160.dp)
        Row(modifier = columnModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CartItemImage(orderItem.itemProduct?.productImages!![0].imageUrl)
            CartItemDetail(orderItem, onItemCountChanged = {
                 onItemCountChanged(it)
            }, onItemRemovedFromCart = {
                 onItemRemovedFromCart(it)
            })
        }
    }




@Composable
fun CartItemImage(imageUrl: String) {
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
                imageRes = imageUrl,
                isAsync = true,
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Composable
fun CartItemDetail(orderItem: OrderItem,onItemCountChanged:(OrderItem) -> Unit, onItemRemovedFromCart: (OrderItem) -> Unit) {
    val orderedProduct = orderItem.itemProduct
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
                text = orderedProduct?.productName!!,
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
            CartProductPriceInfoContent(orderItem)
            CartIncrementDecrementWidget(orderItem,isFromCart = true,onItemCountChanged = {
                onItemCountChanged(it)
            }, onItemRemovedFromCart = {
                onItemRemovedFromCart(it)
            })
        }
    }

@Composable
fun CartProductPriceInfoContent(orderItem: OrderItem) {
    val product = orderItem.itemProduct
    val price = if(product?.isDiscounted == true) product.discount else product?.productPrice
    Row(
        modifier = Modifier
            .height(40.dp)
            .padding(top = 10.dp)
            .fillMaxHeight(),
    ) {
        TextComponent(
            text = "$$price",
            fontSize = 16,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Colors.primaryColor,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            lineHeight = 20,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textModifier = Modifier
                .wrapContentSize())

        if (product?.isDiscounted == true) {
            TextComponent(
                text = "$"+product.productPrice,
                fontSize = 14,
                textStyle = TextStyle(textDecoration = TextDecoration.LineThrough),
                textColor = Color.LightGray,
                fontFamily = GGSansSemiBold,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Medium,
                lineHeight = 20,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textModifier = Modifier
                    .wrapContentSize().padding(start = 10.dp)
            )
        }

    }
}



