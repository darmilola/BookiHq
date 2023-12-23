package screens.UserProfile.Orders

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansSemiBold
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ImageComponent
import components.TextComponent
import components.ToggleButton
import screens.UserProfile.Orders.OrderItemList
import screens.consultation.rightTopBarItem
import screens.main.MainViewModel

class UserOrders (private val mainViewModel: MainViewModel) : Screen {

    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {

        val columnModifier = Modifier
            .padding(top = 5.dp)
            .background(color = Color(0xfffbfafe))
            .fillMaxHeight()
            .fillMaxWidth()

        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
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
                        .background(color = Color(color = 0xFFF3F3F3))
                ) {

                    UserOrdersScreenTopBar()
                    ToggleButton(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(10.dp), style = MaterialTheme.typography.h4, onLeftClicked = {


                    }, onRightClicked = {

                    }, leftText = "Pending", rightText = "Delivered")

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp).fillMaxHeight(),
                        contentPadding = PaddingValues(6.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        items(10) {
                            OrderItemList()
                        }
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
            .padding(top = 35.dp, end = 0.dp)
            .fillMaxWidth()
            .height(40.dp)

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
        val navigator = LocalNavigator.currentOrThrow
        val modifier = Modifier
            .padding(start = 15.dp)
            .clickable {
                   navigator.pop()
              }
            .size(22.dp)
        ImageComponent(imageModifier = modifier, imageRes = "back_arrow.png", colorFilter = ColorFilter.tint(color = Color.DarkGray))
    }

    @Composable
    fun OrderTitle(){
        MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
            TextComponent(
                text = "Orders",
                fontSize = 20,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
            )
        }
    }

}

