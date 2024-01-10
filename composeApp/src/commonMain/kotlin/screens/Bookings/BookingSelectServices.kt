package screens.Bookings

import AppTheme.AppColors
import GGSansRegular
import GGSansSemiBold
import Models.CalendarDataSource
import Models.CalendarUiModel
import Styles.Colors
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import components.ImageComponent
import components.TextComponent
import components.ToggleButton
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.ExperimentalResourceApi
import screens.main.MainViewModel
import widgets.Calendar
import widgets.DropDownWidget
import widgets.HomeDeliveryWidget
import widgets.ParlorDeliveryWidget
import widgets.ServiceLocationToggle

@Composable
fun BookingSelectServices(mainViewModel: MainViewModel) {

    val boxModifier =
        Modifier
            .height(350.dp)
            .fillMaxWidth()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        // AnimationEffect
        Box(contentAlignment = Alignment.TopStart, modifier = boxModifier) {
            AttachServiceImages()
        }
        ServiceTitle()
        ServiceLocationToggle(mainViewModel)
        AttachServiceTypeToggle()
        Calendar()
    }
}

@Composable
fun AttachServiceTypeToggle(){
    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 35.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "What type of Service do you want?",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .padding(bottom = 5.dp)
                .fillMaxWidth()
        )

        AttachDropDownWidget()


    }

}

@Composable
fun AttachDropDownWidget(){
    val serviceList = listOf("Service A ", "Service B ", "Service C ", "Service D ", "Service E ")
    DropDownWidget(menuItems = serviceList,iconRes = "drawable/spa_treatment_leaves.png", placeHolderText = "Select Service Type", iconSize = 30)
}




@Composable
fun ServiceTitle(){
    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Manicure",
            fontSize = 25,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Light,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth()
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AttachServiceImages(){

    val pagerState = rememberPagerState(pageCount = {
        3
    })

    val  boxModifier =
        Modifier
            .padding(bottom = 20.dp)
            .fillMaxHeight()
            .fillMaxWidth()

    // AnimationEffect
    Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            ImageComponent(imageModifier = Modifier.fillMaxWidth().height(350.dp), imageRes = "$page.jpg", contentScale = ContentScale.Crop)
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color: Color
                val width: Int
                if (pagerState.currentPage == iteration){
                    color =  Colors.primaryColor
                    width = 25
                } else{
                    color =  Colors.lightPrimaryColor
                    width = 25
                }
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .height(3.dp)
                        .width(width.dp)
                )
            }

        }
    }

}
