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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import countryList
import domain.Enums.Gender
import domain.Models.PlatformNavigator
import domain.Enums.Screens
import domain.Enums.getCityList
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.PlatformHandler
import presentation.DomainViewHandler.ProfileHandler
import presentation.authentication.AttachCityDropDownWidget
import presentation.authentication.AttachCountryDropDownWidget
import presentation.authentication.AuthenticationPresenter
import presentation.components.ButtonComponent
import presentation.components.ToggleButton
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PlatformViewModel
import presentation.widgets.DropDownWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.AccountProfileImage
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentation.widgets.TitleWidget
import presentations.components.TextComponent
import presentations.widgets.InputWidget
import rememberStackedSnackbarHostState
import utils.InputValidator

class EditProfile(private val mainViewModel: MainViewModel, val  platformNavigator: PlatformNavigator? = null,val preferenceSettings: Settings) : Tab, KoinComponent {

    private val profilePresenter: ProfilePresenter by inject()
    private var actionUIStateViewModel: ActionUIStateViewModel? = null
    val authenticationPresenter : AuthenticationPresenter by inject()
    private var platformViewModel: PlatformViewModel? = null


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
        val userInfo = mainViewModel.currentUserInfo.value
        val userGender = if (userInfo.gender == Gender.MALE.toPath()) Gender.MALE.toPath()
        else Gender.FEMALE.toPath()

        val userAddress = userInfo.address ?: ""

        val userPhone = userInfo.contactPhone ?: ""

        val placeHolderImage = "drawable/user_icon.png"
        val firstname = remember { mutableStateOf(userInfo.firstname) }
        val lastname = remember { mutableStateOf(userInfo.lastname) }
        val gender = remember { mutableStateOf(userGender) }
        val city = remember { mutableStateOf(userInfo.city) }
        val contactPhone = remember { mutableStateOf(userPhone) }
        val address = remember { mutableStateOf(userAddress) }
        val completeProfileInProgress = remember { mutableStateOf(false) }
        val navigateToConnectVendor = remember { mutableStateOf(false) }
        val profileImageUrl = remember { mutableStateOf(placeHolderImage) }
        val imagePickerScope = rememberCoroutineScope()
        val preferenceSettings = Settings()
        val country = remember { mutableStateOf(userInfo.country) }

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

        val handler = ProfileHandler(profilePresenter,
            onUserLocationReady = {
                country.value = it.country.toString()
            },
            actionUIStateViewModel!!)
        handler.init()

        //View Contract Handler Initialisation
        val platformHandler = PlatformHandler(profilePresenter, platformViewModel!!)
        platformHandler.init()

        val rootModifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .background(color = Color.White)


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
                 //   platformNavigator.startImageUpload(it)
                }
            }
        )

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 1,
            animation = StackedSnackbarAnimation.Bounce
        )


        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState)  }
        ) {

            Column(modifier = rootModifier) {
                Column(modifier = topLayoutModifier) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        presentation.authentication.PageTitle()
                    }
                    AccountProfileImage(
                        profileImageUrl = profileImageUrl.value,
                        isAsync = profileImageUrl.value != placeHolderImage,
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
                                isSingleLine = true,
                                maxLines = 1
                            ) {
                                lastname.value = it
                            }
                        }
                    }
                    AttachCountryDropDownWidget() {
                        profilePresenter.getPlatformCities(country = it)
                        country.value = it
                    }

                    AttachCityDropDownWidget(platformViewModel!!) {
                        city.value = it
                    }

                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        InputWidget(
                            iconRes = "drawable/address.png",
                            placeholderText = "Mobile Address",
                            iconSize = 28,
                            text = address.value!!,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            isPasswordField = false,
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
                        ) {}

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

                        }


                    }
                   }
                }
            }
        }
    }


@Composable
fun EditProfileCompose(mainViewModel: MainViewModel, platformNavigator: PlatformNavigator?) {
    val userInfo = mainViewModel.currentUserInfo.value
    val firstname = remember { mutableStateOf(userInfo.firstname) }
    val userEmail = userInfo.email
    val lastname = remember { mutableStateOf(userInfo.lastname) }
    val address = remember { mutableStateOf(userInfo.address) }
    val contactPhone = remember { mutableStateOf(userInfo.contactPhone) }
    val country = remember { mutableStateOf(userInfo.country) }
    val city = remember { mutableStateOf(userInfo.city) }
    val gender = remember { mutableStateOf(userInfo.gender) }
    val profileImageUrl = remember { mutableStateOf(userInfo.profileImageUrl) }
    val imagePickerScope = rememberCoroutineScope()
    val preferenceSettings = Settings()


    preferenceSettings as ObservableSettings

    preferenceSettings.addStringListener("imageUrl","") {
            value: String -> profileImageUrl.value = value
    }

    val imagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = imagePickerScope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                platformNavigator?.startImageUpload(it)
            }
        }
    )

    val rootModifier =
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 50.dp)
            .background(color = Color.White)



    val topLayoutModifier =
        Modifier
            .padding(start = 5.dp, end = 5.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .fillMaxHeight()
            .background(color = Color.White)


    Column(modifier = rootModifier) {
        Column(modifier = topLayoutModifier) {
            AccountProfileImage(
                profileImageUrl = profileImageUrl.value!!,
                isAsync = true,
                onUploadImageClicked = {
                    imagePicker.launch()
                })
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.fillMaxWidth(0.50f), contentAlignment = Alignment.Center) {
                    InputWidget(
                        iconRes = "drawable/card_icon.png",
                        placeholderText = "Firstname",
                        iconSize = 40,
                        text = userInfo.firstname!!
                    ) {
                        firstname.value = it
                    }
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    InputWidget(
                        iconRes = "drawable/card_icon.png",
                        placeholderText = "Lastname",
                        iconSize = 40,
                        text = userInfo.lastname!!
                    ) {
                        lastname.value = it
                    }
                }
            }
            InputWidget(
                iconRes = "drawable/address.png",
                placeholderText = "Address",
                iconSize = 28,
                isPasswordField = false,
                text = userInfo.address!!
            ) {
                address.value = it
            }
            InputWidget(
                iconRes = "drawable/phone_icon.png",
                placeholderText = "Contact Phone",
                iconSize = 28,
                isPasswordField = false,
                text = userInfo.contactPhone.toString()
            ) {
                contactPhone.value = it
            }
          /*  AttachCountryDropDownWidget(userInfo.country!!, onMenuItemClick = {
                country.value = it
            })
            AttachCityDropDownWidget(
                onMenuItemClick = {
                    city.value = it
                })*/

            val isFemale: Boolean = gender.value == "female"

            ToggleButton(shape = RoundedCornerShape(15.dp), isRightSelection = isFemale, onLeftClicked = {

            }, onRightClicked = {

            }, leftText = "Male", rightText = "Female")
        }
    }
   }





@Composable
private fun AttachBackIcon(mainViewModel: MainViewModel) {
        PageBackNavWidget {
            when (mainViewModel.screenNav.value.first) {
                Screens.BOOKING.toPath() -> {
                    mainViewModel.setScreenNav(Pair(Screens.EDIT_PROFILE.toPath(), Screens.BOOKING.toPath()))
                }
                Screens.CART.toPath() -> {
                    mainViewModel.setScreenNav(Pair(Screens.EDIT_PROFILE.toPath(), Screens.CART.toPath()))
                }
                Screens.MAIN_TAB.toPath() -> {
                    mainViewModel.setScreenNav(Pair(Screens.EDIT_PROFILE.toPath(), Screens.MAIN_TAB.toPath()))
                }
                else -> {

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

