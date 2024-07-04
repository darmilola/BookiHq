package presentation.authentication

import StackedSnackbarHost
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.AuthSSOScreenNav
import domain.Models.PlatformNavigator
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.AuthenticationScreenHandler
import presentation.components.ButtonComponent
import presentation.dialogs.LoadingDialog
import presentation.widgets.AuthenticationBackNav
import presentation.widgets.OTPTextField
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentation.widgets.SubtitleTextWidget
import presentation.widgets.TitleWidget
import rememberStackedSnackbarHostState
import theme.styles.Colors
import utils.ParcelableScreen


@Parcelize
class VerifyOTPScreen(val platformNavigator: PlatformNavigator, val verificationPhone: String) : ParcelableScreen, KoinComponent {

    @Transient private val authenticationPresenter : AuthenticationPresenter by inject()
    @Composable
    override fun Content() {

        val verificationInProgress = remember { mutableStateOf(false) }

        val handler = AuthenticationScreenHandler(authenticationPresenter,
            onUserLocationReady = {

            },
            enterPlatform = { userEmail, userPhone ->

            },
            completeProfile = { userEmail, userPhone ->

            },
            connectVendor = { userEmail, userPhone ->

            },
            onVerificationStarted = {
                verificationInProgress.value = true
            },
            onVerificationEnded = {
                verificationInProgress.value = false
            })
        handler.init()

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        var otpValue by remember {
            mutableStateOf("")
        }

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

        if (verificationInProgress.value) {
            Box(modifier = Modifier.fillMaxWidth(0.90f)) {
                LoadingDialog("Verifying Profile...")
            }
        }


        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState)  }
        ) {
            Column(modifier = rootModifier) {
                Column(modifier = topLayoutModifier) {
                    AuthenticationBackNav(AuthSSOScreenNav.PHONE_SCREEN.toPath(), platformNavigator)
                    EnterVerificationCodeTitle()
                    AttachVerificationCodeText(verificationPhone)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(start = 10.dp, end = 10.dp, top = 30.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center

                    ) {

                        OTPTextField(
                            otpText = otpValue,
                            otpCount = 6,
                            onOTPTextChanged = { value, OTPInputField ->
                                otpValue = value
                            }
                        )

                    }
                    ButtonComponent(
                        modifier = buttonStyle,
                        buttonText = "Verify",
                        borderStroke = null,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                        fontSize = 18,
                        shape = RoundedCornerShape(25.dp),
                        textColor = Color(color = 0xFFFFFFFF),
                        style = MaterialTheme.typography.h4
                    ) {
                        if (otpValue == "" || otpValue.length < 6) {
                            ShowSnackBar(title = "Error",
                                description = "Please Input OTP",
                                actionLabel = "",
                                duration = StackedSnackbarDuration.Long,
                                snackBarType = SnackBarType.ERROR,
                                stackedSnackBarHostState = stackedSnackBarHostState,
                                onActionClick = {})
                        } else {
                            verificationInProgress.value = true
                            platformNavigator.verifyOTP(otpValue, onVerificationSuccessful = {
                               authenticationPresenter.validatePhone(it)
                            }, onVerificationFailed = {
                                ShowSnackBar(title = "Error",
                                    description = "Error Occurred Please Try Again",
                                    actionLabel = "",
                                    duration = StackedSnackbarDuration.Long,
                                    snackBarType = SnackBarType.ERROR,
                                    stackedSnackBarHostState = stackedSnackBarHostState,
                                    onActionClick = {})
                            })
                        }
                    }
                }
            }
        }

    }


    @Composable
    fun AttachVerificationCodeText(verificationPhone: String) {
        val rowModifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth()
        val verifyText = "Verification code has been sent to your\n Mobile Number"
        val handleText: String = verificationPhone
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = rowModifier
        ) {

            SubtitleTextWidget(text = verifyText, textAlign = TextAlign.Center)
            SubtitleTextWidget(text = handleText, textColor = Colors.primaryColor, textAlign = TextAlign.Center)
        }
    }


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

}