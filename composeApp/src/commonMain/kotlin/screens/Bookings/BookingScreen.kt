package screens.Bookings

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import components.ButtonComponent
import components.GradientButton
import components.ImageComponent
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import screens.authentication.AuthenticationComposeScreen
import screens.authentication.AuthenticationViewModel
import screens.authentication.ContinueWithPhoneCompose
import screens.authentication.SignUpLoginCompose
import screens.authentication.VerifyOTPCompose
import screens.authentication.attachActionButtons
import screens.authentication.attachAuthenticationButton
import screens.authentication.attachAuthenticationText
import screens.authentication.attachAuthenticationTypeChangeView
import screens.authentication.attachBackIcon
import screens.authentication.attachCompanyLogo
import screens.authentication.attachPages
import screens.authentication.welcomeToBeaMo
import screens.main.AccountTab
import screens.main.ConsultTab
import screens.main.DiaryTab
import screens.main.HomeTab
import screens.main.MainTopBar
import screens.main.MainViewModel
import screens.main.ShopTab
import widgets.WelcomeScreenPagerContent



class BookingScreen(private val mainViewModel: MainViewModel) : Screen {

    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {

        val pagerState = rememberPagerState(pageCount = {
            3
        })


        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
            val topLayoutModifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color(0xFFF3F3F3))
                    Column(modifier = topLayoutModifier) {

                        BookingScreenTopBar(mainViewModel, pagerState)

                        val bgStyle = Modifier
                            .fillMaxWidth()
                            .background(color = Color(0xFFF3F3F3))
                            .fillMaxHeight()
                        MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {

                            Box(contentAlignment = Alignment.TopCenter, modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .background(color = Color.White)
                            ) {

                                Column(
                                    modifier = bgStyle
                                ) {
                                    AttachBookingPages(pagerState)
                                    AttachActionButtons(pagerState)
                                }
                            }

                        }

                    }
        }


    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    @Composable
    fun AttachActionButtons(pagerState: PagerState){

        var btnFraction by remember { mutableStateOf(0f) }

        val currentPage = pagerState.currentPage
        val navigator = LocalNavigator.currentOrThrow

        if (pagerState.currentPage == 1){
            btnFraction = 0.5f
        }
        else {
            btnFraction = 0f
        }

        val bgStyle = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight()

        val coroutineScope = rememberCoroutineScope()

        val buttonStyle = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(btnFraction)
            .height(50.dp)

        val buttonStyle2 = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .height(50.dp)


        Row (modifier = bgStyle,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,) {
            ButtonComponent(modifier = buttonStyle, buttonText = "Add More Services", borderStroke = BorderStroke(1.dp, Color(color = 0xFFF43569)), colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF3F3F3)), fontSize = 18, shape = RoundedCornerShape(10.dp), textColor = Color(color = 0xFFF43569), style = MaterialTheme.typography.h4 ){
                navigator.pop()
            }

            ButtonComponent(modifier = buttonStyle2, buttonText = "Continue", colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFF43569)), fontSize = 18, shape = RoundedCornerShape(10.dp), textColor = Color(color = 0xFFFFFFFF), style = MaterialTheme.typography.h4, borderStroke = null){
                coroutineScope.launch {
                    if(currentPage == 0){
                        pagerState.animateScrollToPage(1)
                    }
                    else if(currentPage == 1){
                        pagerState.animateScrollToPage(2)
                    }
                }
            }
        }

    }


    @OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
    @Composable
    fun AttachBookingPages(pagerState: PagerState){

        val  boxModifier =
            Modifier
                .padding(top = 15.dp)
                .background(color = Color(0xFFF3F3F3))
                .fillMaxHeight(0.85f)
                .fillMaxWidth()

        // AnimationEffect
        Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> BookingSelectServices()
                    1 -> BookingSelectSpecialist()
                    2 -> BookingPayment()
                }
            }

        }

    }
}

