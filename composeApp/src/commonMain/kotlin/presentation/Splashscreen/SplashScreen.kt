package presentation.Splashscreen

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
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.russhwolf.settings.Settings
import di.initKoin
import presentation.components.SplashScreenBackground
import kotlinx.coroutines.delay
import kotlinx.serialization.Transient
import presentation.connectVendor.ConnectVendorScreen
import presentation.authentication.WelcomeScreen
import presentation.main.MainScreen
import presentation.widgets.SplashScreenWidget
import utils.ParcelableScreen

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
                navigator.replaceAll(WelcomeScreen(platformNavigator))
                //navigator.replaceAll(MainScreen(platformNavigator = platformNavigator))
            }, onWelcome = {
                navigator.replaceAll(WelcomeScreen(platformNavigator))
            })

        }
    }

 private fun authenticateSplashScreen(settings: Settings, onAuthenticated :() -> Unit, onWelcome :() -> Unit){
     val userEmail = "devprocess0@gmail.com" // settings.getString("userEmail", "")
     if (userEmail.isNotEmpty()){
         onAuthenticated()
     }
     else{
         onWelcome()
     }

 }

@Parcelize
class SplashScreen(val platformNavigator: PlatformNavigator) : ParcelableScreen {
    @Composable
    override fun Content() {
        initKoin()
        SplashScreenCompose(platformNavigator = platformNavigator)
    }
}

