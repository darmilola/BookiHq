package presentation.UserProfile.UserOrders

import GGSansRegular
import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import presentation.components.ImageComponent
import presentation.viewmodels.MainViewModel
import presentations.components.TextComponent

@Composable
fun OrderDetailList(mainViewModel: MainViewModel) {
    val columnModifier = Modifier
        .padding(start = 5.dp, top = 5.dp, bottom = 10.dp)
        .clickable {}
        .verticalScroll(rememberScrollState())
        .background(color = Color.Transparent, shape = RoundedCornerShape(10.dp))
        .height(((130 * 3)+500).dp)


        Column(modifier = columnModifier,
            verticalArrangement = Arrangement.Top
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OrderDetailsStatusView()
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.padding(top = 10.dp).fillMaxWidth().fillMaxHeight(),
                contentPadding = PaddingValues(6.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(1) {
                    OrderItemDetail(mainViewModel = mainViewModel)
                }
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 5.dp, start = 20.dp, end = 20.dp).height(50.dp).fillMaxWidth()
            ) {

            }
        }
}




@Composable
fun OrderDetailsStatusView(){
    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        TrackMyOrderBottomSheet {
            showSheet = false
        }
    }
    val columnModifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
        .wrapContentHeight()
        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment  = Alignment.Start,
        ) {

            val modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .wrapContentHeight()

            ImageComponent(imageModifier = Modifier.size(150.dp).clickable {
            }, imageRes = "drawable/celebrate_icon.png", colorFilter = ColorFilter.tint(color = Color.DarkGray))


            TextComponent(
                text = "SATURDAY DEC 23, 2023 05:11 AM",
                fontSize = 15,
                fontFamily = GGSansSemiBold,
                textStyle = TextStyle(),
                textColor = Color.LightGray,
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Medium,
                lineHeight = 30,
                textModifier = Modifier
                    .wrapContentSize())

            TextComponent(
                text = "Congratulations, \nYour Order Has Arrived",
                fontSize = 23,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Black,
                lineHeight = 35,
                textModifier = modifier
            )

            TextComponent(
                text = "Track My Order",
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                textStyle = TextStyle(),
                textColor = Colors.primaryColor,
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Black,
                lineHeight = 30,
                textModifier = Modifier
                    .wrapContentSize().padding(top = 10.dp)
                    .clickable {
                        showSheet = true
                    }
            )
        }
    }
