package screens.main

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansBold
import GGSansRegular
import GGSansSemiBold
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.LocalNavigator
import components.ImageComponent
import components.StraightLine
import components.TextComponent
import components.welcomeGradientBlock
import widgets.AppointmentsWidget
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
                        attachBusinessName()
                        attachOurServices()
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


    @OptIn(InternalVoyagerApi::class)
    @Composable
    fun ServicesWidget(iconRes: String, serviceTitle: String){
        val tabNavigator = LocalNavigator.current

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
    fun attachBusinessName(){
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