package screens.main

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansBold
import GGSansSemiBold
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import components.StraightLine
import components.TextComponent
import components.welcomeGradientBlock
import widgets.AppointmentsWidget
import widgets.ServicesWidget
import widgets.WelcomeScreenPagerContent
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
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            scrimColor = Color(color = 0x50000000),
            dragHandle = null,
            tonalElevation = 10.dp,
            contentColor = contentColorFor(Color.Yellow),
            containerColor = Color.White
        ) {

            val  boxModifier =
                Modifier
                    .fillMaxHeight(0.40f)
                    .fillMaxWidth()

            // AnimationEffect
            Box(contentAlignment = Alignment.TopStart, modifier = boxModifier) {
                attachServiceImages()
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
                .fillMaxHeight()
                .fillMaxWidth()

        // AnimationEffect
        Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                ImageComponent(imageModifier = Modifier.fillMaxSize(), imageRes = "$page.jpg")
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
                       width = 24
                    } else{
                       color =  Color.LightGray
                       width = 8
                    }
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .height(8.dp)
                            .width(width.dp)
                    )
                }

            }
        }

    }





    @Composable
    fun ProductPromoCard() {
        val  imageModifier =
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
    fun attachLocationIcon() {
        val modifier = Modifier
            .padding(top = 2.dp)
            .size(18.dp)
        ImageComponent(imageModifier = modifier, imageRes = "location_icon_filled.png", colorFilter = ColorFilter.tint(color = Color.LightGray))
    }
}