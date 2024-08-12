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
import domain.Models.Vendor
import domain.Models.VendorStatusModel
import domain.Models.VideoStatusViewMeta
import theme.styles.Colors
import utils.calculateStatusViewHeightPercent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShopStatusWidget(whatsAppStatusList: List<VendorStatusModel>,vendorInfo: Vendor) {
    val pagerState = rememberPagerState(pageCount = {
        whatsAppStatusList.size
    })
    var currentTimeStamp = remember { mutableStateOf(0L) }

    Box(
        modifier = Modifier
            .fillMaxHeight(),
        contentAlignment = Alignment.TopCenter
    ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
            ) { currentPage ->
                if (currentPage == pagerState.settledPage) {
                    val currentStatus = whatsAppStatusList[currentPage]
                    currentTimeStamp.value = currentStatus.timeStamp
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .background(color = Color.Transparent),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        LoadStatusView(
                            statusModel = currentStatus,
                            currentPage = currentPage,
                            settledPage = pagerState.settledPage
                        )
                    }
                }
        }
        Column(modifier = Modifier.fillMaxWidth().height(80.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center) {
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
                            if (pagerState.currentPage == iteration) Color.White else Color.White.copy(alpha = 0.5f)
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
            Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.TopStart){
                StatusInfoWidget(vendorInfo, currentTimeStamp.value)
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

