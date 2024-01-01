package screens.authentication

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansBold
import Styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ButtonComponent
import components.GradientButton
import components.ImageComponent
import components.TextComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import widgets.WelcomeScreenPagerContent

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun WelcomeScreenCompose() {
    val bgStyle = Modifier
        .fillMaxWidth()
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
                attachPages()
                attachActionButtons(style = MaterialTheme.typography.h4)
            }
            attachCompanyLogo()

        }

    }
 }

@Composable
fun attachCompanyLogo() {
    val modifier = Modifier
        .padding(top = 50.dp, start = 10.dp)
        .fillMaxWidth()
        .size(200.dp)
    ImageComponent(imageModifier = modifier, imageRes = "app_logo_white.png")
}


@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
 fun attachPages(){


    val pagerState = rememberPagerState(pageCount = {
        3
    })



    val  boxModifier =
        Modifier
            .fillMaxHeight(0.75f)
            .fillMaxWidth()

     // AnimationEffect
     Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
         HorizontalPager(
             state = pagerState,
             modifier = Modifier.fillMaxSize()
         ) { page ->
                 WelcomeScreenPagerContent("$page.jpg")
             }

         Row(
             Modifier
                 .wrapContentHeight()
                 .fillMaxWidth()
                 .padding(bottom = 4.dp),
             horizontalArrangement = Arrangement.Center
         ) {
             repeat(pagerState.pageCount) { iteration ->
                 val color = if (pagerState.currentPage == iteration) Color(color = 0xFFF43569) else Color.LightGray
                 Box(
                     modifier = Modifier
                         .padding(2.dp)
                         .clip(CircleShape)
                         .background(color)
                         .size(8.dp)
                 )
             }

         }
     }

 }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun attachActionButtons(style: TextStyle){

    val navigator = LocalNavigator.currentOrThrow

    val bgStyle = Modifier
        .padding(bottom = 40.dp)
        .fillMaxWidth()
        .fillMaxHeight()

    val coroutineScope = rememberCoroutineScope()

    val buttonStyle = Modifier
        .padding(bottom = 10.dp)
        .fillMaxWidth(0.90f)
        .height(56.dp)

    val gradientButtonStyle = Modifier
        .padding(bottom = 5.dp, start = 5.dp, end = 5.dp)
        .fillMaxWidth()
        .height(56.dp)

    Column(modifier = bgStyle, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        GradientButton(modifier = gradientButtonStyle, buttonText = "Continue", borderStroke = null, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(28.dp), textColor = Color.White, style = style, gradient =  Brush.horizontalGradient(
            colors = listOf(
                Color(color = 0xFFF43569),
                Color(color = 0xFFFF823E)
            )
        )){
            navigator.replace(AuthenticationComposeScreen(currentScreen = 1))
        }

        ButtonComponent(modifier = buttonStyle, buttonText = "Login to Your Account", borderStroke = BorderStroke(1.dp, Colors.primaryColor), colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface), fontSize = 18, shape = RoundedCornerShape(28.dp), textColor = Colors.primaryColor, style = style){
            navigator.replace(AuthenticationComposeScreen(currentScreen = 0))
        }
    }

}


object WelcomeScreen : Screen {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        WelcomeScreenCompose()

    }
}
