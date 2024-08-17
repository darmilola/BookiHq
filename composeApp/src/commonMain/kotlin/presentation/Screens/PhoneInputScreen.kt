package presentation.Screens

import StackedSnackbarHost
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.AuthSSOScreenNav
import domain.Models.PlatformNavigator
import kotlinx.serialization.Transient
import presentation.Screens.VerifyOTPScreen
import presentation.components.ButtonComponent
import presentation.viewmodels.MainViewModel
import presentation.widgets.AuthenticationBackNav
import presentation.widgets.PhoneInputWidget
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentation.widgets.SubtitleTextWidget
import presentation.widgets.TitleWidget
import rememberStackedSnackbarHostState
import theme.styles.Colors
import utils.ParcelableScreen
import utils.makeValidPhone

@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class PhoneInputScreen(val platformNavigator: PlatformNavigator) : ParcelableScreen, ScreenTransition {
    @Transient
    private var mainViewModel: MainViewModel? = null

    fun setMainViewModel(mainViewModel: MainViewModel) {
        this.mainViewModel = mainViewModel
    }

    override val key: ScreenKey
        get() = "phoneInputScreen"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val  rootModifier =
            Modifier.fillMaxWidth()
                .fillMaxHeight(0.95f)
                .background(color = Color(color = 0xFFFBFBFB))

        val buttonStyle = Modifier
            .padding(top = 30.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .height(50.dp)

        val topLayoutModifier =
            Modifier
                .padding(top = 40.dp, start = 10.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.87f)
                .background(color = Color(color = 0xFFFBFBFB))

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )


        var phone = remember { mutableStateOf("") }
        var countryCode = remember { mutableStateOf("+234") }

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }



        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState)  }
        ) {

        Column(modifier = rootModifier) {
            Column(modifier = topLayoutModifier) {
                AuthenticationBackNav(mainViewModel = mainViewModel!!)
                EnterPhoneNumberTitle()
                AttachSendCodeDescription()
                PhoneInputWidget(onValueChange = {
                    phone.value = it
                }, onSelectionChange = {
                    countryCode.value = if (it == 0) "+234" else "+27"
                })
                ButtonComponent(
                    modifier = buttonStyle,
                    buttonText = "Continue",
                    borderStroke = null,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                    fontSize = 18,
                    shape = RoundedCornerShape(25.dp),
                    textColor = Color(color = 0xFFFFFFFF),
                    style = MaterialTheme.typography.h4
                ) {
                    if (phone.value == "") {
                        ShowSnackBar(title = "Error",
                            description = "Please Input your Phone number",
                            actionLabel = "",
                            duration = StackedSnackbarDuration.Long,
                            snackBarType = SnackBarType.ERROR,
                            stackedSnackBarHostState = stackedSnackBarHostState,
                            onActionClick = {})
                    } else {
                        val validPhone = makeValidPhone(phone.value)
                        var verificationPhone =  countryCode.value+""+validPhone
                        platformNavigator.startPhoneSS0(verificationPhone)
                        val verifyOTPScreen = VerifyOTPScreen(platformNavigator, verificationPhone)
                        verifyOTPScreen.setMainViewModel(mainViewModel = mainViewModel!!)
                        navigator.push(verifyOTPScreen)
                    }
                }


            }
        }


        }
    }


    @Composable
    fun EnterPhoneNumberTitle(){
        val rowModifier = Modifier
            .padding(bottom = 10.dp, top = 30.dp)
            .fillMaxWidth()
            .wrapContentHeight()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            TitleWidget(title = "Enter Your Phone number", textColor = Colors.primaryColor)
        }
    }


    @Composable
    fun AttachSendCodeDescription() {
        val rowModifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            SubtitleTextWidget(text = "We'll send a verification code to your\n" +
                    " phone via text message", textAlign = TextAlign.Center)
        }
    }

    override fun enter(lastEvent: StackEvent): EnterTransition {
        println("Enter")
        return slideIn { size ->
            val x = if (lastEvent == StackEvent.Pop) -size.width else size.width
            IntOffset(x = x, y = 0)
        }
    }

    override fun exit(lastEvent: StackEvent): ExitTransition {
        println("Enter 2")
        return slideOut { size ->
            val x = if (lastEvent == StackEvent.Pop) size.width else -size.width
            IntOffset(x = x, y = 0)
        }
    }


}