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
import kotlinx.coroutines.launch
import theme.styles.Colors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BusinessWhatsAppStatusWidget(whatsAppStatusList: List<VendorStatusModel>) {
    val pagerState = rememberPagerState(pageCount = {
        whatsAppStatusList.size
    })
    var currentImageId by remember { mutableStateOf(0) }
    var isRestart by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier
            .height(10.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(whatsAppStatusList.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) Colors.darkPrimary else Colors.darkPrimary.copy(alpha = 0.2f)
                Box(modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp, top = 4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .height(2.dp)
                        .weight(1f, fill = true))
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) { page ->
            val currentStatus = whatsAppStatusList[page]
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().background(color = Color.Transparent)) {
                LoadStatusView(currentPage = currentImageId, totalPage = whatsAppStatusList.size, statusModel = currentStatus) {
                    currentImageId = it
                }
            }
        }
    }


    LaunchedEffect(key1 = isRestart) {
        launch {   // animate scrolls to page
            with(pagerState) {
                scrollToPage(page = currentImageId)
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            currentImageId = page //manual scrolls to page and fling animate
            isRestart = false
        }
    }

}




@Composable
private fun LoadStatusView(currentPage: Int, totalPage: Int, statusModel: VendorStatusModel, onNextPage: (page: Int) -> Unit) {

    if(statusModel.statusType == 0){
        //onNextPage(currentPageInView)
        BusinessStatusItemWidget().getImageStatusWidget ("https://cdn.pixabay.com/photo/2024/03/31/06/16/bird-8666099_1280.jpg")
    }
    else{
        BusinessStatusItemWidget().getVideoStatusWidget ("https://s3.eu-central-1.wasabisys.com/in-files/2348102853533/mp4-13e00425a49ca6db8fb32c2a36eb8276-942100-0222b8efc37e.mp4")
       // onNextPage(currentPageInView)
    }
}

