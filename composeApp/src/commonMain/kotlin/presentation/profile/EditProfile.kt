package presentation.profile

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
import domain.Models.PlatformNavigator
import domain.Enums.Screens
import domain.Enums.getCityList
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.ProfileHandler
import presentation.components.ButtonComponent
import presentation.components.ToggleButton
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.widgets.DropDownWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.AccountProfileImage
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentation.widgets.TitleWidget
import presentations.widgets.InputWidget
import rememberStackedSnackbarHostState

class EditProfile(private val mainViewModel: MainViewModel, val  platformNavigator: PlatformNavigator? = null,val preferenceSettings: Settings) : Tab, KoinComponent {
    private val profilePresenter: ProfilePresenter by inject()
    private var actionUIStateViewModel: ActionUIStateViewModel? = null
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
        val latitude = remember { mutableStateOf(0.0) }
        val longitude = remember { mutableStateOf(0.0) }
        val country = remember { mutableStateOf("") }

        if (actionUIStateViewModel == null) {
            actionUIStateViewModel= kmpViewModel(
                factory = viewModelFactory {
                    ActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        preferenceSettings as ObservableSettings

        preferenceSettings.addDoubleListener("latitude",0.0) {
                value: Double -> latitude.value = value
        }
        preferenceSettings.addDoubleListener("longitude",0.0) {
                value: Double -> longitude.value = value
        }

        if (latitude.value != 0.0 && longitude.value != 0.0){
            profilePresenter.getUserLocation(latitude.value, longitude.value)
        }


        val handler = ProfileHandler(profilePresenter,
            onUserLocationReady = {
               country.value = it.country.toString()
            },
           actionUIStateViewModel!!)
        handler.init()

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 1,
            animation = StackedSnackbarAnimation.Bounce
        )

        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                 Box(modifier = Modifier.fillMaxWidth().height(60.dp)) {
                     Box(modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(start = 10.dp), contentAlignment = Alignment.CenterStart) {
                         AttachBackIcon(mainViewModel)
                     }
                     Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(), contentAlignment = Alignment.Center) {
                         PageTitle()
                     }
                 }
            },
            content = {
                if (actionUIStateViewModel!!.uiStateInfo.value.isLoading) {
                    Box(modifier = Modifier.fillMaxWidth(0.80f)) {
                        LoadingDialog("Updating Profile...")
                    }
                } else if (actionUIStateViewModel!!.uiStateInfo.value.isSuccess) {
                    ShowSnackBar(title = "Success!",
                        description = "Account Updated Successfully",
                        actionLabel = "",
                        duration = StackedSnackbarDuration.Short,
                        snackBarType = SnackBarType.SUCCESS,
                        stackedSnackBarHostState,
                        onActionClick = {})
                }
                else{
                    ShowSnackBar(title = "Failed",
                        description = "Error Occurred Please Try Again",
                        actionLabel = "",
                        duration = StackedSnackbarDuration.Short,
                        snackBarType = SnackBarType.SUCCESS,
                        stackedSnackBarHostState,
                        onActionClick = {})
                }

                EditProfileCompose(mainViewModel, platformNavigator)
            },
            bottomBar = {

                val buttonStyle = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth()
                    .height(50.dp)

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
                    preferenceSettings.clear()
                    platformNavigator?.getUserLocation()
                }
            })
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
                text = userInfo.contactPhone!!
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
fun AttachCountryDropDownWidget(selectedCountry: Int = -1, onMenuItemClick : (Int) -> Unit) {
    val countryList = countryList()
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

