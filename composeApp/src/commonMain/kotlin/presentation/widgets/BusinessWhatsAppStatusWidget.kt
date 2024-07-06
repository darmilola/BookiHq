package presentation.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.Models.VendorStatusModel
import theme.styles.Colors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShopStatusWidget(whatsAppStatusList: List<VendorStatusModel>, onStatusViewChanged: (Boolean) -> Unit) {
    val pagerState = rememberPagerState(pageCount = {
        whatsAppStatusList.size
    })

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .height(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(whatsAppStatusList.size) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Colors.darkPrimary else Colors.darkPrimary.copy(
                        alpha = 0.2f
                    )
                Box(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp, top = 4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .height(2.dp)
                        .weight(1f, fill = true)
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) { currentPage ->
            val currentStatus = whatsAppStatusList[currentPage]
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        .background(color = Color.Transparent)
                ) {
                    LoadStatusView(
                        statusModel = currentStatus,
                        onStatusViewChanged = onStatusViewChanged)
                }
            if (pagerState.isScrollInProgress){
                if (pagerState.currentPage == pagerState.settledPage) {
                    onStatusViewChanged(false)
                }
            }
        }

    }
}


@Composable
private fun LoadStatusView(statusModel: VendorStatusModel,onStatusViewChanged: (Boolean) -> Unit) {
    if(statusModel.statusImage != null ){
        BusinessStatusItemWidget().getImageStatusWidget (statusModel.statusImage.imageUrl, vendorStatusModel = statusModel, onStatusViewChanged)
    }
}

