package presentation.authentication

import GGSansBold
import GGSansRegular
import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.IconButtonComponent
import components.ImageComponent
import components.TextComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.viewmodels.AuthenticationViewModel


@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignUpLogin(currentScreen: Int = 0) {

    val viewModel: AuthenticationViewModel = AuthenticationViewModel()
    val authenticationScreenData = viewModel.authenticationScreenData ?: return


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
                AttachBackIcon(-1)
                welcomeToSavanna()
                attachAuthenticationText(currentScreen)
                attachAuthenticationButton()
            }
            attachAuthenticationTypeChangeView(currentScreen)
        }
    }



@Composable
fun attachWaveIcon() {
    val modifier = Modifier
        .padding(end = 10.dp)
        .size(30.dp)
    ImageComponent(imageModifier = modifier, imageRes = "wave_hands.png")
}

@Composable
fun attachAuthenticationText(currentScreen: Int = 0) {
    val authText: String = if(currentScreen == 0){
        "Login to Your Account"
    }
    else{
        "Sign Up"
    }
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
fun attachAuthenticationButton() {
    val columnModifier = Modifier
        .padding(top = 50.dp, start = 10.dp)
        .fillMaxWidth()

    val navigator = LocalNavigator.currentOrThrow

    val buttonStyle = Modifier
        .padding(bottom = 15.dp)
        .fillMaxWidth(0.95f)
        .height(56.dp)

        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = columnModifier
        ) {

            IconButtonComponent(modifier = buttonStyle, buttonText = "Use Phone Number", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFFBFBFB)), fontSize = 16, shape = RoundedCornerShape(28.dp), textColor = Color.Gray, style = MaterialTheme.typography.h4, iconRes = "drawable/phone_light_icon.png", iconSize = 45, colorFilter = ColorFilter.tint(color = Color.Gray)){
                navigator.push(AuthenticationScreen(2))
            }
            IconButtonComponent(modifier = buttonStyle, buttonText = "Continue with Email", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFFBFBFB)), fontSize = 16, shape = RoundedCornerShape(28.dp), textColor = Color.Gray, style = MaterialTheme.typography.h4, iconRes = "drawable/email_icon.png",  colorFilter = ColorFilter.tint(color = Color.Gray)){
                navigator.replace(AuthenticationScreen(3))
            }
            IconButtonComponent(modifier = buttonStyle, buttonText = "Connect with Google", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFFBFBFB)), fontSize = 16, shape = RoundedCornerShape(28.dp), textColor = Color.Gray, style = MaterialTheme.typography.h4, iconRes = "google_icon.png")

            IconButtonComponent(modifier = buttonStyle, buttonText = "Continue with X", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFFBFBFB)), fontSize = 16, shape = RoundedCornerShape(28.dp), textColor = Color.Gray, style = MaterialTheme.typography.h4, iconRes = "drawable/x_logo.png",  colorFilter = ColorFilter.tint(color = Color.Gray)){
                navigator.push(AuthenticationScreen(1))
            }
            IconButtonComponent(modifier = buttonStyle, buttonText = "Continue with Instagram", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFFBFBFB)), fontSize = 16, shape = RoundedCornerShape(28.dp), textColor = Color.Gray, style = MaterialTheme.typography.h4, iconRes = "instagram_icon.png")
        }
    }



@Composable
fun authenticationTypeChangeText(currentScreen: Int = 0) {
    val rowModifier = Modifier
        .padding(top = 20.dp, start = 10.dp)
        .fillMaxWidth()

    val authText: String = if(currentScreen == 0){
        "Login"
    }
    else{
        "Sign Up"
    }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            val modifier = Modifier.padding(start = 5.dp)
            TextComponent(
                textModifier = modifier,
                text = "Don't have an account?",
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
                text = authText,
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
fun attachAuthenticationTypeChangeView(currentScreen: Int = 0) {
    val navigator = LocalNavigator.currentOrThrow
    val rowModifier = Modifier
        .padding(bottom = 40.dp)
        .background(color = Color(color = 0xFFFBFBFB))
        .fillMaxHeight()
        .fillMaxWidth()
        .clickable {
            navigator.replace(AuthenticationScreen(currentScreen))
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            authenticationTypeChangeText(currentScreen = currentScreen)
        }
    }


@Composable
fun welcomeToSavanna(){
    val rowModifier = Modifier
        .padding(top = 50.dp)
        .fillMaxWidth()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            val modifier = Modifier.padding(start = 5.dp)
            attachWaveIcon()
            TextComponent(
                text = "Welcome to Savanna",
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



