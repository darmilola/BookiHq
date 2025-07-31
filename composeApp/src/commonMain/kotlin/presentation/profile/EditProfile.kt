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
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import countryList
import domain.Enums.Gender
import domain.Models.PlatformNavigator
import domain.Models.State
import getCountryId
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
import presentation.viewmodels.StatesViewModel
import presentation.widgets.PageBackNavWidget
import presentation.widgets.AccountProfileImage
import presentation.widgets.DropDownWidget
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentation.widgets.StateDropDownWidget
import presentation.widgets.TitleWidget
import presentations.widgets.InputWidget
import rememberStackedSnackbarHostState
import utils.InputValidator
import utils.ParcelableScreen

@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class EditProfile(val platformNavigator: PlatformNavigator? = null) : KoinComponent, ParcelableScreen, ScreenTransition {

    @Transient
    private val profilePresenter: ProfilePresenter by inject()
    @Transient
    private var performedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient
    private val authenticationPresenter : AuthenticationPresenter by inject()
    @Transient
    private var statesViewModel: StatesViewModel? = null
    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    override val key: ScreenKey = uniqueScreenKey

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        if (statesViewModel == null) {
            statesViewModel = kmpViewModel(
                factory = viewModelFactory {
                    StatesViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }

        val userInfo = mainViewModel!!.currentUserInfo.value
        val userGender = if (userInfo.gender == Gender.MALE.toPath()) Gender.MALE.toPath() else Gender.FEMALE.toPath()
        val firstname = remember { mutableStateOf(userInfo.firstname) }
        val lastname = remember { mutableStateOf(userInfo.lastname) }
        val gender = remember { mutableStateOf(userGender) }
        val contactPhone = remember { mutableStateOf(userInfo.contactPhone) }
        val address = remember { mutableStateOf(userInfo.address) }
        val state = remember { mutableStateOf(userInfo.state) }
        val profileImageUrl = remember { mutableStateOf(userInfo.profileImageUrl) }
        val inputList =  ArrayList<String>()
        val userCountry = remember { mutableStateOf(userInfo.country) }
        val isSavedClicked = remember { mutableStateOf(false) }
        val updateProfileStarted = remember { mutableStateOf(false) }
        val updateProfileEnded = remember { mutableStateOf(false) }
        val updateProfileSuccessful = remember { mutableStateOf(false) }
        val preferenceSettings = Settings()

        if (performedActionUIStateViewModel == null) {
            performedActionUIStateViewModel= kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (statesViewModel == null) {
            statesViewModel = kmpViewModel(
                factory = viewModelFactory {
                    StatesViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        val profileHandler = ProfileHandler(profilePresenter,
            onUserLocationReady = {
                userCountry.value = it.country.toString()
            },
            onVendorInfoReady = {},
            performedActionUIStateViewModel!!)
        profileHandler.init()

        //View Contract Handler Initialisation
        val platformHandler = PlatformHandler(profilePresenter, statesViewModel!!)
        platformHandler.init()





        inputList.add(firstname.value!!.trim())
        inputList.add(lastname.value!!.trim())
        inputList.add(address.value.trim())
        inputList.add(contactPhone.value.trim())
        inputList.add(profileImageUrl.value.toString().trim())

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
            enterPlatform = { _ -> },
            completeProfile = { _, _ -> },
            connectVendor = { _ -> },
            onVerificationStarted = {},
            onVerificationEnded = {}, onCompleteStarted = {}, onCompleteEnded = {},
            connectVendorOnProfileCompleted = { _ -> },
            onUpdateStarted = {
                 updateProfileStarted.value = true
                 updateProfileEnded.value = false
            }, onUpdateEnded = {
                 updateProfileEnded.value = true
                 updateProfileStarted.value = false
                if (it) updateProfileSuccessful.value = true
            }, onVerificationError = {})
        handler.init()

        if (updateProfileStarted.value){
            LoadingDialog(dialogTitle = "Updating Your Profile")
        }
        else if (updateProfileEnded.value && updateProfileSuccessful.value) {
            updateProfileEnded.value = false
            updateProfileSuccessful.value = false
            val splashScreen = SplashScreen(platformNavigator!!)
            splashScreen.setDatabaseBuilder(databaseBuilder!!)
            navigator.replaceAll(splashScreen)
        }
        else if(updateProfileEnded.value && !updateProfileSuccessful.value){
            ErrorDialog(dialogTitle = "Error Occurred", actionTitle = "Retry", onConfirmation = {})
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
                                text = lastname.value!!,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                isPasswordField = false,
                                onSaveClicked = isSavedClicked.value,
                                isSingleLine = true,
                                maxLines = 1,
                                maxLength = 50
                            ) {
                                lastname.value = it
                            }
                        }
                    }

                AttachStateDropDownWidget(selectedState = state.value!!, statesViewModel = statesViewModel!!, onMenuItemClick = {
                    state.value = it
                }, onMenuExpanded = {
                    profilePresenter.getCountryStates(countryId = getCountryId(userCountry.value))
                })

                   Box(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        InputWidget(
                            iconRes = "drawable/address.png",
                            placeholderText = "Home Address",
                            iconSize = 28,
                            text = address.value,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            isPasswordField = false,
                            onSaveClicked = isSavedClicked.value,
                            isSingleLine = true,
                            maxLines = 1,
                            maxLength = 100
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
                            maxLines = 1,
                            maxLength = 12
                        ) {
                            contactPhone.value = it
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
                            isRightSelection = userGender == Gender.FEMALE.toPath(),
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
                            else if (userCountry.value.isEmpty()){
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
                                    address = address.value, contactPhone = contactPhone.value,
                                    country = userCountry.value, state = state.value!!.id,gender = gender.value, profileImageUrl = profileImageUrl.value!!)
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
fun EditProfileCountryDropDownWidget(selectedCountry: String, onMenuItemClick : (String) -> Unit) {
    val countryList = countryList()
    val index = if (selectedCountry ==  CountryEnum.NIGERIA.toPath()) CountryEnum.NIGERIA.getId() else CountryEnum.NIGERIA.getId()
    DropDownWidget(menuItems = countryList, selectedIndex = index, placeHolderText = "Country of Residence", onMenuItemClick = {
        onMenuItemClick(countryList[it])
    }, onExpandMenuItemClick = {})
}


@Composable
fun AttachStateDropDownWidget(selectedState: State, statesViewModel: StatesViewModel, onMenuItemClick : (State) -> Unit, onMenuExpanded:() -> Unit) {
    val cityState = statesViewModel.platformStates.collectAsState()
    if (cityState.value.isEmpty()) {
        cityState.value.add(selectedState)
    }
    StateDropDownWidget(menuItems = cityState.value, selectedIndex = 0,iconRes = "drawable/urban_icon.png", placeHolderText = "Select State", onMenuItemClick = {
        onMenuItemClick(cityState.value[it])
    }, onExpandMenuItemClick = {
        onMenuExpanded()
    })
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

