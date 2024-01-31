package presentation.widgets

import domain.Models.BusinessStatusAdsPage
import domain.Models.BusinessStatusAdsProgress
import theme.styles.Colors
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import presentation.components.ImageComponent
import kotlinx.coroutines.launch

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
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().background(color = Color.Transparent)) {

                LoadBusinessStatus(currentPage = currentImageId, totalPage = adsPageList.size, adsPageList){
                    currentImageId = it
                }

            }
        }

        Row(modifier = Modifier.fillMaxWidth().height(100.dp).padding(start = 5.dp, end = 5.dp, top = 15.dp)) {
            Box(modifier = Modifier.weight(2f).background(color = Color.Transparent).padding(top = 10.dp, start = 10.dp)) {

                Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(color = Colors.transparentPrimaryColor).clickable {
                   if (currentImageId > 0) currentImageId -= 1
                }, contentAlignment = Alignment.Center){

                    ImageComponent(imageModifier = Modifier.size(16.dp), colorFilter = ColorFilter.tint(color = Color.White), imageRes = "drawable/back_arrow_icon.png")

                }

            }
         Row(modifier = Modifier.weight(3f).padding(start = 5.dp, end = 5.dp, top = 20.dp)) {
            for (item in adsPageProgress) {
                Box(modifier = Modifier.weight(1f).height(20.dp)) {
                    item.adsProgress.DotProgressProgressBarWidget(
                        pageId = item.pageId,
                        currentPage = currentImageId
                    )

                }
            }
        }
            Box(modifier = Modifier.weight(2f).background(color = Color.Transparent).padding(top = 10.dp, end = 10.dp), contentAlignment = Alignment.CenterEnd) {

                Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(color = Colors.transparentPrimaryColor).clickable {
                   if (currentImageId < adsPageList.size) currentImageId += 1 else currentImageId = 0
                }, contentAlignment = Alignment.Center){
                    ImageComponent(imageModifier = Modifier.size(16.dp), colorFilter = ColorFilter.tint(color = Color.White), imageRes = "drawable/forward_arrow_icon.png")
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




