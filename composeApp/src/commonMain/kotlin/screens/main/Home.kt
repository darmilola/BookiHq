package screens.main

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansBold
import GGSansSemiBold
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import components.TextComponent
import widgets.ServicesWidget

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
            .fillMaxHeight()
            .fillMaxWidth()

        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = columnModifier
            ) {
                Column(
                    Modifier
                        .padding(bottom = 85.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = Color(0xFFF3F3F3)),

                ) {
                    attachUserAddress()
                    attachSearchBar()
                    ServiceGridScreen()
                }
            }
        }
    }

    @Composable
    fun ServiceGridScreen() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxSize().padding(top = 10.dp),
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
    fun attachTopServices(){
        val rowModifier = Modifier
            .padding(start = 20.dp, top = 20.dp)
            .fillMaxWidth()
        MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = rowModifier
            ) {

                TextComponent(
                    text = "Top Services",
                    fontSize = 20,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color(color = 0xFFFA2D65),
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30,
                    textModifier = Modifier
                )
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


    @Composable
    fun attachLocationIcon() {
        val modifier = Modifier
            .padding(top = 2.dp)
            .size(18.dp)
        ImageComponent(imageModifier = modifier, imageRes = "location_icon_filled.png", colorFilter = ColorFilter.tint(color = Color.LightGray))
    }
}