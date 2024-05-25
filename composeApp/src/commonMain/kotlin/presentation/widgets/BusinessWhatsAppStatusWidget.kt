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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.Models.VendorStatusModel
import domain.Models.VideoStatusViewMeta
import kotlinx.coroutines.launch
import theme.styles.Colors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BusinessWhatsAppStatusWidget(whatsAppStatusList: List<VendorStatusModel>, onStatusViewChanged: (Boolean) -> Unit) {
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
                        currentPage = currentPage,
                        settledPage = pagerState.settledPage,
                        onStatusViewChanged = onStatusViewChanged
                    )
                }

        }

    }
}




@Composable
private fun LoadStatusView(statusModel: VendorStatusModel, currentPage: Int, settledPage: Int, onStatusViewChanged: (Boolean) -> Unit) {
    if(statusModel.statusType == 0){
        onStatusViewChanged(false)
        BusinessStatusItemWidget().getImageStatusWidget ("https://cdn.pixabay.com/photo/2024/03/31/06/16/bird-8666099_1280.jpg", vendorStatusModel = statusModel, onStatusViewChanged)
    }
    else{
        val videoStatusViewMeta = VideoStatusViewMeta(currentPage = currentPage, settledPage = settledPage)
        onStatusViewChanged(false)
        BusinessStatusItemWidget().getVideoStatusWidget ("https://s3.eu-central-1.wasabisys.com/in-files/2348102853533/mp4-0f677c282efef29cec333154a2401188-94e000-0222b4323187.mp4", vendorStatusModel = statusModel,
            videoStatusViewMeta = videoStatusViewMeta, onStatusViewChanged)
    }
}

