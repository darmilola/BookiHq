package presentation.authentication

import StackedSnackbarHost
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import domain.Models.PlatformNavigator
import domain.Enums.getCityList
import presentation.components.ButtonComponent
import presentation.components.ToggleButton
import presentation.widgets.DropDownWidget
import presentation.widgets.AccountProfileImage
import presentation.widgets.SnackBarType
import presentation.widgets.SubtitleTextWidget
import presentation.widgets.TitleWidget
import presentation.widgets.ShowSnackBar
import presentations.widgets.InputWidget
import rememberStackedSnackbarHostState
import utils.InputValidator

@Composable
fun CompleteProfile(authenticationPresenter: AuthenticationPresenter,userEmail: String, platformNavigator: PlatformNavigator) {

    val placeHolderImage = "drawable/user_icon.png"
    val firstname = remember { mutableStateOf("") }
    val lastname = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val contactPhone = remember { mutableStateOf("") }
    val country = remember { mutableStateOf("Ghana") }
    val countryId = remember { mutableStateOf(-1) }
    val city = remember { mutableStateOf(-1) }
    val gender = remember { mutableStateOf("male") }
    val profileImageUrl = remember { mutableStateOf(placeHolderImage) }
    val imagePickerScope = rememberCoroutineScope()
    val preferenceSettings = Settings()
    val inputList =  ArrayList<String>()
    val stackedSnackBarHostState = rememberStackedSnackbarHostState(
        maxStack = 5,
        animation = StackedSnackbarAnimation.Bounce
    )

    preferenceSettings as ObservableSettings

    preferenceSettings.addStringListener("imageUrl","") {
            value: String -> profileImageUrl.value = value
    }

    inputList.add(firstname.value)
    inputList.add(lastname.value)
    inputList.add(address.value)
    inputList.add(contactPhone.value)
    inputList.add(gender.value)
    inputList.add(userEmail)



    val rootModifier =
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.95f)
            .background(color = Color.White)

    val buttonStyle = Modifier
        .padding(start = 10.dp, end = 10.dp, top = 30.dp)
        .fillMaxWidth()
        .height(50.dp)

    val topLayoutModifier =
            Modifier
                .padding(top = 40.dp, start = 5.dp, end = 5.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .fillMaxHeight()
                .background(color = Color.White)

    val imagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = imagePickerScope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                platformNavigator.startImageUpload(it)
            }
        }
    )

    Scaffold(
        snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState)  }
    ) {




        Column(modifier = rootModifier) {
            Column(modifier = topLayoutModifier) {
                PageTitle()
                SubtitleTextWidget(text = "Lorem ipsum is placeholder text commonly used in Printing")
                AccountProfileImage(
                    profileImageUrl = profileImageUrl.value,
                    isAsync = profileImageUrl.value != placeHolderImage,
                    onUploadImageClicked = {
                        imagePicker.launch()
                    })
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier.fillMaxWidth(0.50f),
                        contentAlignment = Alignment.Center
                    ) {
                        InputWidget(
                            iconRes = "drawable/card_icon.png",
                            placeholderText = "Firstname",
                            iconSize = 40
                        ){
                            firstname.value = it
                        }
                    }
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        InputWidget(
                            iconRes = "drawable/card_icon.png",
                            placeholderText = "Lastname",
                            iconSize = 40
                        ){
                            lastname.value = it
                        }
                    }
                }
                InputWidget(
                    iconRes = "drawable/address.png",
                    placeholderText = "Address",
                    iconSize = 28,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    isPasswordField = false
                ){
                    address.value = it
                }
                InputWidget(
                    iconRes = "drawable/phone_icon.png",
                    placeholderText = "Contact Phone",
                    iconSize = 28,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    isPasswordField = false
                ){
                    contactPhone.value = it
                }

                AttachCityDropDownWidget(userCountry = country.value) {
                    city.value = it
                }

                ToggleButton(
                    shape = RoundedCornerShape(15.dp),
                    onLeftClicked = {
                      gender.value = "Male"
                    },
                    onRightClicked = {
                      gender.value = "Female"
                    },
                    leftText = "Male",
                    rightText = "Female"
                )

                ButtonComponent(
                    modifier = buttonStyle,
                    buttonText = "Save",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                    fontSize = 18,
                    shape = CircleShape,
                    textColor = Color(color = 0xFFFFFFFF),
                    style = TextStyle(),
                    borderStroke = null
                ) {
                    if (!InputValidator(inputList).isValidInput()) {
                        ShowSnackBar(title = "Input Required", description = "Please provide the required info", actionLabel = "", duration = StackedSnackbarDuration.Short, snackBarType = SnackBarType.ERROR,
                                onActionClick = {}, stackedSnackBarHostState = stackedSnackBarHostState)
                    } else if (profileImageUrl.value == placeHolderImage) {
                        ShowSnackBar(title = "Profile Image Required", description = "Please Upload a required Profile Image", actionLabel = "", duration = StackedSnackbarDuration.Short, snackBarType = SnackBarType.ERROR,
                            stackedSnackBarHostState,onActionClick = {})
                    } else {
                        preferenceSettings["countryId"] = country.value
                        preferenceSettings["cityId"] = city.value
                        authenticationPresenter.completeProfile(
                            firstname.value, lastname.value,
                            userEmail = userEmail, address = address.value,
                            contactPhone = contactPhone.value, countryId = countryId.value, cityId = city.value,
                            gender = gender.value, profileImageUrl = profileImageUrl.value
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun AttachCityDropDownWidget(userCountry: String = "", onMenuItemClick : (Int) -> Unit) {
    val locations = getCityList(userCountry)
    DropDownWidget(menuItems = locations.values.toList(), iconRes = "drawable/urban_icon.png", placeHolderText = "City", onMenuItemClick = {
        onMenuItemClick(it)
    })
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
