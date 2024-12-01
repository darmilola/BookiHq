package presentation.Orders

import StackedSnackbarHost
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import domain.Enums.SharedPreferenceEnum
import domain.Models.UserOrderItemUIModel
import domain.Models.UserOrders
import drawable.ErrorOccurredWidget
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.OrderHandler
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.consultation.rightTopBarItem
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.OrdersResourceListEnvelopeViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.widgets.EmptyContentWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.TitleWidget
import rememberStackedSnackbarHostState
import theme.Colors
import utils.ParcelableScreen
import utils.getOrderViewHeight

@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class Orders() : ParcelableScreen, KoinComponent, Parcelable, ScreenTransition {


    @Transient
    private val orderPresenter: OrderPresenter by inject()
    @Transient
    private var loadingOrderScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null
    @Transient
    private var ordersResourceListEnvelopeViewModel: OrdersResourceListEnvelopeViewModel? = null
    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    private var reviewActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient
    val preferenceSettings = Settings()

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )
        val navigator = LocalNavigator.currentOrThrow
        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }


        if (loadingOrderScreenUiStateViewModel == null) {
            loadingOrderScreenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    LoadingScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        if (reviewActionUIStateViewModel == null) {
            reviewActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (ordersResourceListEnvelopeViewModel == null) {
            ordersResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    OrdersResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }



        LaunchedEffect(true) {
            if (ordersResourceListEnvelopeViewModel!!.resources.value.isEmpty()) {
                val userId = preferenceSettings[SharedPreferenceEnum.USER_ID.toPath(), -1L]
                orderPresenter.getUserOrders(userId)
            }
        }



        val loadMoreState =
            ordersResourceListEnvelopeViewModel?.isLoadingMore?.collectAsState()
        val ordersList = ordersResourceListEnvelopeViewModel?.resources?.collectAsState()
        val totalOrdersCount =
            ordersResourceListEnvelopeViewModel?.totalItemCount?.collectAsState()
        val displayedOrdersCount =
            ordersResourceListEnvelopeViewModel!!.displayedItemCount.collectAsState()
        val loadOrderUiState = loadingOrderScreenUiStateViewModel!!.uiStateInfo.collectAsState()
        val lastIndex = ordersList?.value?.size?.minus(1)
        val userId = mainViewModel!!.currentUserInfo.value.userId
        val selectedOrder = remember { mutableStateOf(UserOrders()) }


        var userOrderItemUIModel by remember {
            mutableStateOf(
                UserOrderItemUIModel(
                    selectedOrder.value,
                    ordersList?.value!!
                )
            )
        }

        if (!loadMoreState?.value!!) {
            userOrderItemUIModel =
                userOrderItemUIModel.copy(selectedUserOrders = selectedOrder.value,
                    userOrderList = ordersResourceListEnvelopeViewModel!!.resources.value.map { it2 ->
                        it2.copy(
                            isSelected = it2.id == selectedOrder.value.id
                        )
                    })
              }



        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                UserOrdersScreenTopBar(onBackPressed = {
                    navigator.pop()
                })
            },
            content = {
                val handler = OrderHandler(
                    ordersResourceListEnvelopeViewModel!!,
                    loadingOrderScreenUiStateViewModel!!,
                    reviewActionUIStateViewModel!!,
                    orderPresenter
                )
                handler.init()

                if (loadOrderUiState.value.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        IndeterminateCircularProgressBar()
                    }
                }
                else if (loadOrderUiState.value.isFailed) {
                    Box(modifier = Modifier .fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center) {
                        ErrorOccurredWidget(loadOrderUiState.value.errorMessage, onRetryClicked = {
                            val userId = mainViewModel!!.currentUserInfo.value.userId
                            orderPresenter.getUserOrders(userId!!)
                        })
                    }
                }
                else if (loadOrderUiState.value.isEmpty) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        EmptyContentWidget(emptyText = loadOrderUiState.value.emptyMessage)
                    }
                }
                else if (loadOrderUiState.value.isSuccess) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = 50.dp)
                            .height(getOrderViewHeight(userOrderItemUIModel.userOrderList.size).dp),
                        userScrollEnabled = true
                    ) {
                        itemsIndexed(items = userOrderItemUIModel.userOrderList) { it, item ->
                            UserOrderComponent(mainViewModel!!, item.customerOrder!!, reviewActionUIStateViewModel!!)
                            if (it == lastIndex && loadMoreState.value) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().height(60.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    IndeterminateCircularProgressBar()
                                }
                            } else if (it == lastIndex && (displayedOrdersCount.value < totalOrdersCount?.value!!)) {
                                val buttonStyle = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)

                                ButtonComponent(
                                    modifier = buttonStyle,
                                    buttonText = "Show More",
                                    borderStroke = BorderStroke(1.dp, Colors.primaryColor),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                                    fontSize = 16,
                                    shape = CircleShape,
                                    textColor = Colors.primaryColor,
                                    style = TextStyle()
                                ) {
                                    if (ordersResourceListEnvelopeViewModel!!.nextPageUrl.value.isNotEmpty()) {
                                        if (userId != -1L) {
                                            orderPresenter.getMoreUserOrders(userId!!, nextPage = ordersResourceListEnvelopeViewModel!!.currentPage.value + 1)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            })
         }


    @Composable
    fun UserOrdersScreenTopBar(onBackPressed: () -> Unit) {

        val rowModifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 5.dp)
            .height(40.dp)

        Row(modifier = rowModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart) {
                leftTopBarItem(onBackPressed = {
                  onBackPressed()
                })
            }

            Box(modifier =  Modifier.weight(3.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                OrderTitle()
            }

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth(0.20f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                rightTopBarItem()
            }
        }
    }
    @Composable
    fun OrderTitle(){
        TitleWidget(textColor = theme.styles.Colors.primaryColor, title = "Orders")
    }

    @Composable
    fun leftTopBarItem(onBackPressed:() -> Unit) {
        PageBackNavWidget() {
            onBackPressed()
        }
    }

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
