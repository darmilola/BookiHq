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
import domain.Enums.DeliveryMethodEnum
import domain.Models.OrderItem
import domain.Models.OrderItemUIModel
import domain.Enums.PaymentMethod
import domain.Enums.Screens
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.components.StraightLine
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.dialogs.SuccessDialog
import presentation.viewmodels.PerformedActionUIStateViewModel
import UIStates.AppUIStates
import applications.date.getDay
import applications.date.getMonth
import applications.date.getYear
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import dev.icerock.moko.parcelize.Parcelable
import domain.Models.PlatformNavigator
import kotlinx.serialization.Transient
import presentation.viewmodels.CartViewModel
import presentation.viewmodels.MainViewModel
import presentation.widgets.CartItem
import presentation.widgets.CheckOutSummaryWidget
import presentation.widgets.ProductDeliveryAddressWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.PaymentMethodWidget
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import utils.calculateCartCheckoutSubTotal
import utils.calculateTotal


@Parcelize
class CartTab(val platformNavigator: PlatformNavigator) : Tab, KoinComponent, Parcelable {

    @Transient
    private val cartPresenter: CartPresenter by inject()
    @Transient
    private var performedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient
    private var cartViewModel: CartViewModel? = null
    @Transient
    private var mainViewModel: MainViewModel? = null

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

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }


    @Composable
    override fun Content() {

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )
        val coroutineScope = rememberCoroutineScope()

        if (performedActionUIStateViewModel == null) {
            performedActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
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

        cartViewModel!!.setPaymentMethod(PaymentMethod.CARD_PAYMENT.toPath())
        val cartItems = mainViewModel!!.unSavedOrders.collectAsState()
        val cartSize = mainViewModel!!.unSavedOrderSize.collectAsState()
        val deliveryMethod = cartViewModel!!.deliveryMethod.collectAsState()
        val vendorDeliveryFee = mainViewModel!!.connectedVendor.value.deliveryFee
        if (deliveryMethod.value == DeliveryMethodEnum.MOBILE.toPath()){
            cartViewModel!!.setDeliveryFee(vendorDeliveryFee)
        }
        else{
            cartViewModel!!.setDeliveryFee(0L)
        }

        val createOrderActionUiState = performedActionUIStateViewModel!!.uiStateInfo.collectAsState()

        val subtotal = calculateCartCheckoutSubTotal(cartItems.value)
        val total = calculateTotal(subtotal, cartViewModel!!.deliveryFee.value)
        cartViewModel!!.setTotal(total)
        cartViewModel!!.setSubTotal(subtotal)

        if (cartSize.value == 0) {
            mainViewModel!!.setScreenNav(
                Pair(
                    Screens.CART.toPath(),
                    Screens.MAIN_TAB.toPath()
                )
            )
        }



        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {},
            content = {

                // View Contract Handler Initialisation
                val handler = CreateOrderScreenHandler(
                    cartPresenter,
                    performedActionUIStateViewModel!!)
                handler.init()


                if (createOrderActionUiState.value.isLoading) {
                    Box(modifier = Modifier.fillMaxWidth(0.90f)) {
                        LoadingDialog("Creating Order...")
                    }
                }
                else if (createOrderActionUiState.value.isSuccess) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        SuccessDialog("Creating Order Successful", actionTitle = "Done", onConfirmation = {
                            mainViewModel!!.clearUnsavedOrders()
                            mainViewModel!!.clearCurrentOrderReference()
                            coroutineScope.launch {
                                mainViewModel!!.setScreenNav(
                                    Pair(
                                        Screens.CART.toPath(),
                                        Screens.MAIN_TAB.toPath()
                                    )
                                )
                            }
                        })
                    }
                }
                else if (createOrderActionUiState.value.isFailed) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        ErrorDialog("Creating Order Failed", actionTitle = "Retry", onConfirmation = {})
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
                            leftTopBarItem(mainViewModel!!)
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

                        PopulateCartItemList(mainViewModel!!,stackedSnackBarHostState)
                        ProductDeliveryAddressWidget(mainViewModel!!,
                            cartViewModel!!, onMobileSelectedListener = {

                                  cartViewModel!!.setDeliveryMethod(DeliveryMethodEnum.MOBILE.toPath())

                            }, onPickupSelectedListener = {

                                cartViewModel!!.setDeliveryMethod(DeliveryMethodEnum.PICKUP.toPath())

                            })
                        StraightLine()
                        PaymentMethodWidget(onCashSelectedListener = {
                             cartViewModel!!.setPaymentMethod(PaymentMethod.PAYMENT_ON_DELIVERY.toPath())
                        }, onCardPaymentSelectedListener = {
                            cartViewModel!!.setPaymentMethod(PaymentMethod.CARD_PAYMENT.toPath())
                        })
                        StraightLine()
                        CheckOutSummaryWidget(cartViewModel!!,onCreateOrderStarted = {
                             val orderItemList = mainViewModel!!.unSavedOrders.value
                             val vendorId = mainViewModel!!.connectedVendor.value.vendorId
                             val userId = mainViewModel!!.currentUserInfo.value.userId
                             val deliveryLocation = cartViewModel!!.deliveryMethod.value
                             val paymentMethod = cartViewModel!!.paymentMethod.value
                             val year = getYear()
                             val month = getMonth()
                             val day = getDay()

                             cartPresenter.createOrder(orderItemList,vendorId!!,userId!!,deliveryLocation,paymentMethod, day, month, year, user = mainViewModel!!.currentUserInfo.value, vendor = mainViewModel!!.connectedVendor.value,
                                 paymentAmount = cartViewModel!!.total.value.toDouble(), platformNavigator = platformNavigator)

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
                onDismiss = { isAddToCart, item -> showProductDetailBottomSheet = false })

        }


            LazyColumn(
                modifier = Modifier.height((180 * cartItems.value.size).dp),
                userScrollEnabled = true
            ) {
                items(key = { it -> it.itemKey}, items = orderItemUIModel.itemList) { item ->
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
                                if (it2.itemKey == it.itemKey) {
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
                        mainViewModel.setUnsavedOrderSize(orderItemUIModel.itemList.size)
                        val subtotal = calculateCartCheckoutSubTotal(cartItems.value)
                        val total = calculateTotal(subtotal, cartViewModel!!.deliveryFee.value)
                        cartViewModel!!.setTotal(total)
                        cartViewModel!!.setSubTotal(subtotal)

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
                         mainViewModel.setUnsavedOrderSize(orderItemUIModel.itemList.size)
                         val subtotal = calculateCartCheckoutSubTotal(cartItems.value)
                         val total = calculateTotal(subtotal, cartViewModel!!.deliveryFee.value)
                         cartViewModel!!.setTotal(total)
                         cartViewModel!!.setSubTotal(subtotal)
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
    fun ProductDetailBottomSheet(mainViewModel: MainViewModel, isViewedFromCart: Boolean = false, selectedProduct: OrderItem, onDismiss: (isAddToCart: Boolean, OrderItem) -> Unit) {
        val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            modifier = Modifier.padding(top = 20.dp),
            onDismissRequest = { onDismiss(false, selectedProduct) },
            sheetState = modalBottomSheetState,
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
            containerColor = Color(0xFFF3F3F3),
            dragHandle = {},
        ) {
            ProductDetailContent(mainViewModel,isViewedFromCart,selectedProduct, onAddToCart = { onDismiss(it,selectedProduct) })
        }
    }


}

class CreateOrderScreenHandler(
    private val cartPresenter: CartPresenter,
    private val uiStateViewModel: PerformedActionUIStateViewModel
) : CartContract.View {
    fun init() {
        cartPresenter.registerUIContract(this)
    }


    override fun showLce(appUIStates: AppUIStates) {
        uiStateViewModel.switchActionUIState(appUIStates)
    }
}





