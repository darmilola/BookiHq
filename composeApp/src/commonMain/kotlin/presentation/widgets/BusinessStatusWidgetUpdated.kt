package presentation.widgets

import domain.Models.BusinessStatusAdsPage
import domain.Models.BusinessStatusAdsProgress
import theme.styles.Colors
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import presentations.components.ImageComponent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BusinessStatusWidgetUpdated(adsPageList: List<BusinessStatusAdsPage>, adsPageProgress: List<BusinessStatusAdsProgress>) {
    val pagerState = rememberPagerState(pageCount = {
        adsPageList.size
    })
    var currentImageId by remember { mutableStateOf(0) }
    var isRestart by remember { mutableStateOf(false) }

    val boxModifier =
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()

    Box(contentAlignment = Alignment.TopCenter, modifier = boxModifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.60f).background(color = Color.Transparent)) {

                LoadBusinessStatus(currentPage = currentImageId, totalPage = adsPageList.size, adsPageList){
                    currentImageId = it
                }

            }
        }
        Row(
            Modifier
                .height(30.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(adsPageList.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) Colors.primaryColor else Colors.primaryColor.copy(alpha = 0.4f)
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)

                )
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
private fun LoadBusinessStatus(currentPage: Int, totalPage: Int,adsPageList: List<BusinessStatusAdsPage>, onNextPage: (page: Int) -> Unit){

    var currentPageInView = currentPage

        if(currentPageInView >= totalPage || currentPageInView < 0){
            currentPageInView = 0
            onNextPage(currentPageInView)
            adsPageList[currentPageInView].statusWidget.GetStatusWidget ("drawable/sale$currentPageInView.jpg")
        }
        else{
            adsPageList[currentPageInView].statusWidget.GetStatusWidget ("drawable/sale$currentPage.jpg")
            onNextPage(currentPageInView)
        }
}




