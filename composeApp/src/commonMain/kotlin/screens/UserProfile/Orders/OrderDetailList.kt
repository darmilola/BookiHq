package screens.UserProfile.Orders

import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.TextComponent
import screens.Bookings.StraightLine

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderDetailList() {
    val columnModifier = Modifier
        .padding(start = 5.dp, top = 5.dp, bottom = 10.dp)
        .clickable {}
        .verticalScroll(rememberScrollState())
        .background(color = Color.Transparent, shape = RoundedCornerShape(10.dp))
        .height(((130 * 3)+500).dp)
    MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
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
                    OrderItemDetail()
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
}