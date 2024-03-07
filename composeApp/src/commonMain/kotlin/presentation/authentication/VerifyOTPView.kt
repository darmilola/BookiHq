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
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import presentation.components.ButtonComponent
import presentation.widgets.OTPTextField
import presentation.widgets.SubtitleTextWidget
import presentation.widgets.TitleWidget

@Composable
fun VerifyOTP(viewType: Int = 0, platformNavigator: PlatformNavigator) {
    val  rootModifier =
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.95f)
            .background(color = Color(color = 0xFFFBFBFB))

    val buttonStyle = Modifier
        .padding(top = 20.dp, start = 50.dp, end = 50.dp)
        .fillMaxWidth()
        .height(50.dp)

    val navigator = LocalNavigator.currentOrThrow


        val topLayoutModifier =
            Modifier
                .padding(top = 40.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.87f)
                .background(color = Color(color = 0xFFFBFBFB))


        Column(modifier = rootModifier) {
            Column(modifier = topLayoutModifier) {
                AttachBackIcon(0, platformNavigator)
                EnterVerificationCodeTitle()
                AttachVerificationCodeText(viewType = viewType)

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(start = 10.dp, end = 10.dp, top = 30.dp),
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

               // ResendVerificationCode(viewType = viewType,platformNavigator = platformNavigator)
                ButtonComponent(modifier = buttonStyle, buttonText = "Verify", borderStroke = null, colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor), fontSize = 18, shape = RoundedCornerShape(25.dp), textColor = Color(color = 0xFFFFFFFF), style = MaterialTheme.typography.h4) {
                    navigator.replace(AuthenticationScreen(platformNavigator = platformNavigator))
                }
            }
        }
    }




@Composable
fun AttachVerificationCodeText(viewType: Int = 0) {
    val rowModifier = Modifier
        .padding(top = 30.dp)
        .fillMaxWidth()
    val verifyText: String = if(viewType == 0) "Verification code has been sent to your\n Mobile Number" else "Verification code has been sent to your Email"
    val handleText: String = if(viewType == 0) "(+234)\t08102853533" else "damilolaakinterinwa@gmail.com"
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = rowModifier
    ) {

        SubtitleTextWidget(text = verifyText, textAlign = TextAlign.Center)
        SubtitleTextWidget(text = handleText, textColor = Colors.primaryColor, textAlign = TextAlign.Center)
    }
}

/*
@Composable
fun ResendVerificationCode(viewType: Int = 0, platformNavigator: domain.Models.PlatformNavigator) {
    val rowModifier = Modifier
        .padding(top = 50.dp)
        .fillMaxWidth()

    val navigator = LocalNavigator.currentOrThrow

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = rowModifier
        ) {

            SubtitleTextWidget(text = "Did not receive Verification Code?", textAlign = TextAlign.Center)

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .clickable {
                    if(viewType == 0){
                        navigator.push(AuthenticationScreen(2, platformNavigator = platformNavigator))
                    }
                    else{
                        navigator.push(AuthenticationScreen(3, platformNavigator = platformNavigator))
                    }
                 }
                .wrapContentHeight()){
                SubtitleTextWidget(text = "Resend", textAlign = TextAlign.Center, textColor = Colors.primaryColor)
            }
        }
   }
*/




@Composable
fun EnterVerificationCodeTitle(){
    val rowModifier = Modifier
        .padding(bottom = 10.dp, top = 30.dp)
        .fillMaxWidth()
        .wrapContentHeight()
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = rowModifier
    ) {
        TitleWidget(title = "Enter Verification Code", textColor = Colors.primaryColor)
    }
}
