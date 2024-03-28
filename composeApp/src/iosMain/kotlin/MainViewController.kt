
import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.russhwolf.settings.Settings
import domain.Models.Auth0ConnectionResponse
import domain.Models.AuthSSOScreenNav
import domain.Models.AuthenticationAction
import domain.Models.AuthenticationStatus
import domain.Models.PlatformNavigator
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import objcnames.classes.Protocol
import platform.CoreLocation.CLLocation
import platform.Foundation.NSData
import platform.Foundation.create
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.Foundation.NSError
import platform.UIKit.UIViewController
import platform.darwin.NSObject
import platform.darwin.NSUInteger
import presentation.SplashScreen
import presentation.authentication.AuthenticationScreen
import presentation.main.MainScreen


class MainViewController: PlatformNavigator{

    private var onLoginEvent: ((connectionType: String) -> Unit)? = null
    private var onSignupEvent: ((connectionType: String) -> Unit)? = null
    private var onLogoutEvent: ((connectionType: String) -> Unit)? = null
    private var onLocationEvent: (() -> Unit)? = null
    private var onUploadImageEvent: ((data: NSData) -> Unit)? = null
    private val preferenceSettings: Settings = Settings()
    private val authScreen = AuthenticationScreen(currentPosition = AuthSSOScreenNav.AUTH_LOGIN.toPath(), platformNavigator = this)
    private val mainScreen = MainScreen(platformNavigator = this)
    fun MainViewController(onLoginEvent:(connectionType: String) -> Unit,
                           onLogoutEvent:(connectionType: String) -> Unit,
                           onSignupEvent: ((connectionType: String) -> Unit)?,
                           onUploadImageEvent: (data: NSData) -> Unit,
                           onLocationEvent: () -> Unit): UIViewController {

            val view = ComposeUIViewController {
                Navigator(SplashScreen(this)) { navigator ->
                    SlideTransition(navigator)

                 }
            }
            this.onLoginEvent = onLoginEvent
            this.onLogoutEvent = onLogoutEvent
            this.onSignupEvent = onSignupEvent
            this.onUploadImageEvent = onUploadImageEvent
            this.onLocationEvent = onLocationEvent
            return view
    }

    fun setAuthResponse(response: Auth0ConnectionResponse) {
        when (response.action) {
            AuthenticationAction.SIGNUP.toPath() -> {
                onSignupAuthResponse(response)
            }
            AuthenticationAction.LOGIN.toPath() -> {
                onLoginAuthResponse(response)
            }
            AuthenticationAction.LOGOUT.toPath() -> {
                onLogoutAuthResponse(response)
            }
        }
    }

    private fun onLoginAuthResponse(response: Auth0ConnectionResponse) {
        val status = response.status
        if (status == AuthenticationStatus.SUCCESS.toPath()) {
            authScreen.setLoginAuthResponse(response)
        }
    }

   fun onImageUploadResponse(imageUrl: String) {
        authScreen.setImageUploadResponse(imageUrl)
        mainScreen.setImageUploadResponse(imageUrl)
    }

    fun onImageUploadProcessing(isDone: Boolean) {
        authScreen.setImageUploadProcessing(isDone)
        mainScreen.setImageUploadProcessing(isDone)
    }


    private fun onLogoutAuthResponse(response: Auth0ConnectionResponse) {
        preferenceSettings.clear()
    }

    private fun onSignupAuthResponse(response: Auth0ConnectionResponse) {
        val status = response.status
        if (status == AuthenticationStatus.SUCCESS.toPath()) {
            authScreen.setSignupAuthResponse(response)
        }
    }

    override fun startAuth0Login(connectionType: String) {
        onLoginEvent?.let {
            it(connectionType)
        }
    }

    override fun startAuth0Signup(connectionType: String) {
        onSignupEvent?.let {
            it(connectionType)
        }
    }

    override fun startAuth0Logout(connectionType: String) {
        onLogoutEvent?.let {
            it(connectionType)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun startImageUpload(imageByteArray: ByteArray) {
        val data = imageByteArray.usePinned {
            NSData.create(
                bytes = it.addressOf(0),
                length = imageByteArray.size.toULong()
            )
        }
        onUploadImageEvent?.let {
            it(data)
        }
    }


    override fun getUserLocation() {
       onLocationEvent?.let {
           it()
       }
    }

   /* @OptIn(ExperimentalForeignApi::class)
    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        didUpdateLocations.firstOrNull()?.let {
            val location = it as CLLocation
            location.coordinate.useContents {
                onLocationUpdate?.invoke(Location(latitude.toInt(), longitude.toInt()))
            }
        }
    }

    override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
            onLocationUpdate?.invoke(null)
    }*/
}



