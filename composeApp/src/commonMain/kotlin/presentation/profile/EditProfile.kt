package presentation.profile

import StackedSnackbarHost
import StackedSnakbarHostState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import domain.Models.CountryList
import domain.Models.PlatformNavigator
import domain.Models.Screens
import domain.Models.getCityList
import domain.Models.getCountries
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.components.ButtonComponent
import presentation.components.ToggleButton
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.DropDownWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.AccountProfileImage
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentation.widgets.TitleWidget
import presentations.widgets.InputWidget
import rememberStackedSnackbarHostState
import utils.InputValidator

class EditProfile(private val mainViewModel: MainViewModel, val  platformNavigator: PlatformNavigator? = null) : Tab, KoinComponent {

    private val preferenceSettings: Settings = Settings()
    private val profilePresenter: ProfilePresenter by inject()
    private var uiStateViewModel: UIStateViewModel? = null
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
        val isLoading = remember { mutableStateOf(false) }
        val isDone = remember { mutableStateOf(false) }
        val isSuccess = remember { mutableStateOf(false) }

        val handler: ProfileHandler = ProfileHandler(profilePresenter,
            isLoading = {
                isLoading.value = true
                isSuccess.value = false
                isDone.value = false
            }, isDone = {
                isLoading.value = false
                isDone.value = true
                isSuccess.value = false
            }, isSuccess = {
                isLoading.value = false
                isSuccess.value = true
                isDone.value = true
            })
        handler.init()

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 1,
            animation = StackedSnackbarAnimation.Bounce
        )

        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState)  }
        ) {

            if (isLoading.value) {
                Box(modifier = Modifier.fillMaxWidth(0.80f)) {
                    LoadingDialog("Saving Profile...")
                }
            } else if (isSuccess.value) {
                ShowSnackBar(title = "Success!",
                    description = "You have successfully updated your profile",
                    actionLabel = "",
                    duration = StackedSnackbarDuration.Short,
                    snackBarType = SnackBarType.SUCCESS,
                    stackedSnackBarHostState,
                    onActionClick = {})
                isSuccess.value = false
            }

            EditProfileCompose(mainViewModel, platformNavigator, profilePresenter, stackedSnackBarHostState)
        }
    }
}

@Composable
fun EditProfileCompose(mainViewModel: MainViewModel, platformNavigator: PlatformNavigator?, profilePresenter: ProfilePresenter, stackedSnackBarHostState: StackedSnakbarHostState) {
    val userInfo = mainViewModel.currentUserInfo.value
    val firstname = remember { mutableStateOf(userInfo.firstname) }
    val userEmail = userInfo.userEmail
    val lastname = remember { mutableStateOf(userInfo.lastname) }
    val address = remember { mutableStateOf(userInfo.address) }
    val contactPhone = remember { mutableStateOf(userInfo.contactPhone) }
    val country = remember { mutableStateOf(userInfo.countryId) }
    val city = remember { mutableStateOf(userInfo.cityId) }
    val gender = remember { mutableStateOf(userInfo.gender) }
    val profileImageUrl = remember { mutableStateOf(userInfo.profileImageUrl) }
    val imagePickerScope = rememberCoroutineScope()
    val preferenceSettings = Settings()
    val inputList =  ArrayList<String>()


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



    inputList.add(firstname.value!!)
    inputList.add(lastname.value!!)
    inputList.add(address.value!!)
    inputList.add(contactPhone.value!!)
    inputList.add(gender.value!!)

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
                        defaultValue = userInfo.firstname!!
                    ) {
                        firstname.value = it
                    }
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    InputWidget(
                        iconRes = "drawable/card_icon.png",
                        placeholderText = "Lastname",
                        iconSize = 40,
                        defaultValue = userInfo.lastname!!
                    ) {
                        lastname.value = it
                    }
                }
            }
            InputWidget(
                iconRes = "drawable/address.png",
                placeholderText = "Address",
                iconSize = 28,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isPasswordField = false,
                defaultValue = userInfo.address!!
            ) {
                address.value = it
            }
            InputWidget(
                iconRes = "drawable/phone_icon.png",
                placeholderText = "Contact Phone",
                iconSize = 28,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isPasswordField = false,
                defaultValue = userInfo.contactPhone!!
            ) {
                contactPhone.value = it
            }
            AttachCountryDropDownWidget(userInfo.countryId!!, onMenuItemClick = {
                country.value = it
            })
            AttachCityDropDownWidget(
                onMenuItemClick = {
                    city.value = it
                })

            val isFemale: Boolean = gender.value == "female"

            ToggleButton(shape = RoundedCornerShape(15.dp), isRightSelection = isFemale, onLeftClicked = {

            }, onRightClicked = {

            }, leftText = "Male", rightText = "Female")

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
                if (!InputValidator(inputList).isValidInput() || country.value == -1 || city.value == -1) {
                    ShowSnackBar(title = "Input Required",
                        description = "Please provide the required info",
                        actionLabel = "",
                        duration = StackedSnackbarDuration.Short,
                        snackBarType = SnackBarType.ERROR,
                        onActionClick = {},
                        stackedSnackBarHostState = stackedSnackBarHostState
                    )
                } else {
                    preferenceSettings["countryId"] = country.value
                    preferenceSettings["cityId"] = city.value
                    profilePresenter.updateProfile(
                        firstname.value!!,
                        lastname.value!!,
                        userEmail = userEmail!!,
                        address = address.value!!,
                        contactPhone = contactPhone.value!!,
                        countryId = country.value!!,
                        cityId = city.value!!,
                        gender = gender.value!!,
                        profileImageUrl = profileImageUrl.value!!
                    )

                }
            }
        }
    }
   }





@Composable
fun AttachCountryDropDownWidget(selectedCountry: Int = -1, onMenuItemClick : (Int) -> Unit) {
    val countryList = getCountries().values.toList()
    DropDownWidget(menuItems = countryList, selectedIndex = selectedCountry, placeHolderText = "Country of Residence", onMenuItemClick = {
        onMenuItemClick(it)
    })
}

@Composable
fun AttachCityDropDownWidget(userCountry: String = "", onMenuItemClick : (Int) -> Unit) {
    val locations = getCityList(userCountry)
    DropDownWidget(menuItems = locations.values.toList(), iconRes = "drawable/urban_icon.png", placeHolderText = "City", onMenuItemClick = {
        onMenuItemClick(it)
    })
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
                else -> {

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

class ProfileHandler(
    private val profilePresenter: ProfilePresenter,
    private val isLoading: () -> Unit,
    private val isDone: () -> Unit,
    private val isSuccess: () -> Unit,
    ) : ProfileContract.View {
    fun init() {
        profilePresenter.registerUIContract(this)
    }
    override fun onProfileDeleted() {}

    override fun onProfileUpdated() {}

    override fun showLce(asyncUIStates: AsyncUIStates, message: String) {
        asyncUIStates.let {
            when{
                it.isLoading -> {
                    isLoading()
                }

                it.isDone -> {
                    isDone()
                }

                it.isSuccess -> {
                    isSuccess()
                }
            }
        }
    }
}


