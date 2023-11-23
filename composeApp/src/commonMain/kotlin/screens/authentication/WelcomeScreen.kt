package screens.authentication

import AppTheme.AppColors
import AppTheme.AppTypography
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import components.ButtonComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import widgets.WelcomeScreenPagerContent

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreenCompose() {
    val bgStyle = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
    MaterialTheme(colors = AppColors(), typography = AppTypography()) {
        Column(
            modifier = bgStyle
        ) {
            attachPages()
            attachActionButtons(style = MaterialTheme.typography.h4)
        }
    }



    }

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
 fun attachPages(){


    val pagerState = rememberPagerState(pageCount = {
        3
    })



    val  boxModifier =
        Modifier
            .fillMaxHeight(0.80f)
            .fillMaxWidth()

     // AnimationEffect
     Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
         HorizontalPager(
             state = pagerState,
             modifier = Modifier.fillMaxSize()
         ) { page ->
                 WelcomeScreenPagerContent("$page.jpg")
             }

             // Our page content
                // WelcomeScreenPagerContent("0.jpg")
              //   WelcomeScreenPagerContent("1.jpg")

         Row(
             Modifier
                 .wrapContentHeight()
                 .fillMaxWidth()
                 .padding(bottom = 4.dp),
             horizontalArrangement = Arrangement.Center
         ) {
             repeat(pagerState.pageCount) { iteration ->
                 val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
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

@Composable
fun attachActionButtons(style: TextStyle){

    val bgStyle = Modifier
        .fillMaxWidth()
        .fillMaxHeight()

    val buttonStyle = Modifier
        .padding(bottom = 10.dp)
        .fillMaxWidth(0.85f)
        .height(56.dp)

    Column(modifier = bgStyle, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        ButtonComponent(modifier = buttonStyle, buttonText = "Continue", borderStroke = null, colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray), fontSize = 18, shape = RoundedCornerShape(28.dp), textColor = Color.White, style = style)
        ButtonComponent(modifier = buttonStyle, buttonText = "Login to Your Account", borderStroke = BorderStroke(1.dp, Color.DarkGray), colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface), fontSize = 18, shape = RoundedCornerShape(28.dp), textColor = Color.DarkGray, style = style)
    }

}


object WelcomeScreen : Screen {
    @Composable
    override fun Content() {
        WelcomeScreenCompose()
    }
}

