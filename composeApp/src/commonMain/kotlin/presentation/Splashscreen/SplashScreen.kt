package presentation.Splashscreen

import domain.Models.PlatformNavigator
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import di.initKoin
import domain.Enums.AuthType
import presentation.components.SplashScreenBackground
import kotlinx.coroutines.delay
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.AuthenticationScreenHandler
import presentation.authentication.AuthenticationPresenter
import presentation.authentication.CompleteProfileScreen
import presentation.connectVendor.ConnectVendorScreen
import presentation.authentication.WelcomeScreen
import presentation.main.MainScreen
import presentation.widgets.SplashScreenWidget
import utils.ParcelableScreen

@Composable
fun SplashScreenCompose(platformNavigator: PlatformNavigator, authenticationPresenter: AuthenticationPresenter) {

    val preferenceSettings: Settings = Settings()
    val navigateToWelcomeScreen = remember { mutableStateOf(false) }
    val navigateToConnectVendor = remember { mutableStateOf(false) }
    val navigateToPlatform = remember { mutableStateOf(false) }
    val authType = remember { mutableStateOf(AuthType.EMAIL.toPath()) }
    val navigator = LocalNavigator.currentOrThrow


    val handler = AuthenticationScreenHandler(authenticationPresenter,
        onUserLocationReady = {},
        enterPlatform = { user, whatsAppPhone ->
            preferenceSettings["country"] = user.country
            preferenceSettings["profileId"] = user.userId
            preferenceSettings["vendorId"] = user.connectedVendor
            preferenceSettings["whatsappPhone"] = whatsAppPhone
                navigateToPlatform.value = true
        },
        completeProfile = { _,_ ->
               navigateToWelcomeScreen.value = true
        },
        connectVendor = { user ->
            preferenceSettings["country"] = user.country
            preferenceSettings["profileId"] = user.userId
            navigateToConnectVendor.value = true
        },
        onVerificationStarted = {},
        onVerificationEnded = {}, onCompleteStarted = {}, onCompleteEnded = {},
        connectVendorOnProfileCompleted = { _,_ ->})
    handler.init()

    if (navigateToConnectVendor.value){
        navigator.replaceAll(ConnectVendorScreen(platformNavigator))
    }
    else if (navigateToPlatform.value){
        navigator.replaceAll(MainScreen(platformNavigator))
    }
    else if (navigateToWelcomeScreen.value){
        navigator.replaceAll(WelcomeScreen(platformNavigator))
    }


   val  modifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White)
        // AnimationEffect
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            SplashScreenBackground()
            SplashScreenWidget(textStyle = TextStyle())
            Box(modifier = Modifier
                .padding(bottom = 70.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter) {
            }
        }
        LaunchedEffect(key1 = true) {
            delay(3000L)
            authenticateSplashScreen(preferenceSettings,
             onPhoneAuthentication = {
                 authType.value = AuthType.PHONE.toPath()
                  authenticationPresenter.validatePhone(it, requireValidation = false)
             },
             onEmailAuthentication = {
                   authType.value = AuthType.EMAIL.toPath()
                   authenticationPresenter.validateEmail(it)
             }, onWelcome = {
                 navigator.replaceAll(WelcomeScreen(platformNavigator))
                  })
        }
    }

 private fun authenticateSplashScreen(settings: Settings, onPhoneAuthentication: (String) -> Unit, onEmailAuthentication :(String) -> Unit, onWelcome :() -> Unit){
     val authEmail = settings.getString("authEmail", "")
     val authPhone = settings.getString("authPhone", "")

     if (authEmail.isNotEmpty()){
         onEmailAuthentication(authEmail)
     }
     else if (authPhone.isNotEmpty()){
         onPhoneAuthentication(authPhone)
     }
     else{
         onWelcome()
     }
 }

@Parcelize
class SplashScreen(val platformNavigator: PlatformNavigator) : ParcelableScreen, KoinComponent {

    @Transient private val  authenticationPresenter: AuthenticationPresenter by inject()
    @Composable
    override fun Content() {
        initKoin()
        SplashScreenCompose(platformNavigator = platformNavigator, authenticationPresenter)
    }
}

