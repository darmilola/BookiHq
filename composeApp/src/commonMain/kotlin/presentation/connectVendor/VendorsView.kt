package presentation.connectVendor

import GGSansBold
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.Enums.RecommendationType
import domain.Enums.Screens
import domain.Models.Vendor
import kotlinx.coroutines.runBlocking
import presentation.viewmodels.MainViewModel
import presentation.widgets.EmptyContentWidget
import presentation.widgets.NewVendorItem
import presentations.components.TextComponent
import theme.styles.Colors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VendorsView(nearbyVendor: List<Vendor>, newVendor: List<Vendor>, mainViewModel: MainViewModel, onSeeAllNearbyVendor:() -> Unit, onVendorClickListener: (Vendor) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().background(color = theme.Colors.dashboardBackground).verticalScroll(rememberScrollState())) {

        if (nearbyVendor.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 10.dp, top = 15.dp)
                    .height(30.dp)
                    .fillMaxWidth()
            ) {
                TextComponent(
                    text = "Nearby Vendor",
                    textModifier = Modifier.fillMaxWidth(0.70f),
                    fontSize = 20,
                    textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )


                Box(
                    modifier = Modifier.fillMaxWidth().padding(end = 20.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    TextComponent(
                        text = "See All",
                        textModifier = Modifier.wrapContentWidth()
                            .clickable { onSeeAllNearbyVendor() },
                        fontSize = 15,
                        textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                        textColor = Colors.primaryColor,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 30,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }

        if (nearbyVendor.isEmpty()){
                Box(modifier = Modifier.fillMaxWidth().height(320.dp), contentAlignment = Alignment.Center) {
                    EmptyContentWidget(emptyText = "No Nearby Vendor")
                }
        }
        else {

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
                    .height(330.dp),
                contentPadding = PaddingValues(6.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp), userScrollEnabled = true
            ) {
                runBlocking {
                    items(nearbyVendor.size) { i ->
                        SwitchVendorBusinessItemComponent(vendor = nearbyVendor[i]) {
                            onVendorClickListener(it)
                        }
                    }
                }
            }
        }

        /*Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp)
                .height(40.dp)
                .fillMaxWidth()
        ) {
            TextComponent(
                text = "New",
                textModifier = Modifier.fillMaxWidth(0.50f),
                fontSize = 25,
                textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                lineHeight = 30,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }*/

        val pagerState = rememberPagerState(pageCount = {
            newVendor.size
        })


        Column(modifier = Modifier
            .height(400.dp)
            .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.95f),
                pageSpacing = 5.dp,
                pageSize = PageSize.Fixed(300.dp)
            ) { page ->
                NewVendorItem(newVendor[page], onItemClickListener = {
                    onVendorClickListener(it)
                })
            }
            Row(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(newVendor.size) { iteration ->
                    val color: Color
                    val width: Int
                    if (pagerState.currentPage == iteration) {
                        color = Colors.primaryColor
                        width = 20
                    } else {
                        color = Color.LightGray
                        width = 20
                    }
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .height(2.dp)
                            .width(width.dp)
                    )
                }

            }

        }
    }

}