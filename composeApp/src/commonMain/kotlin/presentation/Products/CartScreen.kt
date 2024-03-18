package presentation.Products

import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import domain.Models.Product
import presentation.components.StraightLine
import presentation.main.MainTab
import presentation.viewmodels.MainViewModel
import presentation.widgets.CheckOutSummaryWidget
import presentation.widgets.ProductDeliveryAddressWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.PaymentMethodWidget
import presentations.components.TextComponent


class CartScreen(private val mainViewModel: MainViewModel) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Cart"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title
                )
            }
        }

    @Composable
    override fun Content() {

        val rowModifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 15.dp)

        val colModifier = Modifier
            .padding(top = 40.dp, end = 0.dp)
            .fillMaxSize()

        Column(modifier = colModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = rowModifier,
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {


                Box(modifier =  Modifier.weight(1.0f)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart) {
                    leftTopBarItem(mainViewModel)
                }

                Box(modifier =  Modifier.weight(3.0f)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                    contentAlignment = Alignment.Center) {
                    CartScreenTitle()
                }

                Box(modifier =  Modifier.weight(1.0f)
                    .fillMaxWidth(0.20f)
                    .fillMaxHeight(),
                    contentAlignment = Alignment.Center) {
                    rightTopBarItem()
                }

            }

           val carList: ArrayList<Int> = arrayListOf()

            carList.add(1)
            carList.add(1)
            carList.add(1)
            carList.add(1)


            Column(modifier = Modifier
                .padding(top = 10.dp, end = 0.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .weight(1f, false)) {

                PopulateCartItemList(carList)
                ProductDeliveryAddressWidget(mainViewModel)
                StraightLine()
                PaymentMethodWidget()
                StraightLine()
                CheckOutSummaryWidget()

            }

        }

    }

    @Composable
    private fun PopulateCartItemList(cartItems: List<Int>){
        var showSheet by remember { mutableStateOf(false) }

        if (showSheet) {
            ProductDetailBottomSheet(Product()) {
                showSheet = false
            }
        }
        LazyColumn(modifier = Modifier.height((180 * cartItems.size).dp)) {
            items(cartItems) {item ->
                CartItem(onProductClickListener = {
                    showSheet = true
                })
                StraightLine()
            }
        }
    }

    @Composable
    fun leftTopBarItem(mainViewModel: MainViewModel) {
        val navigator = LocalTabNavigator.current
        PageBackNavWidget(){
          //  navigator.current = MainTab(mainViewModel)
        }
    }


    @Composable
    fun CartScreenTitle(){
            TextComponent(
                text = "Cart(10)",
                fontSize = 20,
                fontFamily = GGSansSemiBold,
                textStyle = TextStyle(),
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
            )
        }


    @Composable
    fun rightTopBarItem() {}

}

