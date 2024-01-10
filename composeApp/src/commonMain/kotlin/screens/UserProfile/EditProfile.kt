package screens.UserProfile

import Styles.Colors
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import components.ButtonComponent
import components.ToggleButton
import screens.Bookings.BookingScreen
import screens.Products.CartScreen
import screens.main.MainTab
import screens.main.MainViewModel
import widgets.DropDownWidget
import widgets.InputWidget
import widgets.PageBackNavWidget
import widgets.ProfileImageUpdate
import widgets.TitleWidget

class EditProfile(private val mainViewModel: MainViewModel) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Edit Profile"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title
                )
            }
        }

    @Composable
    override fun Content() {
        EditProfileCompose(mainViewModel)
    }
}

@Composable
fun EditProfileCompose(mainViewModel: MainViewModel) {
    val rootModifier =
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White)

    val buttonStyle = Modifier
        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
        .fillMaxWidth()
        .height(50.dp)


    val topLayoutModifier =
        Modifier
            .padding(top = 50.dp, start = 5.dp, end = 5.dp, bottom = 20.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .fillMaxHeight()
            .background(color = Color.White)


    Column(modifier = rootModifier) {
        Column(modifier = topLayoutModifier) {
            AttachBackIcon(mainViewModel)
            PageTitle()
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

            val navigator = LocalTabNavigator.current
            ButtonComponent(modifier = buttonStyle, buttonText = "Save", colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor), fontSize = 18, shape = CircleShape, textColor = Color(color = 0xFFFFFFFF), style = TextStyle(), borderStroke = null) {
                navigator.current = MainTab(mainViewModel = mainViewModel)
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
fun AttachBackIcon(mainViewModel: MainViewModel) {
    val navigator = LocalTabNavigator.current
        PageBackNavWidget {
            when (mainViewModel.fromId.value) {
                1 -> {
                    navigator.current = BookingScreen(mainViewModel)
                }
                3 -> {
                    navigator.current = CartScreen(mainViewModel)
                }
                else -> {
                    navigator.current = MainTab(mainViewModel)
                }
            }
        }
    }



@Composable
fun PageTitle(){
    val rowModifier = Modifier
        .padding(start = 10.dp, bottom = 10.dp)
        .fillMaxWidth()
        .wrapContentHeight()
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = rowModifier
    ) {
        TitleWidget(title = "Edit Profile", textColor = Colors.primaryColor)
    }
}

