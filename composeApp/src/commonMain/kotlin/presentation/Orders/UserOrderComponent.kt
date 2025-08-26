package presentation.Orders

import GGSansRegular
import androidx.compose.foundation.BorderStroke
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import applications.formatter.formatNumber
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.Enums.OrderStatusEnum
import domain.Models.CustomerOrder
import domain.Models.PlacedOrderItemComponent
import kotlinx.serialization.json.Json
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent
import utils.calculatePlacedOrderTotalPrice

@Composable
fun UserOrderComponent(mainViewModel: MainViewModel, customerOrder: CustomerOrder, reviewPerformedActionUIStateViewModel: PerformedActionUIStateViewModel) {

    val itemList =
        Json.decodeFromString<ArrayList<PlacedOrderItemComponent>>(customerOrder.orderItems?.orderItemJson!!)
    val totalCost = calculatePlacedOrderTotalPrice(itemList)
    val navigator = LocalNavigator.currentOrThrow
    val currencyUnit = mainViewModel.displayCurrencyUnit.value

    val boxBgModifier =
        Modifier
            .padding(bottom = 5.dp, top = 5.dp, start = 10.dp, end = 10.dp)
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color.White)
            .border(
                border = BorderStroke(1.4.dp, Colors.lightGray),
                shape = RoundedCornerShape(10.dp)
            )

    Box(modifier = boxBgModifier) {
        val columnModifier = Modifier
            .padding(start = 10.dp, top = 25.dp, bottom = 10.dp, end = 10.dp)
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .height(250.dp)
        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                StraightLine()

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(50.dp).fillMaxWidth()
                ) {

                    TextComponent(
                        text = getOrderStatusDisplay(customerOrder.orderStatus.toString()),
                        fontSize = 18,
                        textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                        textColor = Colors.primaryColor,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.SemiBold,
                        textModifier = Modifier.wrapContentHeight().fillMaxWidth(0.50f)
                    )

                    Box(
                        modifier = Modifier.fillMaxWidth().clickable {
                            mainViewModel.setOrderItemComponents(itemList)
                            val details = OrderDetails()
                            details.setMainViewModel(mainViewModel)
                            details.setActionUiStateViewModel(reviewPerformedActionUIStateViewModel)
                            navigator.push(details)
                        },
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        ImageComponent(
                            imageModifier = Modifier.size(24.dp),
                            imageRes = "drawable/forward_arrow.png",
                            colorFilter = ColorFilter.tint(color = Colors.primaryColor)
                        )
                    }
                }
                StraightLine()
            }
            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                modifier = Modifier.padding(top = 10.dp).fillMaxWidth().height(120.dp),
                contentPadding = PaddingValues(6.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(items = itemList) { it, item ->
                    OrderItemImage(item)
                }
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 5.dp, start = 15.dp, end = 15.dp).height(50.dp)
                    .fillMaxWidth()
            ) {
                TextComponent(
                    text = "Total",
                    fontSize = 20,
                    textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                    textColor = theme.Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.SemiBold,
                    textModifier = Modifier.padding(top = 5.dp).height(30.dp).fillMaxWidth(0.20f)
                )
                TextComponent(
                    text = "$currencyUnit${formatNumber(totalCost)}",
                    fontSize = 20,
                    textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                    textColor = theme.Colors.darkPrimary,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.SemiBold,
                    textModifier = Modifier.padding(top = 5.dp).height(30.dp).fillMaxWidth()
                )

            }
            StraightLine()
        }
    }
}

fun getOrderStatusDisplay(orderStatus: String): String{
   val orderStatusDisplay =  when(orderStatus){
        OrderStatusEnum.DELIVERED.toPath() -> "Order Delivered"
        OrderStatusEnum.CANCELLED.toPath() -> "Order Cancelled"
        OrderStatusEnum.PROCESSING.toPath() -> "Order Processing"
        else -> ""

    }
    return orderStatusDisplay
}


@Composable
fun StraightLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = Color(color = 0x40CCCCCC))
    ) {
    }
}

@Composable
fun OrderItemImage(placedOrderItemComponent: PlacedOrderItemComponent) {
    val imageModifier = Modifier
            .height(100.dp)
            .width(100.dp)
            .clip(RoundedCornerShape(10.dp))

    Box(contentAlignment = Alignment.Center) {
            ImageComponent(
                imageModifier = imageModifier,
                isAsync = true,
                imageRes = placedOrderItemComponent.imageUrl!!,
                contentScale = ContentScale.Crop)
        }

}

