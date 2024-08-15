package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.Models.PlacedOrderItemComponent
import presentation.viewmodels.MainViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun OrderDetailList(itemList: ArrayList<PlacedOrderItemComponent>) {
    val columnModifier = Modifier
        .padding(start = 5.dp, top = 5.dp, bottom = 10.dp)
        .clickable {}
        .verticalScroll(rememberScrollState())
        .background(color = Color.Transparent, shape = RoundedCornerShape(10.dp))
        .height(((130 * 3)+500).dp)

        Column(modifier = columnModifier,
            verticalArrangement = Arrangement.Top
        ) {

            Box(modifier = Modifier.padding(top = 10.dp).fillMaxWidth().fillMaxHeight(),) {
                    OrderItemDetail(itemList)
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 5.dp, start = 20.dp, end = 20.dp).height(50.dp).fillMaxWidth()
            ) {

            }
        }
}