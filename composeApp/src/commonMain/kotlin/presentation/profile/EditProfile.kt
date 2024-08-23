package presentation.profile

import StackedSnackbarHost
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import applications.device.deviceInfo
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.ScreenTransition
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import domain.Enums.DeviceType
import domain.Enums.Gender
import domain.Models.PlatformNavigator
import domain.Enums.Screens
import domain.Enums.SharedPreferenceEnum
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.AuthenticationScreenHandler
import presentation.DomainViewHandler.PlatformHandler
import presentation.DomainViewHandler.ProfileHandler
import presentation.Screens.SplashScreen
import presentation.authentication.AuthenticationPresenter
import presentation.components.ButtonComponent
import presentation.components.ToggleButton
import presentation.consultation.rightTopBarItem
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PlatformViewModel
import presentation.widgets.PageBackNavWidget
import presentation.widgets.AccountProfileImage
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentation.widgets.TitleWidget
import presentations.widgets.InputWidget
import rememberStackedSnackbarHostState
import utils.InputValidator
import utils.ParcelableScreen

@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class EditProfile(val  platformNavigator: PlatformNavigator? = null) : KoinComponent, ParcelableScreen, ScreenTransition {

    @Transient
    private val profilePresenter: ProfilePresenter by inject()
    @Transient
    private var performedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient
    private val authenticationPresenter : AuthenticationPresenter by inject()
    @Transient
    private var platformViewModel: PlatformViewModel? = null
    @Transient
    private var mainViewModel: MainViewModel? = null

    override val key: ScreenKey = uniqueScreenKey

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        if (platformViewModel == null) {
            platformViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PlatformViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }

        val userInfo = mainViewModel!!.currentUserInfo.value
        val userGender = if (userInfo.gender == Gender.MALE.toPath()) Gender.MALE.toPath()
        else Gender.FEMALE.toPath()

        val firstname = remember { mutableStateOf(userInfo.firstname) }
        val lastname = remember { mutableStateOf(userInfo.lastname) }
        val gender = remember { mutableStateOf(userGender) }
        val city = remember { mutableStateOf(userInfo.city) }
        val contactPhone = remember { mutableStateOf(userInfo.contactPhone) }
        val address = remember { mutableStateOf(userInfo.address) }
        val profileImageUrl = remember { mutableStateOf(userInfo.profileImageUrl) }
        val inputList =  ArrayList<String>()
        val country = remember { mutableStateOf(userInfo.country) }
        val isSavedClicked = remember { mutableStateOf(false) }
        val updateProfileStarted = remember { mutableStateOf(false) }
        val updateProfileEnded = remember { mutableStateOf(false) }
        val updateProfileSuccessful = remember { mutableStateOf(false) }
        val pattern = remember { Regex("^\\d*\$") }
        val preferenceSettings = Settings()

        if (performedActionUIStateViewModel == null) {
            performedActionUIStateViewModel= kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (platformViewModel == null) {
            platformViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PlatformViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        val profileHandler = ProfileHandler(profilePresenter,
            onUserLocationReady = {
                country.value = it.country.toString()
            },
            onVendorInfoReady = {},
            performedActionUIStateViewModel!!)
        profileHandler.init()

        //View Contract Handler Initialisation
        val platformHandler = PlatformHandler(profilePresenter, platformViewModel!!)
        platformHandler.init()



        inputList.add(firstname.value!!)
        inputList.add(lastname.value!!)
        inputList.add(address.value!!)
        inputList.add(contactPhone.value!!)
        inputList.add(city.value.toString())
        inputList.add(country.value.toString())

        val rootModifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .verticalScroll(rememberScrollState())
                .background(color = Color.White)


        val topLayoutModifier =
            Modifier
                .padding(top = 20.dp, start = 5.dp, end = 5.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.White)

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 1,
            animation = StackedSnackbarAnimation.Bounce
        )


        val handler = AuthenticationScreenHandler(authenticationPresenter,
            onUserLocationReady = {},
            enterPlatform = { user, phone -> },
            completeProfile = { userEmail, userPhone -> },
            connectVendor = { user -> },
            onVerificationStarted = {},
            onVerificationEnded = {}, onCompleteStarted = {}, onCompleteEnded = {},
            connectVendorOnProfileCompleted = { country,profileId, apiKey -> },
            onUpdateStarted = {
                 updateProfileStarted.value = true
                 updateProfileEnded.value = false
            }, onUpdateEnded = {
                 updateProfileEnded.value = true
                 updateProfileStarted.value = false
                if (it) updateProfileSuccessful.value = true
            })
        handler.init()

        if (updateProfileStarted.value){
            LoadingDialog(dialogTitle = "Updating Your Profile")
        }
        else if (updateProfileEnded.value && updateProfileSuccessful.value) {
            //navigator.replaceAll(SplashScreen(platformNavigator!!, mainViewModel!!))
        }
        else if(updateProfileEnded.value && !updateProfileSuccessful.value){
            ErrorDialog(dialogTitle = "Error Occurred", actionTitle = "", onConfirmation = {})
        }

        if (preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath(), ""].isNotEmpty()){
            country.value = preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath(), ""]
        }
        else{
            platformNavigator!!.getUserLocation(onLocationReady = { latitude: String, longitude: String, countryName: String ->
                println("My Name is $countryName")
                preferenceSettings[SharedPreferenceEnum.LATITUDE.toPath()] = latitude
                preferenceSettings[SharedPreferenceEnum.LONGITUDE.toPath()] = longitude
                preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath()] = countryName
                country.value = countryName
            })
        }


        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState)  }
        ) {

            Column(modifier = rootModifier) {
                Column(modifier = topLayoutModifier) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(modifier =  Modifier.weight(1.0f)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                            contentAlignment = Alignment.CenterStart) {
                            leftTopBarItem(onBackPressed = {
                                navigator.pop()
                            })
                        }

                        Box(modifier =  Modifier.weight(3.0f)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                            contentAlignment = Alignment.Center) {
                            PageTitle()
                        }

                        Box(modifier =  Modifier.weight(1.0f)
                            .fillMaxWidth(0.20f)
                            .fillMaxHeight(),
                            contentAlignment = Alignment.Center) {
                            rightTopBarItem()
                        }
                    }
                 }
                    AccountProfileImage(
                        profileImageUrl = profileImageUrl.value!!,
                        isAsync = true,
                        onUploadImageClicked = {
                            platformNavigator!!.startImageUpload {
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
                                onSaveClicked = isSavedClicked.value,
                                text = firstname.value!!,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                isPasswordField = false,
                                isSingleLine = true,
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
                                text = lastname.value!!,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                isPasswordField = false,
                                onSaveClicked = isSavedClicked.value,
                                isSingleLine = true,
                                maxLines = 1
                            ) {
                                lastname.value = it
                            }
                        }
                    }

                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        InputWidget(
                            iconRes = "drawable/address.png",
                            placeholderText = "Mobile Address",
                            iconSize = 28,
                            text = address.value!!,
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
                            text = contactPhone.value!!,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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

                    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                        val buttonStyle = Modifier
                            .padding(end = 10.dp, top = 30.dp)
                            .weight(1f)
                            .height(45.dp)

                        val buttonStyle2 = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 30.dp)
                            .weight(1f)
                            .height(45.dp)

                        ButtonComponent(
                            modifier = buttonStyle2,
                            buttonText = "Cancel",
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            fontSize = 18,
                            shape = RoundedCornerShape(10.dp),
                            textColor = Colors.primaryColor,
                            style = TextStyle(),
                            borderStroke = BorderStroke(1.dp, Colors.primaryColor)
                        ) {
                            navigator.pop()
                        }

                        ButtonComponent(
                            modifier = buttonStyle,
                            buttonText = "Save",
                            colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                            fontSize = 18,
                            shape = RoundedCornerShape(10.dp),
                            textColor = Color(color = 0xFFFFFFFF),
                            style = TextStyle(),
                            borderStroke = null
                        ) {
                            isSavedClicked.value = true
                            if (!InputValidator(inputList).isValidInput()) {
                                ShowSnackBar(title = "Input Required", description = "Please provide the required info", actionLabel = "", duration = StackedSnackbarDuration.Short, snackBarType = SnackBarType.ERROR,
                                    onActionClick = {}, stackedSnackBarHostState = stackedSnackBarHostState)
                            }
                            else if (country.value!!.isEmpty()){
                                ShowSnackBar(title = "Error",
                                    description = "Please Allow Your Location",
                                    actionLabel = "",
                                    duration = StackedSnackbarDuration.Long,
                                    snackBarType = SnackBarType.ERROR,
                                    stackedSnackBarHostState = stackedSnackBarHostState,
                                    onActionClick = {})
                            }
                            else {
                                authenticationPresenter.updateProfile(userId = userInfo.userId!!, firstname = firstname.value!!, lastname = lastname.value!!,
                                    address = address.value!!, contactPhone = contactPhone.value!!,
                                    country = country.value!!, gender = gender.value, profileImageUrl = profileImageUrl.value!!)
                            }

                        }


                   }
                }
            }
        }

    override fun enter(lastEvent: StackEvent): EnterTransition {
        return slideIn { size ->
            val x = if (lastEvent == StackEvent.Pop) -size.width else size.width
            IntOffset(x = x, y = 0)
        }
    }

    override fun exit(lastEvent: StackEvent): ExitTransition {
        return slideOut { size ->
            val x = if (lastEvent == StackEvent.Pop) size.width else -size.width
            IntOffset(x = x, y = 0)
        }
    }

}

@Composable
fun leftTopBarItem(onBackPressed: () -> Unit) {
    PageBackNavWidget {
        onBackPressed()
    }
}



@Composable
fun PageTitle(){
    val rowModifier = Modifier
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

