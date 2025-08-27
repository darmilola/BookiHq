package presentation.Screens

import StackedSnackbarHost
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.room.RoomDatabase
import applications.device.deviceInfo
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
import domain.Enums.DeviceType
import domain.Enums.SharedPreferenceEnum
import domain.Enums.getDisplayCurrency
import domain.Models.PlatformNavigator
import kotlinx.coroutines.launch
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.AuthenticationScreenHandler
import presentation.authentication.AuthenticationPresenter
import presentation.components.ButtonComponent
import presentation.connectVendor.ConnectVendor
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.MainViewModel
import presentation.widgets.AuthenticationBackNav
import presentation.widgets.OTPTextField
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentation.widgets.MultiLineTextWidget
import presentation.widgets.SubtitleTextWidget
import presentation.widgets.TitleWidget
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.styles.Colors
import utils.ParcelableScreen


@Parcelize
class VerifyOTPScreen(val platformNavigator: PlatformNavigator, val verificationPhone: String) : ParcelableScreen, KoinComponent {

    @Transient private val authenticationPresenter : AuthenticationPresenter by inject()
    @Transient private var mainViewModel: MainViewModel? = null
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    override val key: ScreenKey = uniqueScreenKey

    fun setMainViewModel(mainViewModel: MainViewModel) {
        this.mainViewModel = mainViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }


    @Composable
    override fun Content() {

        val verificationInProgress = remember { mutableStateOf(false) }
        val navigateToCompleteProfile = remember { mutableStateOf(false) }
        val navigateToConnectVendor = remember { mutableStateOf(false) }
        val navigateToPlatform = remember { mutableStateOf(false) }
        val authPhone = remember { mutableStateOf("") }
        val preferenceSettings = Settings()
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val testPhone = "555"

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()

        if (onBackPressed.value) {
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        val handler = AuthenticationScreenHandler(authenticationPresenter,
            onUserLocationReady = {},
            enterPlatform = { user ->
                val userCurrency = getDisplayCurrency(user.country)
                val displayCurrencyUnit = userCurrency.toDisplayUnit()
                val displayCurrencyPath = userCurrency.toPath()
                mainViewModel!!.setDisplayCurrencyUnit(displayCurrencyUnit)
                mainViewModel!!.setDisplayCurrencyPath(displayCurrencyPath)
                preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath()] = user.country
                preferenceSettings[SharedPreferenceEnum.STATE.toPath()] = user.state?.id
                preferenceSettings[SharedPreferenceEnum.USER_ID.toPath()] = user.userId
                preferenceSettings[SharedPreferenceEnum.VENDOR_ID.toPath()] = user.connectedVendorId
                preferenceSettings[SharedPreferenceEnum.AUTH_EMAIL.toPath()] = user.email
                preferenceSettings[SharedPreferenceEnum.API_KEY.toPath()] = user.apiKey
                preferenceSettings[SharedPreferenceEnum.AUTH_TYPE.toPath()] =
                    AuthType.PHONE.toPath()
                preferenceSettings[SharedPreferenceEnum.AUTH_PHONE.toPath()] = user.authPhone

                scope.launch {
                    val userDao = databaseBuilder!!.build().getUserDao()
                    val userCount = userDao.count()
                    val vendorCount = userDao.count()
                    if (userCount == 0 && vendorCount == 0) {
                        userDao.insert(user)
                    }
                }

                navigateToPlatform.value = true
            },
            completeProfile = { userEmail, userPhone ->
                authPhone.value = userPhone
                preferenceSettings[SharedPreferenceEnum.AUTH_TYPE.toPath()] =
                    AuthType.PHONE.toPath()
                navigateToCompleteProfile.value = true
            },
            connectVendor = { user ->
                preferenceSettings[SharedPreferenceEnum.AUTH_TYPE.toPath()] =
                    AuthType.PHONE.toPath()
                preferenceSettings[SharedPreferenceEnum.AUTH_PHONE.toPath()] = user.authPhone
                preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath()] = user.country
                preferenceSettings[SharedPreferenceEnum.STATE.toPath()] = user.state?.id
                preferenceSettings[SharedPreferenceEnum.API_KEY.toPath()] = user.apiKey
                preferenceSettings[SharedPreferenceEnum.USER_ID.toPath()] = user.userId

                scope.launch {
                    val userDao = databaseBuilder!!.build().getUserDao()
                    val userCount = userDao.count()
                    val vendorCount = userDao.count()
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
                verificationInProgress.value = false
            },
            onCompleteStarted = {},
            onCompleteEnded = {},
            connectVendorOnProfileCompleted = { _ -> },
            onUpdateStarted = {},
            onUpdateEnded = {},
            onVerificationError = {})
        handler.init()


        if (navigateToCompleteProfile.value) {
            val completeProfile = CompleteProfileScreen(
                platformNavigator,
                authPhone = authPhone.value,
                authEmail = "empty"
            )
            completeProfile.setMainViewModel(mainViewModel = mainViewModel!!)
            completeProfile.setDatabaseBuilder(databaseBuilder = databaseBuilder)
            navigator.replaceAll(completeProfile)
        } else if (navigateToConnectVendor.value) {
            val connectScreen = ConnectVendor(platformNavigator)
            connectScreen.setMainViewModel(mainViewModel!!)
            connectScreen.setDatabaseBuilder(databaseBuilder)
            navigator.replaceAll(connectScreen)
        } else if (navigateToPlatform.value) {
            navigateToPlatform.value = false
            val mainScreen = MainScreen(platformNavigator)
            mainScreen.setDatabaseBuilder(databaseBuilder)
            mainScreen.setMainViewModel(mainViewModel!!)
            navigator.replaceAll(mainScreen)
        }

        var otpValue by remember {
            mutableStateOf("")
        }

        val rootModifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(color = 0xFFFBFBFB))

