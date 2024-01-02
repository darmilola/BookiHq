package widgets

import Models.BusinessStatusAdsPage
import Models.BusinessStatusAdsProgress
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BusinessStatusWidget(adsPageList: List<BusinessStatusAdsPage>, adsPageProgress: List<BusinessStatusAdsProgress>) {
    val pagerState = rememberPagerState(pageCount = {
        adsPageList.size
    })
    var currentPage by remember { mutableStateOf(0) }
    var isRestart by remember { mutableStateOf(false) }
    var currentPageProgress by remember { mutableStateOf(0f) }

    val boxModifier =
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()

    Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            LoadBusinessStatus(currentPage = currentPage, pagerState.pageCount, onNextPage = {
                  println("next is $it")
                  currentPage = it
                  isRestart = true

            }, onProgress = {
                 currentPageProgress = it
            })
            adsPageList[currentPage].statusImage.GetStatusImage("drawable/sale$currentPage.jpg")
        }

        LaunchedEffect(key1 = isRestart) {
            launch {   // animate scrolls to page
                with(pagerState) {
                    scrollToPage(page = currentPage)
                    currentPageProgress = 0f
                }
            }
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                currentPage = page //manual scrolls to page and fling animate
                currentPageProgress = 0f
                isRestart = false
            }
        }

        Row(modifier = Modifier.fillMaxWidth().padding(start = 5.dp, end = 5.dp)) {
                for (item in adsPageProgress) {
                    Box(modifier = Modifier.weight(1f)) {
                            item.adsProgress.GetStatusProgressBar(0f, pageId = item.pageId, currentPage = currentPage)

                    }
                }
           }
        Row(modifier = Modifier.fillMaxWidth().padding(start = 5.dp, end = 5.dp)) {
            for (item in adsPageProgress) {
                Box(modifier = Modifier.weight(1f)) {
                    if(item.pageId == currentPage) {
                        item.adsProgress.GetStatusProgressBar(currentPageProgress)
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadBusinessStatus(currentPage: Int, totalPage: Int, onNextPage: (page: Int) -> Unit, onProgress: (progress: Float) -> Unit){

    var currentTime by remember { mutableStateOf(0f) }
    var currentPageInView by remember { mutableStateOf(0) }
    var progress = 0f
    val totalTime = 2000f

    if (currentPageInView != currentPage){
        currentTime = 0f
        currentPageInView = currentPage
    }

    LaunchedEffect(key1 = currentTime) {
        while (currentTime < totalTime) { // time to view
            delay(10L)
            currentTime += 10
            progress = ((currentTime/totalTime))
            onProgress(progress)
        }

        if(currentPageInView == totalPage - 1){
            currentTime = 0f
            progress = 0f
            onProgress(progress)
            onNextPage(0)
        }

       else if(currentPageInView < totalPage) { //is done next page
            currentTime = 0f
            progress = 0f
            onProgress(progress)
            currentPageInView += 1
            onNextPage(currentPageInView)
        }
        else { // no next go to 0
            currentTime = 0f
            progress = 0f
            onProgress(progress)
            onNextPage(0)
        }

    }
}


