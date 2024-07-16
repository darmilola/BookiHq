package presentation.Orders

import StackedSnackbarHost
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Models.CustomerItemUIModel
import domain.Models.CustomerOrder
import domain.Enums.Screens
import domain.Models.UserOrderItemUIModel
import domain.Models.UserOrders
import kotlinx.serialization.Transient
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.OrderHandler
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.consultation.rightTopBarItem
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.OrdersResourceListEnvelopeViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.PageBackNavWidget
import presentation.widgets.TitleWidget
import rememberStackedSnackbarHostState
import theme.Colors
import utils.getOrderViewHeight

@Parcelize
class Orders() : Tab, KoinComponent, Parcelable {


    @Transient
    private val orderPresenter: OrderPresenter by inject()
    @Transient
    private var uiStateViewModel: UIStateViewModel? = null
    @Transient
    private var ordersResourceListEnvelopeViewModel: OrdersResourceListEnvelopeViewModel? = null
    @Transient
    private var mainViewModel: MainViewModel? = null

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Orders"
            val icon = painterResource("drawable/calender_icon_semi.png")

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )


        if (uiStateViewModel == null) {
            uiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    UIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (ordersResourceListEnvelopeViewModel == null) {
            ordersResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    OrdersResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
            val userId = mainViewModel!!.currentUserInfo.value.userId
            orderPresenter.getUserOrders(userId!!)
        }




        val loadMoreState =
            ordersResourceListEnvelopeViewModel?.isLoadingMore?.collectAsState()
        val ordersList = ordersResourceListEnvelopeViewModel?.resources?.collectAsState()
        val totalOrdersCount =
            ordersResourceListEnvelopeViewModel?.totalItemCount?.collectAsState()
        val displayedOrdersCount =
            ordersResourceListEnvelopeViewModel!!.displayedItemCount.collectAsState()
        val uiState = uiStateViewModel!!.uiStateInfo.collectAsState()
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
                UserOrdersScreenTopBar()
            },
            content = {
                val handler = OrderHandler(
                    ordersResourceListEnvelopeViewModel!!,
                    uiStateViewModel!!,
                    orderPresenter
                )
                handler.init()

                if (uiState.value.loadingVisible) {
                    //Content Loading
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        IndeterminateCircularProgressBar()
                    }
                } else if (uiState.value.errorOccurred) {
                    // Error Occurred Try Again

                } else if (uiState.value.contentVisible) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = 50.dp)
                            .height(getOrderViewHeight(userOrderItemUIModel.userOrderList.size).dp),
                        userScrollEnabled = true
                    ) {
                        itemsIndexed(items = userOrderItemUIModel.userOrderList) { it, item ->
                            OrderItemList(mainViewModel!!, item.customerOrder!!)
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
    fun UserOrdersScreenTopBar() {

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
                leftTopBarItem(mainViewModel!!)
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
    fun leftTopBarItem(mainViewModel: MainViewModel) {
        PageBackNavWidget() {
            mainViewModel.setScreenNav(Pair(Screens.ORDERS.toPath(), Screens.MAIN_TAB.toPath()))
        }
    }

}
