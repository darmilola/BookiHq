package presentation.Orders

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.ScreenTransition
import dev.icerock.moko.parcelize.Parcelize
import domain.Enums.Screens
import domain.Models.OrderItem
import domain.Models.PlacedOrderItemComponent
import domain.Models.Product
import kotlinx.serialization.Transient
import presentation.widgets.OrderDetailList
import presentation.viewmodels.MainViewModel
import presentation.widgets.AddProductReviewBottomSheet
import presentation.widgets.PageBackNavWidget
import presentation.widgets.ProductDetailBottomSheet
import utils.ParcelableScreen

@Parcelize @OptIn(ExperimentalVoyagerApi::class)
class OrderDetails() : ParcelableScreen, ScreenTransition {


    override val key: ScreenKey = uniqueScreenKey

    @Transient
    private var mainViewModel: MainViewModel? = null

    fun setMainViewModel(mainViewModel: MainViewModel) {
        this.mainViewModel = mainViewModel
    }


    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        val showAddReviewBottomSheet = mainViewModel!!.showProductReviewsBottomSheet.collectAsState()
        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }


        if (showAddReviewBottomSheet.value) {
            AddProductReviewBottomSheet(
                mainViewModel!!,
                onDismiss = {
                    mainViewModel!!.showProductReviewsBottomSheet(false)
                },
                onReviewsAdded = {
                    mainViewModel!!.showProductReviewsBottomSheet(false)
                })
        }


        val rowModifier = Modifier
            .fillMaxWidth()
            .height(70.dp)

        val colModifier = Modifier
            .padding(top = 10.dp, start = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight()

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

            }
            val itemList = mainViewModel!!.orderItemComponents.value

            OrderDetailList(itemList, onAddReviewClicked = {
                mainViewModel!!.showProductReviewsBottomSheet(true)
            })
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



    @Composable
    fun leftTopBarItem(onBackPressed: () -> Unit) {
        PageBackNavWidget {
            onBackPressed()
        }
    }
}

