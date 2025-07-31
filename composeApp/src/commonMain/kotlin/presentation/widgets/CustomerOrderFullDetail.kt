package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import applications.formatter.formatNumber
import domain.Models.PlacedOrderItemComponent
import presentation.viewmodels.MainViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors
import utils.calculatePlacedOrderTotalPrice

@Composable
fun OrderDetailList(itemList: ArrayList<PlacedOrderItemComponent>,currencyUnit: String, onAddReviewClicked: (Long) -> Unit) {
    val columnModifier = Modifier
        .padding(start = 5.dp, top = 5.dp, bottom = 10.dp)
        .verticalScroll(rememberScrollState())
        .background(color = Color.Transparent, shape = RoundedCornerShape(10.dp))
        .height(((130 * 3)+500).dp)

        Column(modifier = columnModifier,
            verticalArrangement = Arrangement.Top
        ) {

            Box(modifier = Modifier.padding(top = 10.dp).fillMaxWidth().fillMaxHeight()) {
                    OrderItemDetail(itemList,currencyUnit, onAddReviewClicked = {
                        onAddReviewClicked(it)
                    })
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 5.dp, start = 20.dp, end = 20.dp).height(50.dp).fillMaxWidth()
            ) {}
        }
}

@Composable
fun OrderItemDetail(itemList: ArrayList<PlacedOrderItemComponent>,currencyUnit: String, onAddReviewClicked: (Long) -> Unit) {
    val columnModifier = Modifier
        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
        .fillMaxHeight()
    Column(modifier = columnModifier,
        verticalArrangement = Arrangement.Top
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier.fillMaxWidth().height((150 * 3).dp),
            contentPadding = PaddingValues(6.dp)
        ) {
            itemsIndexed(items = itemList) { it, item ->
                OrderProductItemComponent(item,currencyUnit, onAddReviewClicked = {
                    onAddReviewClicked(it)
                })
            }
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 5.dp, start = 20.dp, end = 20.dp).height(50.dp).fillMaxWidth()
        ) {
            TextComponent(
                text = "Total",
                fontSize = 20,
                fontFamily = GGSansRegular,
                textStyle = TextStyle(),
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Black,
                textModifier = Modifier.padding(top = 5.dp).height(30.dp).fillMaxWidth(0.20f)
            )
            TextComponent(
                text = currencyUnit+formatNumber(calculatePlacedOrderTotalPrice(itemList)),
                fontSize = 20,
                fontFamily = GGSansRegular,
                textStyle = TextStyle(),
                textColor = Colors.primaryColor,
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Black,
                textModifier = Modifier.padding(top = 5.dp).height(30.dp).fillMaxWidth()
            )

        }
    }
}

@Composable
fun OrderProductItemComponent(placedOrderItemComponent: PlacedOrderItemComponent,currencyUnit: String, onAddReviewClicked: (Long) -> Unit) {
    val columnModifier = Modifier
        .padding(start = 5.dp, top = 10.dp, bottom = 10.dp)
        .height(90.dp)
    Row(modifier = columnModifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Box(modifier = Modifier.weight(1f).fillMaxHeight(), contentAlignment = Alignment.Center){
            OrderProductItemImage(placedOrderItemComponent)
        }
        Box(modifier = Modifier.weight(3f).fillMaxHeight(), contentAlignment = Alignment.Center){
            OrderProductItemName(placedOrderItemComponent, currencyUnit)
        }
        Box(modifier = Modifier.weight(1f).fillMaxHeight(), contentAlignment = Alignment.Center){
            TextComponent(
                text = "Add Review",
                fontSize = 13,
                fontFamily = GGSansSemiBold,
                textStyle = TextStyle(),
                textColor = Colors.primaryColor,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Medium,
                lineHeight = 25,
                textModifier = Modifier.fillMaxWidth().wrapContentHeight().clickable {
                    onAddReviewClicked(placedOrderItemComponent.productId)
                },
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
        }
    }
}


@Composable
fun OrderProductItemImage(placedOrderItemComponent: PlacedOrderItemComponent) {
    val imageModifier =
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    Card(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .background(color = Color.White)
            .height(50.dp)
            .width(50.dp),
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
                isAsync = true,
                imageModifier = imageModifier,
                imageRes = placedOrderItemComponent.imageUrl,
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Composable
fun OrderProductItemName(placedOrderItemComponent: PlacedOrderItemComponent, currencyUnit: String) {
    val columnModifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
        .fillMaxHeight()
    Column(
        modifier = columnModifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.Start,
    ) {

        val modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()

        TextComponent(
            text = placedOrderItemComponent.productName,
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Medium,
            lineHeight = 25,
            textModifier = modifier,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis)
        OrderProductItemQty(placedOrderItemComponent, currencyUnit)
    }
}


@Composable
fun OrderProductItemQty(placedOrderItemComponent: PlacedOrderItemComponent, currencyUnit: String) {
    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxHeight()
            .padding(top = 5.dp),
    ) {

        TextComponent(
            text = placedOrderItemComponent.itemCount.toString()+"x",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Color.LightGray,
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.Medium,
            lineHeight = 30,
            textModifier = Modifier
                .padding(end = 10.dp)
                .wrapContentSize())

        TextComponent(
            text = currencyUnit+formatNumber(placedOrderItemComponent.productPrice.toLong()),
            fontSize = 20,
            fontFamily = GGSansRegular,
            textStyle = TextStyle(),
            textColor = Colors.primaryColor,
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .wrapContentSize())
    }
}

