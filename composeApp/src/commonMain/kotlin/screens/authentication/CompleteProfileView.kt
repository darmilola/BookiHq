package screens.authentication

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import components.TextComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import components.ButtonComponent
import components.ImageComponent
import components.TextFieldComponent
import screens.main.MainScreen

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterialApi::class)
@Composable
fun CompleteProfileCompose() {
    val viewModel: AuthenticationViewModel = AuthenticationViewModel()
    val authenticationScreenData = viewModel.authenticationScreenData ?: return

    val  rootModifier =
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.95f)
            .background(color = Color(color = 0xFFFBFBFB))

    val buttonStyle = Modifier
        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
        .fillMaxWidth()
        .height(60.dp)

    val navigator = LocalNavigator.currentOrThrow


    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        val topLayoutModifier =
            Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .background(color = Color(color = 0xFFFBFBFB))


        Column(modifier = rootModifier) {
            Column(modifier = topLayoutModifier) {
                AttachBackIcon(0)
                CompleteProfile()
                ProfileImageUpdate()
                Row(modifier = Modifier.fillMaxWidth().height(80.dp).padding(start = 10.dp, end = 10.dp, top = 20.dp)) {
                   FirstnameInput()
                   LastnameInput()
                }
                EmailInput()
                PasswordInput()
                CountryInput()
                DOBInput()
                //GenderInput()
                ButtonComponent(modifier = buttonStyle, buttonText = "Continue", borderStroke = BorderStroke(1.dp, Color(color = 0xFFF43569)), colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFF43569)), fontSize = 18, shape = RoundedCornerShape(30.dp), textColor = Color(color = 0xFFFFFFFF), style = MaterialTheme.typography.h4) {
                    navigator.replaceAll(MainScreen)
                }
            }
        }
    }
}


@Composable
fun ProfileImageUpdate() {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .padding(top = 20.dp, bottom = 5.dp)
                .size(150.dp)
                .background(color = Color.Transparent)
        ) {
            val modifier = Modifier
                .padding(4.dp)
                .clip(CircleShape)
                .border(
                    width = 4.dp,
                    color = Color(0xffF4F4F4),
                    shape = RoundedCornerShape(75.dp))
                .size(150.dp)
            ImageComponent(imageModifier = modifier, imageRes = "1.jpg")
            EditProfilePictureButton()
        }
    }

}

