package presentation.UserProfile.UserOrders

import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import presentation.consultation.rightTopBarItem
import presentation.main.MainTab
import presentation.main.MainViewModel
import presentation.widgets.PageBackNavWidget
import presentation.widgets.TitleWidget

class UserOrders (private val mainViewModel: MainViewModel) : Tab {


    override val options: TabOptions
        @Composable
        get() {
            val title = "Orders"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title
                )
            }
        }

    @Composable
    override fun Content() {

        val columnModifier = Modifier
            .padding(top = 5.dp)
            .background(color = Color.White)
            .fillMaxHeight()
            .fillMaxWidth()

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = columnModifier
            ) {
                Column(
                    Modifier
                        .padding(bottom = 25.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = Color.White)
                ) {
                    UserOrdersScreenTopBar()
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp).fillMaxHeight(),
                        contentPadding = PaddingValues(6.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        items(10) {
                            OrderItemList(mainViewModel)
                        }
                    }
                }
            }
        }



    @Composable
    fun UserOrdersScreenTopBar() {

        val rowModifier = Modifier
            .fillMaxWidth()
            .height(40.dp)

        val colModifier = Modifier
            .padding(top = 35.dp, end = 0.dp, start = 10.dp)
            .fillMaxWidth()
            .height(70.dp)

        Column(modifier = colModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = rowModifier,
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {

                Box(modifier =  Modifier.weight(1.0f)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart) {
                    leftTopBarItem()
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
    }

    @Composable
    fun leftTopBarItem() {
        val navigator = LocalTabNavigator.current
        PageBackNavWidget {
            println("clicked... ")
            navigator.current = MainTab(mainViewModel)
            //mainViewModel.setId(-1)
        }
    }

    @Composable
    fun OrderTitle(){
            TitleWidget(textColor = Colors.primaryColor, title = "Orders")
        }


}

