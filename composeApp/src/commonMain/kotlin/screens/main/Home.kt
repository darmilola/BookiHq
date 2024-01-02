package screens.main

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansBold
import GGSansRegular
import GGSansSemiBold
import Models.AppointmentItem
import Models.AppointmentItemListModel
import Models.BusinessStatusAdsPage
import Models.BusinessStatusAdsProgress
import Styles.Colors
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import components.ImageComponent
import components.StraightLine
import components.TextComponent
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate
import screens.Bookings.AppointmentItemCard
import screens.Products.BottomSheet
import screens.Products.NewProductItem
import utils.getAppointmentViewHeight
import widgets.AppointmentsWidget
import widgets.BusinessStatusImageWidget
import widgets.BusinessStatusProgressWidget
import widgets.BusinessStatusWidget
import widgets.RecommendedServiceItem
import widgets.attachServiceImage

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


        mainViewModel.setTitle(options.title.toString())
        val columnModifier = Modifier
            .padding(top = 5.dp)
            .fillMaxSize()

        val appointmentList = ArrayList<AppointmentItemListModel>()
        val appointmentItems = ArrayList<AppointmentItem>()

        val appointmentItem1 = AppointmentItem(appointmentType = 1)
        val appointmentItem2 = AppointmentItem(appointmentType = 2)
        val appointmentItem3 = AppointmentItem(appointmentType = 1)

        appointmentItems.add(appointmentItem1)
        appointmentItems.add(appointmentItem2)
        appointmentItems.add(appointmentItem3)


        val datedAppointmentSchedule1 = AppointmentItemListModel(appointmentItems = appointmentItems, appointmentType = 3, appointmentDate = LocalDate(year = 2023, monthNumber = 12, dayOfMonth = 27))
        val datedAppointmentSchedule2 = AppointmentItemListModel(appointmentItems = appointmentItems, appointmentType = 5, appointmentDate = LocalDate(year = 2023, monthNumber = 12, dayOfMonth = 27))
        appointmentList.add(datedAppointmentSchedule1)
        appointmentList.add(datedAppointmentSchedule2)

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
                        attachOurServices()
                        ServiceGridScreen()
                        RecommendedSessions()
                        AttachAppointments()
                        PopulateAppointmentScreen(datedAppointmentScheduleList = appointmentList, mainViewModel = mainViewModel)
                        NewProducts()
                        NewProductScreen()
                    }

                }
            }
    }


    @Composable
    fun ServiceGridScreen() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(250.dp),
            contentPadding = PaddingValues(6.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(8) {
                when (it) {
                    0 -> {
                        ServicesWidget(iconRes = "scissors_icon.png", serviceTitle = "Haircut")
                    }

                    1 -> {
                        ServicesWidget(iconRes = "facials_icon.png", serviceTitle = "Facials")
                    }

                    2 -> {
                        ServicesWidget(iconRes = "paste_icon.png", serviceTitle = "Tooth Wash")
                    }

                    3 -> {
                        ServicesWidget(iconRes = "massage_icon.png", serviceTitle = "Massage")
                    }

                    4 -> {
                        ServicesWidget(iconRes = "paste_icon.png", serviceTitle = "Tooth Wash")
                    }

                    5 -> {
                        ServicesWidget(iconRes = "massage_icon.png", serviceTitle = "Massage")
                    }

                    6 -> {
                        ServicesWidget(iconRes = "facials_icon.png", serviceTitle = "Facials")
                    }

                    7 -> {
                        ServicesWidget(iconRes = "scissors_icon.png", serviceTitle = "Haircut")
                    }

                    else -> {
                        ServicesWidget(iconRes = "facials_icon.png", serviceTitle = "Facials")
                    }

                }
            }
        }
    }


        @Composable
        fun Appointments() {
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(100.dp),
                contentPadding = PaddingValues(6.dp)
            ) {
                items(3) {
                    when (it) {
                        0 -> {
                            AppointmentsWidget(
                                iconRes = "scissors_icon.png",
                                serviceTitle = "Haircut",
                                appointmentTime = "Today  |  5:45pm"
                            )
                        }

                        1 -> {
                            AppointmentsWidget(
                                iconRes = "paste_icon.png",
                                serviceTitle = "Tooth Wash",
                                appointmentTime = "Tomorrow  |  5:00pm"
                            )
                        }

                        2 -> {
                            AppointmentsWidget(
                                iconRes = "scissors_icon.png",
                                serviceTitle = "Aromatherapy",
                                appointmentTime = "August 8th | 8:00pm"
                            )
                        }

                        else -> {
                            AppointmentsWidget(
                                iconRes = "scissors_icon.png",
                                serviceTitle = "IV Drip Therapy",
                                appointmentTime = "Today | 5:45pm"
                            )
                        }

                    }
                }
            }
        }


    @Composable
    fun ServicesWidget(iconRes: String, serviceTitle: String){

        val columnModifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 10.dp)
            .clickable {
                mainViewModel.setId(1)

            }
            .height(100.dp)
        MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
            Column(
                modifier = columnModifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment  = Alignment.CenterHorizontally,
            ) {
                val modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                attachServiceImage(iconRes)
                TextComponent(
                    text = serviceTitle,
                    fontSize = 15,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30,
                    textModifier = modifier
                )
            }
        }
    }





