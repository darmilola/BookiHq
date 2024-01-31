package presentation.authentication

import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import components.ButtonComponent
import components.ToggleButton
import presentation.UserProfile.SwitchVendor.ConnectPage
import presentation.widgets.DropDownWidget
import presentation.widgets.InputWidget
import presentation.widgets.ProfileImageUpdate
import presentation.widgets.SubtitleTextWidget
import presentation.widgets.TitleWidget

@Composable
fun CompleteProfile() {
    val  rootModifier =
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.95f)
            .background(color = Color.White)

    val buttonStyle = Modifier
        .padding(start = 10.dp, end = 10.dp, top = 30.dp)
        .fillMaxWidth()
        .height(50.dp)

    val navigator = LocalNavigator.currentOrThrow

    val topLayoutModifier =
            Modifier
                .padding(top = 40.dp, start = 5.dp, end = 5.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .fillMaxHeight()
                .background(color = Color.White)


    Column(modifier = rootModifier) {
        Column(modifier = topLayoutModifier) {
            AttachBackIcon()
            PageTitle()
            SubtitleTextWidget(text = "Lorem ipsum is placeholder text commonly used in Printing")
            ProfileImageUpdate()
            Row(modifier = Modifier.fillMaxWidth()) {
               Box(modifier = Modifier.fillMaxWidth(0.50f), contentAlignment = Alignment.Center){
                   InputWidget(iconRes = "drawable/card_icon.png", placeholderText = "Firstname", iconSize = 40)
               }
              Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    InputWidget(iconRes = "drawable/card_icon.png", placeholderText = "Lastname", iconSize = 40)
              }
            }
            InputWidget(iconRes = "drawable/address.png", placeholderText = "Address", iconSize = 28, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), isPasswordField = false)
            InputWidget(iconRes = "drawable/phone_icon.png", placeholderText = "Contact Phone", iconSize = 28, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), isPasswordField = false)
            AttachCountryDropDownWidget()

            ToggleButton(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(15.dp), style = TextStyle(), onLeftClicked = {

            }, onRightClicked = {

            }, leftText = "Male", rightText = "Female")

            ButtonComponent(modifier = buttonStyle, buttonText = "Continue", colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor), fontSize = 18, shape = CircleShape, textColor = Color(color = 0xFFFFFFFF), style = TextStyle(), borderStroke = null) {
                navigator.push(ConnectPage)
            }
        }
    }

}




@Composable
fun AttachCountryDropDownWidget(){
    val countryList = listOf("South Africa", "Nigeria")
    DropDownWidget(menuItems = countryList, placeHolderText = "Country of Residence",)
}




@Composable
fun PageTitle(){
    val rowModifier = Modifier
        .padding(start = 10.dp, bottom = 10.dp, top = 15.dp)
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
