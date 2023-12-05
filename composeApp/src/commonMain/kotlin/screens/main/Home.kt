package screens.main

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansBold
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.IconButtonComponent
import components.ImageComponent
import components.TextComponent
import screens.SplashScreenCompose
import screens.authentication.attachWaveIcon

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
                Row(
                    Modifier
                        .padding(bottom = 85.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = Color(0xFFF3F3F3)),
                    horizontalArrangement = Arrangement.Center
                ) {
                    attachUserAddress()
                }

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