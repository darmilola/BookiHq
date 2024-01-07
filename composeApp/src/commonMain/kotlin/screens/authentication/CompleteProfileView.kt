package screens.authentication

import GGSansSemiBold
import Styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import components.ButtonComponent
import components.ImageComponent
import components.TextFieldComponent
import components.ToggleButton
import screens.main.MainScreen
import widgets.DropDownWidget
import widgets.InputWidget
import widgets.PageBackNavWidget
import widgets.ProfileImageUpdate
import widgets.SubtitleTextWidget
import widgets.TitleWidget

@Composable
fun CompleteProfileCompose() {
    val  rootModifier =
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.95f)
            .background(color = Color.White)

    val buttonStyle = Modifier
        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
        .fillMaxWidth()
        .height(60.dp)

    val navigator = LocalNavigator.currentOrThrow



        val topLayoutModifier =
            Modifier
                .padding(top = 40.dp, start = 5.dp, end = 5.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .background(color = Color.White)


        Column(modifier = rootModifier) {
            Column(modifier = topLayoutModifier) {
                AttachBackIcon()
                PageTitle()
                SubtitleTextWidget(text = "Lorem ipsum is placeholder text commonly used in Printing")
                ProfileImageUpdate()
                InputWidget(iconRes = "drawable/card_icon.png", placeholderText = "Preferred Name", iconSize = 40)
                InputWidget(iconRes = "drawable/email_icon.png", placeholderText = "Email", iconSize = 24)
                //InputWidget(iconRes = "drawable/security_icon.png", placeholderText = "Password", iconSize = 24, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), isPasswordField = true)
                AttachDropDownWidget()
                ToggleButton(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(15.dp), style = MaterialTheme.typography.h4, onLeftClicked = {

                }, onRightClicked = {

                }, leftText = "Male", rightText = "Female")


                ButtonComponent(modifier = buttonStyle, buttonText = "Continue", colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor), fontSize = 18, shape = RoundedCornerShape(30.dp), textColor = Color(color = 0xFFFFFFFF), style = MaterialTheme.typography.h4, borderStroke = null) {
                    navigator.replaceAll(MainScreen)
                }
            }
        }
    }




@Composable
fun AttachDropDownWidget(){
    val countryList = listOf("South Africa", "Nigeria")
    DropDownWidget(menuItems = countryList, placeHolderText = "Country of Residence",)
}


@Composable
fun AttachBackIcon() {
    val navigator = LocalNavigator.currentOrThrow
    PageBackNavWidget(){
        navigator.popUntilRoot()
    }
}





@Composable
fun PageTitle(){
    val rowModifier = Modifier
        .padding(start = 10.dp, bottom = 10.dp, top = 30.dp)
        .fillMaxWidth()
        .wrapContentHeight()
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            TitleWidget(title = "Complete Your Profile", textColor = Colors.primaryColor)
        }
}
