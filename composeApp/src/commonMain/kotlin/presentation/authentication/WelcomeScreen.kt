package presentation.authentication

import domain.Models.PlatformNavigator
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.Models.AuthSSOScreenNav
import presentation.components.ButtonComponent
import presentation.widgets.WelcomeScreenPagerContent

@Composable
fun WelcomeScreenCompose(platformNavigator: PlatformNavigator) {
    val bgStyle = Modifier
        .fillMaxWidth()
        .fillMaxHeight()

        Box(contentAlignment = Alignment.TopCenter, modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color.White)
        ) {
            Column(
                modifier = bgStyle,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.90f)) {
                    AttachTopContent()
                }

                Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center) {
                    AttachActionButtons(platformNavigator)
                }

            }

        }

    }

@Composable
 fun AttachTopContent(){
     WelcomeScreenPagerContent("drawable/woman_in_jeans_remove_bg.png")
 }



@Composable
fun AttachActionButtons(platformNavigator: PlatformNavigator){
    val navigator = LocalNavigator.currentOrThrow
    val buttonStyle = Modifier
        .padding(bottom = 25.dp)
        .fillMaxWidth(0.90f)
        .height(50.dp)

        ButtonComponent(modifier = buttonStyle, buttonText = "Continue", borderStroke = BorderStroke(1.dp, Colors.primaryColor), colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor), fontSize = 18, shape = CircleShape, textColor = Color.White, style = TextStyle()) {
            navigator.replace(AuthenticationScreen(currentPosition = AuthSSOScreenNav.AUTH_LOGIN.toPath(), platformNavigator = platformNavigator))
        }
    }


class WelcomeScreen(val platformNavigator: PlatformNavigator) : Screen {
    @Composable
    override fun Content() {
        WelcomeScreenCompose(platformNavigator)
    }
}
