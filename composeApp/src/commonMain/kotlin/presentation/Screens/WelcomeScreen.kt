package presentation.Screens

import GGSansRegular
import domain.Models.PlatformNavigator
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import domain.Enums.AuthType
import domain.Enums.SharedPreferenceEnum
import domain.Enums.getDisplayCurrency
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.AuthenticationScreenHandler
import presentation.authentication.AuthenticationPresenter
import presentation.components.IconButtonComponent
import presentation.connectVendor.ConnectVendor
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.MainViewModel
import presentation.widgets.welcomeScreenScrollWidget
import presentations.components.TextComponent
import utils.ParcelableScreen


@Parcelize
class WelcomeScreen(val platformNavigator: PlatformNavigator, val googleAuthEmail: String = "") : ParcelableScreen, KoinComponent {

    @Transient private val  authenticationPresenter: AuthenticationPresenter by inject()
    @Transient private var mainViewModel: MainViewModel? = null
    @Transient private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    fun setMainViewModel(mainViewModel: MainViewModel) {
        this.mainViewModel = mainViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>){
        this.databaseBuilder = databaseBuilder
    }

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()

        if (onBackPressed.value){
            platformNavigator.exitApp()
        }

        WelcomeScreenCompose(platformNavigator, googleAuthEmail, authenticationPresenter = authenticationPresenter, mainViewModel = mainViewModel!!, databaseBuilder = databaseBuilder!!)
    }
}

@Composable
fun WelcomeScreenCompose(platformNavigator: PlatformNavigator, googleAuthEmail: String = "", authenticationPresenter: AuthenticationPresenter, mainViewModel: MainViewModel,
                         databaseBuilder: RoomDatabase.Builder<AppDatabase>) {

    val verificationInProgress = remember { mutableStateOf(false) }
    val authEmail = remember { mutableStateOf("") }
    val navigateToCompleteProfile = remember { mutableStateOf(false) }
    val navigateToConnectVendor = remember { mutableStateOf(false) }
    val navigateToPlatform = remember { mutableStateOf(false) }
    val preferenceSettings = Settings()
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()

    val userEmailFromGoogleAuth = remember { mutableStateOf(googleAuthEmail) }

    val handler = AuthenticationScreenHandler(authenticationPresenter,
        onUserLocationReady = {},
        enterPlatform = { user ->
            runBlocking {
                val userCurrency = getDisplayCurrency(user.country)
                val displayCurrencyUnit = userCurrency.toDisplayUnit()
                val displayCurrencyPath = userCurrency.toPath()
                mainViewModel.setDisplayCurrencyUnit(displayCurrencyUnit)
                mainViewModel.setDisplayCurrencyPath(displayCurrencyPath)
                mainViewModel.setUserInfo(user)
                mainViewModel.setConnectedVendor(user.vendorInfo!!)
                preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath()] = user.country
                preferenceSettings[SharedPreferenceEnum.STATE.toPath()] = user.state?.id
                preferenceSettings[SharedPreferenceEnum.USER_ID.toPath()] = user.userId
                preferenceSettings[SharedPreferenceEnum.VENDOR_ID.toPath()] = user.connectedVendorId
                preferenceSettings[SharedPreferenceEnum.AUTH_EMAIL.toPath()] = user.email
                preferenceSettings[SharedPreferenceEnum.API_KEY.toPath()] = user.apiKey
                preferenceSettings[SharedPreferenceEnum.AUTH_TYPE.toPath()] = AuthType.EMAIL.toPath()
                    val userDao = databaseBuilder.build().getUserDao()
                    val vendorDao = databaseBuilder.build().getVendorDao()
                    val userCount = userDao.count()
                    val vendorCount = vendorDao.count()
                    if (userCount == 0 && vendorCount == 0) {
                        userDao.insert(user)
                    }
            }
            verificationInProgress.value = false
            navigateToPlatform.value = true
        },
        completeProfile = { userEmail, userAuthPhone ->
            preferenceSettings[SharedPreferenceEnum.AUTH_TYPE.toPath()] = AuthType.EMAIL.toPath()
            navigateToCompleteProfile.value = true
        },
        connectVendor = { user ->
            runBlocking {
                preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath()] = user.country
                preferenceSettings[SharedPreferenceEnum.STATE.toPath()] = user.state?.id
                preferenceSettings[SharedPreferenceEnum.USER_ID.toPath()] = user.userId
                preferenceSettings[SharedPreferenceEnum.VENDOR_ID.toPath()] = user.connectedVendorId
                preferenceSettings[SharedPreferenceEnum.AUTH_EMAIL.toPath()] = user.email
                preferenceSettings[SharedPreferenceEnum.API_KEY.toPath()] = user.apiKey
                preferenceSettings[SharedPreferenceEnum.AUTH_TYPE.toPath()] = AuthType.EMAIL.toPath()
                mainViewModel.setUserInfo(user)
                    val userDao = databaseBuilder.build().getUserDao()
                    val vendorDao = databaseBuilder.build().getVendorDao()
                    val userCount = userDao.count()
                    val vendorCount = vendorDao.count()
                    if (userCount == 0 && vendorCount == 0) {
                        userDao.insert(user)
                    }
            }
            navigateToConnectVendor.value = true
        },
        onVerificationStarted = {
            verificationInProgress.value = true
        },
        onVerificationEnded = {
            userEmailFromGoogleAuth.value = ""
            verificationInProgress.value = false
        }, onCompleteStarted = {}, onCompleteEnded = {},
        connectVendorOnProfileCompleted = { _ -> },
        onUpdateStarted = {}, onUpdateEnded = {},
        onVerificationError = {
            verificationInProgress.value = false
        })
    handler.init()


    if (navigateToCompleteProfile.value){
        val completeProfile = CompleteProfileScreen(platformNavigator, authPhone = "empty", authEmail = authEmail.value)
        completeProfile.setMainViewModel(mainViewModel = mainViewModel)
        completeProfile.setDatabaseBuilder(databaseBuilder = databaseBuilder)
        navigator.replaceAll(completeProfile)
    }
    else if (navigateToConnectVendor.value){
        val connectVendor = ConnectVendor(platformNavigator)
        connectVendor.setMainViewModel(mainViewModel)
        connectVendor.setDatabaseBuilder(databaseBuilder)
        navigator.replaceAll(connectVendor)
    }
    else if (navigateToPlatform.value) {
            navigateToPlatform.value = false
            val mainScreen = MainScreen(platformNavigator)
            mainScreen.setDatabaseBuilder(databaseBuilder)
            mainScreen.setMainViewModel(mainViewModel)
            navigator.replaceAll(mainScreen)
    }

    Box(contentAlignment = Alignment.TopCenter, modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()

        ) {
            if (verificationInProgress.value) {
                Box(modifier = Modifier.fillMaxWidth(0.90f)) {
                    LoadingDialog("Verifying Profile...")
                }
            }

            Column(
                modifier = Modifier.fillMaxHeight().fillMaxWidth().background(color = Color.Black),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Box(
                    modifier = Modifier.fillMaxHeight(0.65f).fillMaxWidth()
                        .background(color = Color.Transparent), contentAlignment = Alignment.Center
                ) {
                    welcomeScreenScrollWidget()
                }
                Column(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight()
                            .background(color = Color.Black), contentAlignment = Alignment.TopCenter
                    ) {
                        AttachActionButtons(platformNavigator, onAuthSuccessful = {
                            authEmail.value = it
                            authenticationPresenter.validateEmail(it)
                        }, onAuthFailed = {
                        }, mainViewModel = mainViewModel, databaseBuilder = databaseBuilder)

                    }
                    Box(
                        modifier = Modifier.fillMaxWidth().height(100.dp)
                            .background(color = Color.Black).padding(start = 20.dp, end = 20.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        TextComponent(
                            text = "An “agree to terms and conditions” is a method of protecting your business by requiring that users acknowledge the rules",
                            fontSize = 14,
                            fontFamily = GGSansRegular,
                            textStyle = TextStyle(),
                            textColor = Color.White,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 15
                        )
                    }
                }

                if (userEmailFromGoogleAuth.value.isNotEmpty()) {
                    authEmail.value = googleAuthEmail
                    authenticationPresenter.validateEmail(googleAuthEmail)
                }
            }

        }

    }

