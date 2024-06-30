package presentation.account

import GGSansSemiBold
import StackedSnackbarHost
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import domain.Enums.Screens
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.components.RightIconButtonComponent
import presentation.viewmodels.MainViewModel
import presentation.widgets.OTPTextField
import presentations.components.ImageComponent
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.styles.Colors

class JoinASpa(private val mainViewModel: MainViewModel) : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Join A Spa"
            val icon = painterResource("profile_icon.png")

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

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )
        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                JoinASpaTopBar(mainViewModel)
            },
            backgroundColor = Color.White,
            floatingActionButton = {},
            content = {

                val columnModifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()
                Box(modifier = Modifier.fillMaxSize()) {
                    Card(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxHeight(0.90f)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = columnModifier,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {

                            AppLogo("drawable/spa_icon.png")

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 5.dp)
                                    .wrapContentHeight(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {

                                TextComponent(
                                    text = "Join A Spa",
                                    fontSize = 35,
                                    fontFamily = GGSansSemiBold,
                                    textStyle = TextStyle(),
                                    textColor = Colors.darkPrimary,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Black,
                                    lineHeight = 45,
                                    letterSpacing = 1,
                                    textModifier = Modifier
                                        .fillMaxWidth())

                            }


                            TextComponent(
                                text = "Enter Invite Code to Proceed",
                                fontSize = 16,
                                fontFamily = GGSansSemiBold,
                                textStyle = MaterialTheme.typography.h6,
                                textColor = Color.LightGray,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 20,
                                maxLines = 2,
                                textModifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                                overflow = TextOverflow.Ellipsis)


                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .padding(start = 10.dp, end = 10.dp, top = 30.dp, bottom = 30.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center

                            ) {

                                var otpValue by remember {
                                    mutableStateOf("")
                                }

                                OTPTextField(
                                    otpText = otpValue,
                                    onOTPTextChanged = { value, OTPInputField ->
                                        otpValue = value
                                    }
                                )

                            }

                            Column(modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally) {

                                 val buttonStyle = Modifier
                                     .padding(bottom = 10.dp, top = 10.dp, start = 20.dp, end = 20.dp)
                                     .fillMaxWidth()
                                     .height(50.dp)

                                 RightIconButtonComponent(modifier = buttonStyle, buttonText = "Proceed", borderStroke = BorderStroke(0.8.dp, Colors.darkPrimary), colors = ButtonDefaults.buttonColors(backgroundColor = Colors.lightPrimaryColor), fontSize = 16, shape = CircleShape, textColor = Colors.darkPrimary, style = MaterialTheme.typography.h4, iconRes = "drawable/forward_arrow.png",  colorFilter = ColorFilter.tint(color = Colors.darkPrimary)){
                                     mainViewModel.setScreenNav(Pair(Screens.JOIN_SPA.toPath(), Screens.VENDOR_INFO.toPath()))
                                 }
                               }
                            }

                    }
                }


            }
        )
    }
    }

    @Composable
    fun AppLogo(logoUrl: String) {
        Box(modifier = Modifier
                .size(150.dp),
            contentAlignment = Alignment.Center
        ) {
            ImageComponent(
                imageModifier = Modifier.fillMaxSize().padding(3.dp), imageRes = logoUrl)
        }
    }


