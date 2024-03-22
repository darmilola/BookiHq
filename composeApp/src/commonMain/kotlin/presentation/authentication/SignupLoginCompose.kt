package presentation.authentication

import GGSansBold
import GGSansRegular
import GGSansSemiBold
import domain.Models.PlatformNavigator
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.Models.Auth0ConnectionType
import domain.Models.AuthSSOScreenNav
import presentation.components.IconButtonComponent
import presentation.widgets.AuthenticationBackNav
import presentations.components.ImageComponent
import presentations.components.TextComponent


@Composable
fun SignUpLogin(platformNavigator: PlatformNavigator) {

    val  rootModifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight(0.95f)
            .background(color = Color(color = 0xFFFBFBFB))


        val topLayoutModifier =
            Modifier
                .padding(top = 40.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.87f)
                .background(color = Color(color = 0xFFFBFBFB))


        Column(modifier = rootModifier) {
            Column(modifier = topLayoutModifier) {
                Box(modifier = Modifier.wrapContentSize().padding(start = 10.dp, top = 10.dp)) {
                    AuthenticationBackNav(AuthSSOScreenNav.WELCOME_SCREEN.toPath(), platformNavigator)
                }
                welcomeToZazzy()
                attachAuthenticationText()
                attachAuthenticationButton(platformNavigator)
            }
        }
    }



@Composable
fun attachWaveIcon() {
    val modifier = Modifier
        .padding(end = 10.dp)
        .size(30.dp)
    ImageComponent(imageModifier = modifier, imageRes = "drawable/wave_hands.png")
}

@Composable
fun attachAuthenticationText() {
    val authText: String =  "Continue to Your Account"
    val rowModifier = Modifier
        .padding(top = 30.dp, start = 10.dp)
        .fillMaxWidth()

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            TextComponent(
                text = authText,
                fontSize = 20,
                fontFamily = GGSansRegular,
                textStyle = TextStyle(),
                textColor = Colors.primaryColor,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 30
            )
        }
    }


@Composable
fun attachAuthenticationButton(platformNavigator: PlatformNavigator) {
    val columnModifier = Modifier
        .padding(top = 50.dp, start = 10.dp)
        .fillMaxWidth()

    val buttonStyle = Modifier
        .padding(bottom = 15.dp)
        .fillMaxWidth(0.95f)
        .height(56.dp)

        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = columnModifier
        ) {

            IconButtonComponent(modifier = buttonStyle, buttonText = "Continue with Email", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFFBFBFB)), fontSize = 16, shape = RoundedCornerShape(28.dp), textColor = Color.Gray, style = MaterialTheme.typography.h4, iconRes = "drawable/email_icon.png",  colorFilter = ColorFilter.tint(color = Color.Gray)){
                    platformNavigator.startAuth0Login(Auth0ConnectionType.EMAIL.toPath())
            }
            IconButtonComponent(modifier = buttonStyle, buttonText = "Connect with Google", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFFBFBFB)), fontSize = 16, shape = RoundedCornerShape(28.dp), textColor = Color.Gray, style = MaterialTheme.typography.h4, iconRes = "drawable/google_icon.png"){
                platformNavigator.startAuth0Login(Auth0ConnectionType.GOOGLE.toPath())
            }

            IconButtonComponent(modifier = buttonStyle, buttonText = "Continue with X", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFFBFBFB)), fontSize = 16, shape = RoundedCornerShape(28.dp), textColor = Color.Gray, style = MaterialTheme.typography.h4, iconRes = "drawable/x_logo.png",  colorFilter = ColorFilter.tint(color = Color.Gray)){
                platformNavigator.startAuth0Login(Auth0ConnectionType.TWITTER.toPath())

            }
        }
    }



@Composable
fun authenticationTypeChangeText(currentScreen: Int = 0) {
    val rowModifier = Modifier
        .padding(top = 20.dp, start = 10.dp)
        .fillMaxWidth()

    val authActionText: String = if(currentScreen == AuthSSOScreenNav.AUTH_LOGIN.toPath()){
        "Sign up"
    }
    else {
        "Login"
    }

    val authText: String = if(currentScreen == AuthSSOScreenNav.AUTH_LOGIN.toPath()){
        "Don't have an account?"
    }
    else {
        "Already Have Account?"
    }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            val modifier = Modifier.padding(start = 5.dp)
            TextComponent(
                textModifier = modifier,
                text = authText,
                fontSize = 15,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 30
            )
            TextComponent(
                textModifier = modifier,
                text = authActionText,
                fontSize = 15,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.primaryColor,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
                lineHeight = 30
            )
        }
    }




@Composable
fun welcomeToZazzy(){
    val rowModifier = Modifier
        .padding(top = 50.dp)
        .fillMaxWidth()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            attachWaveIcon()
            TextComponent(
                text = "Welcome to Zazzy",
                fontSize = 23,
                fontFamily = GGSansBold,
                textStyle = TextStyle(),
                textColor = Colors.primaryColor,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 30
            )
        }
    }



