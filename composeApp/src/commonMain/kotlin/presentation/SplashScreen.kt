package presentation

import domain.Models.PlatformNavigator
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import di.initKoin
import presentation.components.SplashScreenBackground
import kotlinx.coroutines.delay
import presentation.profile.connect_vendor.ConnectPage
import presentation.authentication.WelcomeScreen
import presentation.main.MainScreen
import presentation.widgets.SplashScreenWidget

@Composable
fun SplashScreenCompose(platformNavigator: PlatformNavigator) {

    val preferenceSettings: Settings = Settings()


   val  modifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White)
    val navigator = LocalNavigator.currentOrThrow
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
            //platformNavigator.startVideoCall("MyAuthToken")
            authenticateSplashScreen(preferenceSettings, onAuthenticated = {
                navigator.replaceAll(MainScreen(platformNavigator = platformNavigator))
            }, onConnectVendor = {
                navigator.replaceAll(ConnectPage(platformNavigator))
            }, onWelcome = {
                navigator.replaceAll(WelcomeScreen(platformNavigator))
            })

        }
    }

 private fun authenticateSplashScreen(settings: Settings, onAuthenticated :() -> Unit,
                                      onConnectVendor :() -> Unit, onWelcome :() -> Unit){
     val userEmail = "devprocess0@gmail.com"//settings.getString("userEmail", "")
     val isVendorConnected = true//settings.getBoolean("isVendorConnected", false)

     if (userEmail.isNotEmpty() && isVendorConnected){
         onAuthenticated()
     }
     else if(userEmail.isNotEmpty() && !isVendorConnected){
         onConnectVendor()
     }
     else{
         onWelcome()
     }

 }

class SplashScreen(val platformNavigator: PlatformNavigator) : Screen {
    @Composable
    override fun Content() {
        initKoin()
        SplashScreenCompose(platformNavigator = platformNavigator)
    }
}

