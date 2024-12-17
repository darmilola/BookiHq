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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Enums.DeliveryMethodEnum
import domain.Models.OrderItem
import domain.Models.OrderItemUIModel
import domain.Enums.PaymentMethod
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.components.StraightLine
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.dialogs.SuccessDialog
import presentation.viewmodels.PerformedActionUIStateViewModel
import UIStates.AppUIStates
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.IntOffset
import androidx.room.RoomDatabase
import applications.date.getDay
import applications.date.getMonth
import applications.date.getYear
import applications.room.AppDatabase
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.ActionType
import domain.Enums.CustomerPaymentEnum
import domain.Enums.VendorEnum
import domain.Models.PaymentAuthorizationResult
import domain.Models.PaymentCard
import domain.Models.PlatformNavigator
import domain.Models.Product
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Transient
import presentation.Screens.AddDebitCardScreen
import presentation.dialogs.AddDebitCardDialog
import presentation.payment.PaymentContract
import presentation.payment.PaymentPresenter
import presentation.profile.EditProfile
import presentation.viewmodels.CartViewModel
import presentation.viewmodels.MainViewModel
import presentation.widgets.CartItem
import presentation.widgets.CheckOutSummaryWidget
import presentation.widgets.ProductDeliveryAddressWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.PaymentCardBottomSheet
import presentation.widgets.PaymentMethodWidget
import presentation.widgets.ProductDetailBottomSheet
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import utils.ParcelableScreen
import utils.calculateCartCheckoutSubTotal
import utils.calculateTotal


