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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Models.DeliveryLocation
import domain.Models.OrderItem
import domain.Models.OrderItemUIModel
import domain.Models.PaymentMethod
import domain.Models.Screens
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.components.StraightLine
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.dialogs.SuccessDialog
import presentation.viewmodels.ActionUIStates
import presentation.viewmodels.CartViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.ScreenUIStateViewModel
import presentation.widgets.CheckOutSummaryWidget
import presentation.widgets.ProductDeliveryAddressWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.PaymentMethodWidget
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import utils.calculateCheckoutSubTotal
import utils.calculateTotal


class CartScreen(private val mainViewModel: MainViewModel) : Tab, KoinComponent {

    private val cartPresenter: CartPresenter by inject()
    private var screenUiStateViewModel: ScreenUIStateViewModel? = null
    private var cartViewModel: CartViewModel? = null

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
        val creatingOrderProgress = remember { mutableStateOf(false) }
        val creatingOrderSuccess = remember { mutableStateOf(false) }
        val creatingOrderFailed = remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()


        if (screenUiStateViewModel == null) {
            screenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if(cartViewModel == null) {
            cartViewModel = kmpViewModel(
                factory = viewModelFactory {
                    CartViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        val cartItems = mainViewModel.unSavedOrders.collectAsState()

        val subtotal = calculateCheckoutSubTotal(cartItems.value)
        val total = calculateTotal(subtotal, cartViewModel!!.deliveryFee.value)
        cartViewModel!!.setTotal(total)
        cartViewModel!!.setSubTotal(subtotal)



        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {},
            content = {

                // View Contract Handler Initialisation
                val handler = CreateOrderScreenHandler(
                    cartPresenter,
                    onCreateOrderStarted = {
                        creatingOrderProgress.value = true
                    },
                    onCreateOrderSuccess = {
                        creatingOrderProgress.value = false
                        creatingOrderSuccess.value = true
                    },
                    onCreateOrderFailed = {
                        creatingOrderProgress.value = false
                        creatingOrderFailed.value = true
                    })
                handler.init()


                if (creatingOrderProgress.value) {
                    Box(modifier = Modifier.fillMaxWidth(0.90f)) {
                        LoadingDialog("Creating Order...")
                    }
                }
                else if (creatingOrderSuccess.value) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        SuccessDialog("Creating Order Successful", actionTitle = "Done", onConfirmation = {
                            mainViewModel.clearUnsavedOrders()
                            mainViewModel.clearCurrentOrderReference()
                            coroutineScope.launch {
                                mainViewModel.setScreenNav(
                                    Pair(
                                        Screens.CART.toPath(),
                                        Screens.MAIN_TAB.toPath()
                                    )
                                )
                            }
                        })
                    }
                }
                else if (creatingOrderFailed.value){
                    Box(modifier = Modifier.fillMaxWidth()) {
                        ErrorDialog("Creating Appointment Failed", actionTitle = "Retry", onConfirmation = {
                            val orderItemList = mainViewModel.unSavedOrders.value
                            val vendorId = mainViewModel.connectedVendor.value.vendorId
                            val userId = mainViewModel.currentUserInfo.value.userId
                            val orderReference = mainViewModel.currentOrderReference.value
                            val deliveryLocation = cartViewModel!!.deliveryLocation.value
                            val paymentMethod = cartViewModel!!.paymentMethod.value

                            cartPresenter.createOrder(orderItemList,vendorId!!,userId!!,orderReference,deliveryLocation,paymentMethod)

                        })
                    }
                }


                val rowModifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 15.dp)

                val colModifier = Modifier
                    .padding(top = 10.dp, end = 0.dp)
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
                            CartScreenTitle(cartItems.value.size)
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
                            .padding(end = 0.dp, bottom = 50.dp)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState())
                            .weight(1f, false)
                    ) {

                        PopulateCartItemList(mainViewModel,stackedSnackBarHostState)
                        ProductDeliveryAddressWidget(mainViewModel,
                            cartViewModel!!, onHomeSelectedListener = {

                                  cartViewModel!!.setDeliveryLocation(DeliveryLocation.HOME_DELIVERY.toPath())

                            }, onPickupSelectedListener = {

                                cartViewModel!!.setDeliveryLocation(DeliveryLocation.PICKUP.toPath())

                            })
                        StraightLine()
                        PaymentMethodWidget(onCashSelectedListener = {
                             cartViewModel!!.setPaymentMethod(PaymentMethod.PAYMENT_ON_DELIVERY.toPath())
                        }, onCardPaymentSelectedListener = {
                            cartViewModel!!.setPaymentMethod(PaymentMethod.CARD_PAYMENT.toPath())
                        })
                        StraightLine()
                        CheckOutSummaryWidget(cartViewModel!!, onCreateOrderStarted = {

                             val orderItemList = mainViewModel.unSavedOrders.value
                             val vendorId = mainViewModel.connectedVendor.value.vendorId
                             val userId = mainViewModel.currentUserInfo.value.userId
                             val orderReference = mainViewModel.currentOrderReference.value
                             val deliveryLocation = cartViewModel!!.deliveryLocation.value
                             val paymentMethod = cartViewModel!!.paymentMethod.value

                             cartPresenter.createOrder(orderItemList,vendorId!!,userId!!,orderReference,deliveryLocation,paymentMethod)

                        })

                    }

                }
            })

    }

    @Composable
    private fun PopulateCartItemList(mainViewModel: MainViewModel,stackedSnackBarHostState: StackedSnakbarHostState) {

        val cartItems = mainViewModel.unSavedOrders.collectAsState()

        if (cartItems.value.isNotEmpty()){


        val cartList = cartItems.value
        val selectedItem = remember { mutableStateOf(OrderItem()) }
        var showProductDetailBottomSheet by remember { mutableStateOf(false) }
        var orderItemUIModel by remember {
            mutableStateOf(
                OrderItemUIModel(
                    selectedItem.value,
                    cartList
                )
            )
        }


        if (showProductDetailBottomSheet) {
            ProductDetailBottomSheet(mainViewModel,isViewedFromCart = true,
                selectedProduct = orderItemUIModel.selectedItem!!,
                onDismiss = { isAddToCart, item -> showProductDetailBottomSheet = false },
                onRemoveFromCart = { orderItem ->
                    showProductDetailBottomSheet = false
                    orderItemUIModel.itemList.remove(orderItem)
                    orderItemUIModel = orderItemUIModel.copy(selectedItem = OrderItem(), itemList = orderItemUIModel.itemList)
                    mainViewModel.setCurrentUnsavedOrders(orderItemUIModel.itemList)
                        ShowSnackBar(title = "Product Removed",
                            description = "Product has been Removed from Cart",
                            actionLabel = "",
                            duration = StackedSnackbarDuration.Short,
                            snackBarType = SnackBarType.SUCCESS,
                            stackedSnackBarHostState,
                            onActionClick = {})
                     })
                }


            LazyColumn(
                modifier = Modifier.height((180 * cartItems.value.size).dp),
                userScrollEnabled = false
            ) {
                items(key = { it -> it.itemReference}, items = orderItemUIModel.itemList) { item ->
                    CartItem(item, onProductClickListener = {
                        orderItemUIModel = orderItemUIModel.copy(
                            selectedItem = it,
                            itemList = mainViewModel.unSavedOrders.value
                        )
                        showProductDetailBottomSheet = true
                    }, onItemCountChanged = {
                        // Increase Order Item Count
                        orderItemUIModel = orderItemUIModel.copy(selectedItem = it,
                            itemList = ArrayList(mainViewModel.unSavedOrders.value.map { it2 ->
                                if (it2.itemReference == it.itemReference) {
                                    it2.copy(
                                        itemCount = it.itemCount
                                    )
                                } else {
                                    it2.copy(
                                        itemCount = it2.itemCount
                                    )
                                }
                            }
                        ))
                        mainViewModel.setCurrentUnsavedOrders(orderItemUIModel.itemList)

                    }, onItemRemovedFromCart = {
                        orderItemUIModel.itemList.remove(it)
                        orderItemUIModel = orderItemUIModel.copy(
                            selectedItem = OrderItem(),
                            itemList = orderItemUIModel.itemList
                        )
                        ShowSnackBar(title = "Product Removed",
                            description = "Product has been Removed from Cart",
                            actionLabel = "",
                            duration = StackedSnackbarDuration.Short,
                            snackBarType = SnackBarType.SUCCESS,
                            stackedSnackBarHostState,
                            onActionClick = {})
                        mainViewModel.setCurrentUnsavedOrders(orderItemUIModel.itemList)
                    })
                    StraightLine()
                }
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
    fun CartScreenTitle(itemCount: Int){
            TextComponent(
                text = "Cart($itemCount)",
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ProductDetailBottomSheet(mainViewModel: MainViewModel, isViewedFromCart: Boolean = false, selectedProduct: OrderItem, onDismiss: (isAddToCart: Boolean, OrderItem) -> Unit, onRemoveFromCart: (OrderItem) -> Unit) {
        val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            modifier = Modifier.padding(top = 20.dp),
            onDismissRequest = { onDismiss(false, selectedProduct) },
            sheetState = modalBottomSheetState,
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
            containerColor = Color(0xFFF3F3F3),
            dragHandle = {},
        ) {
            ProductDetailContent(mainViewModel,isViewedFromCart,selectedProduct, onAddToCart = {
                onDismiss(it,selectedProduct)
            }, onRemoveFromCart = {
                onRemoveFromCart(it)
            })
        }
    }


}

class CreateOrderScreenHandler(
    private val cartPresenter: CartPresenter,
    private val onCreateOrderStarted: () -> Unit,
    private val onCreateOrderSuccess: () -> Unit,
    private val onCreateOrderFailed: () -> Unit
) : CartContract.View {
    fun init() {
        cartPresenter.registerUIContract(this)
    }


    override fun showLce(uiState: ActionUIStates, message: String) {
        uiState.let {
            when {
                it.isLoading -> {
                    onCreateOrderStarted()
                }
                it.isSuccess -> {
                   onCreateOrderSuccess()
                }
                it.isFailed -> {
                    onCreateOrderFailed()
                }

            }
        }
    }
}





