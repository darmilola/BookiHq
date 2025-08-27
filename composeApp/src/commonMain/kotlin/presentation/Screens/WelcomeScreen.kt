package presentation.Screens

import GGSansRegular
import domain.Models.PlatformNavigator
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
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
import com.assignment.moniepointtest.ui.theme.AppTheme
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
import presentation.widgets.AttachTextContent
import presentation.widgets.welcomeScreenScrollWidget
import presentations.components.ImageComponent
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

        if (onBackPressed.value) {
            platformNavigator.exitApp()
        }

        AppTheme {
            WelcomeScreenCompose(
                platformNavigator,
                googleAuthEmail,
                authenticationPresenter = authenticationPresenter,
                mainViewModel = mainViewModel!!,
                databaseBuilder = databaseBuilder!!
            )
        }
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
                preferenceSettings[SharedPreferenceEnum.AUTH_TYPE.toPath()] =
                    AuthType.EMAIL.toPath()
                val userDao = databaseBuilder.build().getUserDao()
                val vendorDao = databaseBuilder.build().getVendorDao()
                val userCount = userDao.count()
                val vendorCount = vendorDao.count()
                if (userCount == 0 && vendorCount == 0) {
                    userDao.insert(user)
                }

                verificationInProgress.value = false
                navigateToPlatform.value = true
            }
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
                preferenceSettings[SharedPreferenceEnum.AUTH_TYPE.toPath()] =
                    AuthType.EMAIL.toPath()
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


    if (navigateToCompleteProfile.value) {
        val completeProfile = CompleteProfileScreen(
            platformNavigator,
            authPhone = "empty",
            authEmail = authEmail.value
        )
        completeProfile.setMainViewModel(mainViewModel = mainViewModel)
        completeProfile.setDatabaseBuilder(databaseBuilder = databaseBuilder)
        navigator.replaceAll(completeProfile)
    } else if (navigateToConnectVendor.value) {
        val connectVendor = ConnectVendor(platformNavigator)
        connectVendor.setMainViewModel(mainViewModel)
        connectVendor.setDatabaseBuilder(databaseBuilder)
        navigator.replaceAll(connectVendor)
    } else if (navigateToPlatform.value) {
        navigateToPlatform.value = false
        val mainScreen = MainScreen(platformNavigator)
        mainScreen.setDatabaseBuilder(databaseBuilder)
        mainScreen.setMainViewModel(mainViewModel)
        navigator.replaceAll(mainScreen)
    }

    Box(
        contentAlignment = Alignment.TopCenter, modifier = Modifier
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {

                ImageComponent(
                    imageModifier = Modifier.fillMaxHeight().fillMaxWidth(),
                    imageRes = "drawable/black_woman_hair.jpg", contentScale = ContentScale.Crop
                )

                Box(modifier = Modifier.fillMaxSize().background(color = Color(0x40000000)))

                Column(modifier = Modifier.fillMaxSize()) {


                    Box(
                        modifier = Modifier.fillMaxHeight(0.65f).fillMaxWidth()
                            .background(color = Color.Transparent),
                        contentAlignment = Alignment.BottomCenter
                    ) {


                        Box(
                            modifier = Modifier.fillMaxWidth(0.90f).wrapContentHeight()
                                .padding(start = 20.dp, bottom = 20.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            TextComponent(
                                text = "Book your beauty treatments anytime, anywhere — glow without waiting.",
                                fontSize = 23,
                                textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                                textColor = Color.White,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 30
                            )
                        }

                    }

                    Column(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight().background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0x10ffffff),
                                    Color(0x20ffffff),
                                    Color(0x40ffffff),
                                    Color(0x60ffffff),
                                    Color(0x90ffffff),
                                    Color(0xCCffffff),
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White)
                            )
                        ).padding(top = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {


                        // Box(modifier = bgStyle, contentAlignment = Alignment.TopStart) {}
                        Box(
                            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 20.dp),
                        ) {
                            AttachActionButtons(platformNavigator, onAuthSuccessful = {
                                authEmail.value = it
                                authenticationPresenter.validateEmail(it)
                            }, onAuthFailed = {
                            }, mainViewModel = mainViewModel, databaseBuilder = databaseBuilder)

                        }
                        Box(
                            modifier = Modifier.fillMaxWidth().height(100.dp)
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
                            /*TextComponent(
                            text = "An “agree to terms and conditions” is a method of protecting your business by requiring that users acknowledge the rules",
                            fontSize = 14,
                            fontFamily = GGSansRegular,
                            textStyle = TextStyle(),
                            textColor = Color.White,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 15
                        )*/
                        }
                    }
                }

                if (userEmailFromGoogleAuth.value.isNotEmpty()) {
                    authEmail.value = googleAuthEmail
                    authenticationPresenter.validateEmail(googleAuthEmail)
                }
            }

        }

    }
}