@Composable
    fun attachOurServices(){
        val rowModifier = Modifier
            .padding(start = 20.dp, top = 20.dp)
            .fillMaxWidth()
        MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = "Our Services",
                    fontSize = 18,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30,
                    textModifier = Modifier.fillMaxWidth(0.30f)
                )
                StraightLine()
            }

        }
    }

    @Composable
    fun RecommendedSessions(){
        val rowModifier = Modifier
            .padding(start = 20.dp, top = 20.dp)
            .fillMaxWidth()
        MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = "Recommended",
                    fontSize = 18,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30,
                    textModifier = Modifier.fillMaxWidth(0.34f)
                )
                StraightLine()
            }

            RecommendedAppointmentList()

        }
    }


    @Composable
    fun AttachAppointments(){
        val rowModifier = Modifier
            .padding(start = 20.dp, top = 20.dp)
            .fillMaxWidth()
        MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = "Your Appointment",
                    fontSize = 18,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30,
                    textModifier = Modifier.fillMaxWidth(0.40f)
                )
                StraightLine()
            }

        }
    }

    @Composable
    fun NewProducts(){
        val rowModifier = Modifier
            .padding(start = 20.dp, top = 10.dp)
            .fillMaxWidth()
        MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = "New Products",
                    fontSize = 18,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30,
                    textModifier = Modifier.fillMaxWidth(0.32f)
                )
                StraightLine()
            }

        }
    }



    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun NewProductScreen() {

        val coroutineScope = rememberCoroutineScope()

        Column {

            var showSheet by remember { mutableStateOf(false) }

            if (showSheet) {
                BottomSheet() {
                    showSheet = false
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(480.dp),
                contentPadding = PaddingValues(6.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                userScrollEnabled = false
            ) {
                items(2) {
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

        val boxModifier =
            Modifier
                .height(470.dp)
                .fillMaxWidth()

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
                    modifier = Modifier.fillMaxSize()
                ) { page ->
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
                        var color = Color.LightGray
                        var width = 0
                        if (pagerState.currentPage == iteration) {
                            color = Color(color = 0xFFF43569)
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
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun PopulateAppointmentScreen(datedAppointmentScheduleList: List<AppointmentItemListModel>, mainViewModel: MainViewModel) {
        LazyColumn(modifier = Modifier.fillMaxWidth().height(getAppointmentViewHeight(datedAppointmentScheduleList).dp), userScrollEnabled = false) {
            items(datedAppointmentScheduleList) {item ->
                AppointmentItemCard(viewType = item.appointmentType, contentSize = ((item.appointmentItems.size*145)), bookingItems = item, mainViewModel = mainViewModel)
            }
        }
    }

    @Composable
    fun AttachBusinessName(){
        val rowModifier = Modifier
            .padding(start = 20.dp)
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
                    fontFamily = GGSansBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30,
                    textModifier = modifier
                )
            }
        }

    @Composable
    fun GetBusinessPageList() : List<BusinessStatusAdsPage> {
         val adsList: ArrayList<BusinessStatusAdsPage> = arrayListOf()
         val imageWidget = BusinessStatusImageWidget()
         val statusAdsPage = BusinessStatusAdsPage(statusImage = imageWidget)
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
        val progressWidget = BusinessStatusProgressWidget()
        for(i in 0..< pageCount){
            val adsProgress = BusinessStatusAdsProgress(adsProgress = progressWidget, pageId = i)
            progressList.add(adsProgress)
        }
        return progressList
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun BusinessStatusDisplay() {
        val boxBgModifier =
            Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxHeight()
                .fillMaxWidth()

        Box(modifier = boxBgModifier) {
            val businessPageList = GetBusinessPageList()
            BusinessStatusWidget(businessPageList, GetBusinessPageProgressList(businessPageList.size))
        }


        /* val color = Colors.primaryColor
        var isPaused by remember { mutableStateOf(false) }
        var isRestart by remember { mutableStateOf(false) }
        var pageProgress by remember { mutableStateOf(0f) }*/
        //var savedCurrentPage by remember { mutableStateOf(pagerState.currentPage) }


        /*var key by remember { mutableStateOf(false) }


        animateBusinessStatus(isPaused = false, currentPage = savedCurrentPage, totalPage = pagerState.pageCount - 1, onNextPage = {
            savedCurrentPage = it
            pageProgress = 0f
            isRestart = true
            },
            onProgress = {
                pageProgress = it
            })

        LaunchedEffect(key1 = isRestart) {
            launch { //scrolls to page
                delay(200)
                with(pagerState) {
                    animateScrollToPage(page = savedCurrentPage)
                    isRestart = false
                }
            }
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                savedCurrentPage = page // reset progress
                pageProgress = 0f
            }
        }*/

    }

   @Composable
    private fun animateBusinessStatus(isPaused: Boolean = false, currentPage: Int, totalPage: Int, onNextPage: (page: Int) -> Unit, onProgress: (progress: Float) -> Unit){
       var currentTime by remember { mutableStateOf(0f) }
       var savedCurrentPage by remember { mutableStateOf(0) }
       var progress: Float = 0f
       val totalTime: Float = 5000f

       if (savedCurrentPage != currentPage){
           // User Swipes the View Manually
           currentTime = 0f
           savedCurrentPage = currentPage
       }

        LaunchedEffect(key1 = currentTime, key2 = isPaused) {
            while (currentTime < totalTime && !isPaused) { // time to view
                delay(10L)
                currentTime += 10
                println(currentTime)
                println(totalTime)
                progress = ((currentTime/totalTime))
                onProgress(progress)
            }
            if(currentPage < totalPage) { //is done next page
                currentTime = 0f
                progress = 0f
                onProgress(progress)
                savedCurrentPage = currentPage + 1
                onNextPage(currentPage + 1)
            }
            else{ // no next go to 0
                currentTime = 0f
                progress = 0f
                onProgress(progress)
                savedCurrentPage = 0
                onNextPage(0)
            }

        }
    }




    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun DropDownWidget(menuItems: List<String>,
                       menuExpandedState: Boolean,
                       selectedIndex : Int,
                       updateMenuExpandStatus : () -> Unit,
                       onDismissMenuView : () -> Unit,
                       onMenuItemclick : (Int) -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.CenterStart)
                .clickable(
                    onClick = {
                        updateMenuExpandStatus()
                    },
                ),
        ) {

            MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val modifier = Modifier.padding(start = 10.dp)

                    val textStyle: TextStyle = TextStyle(
                        fontSize = TextUnit(20f, TextUnitType.Sp),
                        fontFamily = GGSansRegular,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.SemiBold
                    )

                    TextComponent(
                        textModifier = Modifier.fillMaxWidth(0.9f).padding(start = 10.dp, end = 10.dp),
                        text = "Service Type Number One is Here",
                        fontSize = 18,
                        fontFamily = GGSansRegular,
                        textStyle = textStyle,
                        textColor = Color.DarkGray,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Black
                    )

                    val imageModifier = Modifier
                        .size(25.dp)
                        .padding(start = 10.dp, top = 3.dp)
                    ImageComponent(
                        imageModifier = imageModifier,
                        imageRes = "chevron_down_icon.png",
                        colorFilter = ColorFilter.tint(color = Color.Gray)
                    )

                }
            }
        }
    }


    @Composable
    fun AttachLocationIcon() {
        val modifier = Modifier
            .padding(top = 2.dp)
            .size(20.dp)
        ImageComponent(imageModifier = modifier, imageRes = "location_icon_filled.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
    }
}