@Composable
fun AttachActionButtons(platformNavigator: PlatformNavigator,  onAuthSuccessful: (String) -> Unit,
                        onAuthFailed: () -> Unit, mainViewModel: MainViewModel, databaseBuilder: RoomDatabase.Builder<AppDatabase>){
    val navigator = LocalNavigator.currentOrThrow
    val buttonStyle = Modifier
        .padding(bottom = 15.dp)
        .fillMaxWidth(0.90f)
        .height(45.dp)

    val phoneButtonStyle = Modifier
        .padding(bottom = 15.dp)
        .fillMaxWidth(0.90f)
        .height(45.dp)
        .background(
            shape = CircleShape,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Colors.primaryColor,
                    Colors.primaryColor,
                    Colors.primaryColor,
                    Colors.postPrimaryColor,
                    Colors.postPrimaryColor
                )
            )
        )
    Column(modifier = Modifier.fillMaxWidth().height(200.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        IconButtonComponent(modifier = buttonStyle, buttonText = "Continue with Google", borderStroke = BorderStroke(0.8.dp, Color.White), iconSize = 20, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 16, shape = CircleShape, textColor = Color.Black, style = MaterialTheme.typography.h4, iconRes = "drawable/google_icon.png"){
            platformNavigator.startGoogleSSO(onAuthSuccessful = {
                onAuthSuccessful(it)
            }, onAuthFailed = {
                onAuthFailed()
            })
        }

        IconButtonComponent(modifier = phoneButtonStyle, buttonText = "Sign In with Phone Number", borderStroke = BorderStroke((0.01).dp, Colors.primaryColor), iconSize = 24, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 16, shape = CircleShape, textColor = Color.White, style = MaterialTheme.typography.h4, iconRes = "drawable/care_icon.png", colorFilter = ColorFilter.tint(color = Color.White)){
           val continueWithPhone = PhoneInputScreen(platformNavigator)
            continueWithPhone.setMainViewModel(mainViewModel = mainViewModel)
            continueWithPhone.setDatabaseBuilder(databaseBuilder = databaseBuilder)
            navigator.push(continueWithPhone)
        }

        IconButtonComponent(modifier = buttonStyle, buttonText = "Continue with X", borderStroke = BorderStroke(0.8.dp, Color.White), iconSize = 20, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 16, shape = CircleShape, textColor = Color.Black, style = MaterialTheme.typography.h4, iconRes = "drawable/x_icon.png", colorFilter = ColorFilter.tint(color = Color.Black)){
            platformNavigator.startXSSO(onAuthSuccessful = {
                onAuthSuccessful(it)
            }, onAuthFailed = {
                onAuthFailed()
            })
        }



    }
}