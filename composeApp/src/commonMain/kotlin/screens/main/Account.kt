package screens.main


import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ButtonComponent
import components.GradientButton
import components.ImageComponent
import components.TextComponent
import screens.SplashScreenCompose
import screens.authentication.AuthenticationComposeScreen
import screens.authentication.attachWaveIcon

class AccountTab(private val mainViewModel: MainViewModel) : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Account"
            val icon = painterResource("account.png")



            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        mainViewModel.setTitle(options.title.toString())
        val columnModifier = Modifier
            .background(color = Color(0xFBFBFB))
            .padding(top = 5.dp)
            .fillMaxHeight()
            .fillMaxWidth()

        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = columnModifier
            ) {
                    attachUserProfileImage()
                    UserFullNameComp()
                    EditProfileComp(MaterialTheme.typography.button)
            }
        }
    }

    @Composable
    fun attachUserProfileImage() {
        Box(
            Modifier
                .padding(20.dp)
                .border(width = 3.dp, color = Color.DarkGray, shape = RoundedCornerShape(75.dp))
                .size(150.dp)
                .background(color = Color(0xFBFBFB))
        ) {
            val modifier = Modifier
                .padding(6.dp)
                .clip(CircleShape)
                .size(150.dp)
            ImageComponent(imageModifier = modifier, imageRes = "1.jpg")
        }

    }

    @Composable
    fun UserFullNameComp(){
        val rowModifier = Modifier
            .fillMaxWidth()
        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = rowModifier
            ) {
                val modifier = Modifier.padding(start = 5.dp)
                TextComponent(
                    text = "Tessy Ogechi Iwunze",
                    fontSize = 23,
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


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun EditProfileComp(style: TextStyle){

        val buttonStyle = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth(0.45f)
            .height(50.dp)

        ButtonComponent(modifier = buttonStyle, buttonText = "Edit Profile", borderStroke = null, colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray), fontSize = 16, shape = RoundedCornerShape(12.dp), textColor = Color.White, style = style){}

    }
}