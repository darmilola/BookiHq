package screens.authentication

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansBold
import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ImageComponent
import components.TextComponent
import components.TextFieldComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import components.ButtonComponent
import screens.main.MainScreen
import widgets.OtpTextField

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterialApi::class)
@Composable
fun VerifyOTPCompose() {
    val viewModel: AuthenticationViewModel = AuthenticationViewModel()
    val authenticationScreenData = viewModel.authenticationScreenData ?: return


    val  rootModifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight(0.95f)
            .background(color = Color(color = 0xFFFBFBFB))

    val buttonStyle = Modifier
        .padding(top = 20.dp, start = 50.dp, end = 50.dp)
        .fillMaxWidth()
        .height(50.dp)

    val navigator = LocalNavigator.currentOrThrow


    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        val topLayoutModifier =
            Modifier.fillMaxWidth()
                .fillMaxHeight(0.87f)
                .background(color = Color(color = 0xFFFBFBFB))


        Column(modifier = rootModifier) {
            Column(modifier = topLayoutModifier) {
                attachBackIcon()
                enterVerificationCode()
                attachVerificationCodeText()

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
                    .padding(start = 30.dp, end = 30.dp, top = 30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ) {

                    var otpValue by remember {
                        mutableStateOf("")
                    }

                    OtpTextField(
                        otpText = otpValue,
                        onOtpTextChange = { value, otpInputFilled ->
                            otpValue = value
                        }
                    )

                }

                resendVerificationCode()

                ButtonComponent(modifier = buttonStyle, buttonText = "Verify", borderStroke = BorderStroke(1.dp, Color(color = 0xFFF43569)), colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFF43569)), fontSize = 18, shape = RoundedCornerShape(25.dp), textColor = Color(color = 0xFFFFFFFF), style = MaterialTheme.typography.h4 ){
                    navigator.push(MainScreen)
                }


            }


        }
    }
}




@Composable
fun attachVerificationCodeText() {
    val rowModifier = Modifier
        .padding(top = 30.dp, start = 30.dp)
        .fillMaxWidth()

    val textStyle: TextStyle = TextStyle(
        fontSize = TextUnit(15f, TextUnitType.Sp),
        fontFamily = GGSansRegular,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Normal
    )

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            val modifier = Modifier.padding(start = 5.dp)
            TextComponent(
                text = "Verification code has been sent to your Phone\n(+234) 08102853533",
                fontSize = 16,
                fontFamily = GGSansRegular,
                textStyle = textStyle,
                textColor = Color.Gray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 25
            )
        }
    }
}


@Composable
fun resendVerificationCode() {
    val rowModifier = Modifier
        .padding(top = 50.dp)
        .fillMaxWidth()

    val navigator = LocalNavigator.currentOrThrow


    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            val modifier = Modifier.padding(start = 5.dp)
                .clickable {
                    navigator.push(AuthenticationScreen(2))
                }
            TextComponent(
                text = "Did not receive Verification Code?",
                fontSize = 14,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 30
            )
            TextComponent(
                textModifier = modifier,
                text = "Resend",
                fontSize = 15,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color(color = 0xFFFA2D65),
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
                lineHeight = 30
            )
        }
    }
}



@Composable
fun enterVerificationCode(){
    val rowModifier = Modifier
        .padding(top = 50.dp, start = 30.dp)
        .fillMaxWidth()
    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            val modifier = Modifier.padding(start = 5.dp)
            TextComponent(
                text = "Enter Verification Code",
                fontSize = 20,
                fontFamily = GGSansBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 30
            )
        }
    }
}


object VerifyOTPScreen : Screen {
    @Composable
    override fun Content() {
        ContinueWithPhoneCompose()
    }
}
