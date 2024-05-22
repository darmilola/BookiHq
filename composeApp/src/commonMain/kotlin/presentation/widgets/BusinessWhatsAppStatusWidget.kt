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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import domain.Models.BusinessStatusAdsPage
import kotlinx.coroutines.launch
import theme.styles.Colors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BusinessWhatsAppStatusWidget(adsPageList: List<BusinessStatusAdsPage>) {
    val pagerState = rememberPagerState(pageCount = {
        10
    })
    var currentImageId by remember { mutableStateOf(0) }
    var isRestart by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier
            .height(30.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(adsPageList.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) Colors.primaryColor else Colors.primaryColor.copy(alpha = 0.2f)
                Box(modifier = Modifier
                        .padding(4.dp)
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
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().background(color = Color.Transparent)) {
                LoadBusinessStatus(currentPage = currentImageId, totalPage = adsPageList.size, adsPageList) {
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
private fun LoadBusinessStatus(currentPage: Int, totalPage: Int, adsPageList: List<BusinessStatusAdsPage>, onNextPage: (page: Int) -> Unit) {

    var currentPageInView = currentPage

    if(currentPageInView >= totalPage || currentPageInView < 0){
        currentPageInView = 0
        onNextPage(currentPageInView)
        //adsPageList[currentPageInView].statusWidget.GetStatusWidget (adsPageList[currentPageInView].imageUrl)
        adsPageList[currentPageInView].statusWidget.GetStatusWidget ("https://cdn.pixabay.com/photo/2024/03/31/06/16/bird-8666099_1280.jpg")
    }
    else{
        adsPageList[currentPageInView].statusWidget.GetStatusWidget ("https://cdn.pixabay.com/photo/2024/03/31/06/16/bird-8666099_1280.jpg")
        //adsPageList[currentPageInView].statusWidget.GetStatusWidget (adsPageList[currentPageInView].imageUrl)
        onNextPage(currentPageInView)
    }
}

