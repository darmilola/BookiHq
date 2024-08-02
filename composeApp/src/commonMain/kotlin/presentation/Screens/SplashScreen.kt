package presentation.Screens

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
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import di.initKoin
import domain.Enums.AuthType
import presentation.components.SplashScreenBackground
import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.AuthenticationScreenHandler
import presentation.authentication.AuthenticationPresenter
import presentation.viewmodels.MainViewModel
import presentation.widgets.SplashScreenWidget

@Composable
fun SplashScreenCompose(platformNavigator: PlatformNavigator, authenticationPresenter: AuthenticationPresenter, mainViewModel: MainViewModel) {

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
            preferenceSettings["firstname"] = user.firstname
            preferenceSettings["vendorId"] = user.connectedVendor
            preferenceSettings["whatsappPhone"] = whatsAppPhone
            preferenceSettings["country"] = user.country
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
        connectVendorOnProfileCompleted = { _,_ ->}, onUpdateStarted = {}, onUpdateEnded = {})
    handler.init()

    if (navigateToConnectVendor.value){
        val connectVendorScreen = ConnectVendorScreen(platformNavigator)
        connectVendorScreen.setMainViewModel(mainViewModel)
        navigator.replaceAll(connectVendorScreen)
    }
    else if (navigateToPlatform.value){
        val mainScreen = MainScreen(platformNavigator)
        mainScreen.setMainViewModel(mainViewModel)
        navigator.replaceAll(mainScreen)
    }
    else if (navigateToWelcomeScreen.value){
        val welcomeScreen = WelcomeScreen(platformNavigator)
        welcomeScreen.setMainViewModel(mainViewModel)
        navigator.replaceAll(welcomeScreen)
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
                    val welcomeScreen = WelcomeScreen(platformNavigator)
                    welcomeScreen.setMainViewModel(mainViewModel)
                    navigator.replaceAll(welcomeScreen)

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

class SplashScreen(val platformNavigator: PlatformNavigator?, val mainViewModel: MainViewModel) : Screen, KoinComponent {

    private val  authenticationPresenter: AuthenticationPresenter by inject()

    @Composable
    override fun Content() {
        initKoin()
        SplashScreenCompose(platformNavigator = platformNavigator!!, authenticationPresenter,mainViewModel)
    }
}

