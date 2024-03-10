package presentation.main

import GGSansSemiBold
import domain.Models.AppointmentItem
import domain.Models.BusinessStatusAdsPage
import domain.Models.BusinessStatusAdsProgress
import theme.styles.Colors
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import presentation.components.StraightLine
import presentation.Products.BottomSheet
import presentation.Products.NewProductItem
import presentation.viewmodels.MainViewModel
import utils.getAppointmentViewHeight
import presentation.widgets.AppointmentWidget
import presentation.widgets.BusinessStatusItemWidget
import presentation.widgets.BusinessStatusWidgetUpdated
import presentation.widgets.HomeServicesWidget
import presentation.widgets.RecommendedServiceItem
import presentation.widgets.StatusProgressWidget
import presentations.components.ImageComponent
import presentations.components.TextComponent

class HomeTab(private val mainViewModel: MainViewModel) : Tab {
    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
            val icon = painterResource("home_icon.png")

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val columnModifier = Modifier
            .padding(top = 5.dp)
            .fillMaxSize()

        val appointmentList = ArrayList<AppointmentItem>()

        val appointmentItem1 = AppointmentItem(appointmentType = 1)
        val appointmentItem2 = AppointmentItem(appointmentType = 2)
        val appointmentItem3 = AppointmentItem(appointmentType = 3)
        val appointmentItem4 = AppointmentItem(appointmentType = 4)
        val appointmentItem5 = AppointmentItem(appointmentType = 5)

        appointmentList.add(appointmentItem1)
        appointmentList.add(appointmentItem2)
        appointmentList.add(appointmentItem3)
        appointmentList.add(appointmentItem1)
        appointmentList.add(appointmentItem5)
        appointmentList.add(appointmentItem4)
        appointmentList.add(appointmentItem2)


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = columnModifier
            ) {
                Column(
                    Modifier
                        .padding(bottom = 85.dp)
                        .fillMaxSize()
                        .background(color = Color.White)

                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())) {
                        AttachBusinessName()
                        BusinessStatusDisplay()
                        AttachOurServices()
                        ServiceGridScreen()
                        RecommendedSessions()
                        AttachAppointments()
                        PopulateAppointmentScreen(appointmentList = appointmentList, mainViewModel = mainViewModel)
                        PopularProducts()
                        PopularProductScreen()
                    }

                }
            }
    }


    @Composable
    fun ServiceGridScreen() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth().height(300.dp),
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center
        ) {
            items(8) {
                when (it) {
                    0 -> {
                        HomeServicesWidget(iconRes = "drawable/hair_cut.png", serviceTitle = "Haircut", mainViewModel)
                    }

                    1 -> {
                        HomeServicesWidget(iconRes = "drawable/beauty_treatment.png", serviceTitle = "Facials", mainViewModel, iconSize = 50)
                    }

                    2 -> {
                        HomeServicesWidget(iconRes = "drawable/nail.png", serviceTitle = "Nails", mainViewModel, iconSize = 45)
                    }

                    3 -> {
                        HomeServicesWidget(iconRes = "drawable/hair_dye.png", serviceTitle = "Coloring", mainViewModel, iconSize = 45)
                    }

                    4 -> {
                        HomeServicesWidget(iconRes = "drawable/spa.png", serviceTitle = "Spa", mainViewModel, iconSize = 45)
                    }

                    5 -> {
                        HomeServicesWidget(iconRes = "drawable/waxing.png", serviceTitle = "Waxing", mainViewModel)
                    }

                    6 -> {
                        HomeServicesWidget(iconRes = "drawable/lipstick.png", serviceTitle = "Makeup", mainViewModel)
                    }

                    7 -> {
                        HomeServicesWidget(iconRes = "drawable/massage.png", serviceTitle = "Massage", mainViewModel)
                    }

                    else -> {
                        HomeServicesWidget(iconRes = "drawable/spa.png", serviceTitle = "Facials", mainViewModel)
                    }

                }
            }
        }
    }

