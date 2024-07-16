package presentation.profile

import StackedSnackbarHost
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import domain.Enums.AuthType
import domain.Enums.Gender
import domain.Models.PlatformNavigator
import domain.Enums.Screens
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.AuthenticationScreenHandler
import presentation.DomainViewHandler.PlatformHandler
import presentation.DomainViewHandler.ProfileHandler
import presentation.authentication.AttachCityDropDownWidget
import presentation.authentication.AttachCountryDropDownWidget
import presentation.authentication.AuthenticationPresenter
import presentation.components.ButtonComponent
import presentation.components.ToggleButton
import presentation.consultation.rightTopBarItem
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.ActionUIStateViewModel
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

@Parcelize
class EditProfileTab(val  platformNavigator: PlatformNavigator? = null) : Tab, KoinComponent, Parcelable {

    @Transient
    private val profilePresenter: ProfilePresenter by inject()
    @Transient
    private var actionUIStateViewModel: ActionUIStateViewModel? = null
    @Transient
    private val authenticationPresenter : AuthenticationPresenter by inject()
    @Transient
    private var platformViewModel: PlatformViewModel? = null
    @Transient
    private var mainViewModel: MainViewModel? = null



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

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    @Composable
    override fun Content() {

        if (platformViewModel == null) {
            platformViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PlatformViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }



        val userInfo = mainViewModel!!.currentUserInfo.value
        val userGender = if (userInfo.gender == Gender.MALE.toPath()) Gender.MALE.toPath()
        else Gender.FEMALE.toPath()

        val userAddress = userInfo.address ?: ""
        val userPhone = userInfo.contactPhone ?: ""
        val firstname = remember { mutableStateOf(userInfo.firstname) }
        val lastname = remember { mutableStateOf(userInfo.lastname) }
        val gender = remember { mutableStateOf(userGender) }
        val city = remember { mutableStateOf(userInfo.city) }
        val contactPhone = remember { mutableStateOf(userPhone) }
        val address = remember { mutableStateOf(userAddress) }
        val profileImageUrl = remember { mutableStateOf(userInfo.profileImageUrl) }
        val imagePickerScope = rememberCoroutineScope()
        val inputList =  ArrayList<String>()
        val country = remember { mutableStateOf(userInfo.country) }
        val isSavedClicked = remember { mutableStateOf(false) }
        val updateProfileStarted = remember { mutableStateOf(false) }
        val updateProfileEnded = remember { mutableStateOf(false) }
        val updateProfileSuccessful = remember { mutableStateOf(false) }

        if (actionUIStateViewModel == null) {
            actionUIStateViewModel= kmpViewModel(
                factory = viewModelFactory {
                    ActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
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
            actionUIStateViewModel!!)
        profileHandler.init()

        //View Contract Handler Initialisation
        val platformHandler = PlatformHandler(profilePresenter, platformViewModel!!)
        platformHandler.init()



        inputList.add(firstname.value!!)
        inputList.add(lastname.value!!)
        inputList.add(address.value)
        inputList.add(contactPhone.value)

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

        val imagePicker = rememberImagePickerLauncher(
            selectionMode = SelectionMode.Single,
            scope = imagePickerScope,
            onResult = { byteArrays ->
                byteArrays.firstOrNull()?.let {
                 //   platformNavigator.startImageUpload(it)
                }
            }
        )

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
            connectVendorOnProfileCompleted = { country, profileId -> },
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
        else if (updateProfileEnded.value && updateProfileSuccessful.value){
            mainViewModel!!.setRestartApp(isRestart = true)
            mainViewModel!!.setScreenNav(Pair(Screens.EDIT_PROFILE.toPath(), Screens.MAIN_TAB.toPath()))
        }
        else if(updateProfileEnded.value && !updateProfileSuccessful.value){
            ErrorDialog(dialogTitle = "Error Occurred", actionTitle = "", onConfirmation = {})
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
                            leftTopBarItem(mainViewModel!!)
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
                            imagePicker.launch()
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
                    AttachCountryDropDownWidget(defaultValue = country.value!!) {
                        profilePresenter.getPlatformCities(country = it)
                        country.value = it
                    }

                    AttachCityDropDownWidget(defaultValue = city.value!!,platformViewModel = platformViewModel!!) {
                        city.value = it
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
                            text = contactPhone.value!!,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            isPasswordField = false,
                            onSaveClicked = isSavedClicked.value,
                            isSingleLine = true,
                            maxLines = 1
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
                            .height(50.dp)

                        val buttonStyle2 = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 30.dp)
                            .weight(1f)
                            .height(50.dp)

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
                            when (mainViewModel?.screenNav?.value?.first) {
                                Screens.MAIN_TAB.toPath() -> {
                                    mainViewModel!!.setScreenNav(
                                        Pair(
                                            Screens.EDIT_PROFILE.toPath(),
                                            Screens.MAIN_TAB.toPath()
                                        )
                                    )
                                }

                                Screens.BOOKING.toPath() -> {
                                    mainViewModel!!.setScreenNav(
                                        Pair(
                                            Screens.EDIT_PROFILE.toPath(),
                                            Screens.BOOKING.toPath()
                                        )
                                    )
                                }

                                Screens.CART.toPath() -> {
                                    mainViewModel!!.setScreenNav(
                                        Pair(
                                            Screens.EDIT_PROFILE.toPath(),
                                            Screens.CART.toPath()
                                        )
                                    )
                                }

                            }
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
                            profileImageUrl.value = "https://cdn.pixabay.com/photo/2016/11/29/06/08/woman-1867715_1280.jpg"
                            if (!InputValidator(inputList).isValidInput()) {
                                ShowSnackBar(title = "Input Required", description = "Please provide the required info", actionLabel = "", duration = StackedSnackbarDuration.Short, snackBarType = SnackBarType.ERROR,
                                    onActionClick = {}, stackedSnackBarHostState = stackedSnackBarHostState)
                            }
                            else if (country.value?.isEmpty() == true){
                                ShowSnackBar(title = "Error",
                                    description = "Please Select your country of residence",
                                    actionLabel = "",
                                    duration = StackedSnackbarDuration.Long,
                                    snackBarType = SnackBarType.ERROR,
                                    stackedSnackBarHostState = stackedSnackBarHostState,
                                    onActionClick = {})
                            }
                            else if (city.value?.isEmpty() == true){
                                ShowSnackBar(title = "Error",
                                    description = "Please Select your City",
                                    actionLabel = "",
                                    duration = StackedSnackbarDuration.Long,
                                    snackBarType = SnackBarType.ERROR,
                                    stackedSnackBarHostState = stackedSnackBarHostState,
                                    onActionClick = {})
                            }

                            else {
                                authenticationPresenter.updateProfile(userId = userInfo.userId!!, firstname = firstname.value!!, lastname = lastname.value!!,
                                    address = address.value, contactPhone = contactPhone.value!!,
                                    country = country.value!!, city = city.value!!, gender = gender.value!!, profileImageUrl = profileImageUrl.value!!)
                            }

                        }


                   }
                }
            }
        }
    }

@Composable
fun leftTopBarItem(mainViewModel: MainViewModel) {
    PageBackNavWidget {
        when (mainViewModel?.screenNav?.value?.first) {
            Screens.MAIN_TAB.toPath() -> {
                mainViewModel.setScreenNav(
                    Pair(
                        Screens.EDIT_PROFILE.toPath(),
                        Screens.MAIN_TAB.toPath()
                    )
                )
            }

            Screens.BOOKING.toPath() -> {
                mainViewModel.setScreenNav(
                    Pair(
                        Screens.EDIT_PROFILE.toPath(),
                        Screens.BOOKING.toPath()
                    )
                )
            }

            Screens.CART.toPath() -> {
                mainViewModel.setScreenNav(
                    Pair(
                        Screens.EDIT_PROFILE.toPath(),
                        Screens.CART.toPath()
                    )
                )
            }

            else -> {
                // navigator.current = MainTab(mainViewModel)
            }
        }
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

