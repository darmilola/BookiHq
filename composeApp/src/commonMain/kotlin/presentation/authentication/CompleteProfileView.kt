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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import countryList
import domain.Enums.AuthType
import domain.Enums.Gender
import domain.Enums.SharedPreferenceEnum
import domain.Models.PlatformNavigator
import presentation.DomainViewHandler.AuthenticationScreenHandler
import presentation.DomainViewHandler.PlatformHandler
import presentation.components.ButtonComponent
import presentation.components.ToggleButton
import presentation.Screens.ConnectVendor
import presentation.dialogs.LoadingDialog
import presentation.profile.ProfilePresenter
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.CityViewModel
import presentation.widgets.AccountProfileImage
import presentation.widgets.DropDownWidget
import presentation.widgets.SnackBarType
import presentation.widgets.TitleWidget
import presentation.widgets.ShowSnackBar
import presentations.components.TextComponent
import presentations.widgets.InputWidget
import rememberStackedSnackbarHostState
import utils.InputValidator

@Composable
fun CompleteProfile(authenticationPresenter: AuthenticationPresenter, authEmail: String, authPhone: String,
                    platformNavigator: PlatformNavigator, cityViewModel: CityViewModel, profilePresenter: ProfilePresenter, mainViewModel: MainViewModel, databaseBuilder: RoomDatabase.Builder<AppDatabase>?) {

    val placeHolderImage = "drawable/user_icon.png"
    val firstname = remember { mutableStateOf("") }
    val lastname = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf(Gender.MALE.toPath()) }
    val contactPhone = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val userCountry = remember { mutableStateOf("") }
    val city = remember { mutableStateOf("") }
    val completeProfileInProgress = remember { mutableStateOf(false) }
    val navigateToConnectVendor = remember { mutableStateOf(false) }
    val profileImageUrl = remember { mutableStateOf(placeHolderImage) }
    val preferenceSettings = Settings()
    val authType = if (authEmail.isNotEmpty()) AuthType.EMAIL.toPath() else AuthType.PHONE.toPath()
    val inputList =  ArrayList<String>()
    val isSavedClicked = remember {
        mutableStateOf(false)
    }
    val navigator = LocalNavigator.currentOrThrow
    val stackedSnackBarHostState = rememberStackedSnackbarHostState(
        maxStack = 5,
        animation = StackedSnackbarAnimation.Bounce
    )

    //View Contract Handler Initialisation
    val handler = PlatformHandler(profilePresenter, cityViewModel)
    handler.init()

    inputList.add(firstname.value)
    inputList.add(lastname.value)
    inputList.add(contactPhone.value)
    inputList.add(address.value)


    val authHandler = AuthenticationScreenHandler(authenticationPresenter,
        onUserLocationReady = {},
        enterPlatform = { _, _ -> },
        completeProfile = { _, _ -> },
        connectVendor = { _ -> },
        onVerificationStarted = {},
        onVerificationEnded = {}, onCompleteStarted = {
            completeProfileInProgress.value = true
        }, onCompleteEnded = { isSuccessful -> completeProfileInProgress.value = false },
        connectVendorOnProfileCompleted = {
                country,userCity, profileId, apiKey ->
                preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath()] = country
                preferenceSettings[SharedPreferenceEnum.CITY.toPath()] = userCity
                preferenceSettings[SharedPreferenceEnum.USER_ID.toPath()] = profileId
                preferenceSettings[SharedPreferenceEnum.API_KEY.toPath()] = apiKey
                navigateToConnectVendor.value = true

        }, onUpdateStarted = {}, onUpdateEnded = {}, onVerificationError = {})
    authHandler.init()

    val pattern = remember { Regex("^\\d*\$") }

    if (completeProfileInProgress.value) {
        Box(modifier = Modifier.fillMaxWidth(0.90f)) {
            LoadingDialog("Saving Profile...")
        }
    }

    else if (navigateToConnectVendor.value){
        val connectVendor = ConnectVendor(platformNavigator)
        connectVendor.setMainViewModel(mainViewModel)
        connectVendor.setDatabaseBuilder(databaseBuilder = databaseBuilder)
        navigator.replaceAll(connectVendor)
    }

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
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .background(color = Color.White)

    LaunchedEffect(key1 = true) {
        platformNavigator.getUserLocation(onLocationReady = { latitude: String, longitude: String, countryName: String, cityName: String ->
            preferenceSettings[SharedPreferenceEnum.LATITUDE.toPath()] = latitude
            preferenceSettings[SharedPreferenceEnum.LONGITUDE.toPath()] = longitude
        })
    }





    Scaffold(
        snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState)  }
    ) {

        Column(modifier = rootModifier) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                PageTitle()
            }
            Column(modifier = topLayoutModifier) {
                AccountProfileImage(
                    profileImageUrl = profileImageUrl.value,
                    isAsync = profileImageUrl.value != placeHolderImage,
                    onUploadImageClicked = {
                        platformNavigator.startImageUpload {
                            profileImageUrl.value = it
                        }
                    })
                Row(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        InputWidget(
                            iconRes = "drawable/card_icon.png",
                            placeholderText = "Firstname",
                            iconSize = 28,
                            text = firstname.value!!,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            isPasswordField = false,
                            isSingleLine = true,
                            onSaveClicked = isSavedClicked.value,
                            maxLines = 1
                        ) {
                            firstname.value = it
                        }
                    }
                    Box(modifier = Modifier.weight(1f).padding(start = 10.dp), contentAlignment = Alignment.Center) {
                        InputWidget(
                            iconRes = "drawable/card_icon.png",
                            placeholderText = "Lastname",
                            iconSize = 28,
                            text = lastname.value,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            isPasswordField = false,
                            isSingleLine = true,
                            onSaveClicked = isSavedClicked.value,
                            maxLines = 1
                        ) {
                            lastname.value = it
                        }
                    }
                }
                AttachCountryDropDownWidget(selectedCountry = userCountry.value) {
                    profilePresenter.getCities(country = it)
                    userCountry.value = it
                }
                AttachCityDropDownWidget(cityViewModel = cityViewModel, onMenuItemClick = {
                    city.value = it
                })
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    InputWidget(
                        iconRes = "drawable/address.png",
                        placeholderText = "Mobile Address",
                        iconSize = 28,
                        text = address.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        isPasswordField = false,
                        onSaveClicked = isSavedClicked.value,
                        isSingleLine = true,
                        maxLines = 1
                    ) {
                        address.value = it
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    InputWidget(
                        iconRes = "drawable/phone_icon.png",
                        placeholderText = "Contact Phone",
                        iconSize = 28,
                        text = contactPhone.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isPasswordField = false,
                        onSaveClicked = isSavedClicked.value,
                        isSingleLine = true,
                        maxLines = 1
                    ) {
                        if (it.matches(pattern)) {
                            contactPhone.value = it
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                    TextComponent(
                        text = "Gender",
                        fontSize = 15,
                        textStyle = TextStyle(),
                        textColor = theme.Colors.darkPrimary,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 30,
                        textModifier = Modifier.padding(end = 10.dp, start = 10.dp)
                    )

                    ToggleButton(
                        shape = RoundedCornerShape(10.dp),
                        onLeftClicked = {
                            gender.value = Gender.MALE.toPath()
                        },
                        onRightClicked = {
                            gender.value = Gender.FEMALE.toPath()
                        },
                        leftText = Gender.MALE.toPath(),
                        rightText = Gender.FEMALE.toPath(),
                    )
                }

                ButtonComponent(
                    modifier = buttonStyle,
                    buttonText = "Save",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                    fontSize = 18,
                    shape = RoundedCornerShape(15.dp),
                    textColor = Color(color = 0xFFFFFFFF),
                    style = TextStyle(),
                    borderStroke = null
                ) {
                    isSavedClicked.value = true
                    if (!InputValidator(inputList).isValidInput()) {
                        ShowSnackBar(title = "Input Required", description = "Please provide the required info", actionLabel = "", duration = StackedSnackbarDuration.Short, snackBarType = SnackBarType.ERROR,
                                onActionClick = {}, stackedSnackBarHostState = stackedSnackBarHostState)
                    }
                    else if (profileImageUrl.value == placeHolderImage) {
                        ShowSnackBar(title = "Profile Image Required", description = "Please Upload a required Profile Image", actionLabel = "", duration = StackedSnackbarDuration.Short, snackBarType = SnackBarType.ERROR,
                            stackedSnackBarHostState,onActionClick = {})
                    }
                    else {
                        authenticationPresenter.completeProfile(
                            firstname.value, lastname.value,
                            userEmail = authEmail, authPhone = authPhone, contactPhone = contactPhone.value ,address = address.value, signupType = authType, country = userCountry.value,
                            city = city.value, gender = gender.value, profileImageUrl = profileImageUrl.value)
                    }
                }
            }
        }
    }
}

@Composable
fun AttachCountryDropDownWidget(selectedCountry: String = "", onMenuItemClick : (String) -> Unit) {
    val countryList = countryList()

    val index = if (selectedCountry ==  CountryEnum.SOUTH_AFRICA.toPath()) CountryEnum.SOUTH_AFRICA.getId() else CountryEnum.NIGERIA.getId()

    DropDownWidget(menuItems = countryList, selectedIndex = index, placeHolderText = "Country of Residence", onMenuItemClick = {
        onMenuItemClick(countryList[it])
    })
}

@Composable
fun AttachCityDropDownWidget(cityViewModel: CityViewModel, onMenuItemClick : (String) -> Unit) {
    val cityListState = cityViewModel.cities.collectAsState()
    val cityList = cityListState.value

    DropDownWidget(menuItems = cityList,iconRes = "drawable/urban_icon.png", placeHolderText = "City", onMenuItemClick = {
        onMenuItemClick(cityList[it])
    })
}



@Composable
fun PageTitle(){
    val rowModifier = Modifier
        .padding(bottom = 10.dp, top = 10.dp)
        .wrapContentWidth()
        .wrapContentHeight()
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            TitleWidget(title = "Complete Your Profile", textColor = Colors.primaryColor)
     }
}
