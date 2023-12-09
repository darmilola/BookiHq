package screens.main

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansBold
import GGSansRegular
import GGSansSemiBold
import Models.AvailableSlotsUIModel
import Models.CalendarDataSource
import Models.CalendarUiModel
import Models.WorkingHoursDataSource
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import components.IconTextFieldComponent
import components.ImageComponent
import components.LocationToggleButton
import components.StraightLine
import components.TextComponent
import components.welcomeGradientBlock
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import widgets.AppointmentsWidget
import widgets.AttachTherapistWidget
import widgets.ReviewsWidget
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


        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = columnModifier
            ) {
                Column(
                    Modifier
                        .padding(bottom = 85.dp)
                        .fillMaxSize()
                        .background(color = Color(0xFFF3F3F3))

                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())) {
                        attachUserAddress()
                        attachSearchBar()
                        attachTopServices()
                        ServiceGridScreen()
                        ProductPromoCard()
                        attachAppointments()
                        Appointments()
                    }

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
        var showSheet by remember { mutableStateOf(false) }
        if (showSheet) {
            BottomSheet() {
                showSheet = false
            }
        }
        val columnModifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 10.dp)
            .clickable {
                showSheet = true
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
    fun attachTopServices(){
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
                    text = "Top Services",
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
    fun attachAppointments(){
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
                    text = "My Appointments",
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
    fun attachSearchBar(){
        var text by remember { mutableStateOf(TextFieldValue("")) }


        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
            val textStyle: TextStyle = TextStyle(
                fontSize = TextUnit(20f, TextUnitType.Sp),
                fontFamily = GGSansSemiBold,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal
            )

            val modifier  = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 15.dp)
                .wrapContentWidth()
                .height(55.dp)
                .border(width = 1.dp, color = Color.Gray, shape =  RoundedCornerShape(30.dp))


            Box(modifier = modifier) {
                IconTextFieldComponent(
                    text = text,
                    readOnly = false,
                    textStyle = textStyle,
                    modifier = Modifier,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = { it ->
                        text = it
                    }, isSingleLine = true, iconRes = "search_icon.png"
                )
            }
        }
    }

    @Composable
    fun attachUserAddress(){
        val rowModifier = Modifier
            .padding(start = 20.dp)
            .fillMaxWidth()
        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = rowModifier
            ) {
                val modifier = Modifier.padding(start = 3.dp)
                attachLocationIcon()
                TextComponent(
                    text = "301 Dorthy Walks, Chicago, Illinois",
                    fontSize = 16,
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
    }



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BottomSheet(onDismiss: () -> Unit) {
        val modalBottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = modalBottomSheetState,
            modifier = Modifier.fillMaxHeight(0.95f),
            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
            scrimColor = Color(color = 0x50000000),
            dragHandle = null,
            tonalElevation = 10.dp,
            contentColor = contentColorFor(Color.Yellow),
            containerColor = Color(0xFFF3F3F3)
        ) {

            val boxModifier =
                Modifier
                    .fillMaxHeight(0.45f)
                    .fillMaxWidth()
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                // AnimationEffect
                Box(contentAlignment = Alignment.TopStart, modifier = boxModifier) {
                    attachServiceImages()
                }

                ServiceTitle()
                ServiceLocationToggle()
                AttachServiceTypeToggle()
                BookingCalendar()
                TherapistContent()
                AvailableSlotsContent()
                AttachServiceReviews()

            }
        }
    }


    @OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
    @Composable
    fun attachServiceImages(){

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
                ImageComponent(imageModifier = Modifier.fillMaxSize(), imageRes = "$page.jpg", contentScale = ContentScale.Crop)
            }
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                        var color = Color.White
                        var width = 0
                        if (pagerState.currentPage == iteration){
                       color =  Color(color = 0xFFF43569)
                       width = 20
                    } else{
                       color =  Color(0xFFF3F3F3)
                       width = 20
                    }
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .height(5.dp)
                            .width(width.dp)
                    )
                }

            }
        }

    }

    @Composable
    fun ProductPromoCard() {
        val imageModifier =
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        Card(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                .background(color = Color.White)
                .height(300.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            border = null
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.BottomStart
            ) {
                ImageComponent(imageModifier = imageModifier, imageRes = "oil.jpg", contentScale = ContentScale.Crop)
                welcomeGradientBlock()
                DealText()
            }
        }
    }


    @Composable
    fun DealText(){
        val rowModifier = Modifier
            .padding(start = 25.dp, top = 5.dp, bottom = 25.dp)
            .fillMaxWidth()
        MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
            Column(
                modifier = rowModifier
            ) {
                val modifier = Modifier.padding(start = 5.dp)
                TextComponent(
                    text = "Exclusive Deal",
                    fontSize = 25,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.White,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30
                )

                TextComponent(
                    text = "Flat 60% off on Curology",
                    fontSize = 16,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.White,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30
                )

            }
        }
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
                text = "Body Massage",
                fontSize = 25,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Light,
                lineHeight = 30,
                textModifier = Modifier
                    .fillMaxWidth()
            )
        }

    }

    @Composable
    fun ServiceLocationToggle(){
        val checked by remember { mutableStateOf(false) }

        val tint by animateColorAsState(if (checked) Color.Cyan else Color.Black)
        val textColor = if (checked) Color.White else Color.Cyan

        val background = Brush.horizontalGradient(
            colors = listOf(
                Color(color = 0xFFF43569),
                Color(color = 0xFFFF823E)
            ))


        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 25.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment  = Alignment.CenterHorizontally,
        ) {

            TextComponent(
                text = "Where do you want your Service?",
                fontSize = 18,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Black,
                lineHeight = 30,
                textModifier = Modifier
                    .fillMaxWidth()
            )

            LocationToggleButton(borderStroke = BorderStroke(1.dp, Color(color = 0xFFF43569)), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 20, shape = RoundedCornerShape(10.dp), style = MaterialTheme.typography.h4)

        }

    }



    @Composable
    fun AttachServiceTypeToggle(){
        var checked by remember { mutableStateOf(false) }

        val tint by animateColorAsState(if (checked) Color.Cyan else Color.Black)
        val textColor = if (checked) Color.White else Color.Cyan

        val background = Brush.horizontalGradient(
            colors = listOf(
                Color(color = 0xFFF43569),
                Color(color = 0xFFFF823E)
            ))


        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 10.dp, top = 25.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment  = Alignment.CenterHorizontally,
        ) {

            TextComponent(
                text = "What type of Service do you want?",
                fontSize = 18,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Black,
                lineHeight = 30,
                textModifier = Modifier
                    .padding(bottom = 15.dp)
                    .fillMaxWidth()
            )

            attachDropDownWidget()


        }

    }


    @Composable
    fun attachDropDownWidget(){
        val serviceTypes = listOf("Service Type Number One is Here", "Service Type Number One is Here",)

        var serviceTypesExpanded = remember { mutableStateOf(false) }

        var selectedIndex = remember { mutableStateOf(0) }

        Column (
            modifier = Modifier
                .height(55.dp)
                .border(border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(8.dp))
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,

            ) {
            DropDownWidget(
                menuItems = serviceTypes,
                menuExpandedState = serviceTypesExpanded.value,
                selectedIndex = selectedIndex.value,
                updateMenuExpandStatus = {
                    serviceTypesExpanded.value = true
                },
                onDismissMenuView = {
                    serviceTypesExpanded.value = false
                },
                onMenuItemclick = { index->
                    selectedIndex.value = index
                    serviceTypesExpanded.value = false
                }
            )
        }

    }

    @Composable
    fun BookingCalendar(modifier: Modifier = Modifier) {

        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()

        Column(modifier = modifier.fillMaxSize().padding(start = 15.dp, end = 15.dp, top = 30.dp)) {
            val dataSource = CalendarDataSource()
            // get CalendarUiModel from CalendarDataSource, and the lastSelectedDate is Today.
            var calendarUiModel = dataSource.getData(lastSelectedDate = dataSource.today)

            var selectedUIModel by remember { mutableStateOf(calendarUiModel) }

            var initialVisibleDates by remember { mutableStateOf(5) }


            CalenderHeader(selectedUIModel, onPrevClickListener = { startDate ->
                coroutineScope.launch {
                   if(initialVisibleDates > 0)  initialVisibleDates--
                    listState.animateScrollToItem(index = initialVisibleDates)
                }
            },
            onNextClickListener = { endDate ->
                coroutineScope.launch {
                   if(initialVisibleDates < listState.layoutInfo.totalItemsCount - 5) initialVisibleDates++
                    listState.animateScrollToItem(index = initialVisibleDates )
                }
            })
            CalenderContent(selectedUIModel, onDateClickListener = {
                it -> selectedUIModel = selectedUIModel.copy(
                selectedDate = it,
                visibleDates = selectedUIModel.visibleDates.map { it2 ->
                    it2.copy(
                        isSelected = it2.date == it.date
                    )
                }
            )}, listState = listState)
        }
    }
    @Composable
    fun CalenderHeader(calendarUiModel: CalendarUiModel, onPrevClickListener: (LocalDate) -> Unit,
                       onNextClickListener: (LocalDate) -> Unit,) {
        val imageModifier = Modifier
            .size(25.dp)

        Row {
            Row(modifier = Modifier
                .weight(1f)
                .clickable {
                    onPrevClickListener(calendarUiModel.startDate.date)
                }) {
                ImageComponent(
                    imageModifier = imageModifier.rotate(180f),
                    imageRes = "left_arrow.png",
                    colorFilter = ColorFilter.tint(color = Color.Gray)
                )
            }

            Row(modifier = Modifier.weight(2f),horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top) {
                TextComponent(
                    text = if (calendarUiModel.selectedDate.isToday) {
                        "Today"
                    } else {
                        calendarUiModel.selectedDate.date.month.toString()
                            .lowercase() + ", " + calendarUiModel.selectedDate.date.year.toString()
                    },
                    textModifier = Modifier
                        .align(Alignment.CenterVertically),
                    fontSize = 20,
                    fontFamily = GGSansSemiBold,
                    fontWeight = FontWeight.Light,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    textStyle = MaterialTheme.typography.h4
                )
            }

            Row(modifier = Modifier
                .weight(1f)
                .clickable {
                    onNextClickListener(calendarUiModel.endDate.date)
                },
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top) {
                ImageComponent(
                    imageModifier = imageModifier,
                    imageRes = "left_arrow.png",
                    colorFilter = ColorFilter.tint(color = Color.Gray)
                )
            }

        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ContentItem(date: CalendarUiModel.Date, onClickListener: (CalendarUiModel.Date) -> Unit) {
        val textColor: Color = if(date.isSelected){
            Color.White
        }
        else{
            Color.DarkGray
        }

        val bgColor: Color = if(date.isSelected){
            Color(color = 0xFFFA2D65)
        }
        else{
            Color.White
        }


        Card(colors = CardDefaults.cardColors(
                containerColor = bgColor
            ),
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 4.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Column(
                modifier = Modifier
                    .width(70.dp)
                    .height(80.dp)
                    .clickable {
                        onClickListener(date)
                    }
                    .padding(4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextComponent(
                    text = date.date.dayOfMonth.toString(),
                    textModifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 25,
                    fontFamily = GGSansSemiBold,
                    fontWeight = FontWeight.ExtraBold,
                    textColor = textColor,
                    textAlign = TextAlign.Center,
                    textStyle = MaterialTheme.typography.h4
                )
                TextComponent(
                    text = date.date.dayOfWeek.toString().substring(0,3),
                    textModifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 16,
                    fontFamily = GGSansSemiBold,
                    fontWeight = FontWeight.Light,
                    textColor = textColor,
                    textAlign = TextAlign.Center,
                    textStyle = MaterialTheme.typography.h4)
            }
        }
    }

    @Composable
    fun CalenderContent(calendarUiModel: CalendarUiModel, onDateClickListener: (CalendarUiModel.Date) -> Unit, listState: LazyListState) {

        LazyRow(modifier = Modifier.padding(start = 10.dp, top = 10.dp).fillMaxWidth(), state = listState) {
            // pass the visibleDates to the UI
            items(items = calendarUiModel.visibleDates) { date ->
                ContentItem(date, onDateClickListener)
            }
        }
    }



    @Composable
    fun TherapistContent() {
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 10.dp, top = 25.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment  = Alignment.CenterHorizontally,
        ) {

            TextComponent(
                text = "Choose Specialist",
                fontSize = 18,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Black,
                lineHeight = 30,
                textModifier = Modifier.fillMaxWidth()
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(160.dp),
                contentPadding = PaddingValues(6.dp)
            ) {
                items(5) {
                    AttachTherapistWidget()
                }
            }
        }
    }


    @Composable
    fun AvailableSlotsGrid() {

        val dataSource = WorkingHoursDataSource()
        // get CalendarUiModel from CalendarDataSource, and the lastSelectedDate is Today.
        val timePair = Pair(Pair("7:00","12:00"), false)
        var workingHours = dataSource.getWorkingHours(lastSelectedSlot = timePair)

        var selectedWorkHourUIModel by remember { mutableStateOf(workingHours) }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth().padding(top = 5.dp).height(300.dp),
            contentPadding = PaddingValues(6.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(selectedWorkHourUIModel.visibleSlots.size) { i ->
                AvailableSlotsItem(selectedWorkHourUIModel.visibleSlots[i], onWorkHourClickListener = {
                        it -> selectedWorkHourUIModel = selectedWorkHourUIModel.copy(
                    selectedSlot = it,
                    visibleSlots = selectedWorkHourUIModel.visibleSlots.map { it2 ->
                        it2.copy(
                            isSelected = it2.timeSlot == it.timeSlot
                        )
                    }
                )})
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
    @Composable
    fun AttachServiceReviews(){

        TextComponent(
            text = "Latest Reviews",
            fontSize = 20,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .padding(bottom = 15.dp, start = 15.dp)
                .fillMaxWidth()
        )

        val pagerState = rememberPagerState(pageCount = {
            5
        })

        val boxModifier =
            Modifier
                .padding(bottom = 20.dp, top = 20.dp, start = 15.dp)
                .fillMaxHeight()
                .fillMaxWidth()

        val boxBgModifier =
            Modifier
                .padding(bottom = 10.dp, top = 10.dp, start = 15.dp)
                .fillMaxHeight()
                .fillMaxWidth()
                .border(border = BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(topStart = 7.dp, bottomStart = 7.dp))


        Box(modifier = boxBgModifier) {

            Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    ReviewsWidget()
                }
                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
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
                                .height(5.dp)
                                .width(width.dp)
                        )
                    }

                }
            }
        }

    }




    @Composable
    fun AvailableSlotsContent() {
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 10.dp, top = 15.dp, bottom = 10.dp)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment  = Alignment.CenterHorizontally,
        ) {

            TextComponent(
                text = "Available Slots",
                fontSize = 18,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Black,
                lineHeight = 30,
                textModifier = Modifier.fillMaxWidth()
            )

            AvailableSlotsGrid()
        }
    }



    @Composable
    fun AvailableSlotsItem(availableSlot: AvailableSlotsUIModel.AvailableSlot, onWorkHourClickListener: (AvailableSlotsUIModel.AvailableSlot) -> Unit) {
        val timeStampObject = availableSlot.timeSlot
        val timeRange = timeStampObject.first
        val meridianVal: String = if(timeStampObject.second) "AM" else "PM"
        val color: Color = if(availableSlot.isSelected) Color(color = 0xFFFA2D65) else Color.Gray

        Column(
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp, top = 15.dp)
                .fillMaxWidth()
                .clickable {
                    onWorkHourClickListener(availableSlot)
                }
                .border(border = BorderStroke((1.5).dp, color), shape = RoundedCornerShape(3.dp))
                .height(50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextComponent(
                text = timeRange.first+" - "+timeRange.second+ " "+meridianVal,
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = color,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                lineHeight = 30,
                textModifier = Modifier.fillMaxWidth()
            )
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
    fun attachLocationIcon() {
        val modifier = Modifier
            .padding(top = 2.dp)
            .size(18.dp)
        ImageComponent(imageModifier = modifier, imageRes = "location_icon_filled.png", colorFilter = ColorFilter.tint(color = Color.LightGray))
    }
}