@Composable
    fun AttachOurServices(){
        val rowModifier = Modifier
            .padding(start = 10.dp, top = 20.dp)
            .fillMaxWidth()
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = "Services",
                    fontSize = 18,
                    fontFamily = GGSansSemiBold,
                    textStyle = TextStyle(),
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    lineHeight = 30,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textModifier = Modifier.fillMaxWidth(0.20f)
                )
                StraightLine()
            }
    }

    @Composable
    fun RecommendedSessions(){
        val rowModifier = Modifier
            .padding(start = 10.dp, top = 10.dp)
            .fillMaxWidth()
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = "Recommended",
                    fontSize = 18,
                    textModifier = Modifier.fillMaxWidth(0.32f),
                    fontFamily = GGSansSemiBold,
                    textStyle = TextStyle(),
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    lineHeight = 30,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                StraightLine()
            }

            RecommendedAppointmentList()

        }


    @Composable
    fun AttachAppointments(){
        val rowModifier = Modifier
            .padding(start = 10.dp, top = 30.dp, bottom = 20.dp)
            .fillMaxWidth()
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = "Recent Appointments",
                    fontSize = 18,
                    fontFamily = GGSansSemiBold,
                    textStyle = TextStyle(),
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    lineHeight = 30,
                    textModifier = Modifier.fillMaxWidth(0.45f)
                )
                StraightLine()
            }
    }


    @Composable
    fun PopularProducts(){
        val rowModifier = Modifier
            .padding(start = 10.dp, top = 20.dp)
            .fillMaxWidth()
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = "Popular Products",
                    fontSize = 18,
                    fontFamily = GGSansSemiBold,
                    textStyle = TextStyle(),
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    lineHeight = 30,
                    textModifier = Modifier.fillMaxWidth(0.42f)
                )
                StraightLine()
            }

        }




    @Composable
    fun PopularProductScreen() {

        Column {

            var showSheet by remember { mutableStateOf(false) }

            if (showSheet) {
                BottomSheet() {
                    showSheet = false
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(960.dp),
                contentPadding = PaddingValues(top = 6.dp, bottom = 6.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                userScrollEnabled = false
            ) {
                items(4) {
                    NewProductItem(onProductClickListener = {
                        showSheet = true
                    })
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun RecommendedAppointmentList() {
        val pagerState = rememberPagerState(pageCount = {
            3
        })

        var viewType by remember { mutableStateOf(0) }
        val itemColor: Color = Colors.primaryColor

        val boxModifier =
            Modifier
                .background(color = Color.White)
                .height(450.dp)
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp)

        val boxBgModifier =
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()



        Box(modifier = boxBgModifier) {

            var showSheet by remember { mutableStateOf(false) }
            if (showSheet) {
                BottomSheet() {
                    showSheet = false
                }
            }


            Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    pageSpacing = 15.dp
                ) { page ->
                    viewType = page
                    RecommendedServiceItem (viewType = page, onSessionClickListener = {
                        when (page) {
                            0 -> mainViewModel.setId(1)
                            1 -> {
                                 mainViewModel.setId(2)
                            }
                            else -> {
                                showSheet = true
                            }
                        }
                    })
                }
                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color: Color
                        val width: Int
                        if (pagerState.currentPage == iteration) {
                            color = itemColor
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
                                .height(3.dp)
                                .width(width.dp)
                        )
                    }

                }
            }
        }

    }

    @Composable
    fun PopulateAppointmentScreen(appointmentList: List<AppointmentItem>, mainViewModel: MainViewModel) {
        LazyColumn(modifier = Modifier.fillMaxWidth().height(getAppointmentViewHeight(appointmentList).dp), userScrollEnabled = false) {
            items(appointmentList) {item ->
                AppointmentWidget(itemType = item.appointmentType, mainViewModel)
            }
        }
    }

    @Composable
    fun AttachBusinessName(){
        val rowModifier = Modifier
            .padding(start = 10.dp)
            .fillMaxWidth()
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = rowModifier
            ) {
                val modifier = Modifier.padding(start = 3.dp)
                AttachLocationIcon()
                TextComponent(
                    text = "JonJo, Beauty and Spa Services",
                    fontSize = 18,
                    fontFamily = GGSansSemiBold,
                    textStyle = TextStyle(),
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    lineHeight = 30,
                    textModifier = modifier
                )
            }
        }

    @Composable
    fun GetBusinessPageList() : List<BusinessStatusAdsPage> {
         val adsList: ArrayList<BusinessStatusAdsPage> = arrayListOf()
         val imageWidget = BusinessStatusItemWidget()
         val statusAdsPage = BusinessStatusAdsPage(statusWidget = imageWidget)
         adsList.add(statusAdsPage)
         adsList.add(statusAdsPage)
         adsList.add(statusAdsPage)
         adsList.add(statusAdsPage)
         adsList.add(statusAdsPage)
         return adsList
    }

    @Composable
    fun GetBusinessPageProgressList(pageCount: Int) : List<BusinessStatusAdsProgress> {
        val progressList: ArrayList<BusinessStatusAdsProgress> = arrayListOf()
        val progressWidget = StatusProgressWidget()
        for(i in 0..< pageCount){
            val adsProgress = BusinessStatusAdsProgress(adsProgress = progressWidget, pageId = i)
            progressList.add(adsProgress)
        }
        return progressList
    }

    @Composable
    fun BusinessStatusDisplay() {
        val boxBgModifier =
            Modifier
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxHeight()
                .fillMaxWidth()

        Box(modifier = boxBgModifier) {
            val businessPageList = GetBusinessPageList()
            BusinessStatusWidgetUpdated(businessPageList, GetBusinessPageProgressList(businessPageList.size))
        }
    }


    @Composable
    fun AttachLocationIcon() {
        val modifier = Modifier
            .padding(top = 2.dp)
            .size(20.dp)
        ImageComponent(imageModifier = modifier, imageRes = "drawable/location_icon_filled.png", colorFilter = ColorFilter.tint(color = Colors.pinkColor))
    }
}