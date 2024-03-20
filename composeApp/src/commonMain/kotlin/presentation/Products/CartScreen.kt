package presentation.Products

import GGSansSemiBold
import StackedSnackbarHost
import StackedSnakbarHostState
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
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import domain.Models.OrderItem
import domain.Models.OrderItemUIModel
import domain.Models.Screens
import presentation.components.StraightLine
import presentation.viewmodels.MainViewModel
import presentation.widgets.CheckOutSummaryWidget
import presentation.widgets.ProductDeliveryAddressWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.PaymentMethodWidget
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentations.components.TextComponent
import rememberStackedSnackbarHostState


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

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {},
            content = {

                val rowModifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 15.dp)

                val colModifier = Modifier
                    .padding(top = 40.dp, end = 0.dp)
                    .fillMaxSize()

                Column(
                    modifier = colModifier,
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = rowModifier,
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier.weight(1.0f)
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            leftTopBarItem(mainViewModel)
                        }

                        Box(
                            modifier = Modifier.weight(3.0f)
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            CartScreenTitle()
                        }

                        Box(
                            modifier = Modifier.weight(1.0f)
                                .fillMaxWidth(0.20f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            rightTopBarItem()
                        }

                    }


                    Column(
                        modifier = Modifier
                            .padding(top = 10.dp, end = 0.dp)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState())
                            .weight(1f, false)
                    ) {

                        PopulateCartItemList(mainViewModel,stackedSnackBarHostState)
                        ProductDeliveryAddressWidget(mainViewModel)
                        StraightLine()
                        PaymentMethodWidget()
                        StraightLine()
                        CheckOutSummaryWidget()

                    }

                }
            })

    }

    @Composable
    private fun PopulateCartItemList(mainViewModel: MainViewModel,stackedSnackBarHostState: StackedSnakbarHostState){

        val cartItems = mainViewModel.unSavedOrders.collectAsState()
        val selectedProduct = remember { mutableStateOf(OrderItem()) }
        var showProductDetailBottomSheet by remember { mutableStateOf(false) }
        var orderItemUIModel by remember {
            mutableStateOf(
                OrderItemUIModel(
                    selectedProduct.value,
                    cartItems.value
                )
            )
        }


        if (showProductDetailBottomSheet) {
            ProductDetailBottomSheet(mainViewModel,isViewedFromCart = true,
                cartItem = orderItemUIModel.selectedItem!!,
                onDismiss = { showProductDetailBottomSheet = false },
                onRemoveFromCart = {isRemoved ->
                    showProductDetailBottomSheet = false
                    if(isRemoved){
                        ShowSnackBar(title = "Product Removed",
                            description = "Product has been Removed from Cart",
                            actionLabel = "",
                            duration = StackedSnackbarDuration.Short,
                            snackBarType = SnackBarType.SUCCESS,
                            stackedSnackBarHostState,
                            onActionClick = {})
                    }

                })
        }

        LazyColumn(modifier = Modifier.height((180 * cartItems.value.size).dp)) {
            items(orderItemUIModel.itemList) {item ->
                CartItem(item,onProductClickListener = {
                    selectedProduct.value = it
                    showProductDetailBottomSheet = true
                }, onItemCountChanged = {
                    selectedProduct.value = it
                    orderItemUIModel = orderItemUIModel.copy(selectedItem = selectedProduct.value,
                        itemList = mainViewModel.unSavedOrders.value.map { it2 ->
                            if(it2.itemReference == it.itemReference) {
                                it2.copy(
                                    itemCount = it.itemCount
                                )
                            }
                            else{
                                it2.copy(
                                    itemCount = it2.itemCount
                                )
                            }
                        })

                }, onItemRemovedFromCart = {
                    orderItemUIModel.itemList.toMutableList().remove(it)
                    mainViewModel.setCurrentUnsavedOrders(orderItemUIModel.itemList.toMutableList())
                })
                StraightLine()
            }
        }
    }

    @Composable
    fun leftTopBarItem(mainViewModel: MainViewModel) {
        PageBackNavWidget {
            mainViewModel.setScreenNav(
                Pair(
                    Screens.CART.toPath(),
                    Screens.MAIN_TAB.toPath()
                )
            )
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

