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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import domain.Enums.getDisplayCurrency
import domain.Models.PlatformNavigator
import domain.Models.State
import getCountryId
import kotlinx.coroutines.launch
import presentation.DomainViewHandler.AuthenticationScreenHandler
import presentation.DomainViewHandler.PlatformHandler
import presentation.components.ButtonComponent
import presentation.components.ToggleButton
import presentation.connectVendor.ConnectVendor
import presentation.dialogs.LoadingDialog
import presentation.profile.ProfilePresenter
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.StatesViewModel
import presentation.widgets.AccountProfileImage
import presentation.widgets.DropDownWidget
import presentation.widgets.SnackBarType
import presentation.widgets.TitleWidget
import presentation.widgets.ShowSnackBar
import presentation.widgets.StateDropDownWidget
import presentations.components.TextComponent
import presentations.widgets.InputWidget
import rememberStackedSnackbarHostState
import utils.InputValidator

@Composable
fun CompleteProfile(authenticationPresenter: AuthenticationPresenter, authEmail: String, authPhone: String,
                    platformNavigator: PlatformNavigator, statesViewModel: StatesViewModel, profilePresenter: ProfilePresenter, mainViewModel: MainViewModel, databaseBuilder: RoomDatabase.Builder<AppDatabase>?) {

    val placeHolderImage = "drawable/user_icon.png"
    val firstname = remember { mutableStateOf("") }
    val lastname = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf(Gender.MALE.toPath()) }
    val userCountry = remember { mutableStateOf("") }
    val state = remember { mutableStateOf(State()) }
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
    val scope = rememberCoroutineScope()

    //View Contract Handler Initialisation
    val handler = PlatformHandler(profilePresenter, statesViewModel)
    handler.init()

    inputList.add(firstname.value)
    inputList.add(lastname.value)
    inputList.add(state.value.stateName)


    val authHandler = AuthenticationScreenHandler(authenticationPresenter,
        onUserLocationReady = {},
        enterPlatform = { _ -> },
        completeProfile = { _, _ -> },
        connectVendor = { _ -> },
        onVerificationStarted = {},
        onVerificationEnded = {}, onCompleteStarted = {
            completeProfileInProgress.value = true
        }, onCompleteEnded = { isSuccessful -> completeProfileInProgress.value = false },
        connectVendorOnProfileCompleted = { userInfo  ->
                val userCurrency = getDisplayCurrency(userInfo.country)
                val displayCurrencyUnit = userCurrency.toDisplayUnit()
                val displayCurrencyPath = userCurrency.toPath()
                mainViewModel.setDisplayCurrencyUnit(displayCurrencyUnit)
                mainViewModel.setDisplayCurrencyPath(displayCurrencyPath)
                mainViewModel.setUserInfo(userInfo)
                preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath()] = userInfo.country
                preferenceSettings[SharedPreferenceEnum.STATE.toPath()] = userInfo.state?.id
                preferenceSettings[SharedPreferenceEnum.USER_ID.toPath()] = userInfo.userId
                preferenceSettings[SharedPreferenceEnum.API_KEY.toPath()] = userInfo.apiKey
                preferenceSettings[SharedPreferenceEnum.AUTH_EMAIL.toPath()] = authEmail
                preferenceSettings[SharedPreferenceEnum.AUTH_PHONE.toPath()] = authPhone
                mainViewModel.setUserInfo(userInfo)
                scope.launch {
                val userDao = databaseBuilder!!.build().getUserDao()
                val userCount = userDao.count()
                val vendorCount = userDao.count()
                if (userCount == 0 && vendorCount == 0) {
                    userDao.insert(userInfo)
                }
            }
                navigateToConnectVendor.value = true

        }, onUpdateStarted = {}, onUpdateEnded = {}, onVerificationError = {})
    authHandler.init()

    if (completeProfileInProgress.value) {
        Box(modifier = Modifier.fillMaxWidth(0.90f)) {
            LoadingDialog("Saving Profile...")
        }
    }

    else if (navigateToConnectVendor.value){
        navigateToConnectVendor.value = false
        val connectVendor = ConnectVendor(platformNavigator)
        connectVendor.setMainViewModel(mainViewModel)
        connectVendor.setDatabaseBuilder(databaseBuilder = databaseBuilder)
        navigator.push(connectVendor)
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
                            maxLines = 1,
                            maxLength = 50
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
                            maxLines = 1,
                            maxLength = 50
                        ) {
                            lastname.value = it
                        }
                    }
                }
                CountryDropDownWidget {
                    userCountry.value = it
                    profilePresenter.getCountryStates(countryId = getCountryId(it))
                }

                AttachStateDropDownWidget(statesViewModel = statesViewModel, onMenuItemClick = {
                    state.value = it
                }, onMenuExpanded = {})

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
                    else if (state.value.stateName.isEmpty()) {
                        ShowSnackBar(title = "Input Required", description = "Please Select your State", actionLabel = "", duration = StackedSnackbarDuration.Short, snackBarType = SnackBarType.ERROR,
                            stackedSnackBarHostState,onActionClick = {})
                    }
                    else {
                        authenticationPresenter.completeProfile(
                            firstname.value, lastname.value,
                            userEmail = authEmail, authPhone = authPhone, signupType = authType, country = userCountry.value,
                            state = state.value.id, gender = gender.value, profileImageUrl = profileImageUrl.value)
                    }
                }
            }
        }
    }
}

@Composable
fun CountryDropDownWidget(onMenuItemClick : (String) -> Unit) {
    val countryList = countryList()
    DropDownWidget(menuItems = countryList, placeHolderText = "Country of Residence", onMenuItemClick = {
        onMenuItemClick(countryList[it])
    }, onExpandMenuItemClick = {})
}



@Composable
fun AttachStateDropDownWidget(statesViewModel: StatesViewModel, onMenuItemClick : (State) -> Unit, onMenuExpanded:() -> Unit) {
    val cityListState = statesViewModel.platformStates.collectAsState()
    val cityList = cityListState.value
    StateDropDownWidget(menuItems = cityList, iconRes = "drawable/urban_icon.png", placeHolderText = "Select City", onMenuItemClick = {
        onMenuItemClick(cityList[it])
    }, onExpandMenuItemClick = {
        onMenuExpanded()
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
