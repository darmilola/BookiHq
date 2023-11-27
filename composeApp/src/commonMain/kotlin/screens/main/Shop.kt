package screens.main

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import screens.SplashScreenCompose

object ShopTab : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Shop"
            val icon = painterResource("shop.png")

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
            .padding(top = 30.dp)
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
                        .fillMaxHeight(0.10f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    HomeTab.attachCompanyLogo()


                }

                Row(
                    Modifier
                        .padding(bottom = 85.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = Color.Magenta),
                    horizontalArrangement = Arrangement.Center
                ) {}

            }
        }
    }

}