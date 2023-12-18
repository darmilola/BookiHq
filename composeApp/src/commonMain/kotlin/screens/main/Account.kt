package screens.main


import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppRegularTypography
import AppTheme.AppSemiBoldTypography
import GGSansBold
import GGSansRegular
import GGSansSemiBold
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ButtonComponent
import components.GradientButton
import components.IconButtonComponent
import components.ImageComponent
import components.TextComponent
import screens.SplashScreenCompose
import screens.authentication.AuthenticationComposeScreen
import screens.authentication.attachAuthenticationButton
import screens.authentication.attachWaveIcon
import widgets.ActionItemComponent

class AccountTab(private val mainViewModel: MainViewModel) : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Profile"
            val icon = painterResource("profile_icon.png")



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
            .padding(top = 5.dp, bottom = 100.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()

        MaterialTheme(colors = AppColors(), typography = AppRegularTypography()) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = columnModifier
            ) {
                    attachUserProfileImage()
                    UserFullNameComp()
                    EditProfileComp(TextStyle(fontFamily = GGSansSemiBold, fontWeight = FontWeight.Black, fontSize = TextUnit(18f, TextUnitType.Sp)))
                    Divider(color = Color(color = 0x90C8C8C8), thickness = 2.dp, modifier = Modifier.fillMaxWidth(0.90f).padding(top = 30.dp))
                    AttachAccountAction()
            }
        }
    }

    @Composable
    fun attachUserProfileImage() {
        Box(
            Modifier
                .padding(top = 20.dp, bottom = 5.dp)
                .border(width = 2.dp, color = Color(color = 0xfffa2d65), shape = RoundedCornerShape(75.dp))
                .size(150.dp)
                .background(color = Color(0xFBFBFB))
        ) {
            val modifier = Modifier
                .padding(4.dp)
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
                    text = "Dianne Williamson",
                    fontSize = 25,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    letterSpacing = TextUnit(1f, TextUnitType.Sp)
                )
            }
        }
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun EditProfileComp(style: TextStyle){

        val buttonStyle = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth(0.40f)
            .background(color = Color.Transparent)
            .height(50.dp)

        ButtonComponent(modifier = buttonStyle, buttonText = "Edit Profile", borderStroke = BorderStroke((1.5).dp, color = Color.DarkGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 19, shape = RoundedCornerShape(25.dp), textColor =  Color.DarkGray, style = style){}

    }



    @Composable
    fun AttachAccountAction() {
        val columnModifier = Modifier
            .padding(top = 20.dp, start = 5.dp)
            .fillMaxWidth()

        val buttonStyle = Modifier
            .padding(bottom = 5.dp)
            .fillMaxWidth()
            .height(60.dp)

        MaterialTheme(colors = AppColors(), typography = AppRegularTypography()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = columnModifier
            ) {

                ActionItemComponent(
                    modifier = buttonStyle,
                    buttonText = "Orders",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Color(color = 0xFF3d3d4e),
                    style = MaterialTheme.typography.button,
                    iconRes = "drawable/order_icon.png",
                    isDestructiveAction = false)


                ActionItemComponent(
                    modifier = buttonStyle,
                    buttonText = "Switch Business",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Color(color = 0xFF3d3d4e),
                    style = MaterialTheme.typography.button,
                    iconRes = "drawable/switch_icon.png",
                    isDestructiveAction = false)


                ActionItemComponent(
                    modifier = buttonStyle,
                    buttonText = "Invite Friends",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Color(color = 0xFF3d3d4e),
                    style = MaterialTheme.typography.button,
                    iconRes = "drawable/share_icon.png",
                    isDestructiveAction = false)



                Divider(color = Color(color = 0x90C8C8C8), thickness = 2.dp, modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, bottom = 10.dp, top = 10.dp))

                ActionItemComponent(
                    modifier = buttonStyle,
                    buttonText = "Help & Support",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Color(color = 0xFF3d3d4e),
                    style = MaterialTheme.typography.button,
                    iconRes = "drawable/help_icon.png",
                    isDestructiveAction = false)

                ActionItemComponent(
                    modifier = buttonStyle,
                    buttonText = "Terms And Condition",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Color(color = 0xFF3d3d4e),
                    style = MaterialTheme.typography.button,
                    iconRes = "drawable/terms_icon.png",
                    isDestructiveAction = false)


                Divider(color = Color(color = 0x90C8C8C8), thickness = 2.dp, modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, bottom = 10.dp, top = 10.dp))


                ActionItemComponent(
                    modifier = buttonStyle,
                    buttonText = "Log Out",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Color(color = 0xfffa2d65),
                    style = MaterialTheme.typography.button,
                    iconRes = "drawable/power_icon.png",
                    isDestructiveAction = true
                )

            }
        }
    }

}