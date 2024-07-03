package presentation.authentication

import GGSansRegular
import domain.Models.PlatformNavigator
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.Enums.Auth0ConnectionType
import domain.Enums.AuthSSOScreenNav
import presentation.components.ButtonComponent
import presentation.components.IconButtonComponent
import presentation.widgets.WelcomeScreenPagerContent
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun WelcomeScreenCompose(platformNavigator: PlatformNavigator, userEmail: String = "") {
    val bgStyle = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color(color = 0x60000000),
                    Color(color = 0x80000000),
                    Color.Black,
                    Color.Black)
            )
        )

        Box(contentAlignment = Alignment.TopCenter, modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()

        ) {
            ImageComponent(imageModifier = Modifier.fillMaxHeight().fillMaxWidth(),
                imageRes = "drawable/woman_welcome.jpg", contentScale = ContentScale.FillHeight)

            Column(
                modifier = bgStyle,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.60f).padding(bottom = 40.dp), contentAlignment = Alignment.BottomCenter) {
                    AttachTopContent()
                }

                Box(modifier = Modifier.fillMaxWidth().wrapContentHeight(), contentAlignment = Alignment.TopCenter) {
                    AttachActionButtons(platformNavigator)

                }
                Box(modifier = Modifier.fillMaxWidth().height(100.dp).padding(start = 20.dp, end = 20.dp), contentAlignment = Alignment.TopCenter) {
                    TextComponent(
                        text = "An “agree to terms and conditions” is a method of protecting your business by requiring that users acknowledge the rules they must abide by when using your services.",
                        fontSize = 14,
                        fontFamily = GGSansRegular,
                        textStyle = TextStyle(),
                        textColor = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 15
                    )
                }

                if (userEmail.isNotEmpty()){
                    println("Success 0 $userEmail")
                }
                else{
                    println("Success 1 $userEmail")
                }


            }

        }

    }

@Composable
 fun AttachTopContent(){
     Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.50f), contentAlignment = Alignment.BottomCenter) {
         ImageComponent(imageModifier = Modifier.fillMaxWidth(0.60f).height(150.dp),
             imageRes = "drawable/carevida.png", contentScale = ContentScale.Inside)
     }
 }



@Composable
fun AttachActionButtons(platformNavigator: PlatformNavigator){
    val navigator = LocalNavigator.currentOrThrow
    val buttonStyle = Modifier
        .padding(bottom = 20.dp)
        .fillMaxWidth(0.90f)
        .height(45.dp)

    val phoneButtonStyle = Modifier
        .padding(bottom = 20.dp)
        .fillMaxWidth(0.90f)
        .height(45.dp)
        .background(
            shape = CircleShape,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Colors.primaryColor,
                    Colors.primaryColor,
                    Colors.primaryColor,
                    Colors.postPrimaryColor,
                    Colors.postPrimaryColor
                )
            )
        )
    Column(modifier = Modifier.fillMaxWidth().height(200.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        IconButtonComponent(modifier = phoneButtonStyle, buttonText = "Continue with phone number", borderStroke = BorderStroke((0.01).dp, Colors.primaryColor), iconSize = 24, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 16, shape = CircleShape, textColor = Color.White, style = MaterialTheme.typography.h4, iconRes = "drawable/care_icon.png", colorFilter = ColorFilter.tint(color = Color.White)){
           navigator.replaceAll(PhoneInputScreen(platformNavigator))
        }

        IconButtonComponent(modifier = buttonStyle, buttonText = "Continue with Facebook", borderStroke = BorderStroke(0.8.dp, Colors.blue), iconSize = 20, colors = ButtonDefaults.buttonColors(backgroundColor = Colors.blue), fontSize = 16, shape = CircleShape, textColor = Color.White, style = MaterialTheme.typography.h4, iconRes = "drawable/facebook_icon.png", colorFilter = ColorFilter.tint(color = Color.White)){
            platformNavigator.startFacebookSSO(onAuthSuccessful = {
                println("Success 0 $it")
            }, onAuthFailed = {
                println("Success 1")
            })
        }

        IconButtonComponent(modifier = buttonStyle, buttonText = "Sign in with Google", borderStroke = BorderStroke(0.8.dp, Color.White), iconSize = 20, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 16, shape = CircleShape, textColor = Color.Black, style = MaterialTheme.typography.h4, iconRes = "drawable/google_icon.png"){
            platformNavigator.startGoogleSSO(onAuthSuccessful = {}, onAuthFailed = {})
        }


    }


       /* ButtonComponent(modifier = buttonStyle, buttonText = "Continue", borderStroke = BorderStroke(1.dp, Colors.primaryColor), colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor), fontSize = 18, shape = CircleShape, textColor = Color.White, style = TextStyle()) {
            navigator.replace(AuthenticationScreen(currentPosition = AuthSSOScreenNav.AUTH_LOGIN.toPath(), platformNavigator = platformNavigator))
        }*/
    }


class WelcomeScreen(val platformNavigator: PlatformNavigator, val userEmail: String = "") : Screen {
    @Composable
    override fun Content() {
        WelcomeScreenCompose(platformNavigator, userEmail)
    }
}