@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class Cart(val platformNavigator: PlatformNavigator) : ParcelableScreen, KoinComponent, ScreenTransition {

    @Transient
    private val cartPresenter: CartPresenter by inject()
    @Transient
    private val paymentPresenter: PaymentPresenter by inject()
    @Transient
    private var createOrderActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient
    private var paymentActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient
    private var cartViewModel: CartViewModel? = null
    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null
    @Transient var cardList = listOf<PaymentCard>()
    private var selectedCard: PaymentCard? = null
    private var customerPaidAmount: Long = 0L

    override val key: ScreenKey = uniqueScreenKey

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }




    @Composable
    override fun Content() {
        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce)

        if (createOrderActionUIStateViewModel == null) {
            createOrderActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (paymentActionUIStateViewModel == null) {
            paymentActionUIStateViewModel = kmpViewModel(
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
        val completeProfile = remember { mutableStateOf(false) }
        val paystackPaymentFailed = remember { mutableStateOf(false) }
        val userProfile = mainViewModel!!.currentUserInfo.value
        val cartItems = mainViewModel!!.unSavedOrders.collectAsState()
        val cartSize = mainViewModel!!.unSavedOrderSize.collectAsState()
        val deliveryMethod = mainViewModel!!.deliveryMethod.collectAsState()
        val isProfileCompleted = userProfile.address.trim().isNotEmpty() && userProfile.contactPhone.trim().isNotEmpty()
        val vendorDeliveryFee = mainViewModel!!.connectedVendor.value.deliveryFee
        if (deliveryMethod.value == DeliveryMethodEnum.MOBILE.toPath()){
            cartViewModel!!.setDeliveryFee(vendorDeliveryFee)
        }
        else{
            cartViewModel!!.setDeliveryFee(0L)
        }

        LaunchedEffect(true) {

            if (!isProfileCompleted){
                ShowSnackBar(title = "Only Pickup is Available",
                    description = "Please Complete Your Profile for Mobile Delivery",
                    actionLabel = "Complete Profile",
                    duration = StackedSnackbarDuration.Long,
                    snackBarType = SnackBarType.INFO,
                    stackedSnackBarHostState,
                    onActionClick = {
                        completeProfile.value = true
                    })
            }

        }

        if (completeProfile.value){
            completeProfile.value = false
            val editProfile = EditProfile(platformNavigator)
            editProfile.setMainViewModel(mainViewModel!!)
            editProfile.setDatabaseBuilder(databaseBuilder)
            val nav = LocalNavigator.currentOrThrow
            nav.push(editProfile)
        }

        val actionUiState = createOrderActionUIStateViewModel!!.uiStateInfo.collectAsState()
        val paymentActionUiState = paymentActionUIStateViewModel!!.paymentUiStateInfo.collectAsState()

        val subtotal = calculateCartCheckoutSubTotal(cartItems.value)
        val total = calculateTotal(subtotal, cartViewModel!!.deliveryFee.value)
        cartViewModel!!.setTotal(total)
        cartViewModel!!.setSubTotal(subtotal)

        val navigator = LocalNavigator.currentOrThrow
        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value || cartSize.value == 0){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }

        val openAddDebitCardDialog = remember { mutableStateOf(false) }

        when {
            openAddDebitCardDialog.value -> {
                AddDebitCardDialog(databaseBuilder, onDismissRequest = {
                    openAddDebitCardDialog.value = false
                }, onConfirmation = {
                    openAddDebitCardDialog.value = false
                })
            }
        }



        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {},
            content = {

                LaunchedEffect(true) {
                    mainViewModel!!.setDeliveryMethod(DeliveryMethodEnum.PICKUP.toPath())
                }

                val customerEmail = CustomerPaymentEnum.PAYMENT_EMAIL.toPath()
                val paymentAmount = cartViewModel!!.total.value
                val handler = CreateOrderScreenHandler(
                    cartPresenter,
                    paymentPresenter = paymentPresenter,
                    createOrderActionUIStateViewModel!!,
                    paymentActionUIStateViewModel!!,
                    onAuthorizationSuccessful = {
                        if (it.status) {
                            platformNavigator.startPaymentProcess(paymentAmount = (paymentAmount * 100).toString(),
                                customerEmail = customerEmail,
                                accessCode = it.paymentAuthorizationData.accessCode,
                                currency = mainViewModel!!.displayCurrencyPath.value,
                                cardNumber = selectedCard!!.cardNumber,
                                expiryMonth = selectedCard!!.expiryMonth,
                                expiryYear = selectedCard!!.expiryYear,
                                cvv = selectedCard!!.cvv,
                                onPaymentLoading = {},
                                onPaymentSuccessful = {
                                    paystackPaymentFailed.value = false
                                     customerPaidAmount = paymentAmount
                                     createOrder()
                                },
                                onPaymentFailed = {
                                    paystackPaymentFailed.value = true
                                })
                        }
                    })
                handler.init()


                if (actionUiState.value.isLoading) {
                    Box(modifier = Modifier.fillMaxWidth(0.90f)) {
                        LoadingDialog(actionUiState.value.loadingMessage)
                    }
                }
                else if (actionUiState.value.isSuccess) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                            SuccessDialog(
                                "Creating Order Successful",
                                actionTitle = "Done",
                                onConfirmation = {
                                    mainViewModel!!.clearUnsavedOrders()
                                    mainViewModel!!.clearCurrentOrderReference()
                                    navigator.pop()
                                })
                    }
                }
                else if (actionUiState.value.isFailed) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        ErrorDialog("Creating Order Failed", actionTitle = "Retry",
                            onConfirmation = {
                                 createOrder()
                            })
                    }
                }

                if (paymentActionUiState.value.isLoading) {
                    Box(modifier = Modifier.fillMaxWidth(0.90f)) {
                        LoadingDialog("Processing Payment")
                    }
                }
                else if (paymentActionUiState.value.isFailed) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        ErrorDialog("Payment Authentication Failed", actionTitle = "Retry", onConfirmation = {})
                    }
                }

                if (paystackPaymentFailed.value){
                    Box(modifier = Modifier.fillMaxWidth()) {
                        ErrorDialog("Payment Failed", actionTitle = "Retry", onConfirmation = {})
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
                            leftTopBarItem(onBackPressed = {
                                navigator.pop()
                            })
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




                    val showSelectPaymentCards = mainViewModel!!.showPaymentCardsBottomSheet.collectAsState()
                    val paymentCurrency = mainViewModel!!.displayCurrencyPath.value

                    if (showSelectPaymentCards.value) {
                        mainViewModel!!.showPaymentMethodBottomSheet(false)
                        PaymentCardBottomSheet(
                            mainViewModel!!,
                            cardList,
                            databaseBuilder = databaseBuilder,
                            onCardSelected = {
                                mainViewModel!!.showPaymentCardsBottomSheet(false)
                                selectedCard = it
                                paymentPresenter.initCheckOut(amount = (paymentAmount * 100).toString(), customerEmail = customerEmail, currency = paymentCurrency)
                            },
                            onDismiss = {
                                mainViewModel!!.showPaymentCardsBottomSheet(false)
                            }, onAddNewSelected = {
                                mainViewModel!!.showPaymentCardsBottomSheet(false)
                                openAddDebitCardDialog.value = true
                            })
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


                        ProductDeliveryAddressWidget(mainViewModel!!, isDisabled = !isProfileCompleted, onMobileSelectedListener = {
                                mainViewModel!!.setDeliveryMethod(DeliveryMethodEnum.MOBILE.toPath())

                            }, onPickupSelectedListener = {
                                mainViewModel!!.setDeliveryMethod(DeliveryMethodEnum.PICKUP.toPath())

                            })
                        PaymentMethodWidget(onCashSelectedListener = {
                             cartViewModel!!.setPaymentMethod(PaymentMethod.PAYMENT_ON_DELIVERY.toPath())
                        }, onCardPaymentSelectedListener = {
                            cartViewModel!!.setPaymentMethod(PaymentMethod.CARD_PAYMENT.toPath())
                        })
                        CheckOutSummaryWidget(cartViewModel!!,mainViewModel!!,onCardCheckOutStarted = {
                            runBlocking {
                                cardList = databaseBuilder!!.build().getPaymentCardDao().getAllPaymentCards()
                                mainViewModel!!.showPaymentCardsBottomSheet(true)
                            }
                        }, onCheckOutStarted = {
                             createOrder()
                        })

                    }

                }
            })

    }

    private fun createOrder(){
        val orderItemList = mainViewModel!!.unSavedOrders.value
        val vendorId = mainViewModel!!.connectedVendor.value.vendorId
        val userId = mainViewModel!!.currentUserInfo.value.userId
        val deliveryLocation = mainViewModel!!.deliveryMethod.value
        val paymentMethod = cartViewModel!!.paymentMethod.value
        val year = getYear()
        val month = getMonth()
        val day = getDay()

        cartPresenter.createOrder(
            orderItemList,
            vendorId!!,
            userId!!,
            deliveryLocation,
            paymentMethod,
            day,
            month,
            year,
            user = mainViewModel!!.currentUserInfo.value,
            vendor = mainViewModel!!.connectedVendor.value,
            paymentAmount = customerPaidAmount,
            platformNavigator = platformNavigator
        )
    }

    @Composable
    private fun PopulateCartItemList(mainViewModel: MainViewModel,stackedSnackBarHostState: StackedSnakbarHostState) {

        val cartItems = mainViewModel.unSavedOrders.collectAsState()
        val currencyUnit = mainViewModel.displayCurrencyUnit.value
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
            ProductDetailBottomSheet(
                mainViewModel,
                isViewOnly = true,
                OrderItem(itemProduct = orderItemUIModel.selectedItem!!.itemProduct!!),
                onDismiss = {
                   showProductDetailBottomSheet = false
                },
                onAddToCart = { isAddToCart, item -> })

        }


            LazyColumn(
                modifier = Modifier.height((180 * cartItems.value.size).dp),
                userScrollEnabled = true
            ) {
                items(key = { it -> it.itemKey}, items = orderItemUIModel.itemList) { item ->
                    CartItem(item, currencyUnit, onProductClickListener = {
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


    @Composable
    fun leftTopBarItem(onBackPressed: () -> Unit) {
        PageBackNavWidget {
            onBackPressed()
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

    override fun enter(lastEvent: StackEvent): EnterTransition {
        return slideIn { size ->
            val x = if (lastEvent == StackEvent.Pop) -size.width else size.width
            IntOffset(x = x, y = 0)
        }
    }

    override fun exit(lastEvent: StackEvent): ExitTransition {
        return slideOut { size ->
            val x = if (lastEvent == StackEvent.Pop) size.width else -size.width
            IntOffset(x = x, y = 0)
        }
    }


}

class CreateOrderScreenHandler(
    private val cartPresenter: CartPresenter,
    private val paymentPresenter: PaymentPresenter,
    private val uiStateViewModel: PerformedActionUIStateViewModel,
    private val paymentUiStateViewModel: PerformedActionUIStateViewModel,
    private val onAuthorizationSuccessful: (PaymentAuthorizationResult) -> Unit
) : CartContract.View, PaymentContract.View {
    fun init() {
        paymentPresenter.registerUIContract(this)
        cartPresenter.registerUIContract(this)
    }


    override fun showPaymentLce(appUIStates: AppUIStates) {
        paymentUiStateViewModel.switchPaymentActionUIState(appUIStates)
    }

    override fun showAuthorizationResult(paymentAuthorizationResult: PaymentAuthorizationResult) {
        onAuthorizationSuccessful(paymentAuthorizationResult)
    }

    override fun showLce(appUIStates: AppUIStates) {
        uiStateViewModel.switchActionUIState(appUIStates)
    }
}





