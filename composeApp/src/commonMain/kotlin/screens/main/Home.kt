package screens.main

import AppTheme.AppBoldTypography
import AppTheme.AppColors
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.IconButtonComponent
import components.ImageComponent
import screens.SplashScreenCompose
import screens.authentication.AuthenticationScreen

object HomeTab : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
            val icon = painterResource("home.png")

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    fun attachCompanyLogo() {
        val modifier = Modifier
            .padding(top = 15.dp)
            .width(300.dp)
            .height(300.dp)
        ImageComponent(imageModifier = modifier, imageRes = "wave_hands.png")
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


                    attachCompanyLogo()


                }

                Row(
                    Modifier
                        .padding(bottom = 85.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = Color.Gray),
                    horizontalArrangement = Arrangement.Center
                ) {}

            }
        }
    }
}