@Composable
fun FirstnameInput() {
         var text by remember { mutableStateOf(TextFieldValue("")) }
         MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {

            val modifier  = Modifier
                .padding(end = 5.dp)
                .fillMaxWidth(0.50f)
                .height(60.dp)
                .border(width = 1.dp, color = Color.Gray, shape =  RoundedCornerShape(30.dp))


            Box(modifier = modifier,
                contentAlignment = Alignment.CenterStart) {
                TextFieldComponent(
                    text = text,
                    readOnly = false,
                    textStyle = TextStyle(fontSize = TextUnit(20f, TextUnitType.Sp)),
                    modifier = Modifier.fillMaxHeight(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = { it ->
                        text = it
                    }, isSingleLine = true, placeholderTile = "Firstname..."
                )
            }
        }
}


@Composable
fun LastnameInput() {
      var text by remember { mutableStateOf(TextFieldValue("")) }

        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
            val modifier  = Modifier
                .padding(start = 5.dp)
                .fillMaxWidth()
                .height(60.dp)
                .border(width = 1.dp, color = Color.Gray, shape =  RoundedCornerShape(30.dp))


            Box(modifier = modifier,
                contentAlignment = Alignment.CenterStart) {
                TextFieldComponent(
                    text = text,
                    readOnly = false,
                    textStyle = TextStyle(fontSize = TextUnit(20f, TextUnitType.Sp)),
                    modifier = Modifier.fillMaxHeight(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = { it ->
                        text = it
                    }, isSingleLine = true, placeholderTile = "Lastname..."
                )
            }
        }
}

@Composable
fun EmailInput() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {

        val modifier  = Modifier
            .padding(end = 10.dp, start = 10.dp, top = 20.dp, bottom = 10.dp)
            .fillMaxWidth()
            .height(60.dp)
            .border(width = 1.dp, color = Color.Gray, shape =  RoundedCornerShape(30.dp))


        Box(modifier = modifier,
            contentAlignment = Alignment.CenterStart) {
            TextFieldComponent(
                text = text,
                readOnly = false,
                textStyle = TextStyle(fontSize = TextUnit(20f, TextUnitType.Sp)),
                modifier = Modifier.fillMaxHeight(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = { it ->
                    text = it
                }, isSingleLine = true, placeholderTile = "Email Address"
            )
        }
    }
}

@Composable
fun PasswordInput() {

    var text by remember { mutableStateOf(TextFieldValue("")) }
    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {

        val modifier  = Modifier
            .padding(end = 10.dp, start = 10.dp, top = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .height(60.dp)
            .border(width = 1.dp, color = Color.Gray, shape =  RoundedCornerShape(30.dp))


        Box(modifier = modifier,
            contentAlignment = Alignment.CenterStart) {
            TextFieldComponent(
                text = text,
                readOnly = false,
                textStyle = TextStyle(fontSize = TextUnit(20f, TextUnitType.Sp)),
                modifier = Modifier.fillMaxHeight(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = { it ->
                    text = it
                }, isSingleLine = true, placeholderTile = "Password"
            )
        }
    }

}


@Composable
fun CountryInput() {

    var text by remember { mutableStateOf(TextFieldValue("")) }
    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {

        val modifier  = Modifier
            .padding(end = 10.dp, start = 10.dp, top = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .height(60.dp)
            .border(width = 1.dp, color = Color.Gray, shape =  RoundedCornerShape(30.dp))


        Box(modifier = modifier,
            contentAlignment = Alignment.CenterStart) {
            TextFieldComponent(
                text = text,
                readOnly = false,
                textStyle = TextStyle(fontSize = TextUnit(20f, TextUnitType.Sp)),
                modifier = Modifier.fillMaxHeight(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = { it ->
                    text = it
                }, isSingleLine = true, placeholderTile = "Country"
            )
        }
    }
}

@Composable
fun DOBInput() {

    var text by remember { mutableStateOf(TextFieldValue("")) }
    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {

        val modifier  = Modifier
            .padding(end = 10.dp, start = 10.dp, top = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .height(60.dp)
            .border(width = 1.dp, color = Color.Gray, shape =  RoundedCornerShape(30.dp))


        Box(modifier = modifier,
            contentAlignment = Alignment.CenterStart) {
            TextFieldComponent(
                text = text,
                readOnly = false,
                textStyle = TextStyle(fontSize = TextUnit(20f, TextUnitType.Sp)),
                modifier = Modifier.fillMaxHeight(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = { it ->
                    text = it
                }, isSingleLine = true, placeholderTile = "Date of Birth"
            )
        }
    }
}

@Composable
fun GenderInput() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        val modifier  = Modifier
            .padding(end = 10.dp, start = 10.dp, top = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .height(60.dp)
            .border(width = 1.dp, color = Color.Gray, shape =  RoundedCornerShape(30.dp))

        Box(modifier = modifier,
            contentAlignment = Alignment.CenterStart) {
            TextFieldComponent(
                text = text,
                readOnly = false,
                textStyle = TextStyle(fontSize = TextUnit(20f, TextUnitType.Sp)),
                modifier = Modifier.fillMaxHeight(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = { it ->
                    text = it
                }, isSingleLine = true, placeholderTile = "Gender"
            )
        }
    }
}

@Composable
fun EditProfilePictureButton() {
    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.BottomEnd) {
        val modifier = Modifier
            .padding(end = 10.dp)
            .background(color = Color.Blue, shape = CircleShape)
            .border(
                width = 2.dp,
                color = Color(0xffF4F4F4),
                shape = CircleShape
            )
            .size(width = 50.dp, height = 50.dp)

        Box(modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            ImageComponent(imageModifier = Modifier.size(25.dp).clickable {
            }, imageRes = "drawable/edit_icon.png", colorFilter = ColorFilter.tint(color = Color(0xffF4F4F4)))
        }
    }
}

@Composable
fun AttachBackIcon(goToScreen: Int = 0) {
    val navigator = LocalNavigator.currentOrThrow
    val modifier = Modifier
        .padding(30.dp)
        .clickable {
            navigator.popUntilRoot()
        }
        .size(22.dp)
    ImageComponent(imageModifier = modifier, imageRes = "back_arrow.png", colorFilter = ColorFilter.tint(color = Color.DarkGray))
}





@Composable
fun CompleteProfile(){
    val rowModifier = Modifier
        .padding(start = 10.dp, bottom = 10.dp)
        .fillMaxWidth()
        .wrapContentHeight()
    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            TextComponent(
                text = "Complete Your Profile",
                fontSize = 25,
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