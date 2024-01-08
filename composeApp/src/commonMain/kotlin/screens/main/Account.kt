package screens.main


import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppRegularTypography
import GGSansRegular
import GGSansSemiBold
import Styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import components.ButtonComponent
import components.TextComponent
import widgets.ActionItemComponent
import widgets.ProfileImageUpdate

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
        val columnModifier = Modifier
            .padding(top = 5.dp, bottom = 100.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = columnModifier
            ) {
                    ProfileImageUpdate()
                    UserPreferredName()
                    EditProfileButton(TextStyle(fontFamily = GGSansSemiBold, fontWeight = FontWeight.Black, fontSize = TextUnit(18f, TextUnitType.Sp)))
                    Divider(color = Color(color = 0x90C8C8C8), thickness = 2.dp, modifier = Modifier.fillMaxWidth(0.90f).padding(top = 30.dp))
                    AttachAccountAction()
            }
        }


    @Composable
    fun UserPreferredName(){
        val rowModifier = Modifier
            .fillMaxWidth()
        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = "Dianne Williamson",
                    fontSize = 25,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }


    @Composable
    fun EditProfileButton(style: TextStyle){
        val buttonStyle = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth(0.40f)
            .background(color = Color.Transparent)
            .height(50.dp)

        ButtonComponent(modifier = buttonStyle, buttonText = "Edit Profile", borderStroke = BorderStroke((1.5).dp, color = Color.DarkGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 20, shape = RoundedCornerShape(25.dp), textColor =  Color.DarkGray, style = style){
            mainViewModel.setId(9)
        }

    }



    @Composable
    fun AttachAccountAction() {
        val columnModifier = Modifier
            .padding(top = 10.dp, start = 5.dp)
            .fillMaxWidth()

        val actionStyle = Modifier
            .padding(bottom = 5.dp)
            .fillMaxWidth()
            .height(65.dp)

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = columnModifier
            ) {

                ActionItemComponent(
                    modifier = actionStyle,
                    buttonText = "Orders",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Colors.darkPrimary,
                    style = TextStyle(),
                    iconRes = "drawable/shopping_basket.png",
                    isDestructiveAction = false, onClick = {
                        mainViewModel.setId(5)
                    })


                ActionItemComponent(
                    modifier = actionStyle,
                    buttonText = "Switch Vendor",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Colors.darkPrimary,
                    style = TextStyle(),
                    iconRes = "drawable/switch.png",
                    isDestructiveAction = false, onClick = {
                        mainViewModel.setId(6)
                    })


                ActionItemComponent(
                    modifier = actionStyle,
                    buttonText = "Invite Friends",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Colors.darkPrimary,
                    style = TextStyle(),
                    iconRes = "drawable/invite.png",
                    isDestructiveAction = false)



                Divider(color = Color(color = 0x90C8C8C8), thickness = 2.dp, modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, bottom = 10.dp, top = 10.dp))


                ActionItemComponent(
                    modifier = actionStyle,
                    buttonText = "FAQs",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Colors.darkPrimary,
                    style = TextStyle(),
                    iconRes = "drawable/faq.png",
                    isDestructiveAction = false)

                ActionItemComponent(
                    modifier = actionStyle,
                    buttonText = "Help & Support",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Colors.darkPrimary,
                    style = TextStyle(),
                    iconRes = "drawable/support.png",
                    isDestructiveAction = false)

                ActionItemComponent(
                    modifier = actionStyle,
                    buttonText = "Terms And Condition",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Colors.darkPrimary,
                    style = TextStyle(),
                    iconRes = "drawable/terms.png",
                    isDestructiveAction = false)

                Divider(color = Color(color = 0x90C8C8C8), thickness = 2.dp, modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, bottom = 10.dp, top = 10.dp))

                ActionItemComponent(
                    modifier = actionStyle,
                    buttonText = "Log Out",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Colors.pinkColor,
                    style = TextStyle(),
                    iconRes = "drawable/sign_out.png",
                    isDestructiveAction = true
                )

            }
        }
    }

