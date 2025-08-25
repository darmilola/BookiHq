package presentation.widgets

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import applications.formatter.formatNumber
import domain.Enums.Currency
import domain.Models.OrderItem
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun CartItem(orderItem: OrderItem,currencyUnit: String,onProductClickListener: (OrderItem) -> Unit, onItemCountChanged:(OrderItem) -> Unit, onItemRemovedFromCart: (OrderItem) -> Unit) {
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
            CartItemDetail(orderItem,currencyUnit,onItemCountChanged = {
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
fun CartItemDetail(orderItem: OrderItem,currencyUnit: String,onItemCountChanged:(OrderItem) -> Unit, onItemRemovedFromCart: (OrderItem) -> Unit) {
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
                textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                lineHeight = 20,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textModifier = modifier
            )
            CartProductPriceInfoContent(orderItem,currencyUnit)
            productItemIncrementDecrementWidget(orderItem,isFromCart = true,onItemCountChanged = {
                onItemCountChanged(it)
            }, onItemRemovedFromCart = {
                onItemRemovedFromCart(it)
            })
        }
    }

@Composable
fun CartProductPriceInfoContent(orderItem: OrderItem,currencyUnit: String,) {
    val product = orderItem.itemProduct
    Row(
        modifier = Modifier
            .height(40.dp)
            .padding(top = 10.dp)
            .fillMaxHeight(),
    ) {
        TextComponent(
            text = "$currencyUnit${formatNumber(product!!.productPrice)}",
            fontSize = 16,
            textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge,
            textColor = theme.Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            lineHeight = 20,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textModifier = Modifier
                .wrapContentSize())

    }
}



