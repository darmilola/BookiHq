package presentation.Orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import domain.Enums.Screens
import domain.Models.PlacedOrderItemComponent
import kotlinx.serialization.Transient
import presentation.widgets.OrderDetailList
import presentation.viewmodels.MainViewModel
import presentation.widgets.PageBackNavWidget

class OrderDetails() : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Order Details"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title
                )
            }
        }
    @Transient
    private var mainViewModel: MainViewModel? = null

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }


    @Composable
    override fun Content() {
        val rowModifier = Modifier
            .fillMaxWidth()
            .height(70.dp)

        val colModifier = Modifier
            .padding(top = 10.dp, start = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight()

        Column(modifier = colModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
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

            }
            val itemList = mainViewModel!!.orderItemComponents.value
            OrderDetailList(itemList)
        }
    }
}

@Composable
fun leftTopBarItem(mainViewModel: MainViewModel) {
    PageBackNavWidget {
        mainViewModel.setScreenNav(Pair(Screens.ORDER_DETAILS.toPath(), Screens.ORDERS.toPath()))
      }
  }

