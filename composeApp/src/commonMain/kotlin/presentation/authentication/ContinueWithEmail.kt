package presentation.authentication

import domain.Models.PlatformNavigator
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentation.components.ButtonComponent
import presentation.widgets.InputWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.SubtitleTextWidget
import presentation.widgets.TitleWidget


@Composable
fun ContinueWithEmail(platformNavigator: PlatformNavigator) {

    val navigator = LocalNavigator.currentOrThrow
    val  rootModifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight(0.95f)
            .background(color = Color(color = 0xFFFBFBFB))

    val buttonStyle = Modifier
        .padding(top = 50.dp, start = 50.dp, end = 50.dp)
        .fillMaxWidth()
        .height(50.dp)

    val topLayoutModifier =
        Modifier
            .padding(top = 40.dp, start = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.87f)
            .background(color = Color(color = 0xFFFBFBFB))


    Column(modifier = rootModifier) {
        Column(modifier = topLayoutModifier) {
            AttachEmailBackIcon(platformNavigator = platformNavigator)
            EnterEmailTitle()
            AttachSendCodeToEmailDescription()
            InputWidget(iconRes = "drawable/email_icon.png", placeholderText = "Email", iconSize = 24, isFocusedByDefault = true)
            ButtonComponent(modifier = buttonStyle, buttonText = "Continue", borderStroke = null, colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor), fontSize = 18, shape = RoundedCornerShape(25.dp), textColor = Color(color = 0xFFFFFFFF), style = TextStyle() ){
               // navigator.replace(AuthenticationScreen(currentScreen = 4, viewType = 1, platformNavigator = platformNavigator))
            }


        }


    }
}

@Composable
fun AttachEmailBackIcon(goToScreen: Int = 0, platformNavigator: PlatformNavigator) {
    val navigator = LocalNavigator.currentOrThrow
    PageBackNavWidget(){
        if(goToScreen == -1){
            navigator.replaceAll(WelcomeScreen(platformNavigator))
        }

        else {
           // navigator.replace(AuthenticationScreen(goToScreen, platformNavigator = platformNavigator))
        }
    }
}


@Composable
fun EnterEmailTitle(){
    val rowModifier = Modifier
        .padding(bottom = 10.dp, top = 30.dp)
        .fillMaxWidth()
        .wrapContentHeight()
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = rowModifier
    ) {
        TitleWidget(title = "Enter Your Email", textColor = Colors.primaryColor)
    }
}


@Composable
fun AttachSendCodeToEmailDescription() {
    val rowModifier = Modifier
        .padding(top = 30.dp)
        .fillMaxWidth()
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = rowModifier
    ) {
        SubtitleTextWidget(text = "We'll send a verification code to your\n" +
                "email kindly check your Inbox", textAlign = TextAlign.Center)
    }
}
