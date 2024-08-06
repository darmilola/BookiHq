package presentation.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import applications.videoplayer.VideoPlayer
import domain.Models.VendorStatusModel
import domain.Models.VideoStatusViewMeta
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
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (whatsAppStatusList.size > 1) {
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
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        ) { currentPage ->
            val currentStatus = whatsAppStatusList[currentPage]
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .background(color = Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                LoadStatusView(
                    statusModel = currentStatus,
                    currentPage = currentPage,
                    settledPage = pagerState.settledPage)
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
private fun LoadStatusView(statusModel: VendorStatusModel, currentPage: Int, settledPage: Int) {
    if (statusModel.statusImage != null) {
        BusinessStatusItemWidget().getImageStatusWidget(
            statusModel.statusImage.imageUrl,
            vendorStatusModel = statusModel)
    } else if (statusModel.statusVideoModel != null) {
        val videoStatusViewMeta =
            VideoStatusViewMeta(currentPage = currentPage, settledPage = settledPage)
        BusinessStatusItemWidget().getVideoStatusWidget(statusModel.statusVideoModel,
            videoStatusViewMeta = videoStatusViewMeta)
    }
    else if (statusModel.statusText != null){
        BusinessStatusItemWidget().getTextStatusWidget(vendorStatusModel = statusModel)
    }
}