        val buttonStyle = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .height(45.dp)


        val topLayoutModifier =
            Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(color = 0xFFFBFBFB))

        if (verificationInProgress.value) {
            Box(modifier = Modifier.fillMaxWidth(0.90f)) {
                LoadingDialog("Verifying Profile...")
            }
        }


        AppTheme {

            Scaffold(
                snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) }
            ) {
                Column(modifier = rootModifier) {
                    Column(modifier = topLayoutModifier) {
                        AuthenticationBackNav(mainViewModel!!)
                        EnterVerificationCodeTitle()
                        AttachVerificationCodeText(verificationPhone)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .padding(start = 10.dp, end = 10.dp, top = 30.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {

                            OTPTextField(
                                otpText = otpValue,
                                otpCount = 6,
                                onOTPTextChanged = { value, OTPInputField ->
                                    otpValue = value
                                }
                            )

                        }
                        ButtonComponent(
                            modifier = buttonStyle,
                            buttonText = "Verify",
                            borderStroke = null,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Colors.darkPrimary),
                            fontSize = 18,
                            shape = RoundedCornerShape(25.dp),
                            textColor = Color(color = 0xFFFFFFFF),
                            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                        ) {
                            if (otpValue.isEmpty() || otpValue.length < 6) {
                                ShowSnackBar(title = "Error",
                                    description = "Please Input OTP",
                                    actionLabel = "",
                                    duration = StackedSnackbarDuration.Long,
                                    snackBarType = SnackBarType.ERROR,
                                    stackedSnackBarHostState = stackedSnackBarHostState,
                                    onActionClick = {})
                            } else {
                                verificationInProgress.value = true

                                if (testPhone == verificationPhone) {

                                    authenticationPresenter.validatePhone(
                                        testPhone,
                                        requireValidation = true
                                    )

                                } else {
                                    platformNavigator.verifyOTP(
                                        otpValue,
                                        onVerificationSuccessful = {
                                            authenticationPresenter.validatePhone(
                                                it,
                                                requireValidation = true
                                            )
                                        },
                                        onVerificationFailed = {
                                            ShowSnackBar(title = "Error",
                                                description = "Error Occurred Please Try Again",
                                                actionLabel = "",
                                                duration = StackedSnackbarDuration.Long,
                                                snackBarType = SnackBarType.ERROR,
                                                stackedSnackBarHostState = stackedSnackBarHostState,
                                                onActionClick = {})
                                        })
                                }
                            }
                        }
                    }
                }
            }

        }
    }


    @Composable
    fun AttachVerificationCodeText(verificationPhone: String) {
        val rowModifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth()
        val verifyText = "Verification code has been sent to your\n Mobile Number"
        val handleText: String = verificationPhone
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = rowModifier
        ) {

            SubtitleTextWidget(text = verifyText, textAlign = TextAlign.Center, fontSize = 16)
            SubtitleTextWidget(text = handleText, textAlign = TextAlign.Center, fontSize = 16)
        }
    }


    @Composable
    fun EnterVerificationCodeTitle(){
        val rowModifier = Modifier
            .padding(bottom = 10.dp, top = 30.dp)
            .fillMaxWidth()
            .wrapContentHeight()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            TextComponent(text = "Enter Verification Code", textColor = Colors.darkPrimary,
                textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Normal,
                fontSize = 20, textAlign = TextAlign.Center)
        }
    }

}