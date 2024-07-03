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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import theme.styles.Colors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun welcomeScreenScrollWidget() {
    val pagerState = rememberPagerState(pageCount = {
        3
    })

    val textList = arrayListOf<String>()

    val imgList = arrayListOf<String>()

    textList.add("Lorem Ipsum is a dummy in typesetting industry")
    textList.add("Lorem Ipsum is a dummy in typesetting industry since 1900")
    textList.add("Lorem Ipsum is a dummy in typesetting industry")

    imgList.add("drawable/afro_hair.jpg")
    imgList.add("drawable/woman_welcome.jpg")
    imgList.add("drawable/massage_therapy.jpg")

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth().background(color = Color.Transparent),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.97f)
        ) { currentPage ->
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .background(color = Color.Transparent)
            ) {
                welcomeScreenView(textList[currentPage], imgList[currentPage])
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