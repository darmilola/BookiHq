package screens.main

import GGSansRegular
import GGSansSemiBold
import Styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import components.ImageComponent
import components.TextComponent
import screens.Products.SearchBar
import screens.Products.CategoryScreen


class ShopTab(private val mainViewModel: MainViewModel) : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Products"
            val icon = painterResource("drawable/shop_icon.png")

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
        mainViewModel.setTitle("Products")
        Scaffold(
            topBar = {
                     SearchBar()
            },
            content = {
                content()
            },
         backgroundColor = Color.White,
         floatingActionButton = {
             AttachShoppingCartImage("shopping_cart.png")
         }
        )
    }

    @Composable
    fun AttachShoppingCartImage(iconRes: String) {


        val indicatorModifier = Modifier
            .padding(end = 15.dp, bottom = 20.dp)
            .background(color = Color.Transparent)
            .size(30.dp)
            .clip(CircleShape)
            .background(color =  Color(color = 0xFFFF5080))

        Box(
            Modifier
                .padding(bottom = 70.dp)
                .clip(CircleShape)
                .size(70.dp)
                .clickable {
                   mainViewModel.setId(3)
                }
                .background(color = Colors.darkPrimary),
            contentAlignment = Alignment.Center
        ) {
            val modifier = Modifier
                .size(40.dp)
            ImageComponent(imageModifier = modifier, imageRes = iconRes, colorFilter = ColorFilter.tint(color = Color.White))

            Box(modifier = indicatorModifier,
                contentAlignment = Alignment.Center){
                TextComponent(
                    text = "5",
                    fontSize = 17,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun content() {
        val columnModifier = Modifier
            .padding(top = 5.dp)
            .fillMaxHeight()
            .fillMaxWidth()
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = columnModifier
            ) {
                Row(
                    Modifier
                        .padding(bottom = 85.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = Color.White),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TabScreen()
                }

            }
        }


    @Composable
    fun TabScreen() {

        var tabIndex by remember { mutableStateOf(0) }

        val tabs = listOf("Face", "Body Treatment", "Hydro Treatment", "Body Treatment", "Hydro Treatment")

        Column(modifier = Modifier.fillMaxWidth().padding(top = 10.dp)) {
            ScrollableTabRow(selectedTabIndex = tabIndex,
                modifier = Modifier.height(50.dp),
                backgroundColor = Color.Transparent,
                indicator = { tabPositions ->
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[tabIndex])
                            .height(4.dp)
                            .padding(start = 30.dp, end = 30.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(color = Colors.primaryColor)
                    )

                },
                divider = {}) {
                tabs.forEachIndexed { index, title ->
                    Tab(text =
                    {
                        TextComponent(
                            text = title,
                            fontSize = 20,
                            fontFamily = GGSansSemiBold,
                            textStyle = TextStyle(),
                            textColor = Colors.darkPrimary,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 30)
                    },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }

                    )
                }
            }
            when (tabIndex) {
                0 -> CategoryScreen()
                1 -> CategoryScreen()
                2 -> CategoryScreen()
            }
        }
    }
}