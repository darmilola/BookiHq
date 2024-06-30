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
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import domain.Models.CustomerOrder
import presentation.widgets.OrderDetailList
import presentation.viewmodels.MainViewModel
import presentation.widgets.PageBackNavWidget

class OrderDetails(private val mainViewModel: MainViewModel,private val customerOrder: CustomerOrder) : Tab {

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
                    leftTopBarItem(mainViewModel)
                }

            }
            OrderDetailList(mainViewModel, customerOrder = customerOrder )
        }
    }
}

@Composable
fun leftTopBarItem(mainViewModel: MainViewModel) {
    val tabNavigator = LocalTabNavigator.current
    PageBackNavWidget {
        tabNavigator.current = Orders(mainViewModel)

       }

    }

