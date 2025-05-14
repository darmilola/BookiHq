package presentation.Screens

import domain.Models.PlatformNavigator
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import di.initKoin
import domain.Enums.AuthType
import domain.Enums.SharedPreferenceEnum
import domain.Enums.getDisplayCurrency
import presentation.components.SplashScreenBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.AuthenticationScreenHandler
import presentation.Screens.welcomeScreen.LandingScreen
import presentation.authentication.AuthenticationPresenter
import presentation.connectVendor.ConnectVendor
import presentation.viewmodels.MainViewModel
import presentation.widgets.SplashScreenWidget

@Composable
fun SplashScreenCompose(platformNavigator: PlatformNavigator, authenticationPresenter: AuthenticationPresenter, mainViewModel: MainViewModel, databaseBuilder: RoomDatabase.Builder<AppDatabase>) {

    val preferenceSettings: Settings = Settings()
    val authType = remember { mutableStateOf(AuthType.EMAIL.toPath()) }
    val authEmail = remember { mutableStateOf("") }
    val authPhone = remember { mutableStateOf("") }
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()

    val handler = AuthenticationScreenHandler(authenticationPresenter,
        onUserLocationReady = {},
        enterPlatform = { user ->
            runBlocking {
                val userCurrency = getDisplayCurrency(user.country)
                val displayCurrencyUnit = userCurrency.toDisplayUnit()
                val displayCurrencyPath = userCurrency.toPath()
                mainViewModel.setUserInfo(user)
                mainViewModel.setConnectedVendor(user.vendorInfo!!)
                mainViewModel.setDisplayCurrencyUnit(displayCurrencyUnit)
                mainViewModel.setDisplayCurrencyPath(displayCurrencyPath)
                preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath()] = user.country
                preferenceSettings[SharedPreferenceEnum.STATE.toPath()] = user.state?.id
                preferenceSettings[SharedPreferenceEnum.USER_ID.toPath()] = user.userId
                preferenceSettings[SharedPreferenceEnum.VENDOR_ID.toPath()] = user.connectedVendorId
                preferenceSettings[SharedPreferenceEnum.API_KEY.toPath()] = user.apiKey
                scope.launch {
                    val userDao = databaseBuilder.build().getUserDao()
                    val userCount = userDao.count()
                    val vendorCount = userDao.count()
                    if (userCount == 0 && vendorCount == 0) {
                        userDao.insert(user)
                    }
                }
                val mainScreen = MainScreen(platformNavigator)
                mainScreen.setDatabaseBuilder(databaseBuilder)
                mainScreen.setMainViewModel(mainViewModel)
                navigator.replaceAll(mainScreen)
            }
        },
        completeProfile = { _,_ -> },
        connectVendor = { user ->
            runBlocking {
                preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath()] = user.country
                preferenceSettings[SharedPreferenceEnum.STATE.toPath()] = user.state?.id
                preferenceSettings[SharedPreferenceEnum.USER_ID.toPath()] = user.userId
                preferenceSettings[SharedPreferenceEnum.VENDOR_ID.toPath()] = user.connectedVendorId
                preferenceSettings[SharedPreferenceEnum.API_KEY.toPath()] = user.apiKey
                mainViewModel.setUserInfo(user)
                scope.launch {
                    val userDao = databaseBuilder.build().getUserDao()
                    val userCount = userDao.count()
                    val vendorCount = userDao.count()
                    if (userCount == 0 && vendorCount == 0) {
                        userDao.insert(user)
                    }
                }
                val connectVendor = ConnectVendor(platformNavigator)
                connectVendor.setMainViewModel(mainViewModel)
                connectVendor.setDatabaseBuilder(databaseBuilder)
                navigator.replaceAll(connectVendor)
            }
        },
        onVerificationStarted = {},
        onVerificationEnded = {}, onCompleteStarted = {}, onCompleteEnded = {},
        connectVendorOnProfileCompleted = { _ ->}, onUpdateStarted = {}, onUpdateEnded = {}, onVerificationError = {
            if (authType.value == AuthType.EMAIL.toPath()) {
                authenticationPresenter.validateEmail(authEmail.value)
            }
            else if (authType.value == AuthType.PHONE.toPath()){
                authenticationPresenter.validatePhone(authPhone.value)
            }
        })
    handler.init()

   val  modifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White)
        // AnimationEffect
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            SplashScreenBackground()
            SplashScreenWidget(textStyle = TextStyle())
            Box(modifier = Modifier
                .padding(bottom = 70.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter) {
            }
        }
        LaunchedEffect(key1 = true) {
            delay(3000L)
            authenticateSplashScreen(preferenceSettings,
             onPhoneAuthentication = {
                  authType.value = AuthType.PHONE.toPath()
                  authPhone.value = it
                  authenticationPresenter.validatePhone(it, requireValidation = false)
             },
             onEmailAuthentication = {
                   authEmail.value = it
                   authType.value = AuthType.EMAIL.toPath()
                   authenticationPresenter.validateEmail(it)
             }, onWelcome = {
                    val welcomeScreen = WelcomeScreen(platformNavigator)
                    welcomeScreen.setDatabaseBuilder(databaseBuilder)
                    welcomeScreen.setMainViewModel(mainViewModel)
                    navigator.replaceAll(LandingScreen())

             })
        }
    }

 private fun authenticateSplashScreen(settings: Settings, onPhoneAuthentication: (String) -> Unit, onEmailAuthentication :(String) -> Unit, onWelcome :() -> Unit){
     val authEmail = settings.getString(SharedPreferenceEnum.AUTH_EMAIL.toPath(), "")
     val authPhone = settings.getString(SharedPreferenceEnum.AUTH_PHONE.toPath(), "")

     if (authEmail.trim().isNotEmpty()){
         onEmailAuthentication(authEmail)
     }
     else if (authPhone.trim().isNotEmpty()){
         onPhoneAuthentication(authPhone)
     }
     else{
         onWelcome()
     }
 }

@Parcelize
class SplashScreen(val platformNavigator: PlatformNavigator) : Screen, KoinComponent, Parcelable {

    @Transient private val  authenticationPresenter: AuthenticationPresenter by inject()
    @Transient private var mainViewModel: MainViewModel? = null
    @Transient private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    override val key: ScreenKey = uniqueScreenKey

    fun setDatabaseBuilder(builder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = builder
    }

    fun setMainViewModel(mainViewModel: MainViewModel? = null){
        this.mainViewModel = mainViewModel
    }

    @Composable
    override fun Content() {

        if (mainViewModel == null) {
            mainViewModel = kmpViewModel(
                factory = viewModelFactory {
                    MainViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        initKoin()
        SplashScreenCompose(platformNavigator = platformNavigator, authenticationPresenter,mainViewModel!!,databaseBuilder!!)
    }
}

