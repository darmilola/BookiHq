package presentation.widgets

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import theme.styles.Colors
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun welcomeScreenScrollWidget() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val textList = arrayListOf<String>()
    val imgList = arrayListOf<String>()

    textList.add("Lorem Ipsum is a dummy in typesetting industry")
    textList.add("Lorem Ipsum is a dummy in typesetting industry since 1900")
    textList.add("Lorem Ipsum is a dummy in typesetting industry")

    imgList.add("drawable/washing_man.jpg")
    imgList.add("drawable/woman_welcome.jpg")
    imgList.add("drawable/massage_therapy.jpg")

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth().background(color = Color.Transparent),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        val isDraggedState = pagerState.interactionSource.collectIsDraggedAsState()
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            flingBehavior = PagerDefaults.flingBehavior(state = pagerState, snapPositionalThreshold = 0.2f),
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.97f)
        ) { currentPage ->
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .pagerFadeTransition(pagerState.currentPage, pagerState)
                    .background(color = Color.Transparent)
            ) {
                welcomeScreenView(textList[currentPage], imgList[currentPage])
            }
        }
        // Start auto-scroll effect
        var isForwardDirection = true
        LaunchedEffect(isDraggedState) {
            // convert compose state into flow
            snapshotFlow { isDraggedState.value }
                .collectLatest { isDragged ->
                    // if not isDragged start slide animation
                    if (!isDragged) {
                        // infinity loop
                        while (true) {
                            // duration before each scroll animation
                            delay(4_000L)
                            runCatching {
                                if (pagerState.currentPage == 0){
                                    isForwardDirection = true
                                }
                                else if (pagerState.currentPage == pagerState.pageCount - 1){
                                    isForwardDirection = false
                                }
                                val target = if (isForwardDirection) pagerState.currentPage + 1 else pagerState.currentPage - 1
                                pagerState.animateScrollToPage(target, animationSpec = tween(easing = LinearEasing, durationMillis = 1000))
                            }
                        }
                    }
                }
        }
        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()
            .padding(start = 30.dp)
            .background(color = Color.Black), contentAlignment = Alignment.CenterStart){
          Row(
            Modifier
                .height(10.dp)
                .fillMaxWidth(0.20f)
                .background(color = Color.Transparent),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(3) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.White else Color.White.copy(
                        alpha = 0.5f
                    )
                Box(
                    modifier = Modifier
                        .padding(start = 3.dp, end = 3.dp)
                        .clip(CircleShape)
                        .background(color)
                        .height(3.dp)
                        .weight(1f, fill = true)
                )
            }

          }

        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.pagerFadeTransition(page: Int, pagerState: PagerState) =
    graphicsLayer {
        val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
        translationX = pageOffset * size.width
        alpha = 1 - pageOffset.absoluteValue
    }

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}