@Composable
fun AttachActionButtons(platformNavigator: PlatformNavigator,  onAuthSuccessful: (String) -> Unit,
                        onAuthFailed: () -> Unit, mainViewModel: MainViewModel, databaseBuilder: RoomDatabase.Builder<AppDatabase>){
    val navigator = LocalNavigator.currentOrThrow
    val buttonStyle = Modifier
        .padding(bottom = 15.dp, top = 15.dp)
        .fillMaxWidth(0.90f)
        .height(45.dp)

    val phoneButtonStyle = Modifier
        .fillMaxWidth(0.90f)
        .height(45.dp)
        .background(
            shape = CircleShape,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Colors.primaryColor,
                    Colors.primaryColor,
                    Colors.primaryColor,
                    Colors.primaryColor,
                    Colors.primaryColor
                )
            )
        )
    Column(modifier = Modifier.fillMaxWidth().height(200.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        IconButtonComponent(modifier = buttonStyle, buttonText = "Continue with Google", borderStroke = BorderStroke(0.8.dp, Colors.darkPrimary), iconSize = 20, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 16, shape = CircleShape, textColor = Colors.darkPrimary, style = androidx.compose.material3.MaterialTheme.typography.titleLarge, iconRes = "drawable/google_icon.png"){
            platformNavigator.startGoogleSSO(onAuthSuccessful = {
                onAuthSuccessful(it)
            }, onAuthFailed = {
                onAuthFailed()
            })
        }

        /*Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            TextComponent(
                text = "Or",
                fontSize = 23,
                fontFamily = GGSansRegular,
                textStyle = TextStyle(),
                textColor = Color.White,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 30
            )
        }*/

        IconButtonComponent(modifier = phoneButtonStyle, buttonText = "Sign In with Phone Number", borderStroke = BorderStroke((0.01).dp, Colors.primaryColor), iconSize = 24, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 16, shape = CircleShape, textColor = Color.White, style = androidx.compose.material3.MaterialTheme.typography.titleLarge, iconRes = "drawable/care_icon.png", colorFilter = ColorFilter.tint(color = Color.White)){
           val continueWithPhone = PhoneInputScreen(platformNavigator)
            continueWithPhone.setMainViewModel(mainViewModel = mainViewModel)
            continueWithPhone.setDatabaseBuilder(databaseBuilder = databaseBuilder)
            navigator.push(continueWithPhone)
        }

        IconButtonComponent(modifier = buttonStyle, buttonText = "Continue with X", borderStroke = BorderStroke(0.8.dp, Colors.darkPrimary), iconSize = 20, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 16, shape = CircleShape, textColor = Colors.darkPrimary, style = androidx.compose.material3.MaterialTheme.typography.titleLarge, iconRes = "drawable/x_icon.png", colorFilter = ColorFilter.tint(color = Color.Black)){
            platformNavigator.startXSSO(onAuthSuccessful = {
                onAuthSuccessful(it)
            }, onAuthFailed = {
                onAuthFailed()
            })
        }



    }
}