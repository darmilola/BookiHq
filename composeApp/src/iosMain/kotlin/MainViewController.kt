
import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.russhwolf.settings.Settings
import domain.Models.Auth0ConnectionResponse
import domain.Models.PlatformNavigator
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.create
import platform.UIKit.UIViewController
import presentation.Screens.SplashScreen
import presentation.Screens.MainScreen


class MainViewController: PlatformNavigator {

    private var onLoginEvent: ((connectionType: String) -> Unit)? = null
    private var onSignupEvent: ((connectionType: String) -> Unit)? = null
    private var onLogoutEvent: ((connectionType: String) -> Unit)? = null
    private var onLocationEvent: (() -> Unit)? = null
    private var onUploadImageEvent: ((data: NSData) -> Unit)? = null
    private val preferenceSettings: Settings = Settings()
    private val mainScreen = MainScreen(platformNavigator = this)
    fun MainViewController(onLoginEvent:(connectionType: String) -> Unit,
                           onLogoutEvent:(connectionType: String) -> Unit,
                           onSignupEvent: ((connectionType: String) -> Unit)?,
                           onUploadImageEvent: (data: NSData) -> Unit,
                           onLocationEvent: () -> Unit): UIViewController {

            val view = ComposeUIViewController(configure = {
                onFocusBehavior = OnFocusBehavior. DoNothing }) {
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



    private fun onLogoutAuthResponse(response: Auth0ConnectionResponse) {
        preferenceSettings.clear()
    }


    override fun startVideoCall(authToken: String) {
        TODO("Not yet implemented")
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

    override fun startGoogleSSO(onAuthSuccessful: (String) -> Unit, onAuthFailed: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun startPhoneSS0(phone: String) {
        TODO("Not yet implemented")
    }

    override fun verifyOTP(
        verificationCode: String,
        onVerificationSuccessful: (String) -> Unit,
        onVerificationFailed: () -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun startXSSO(onAuthSuccessful: (String) -> Unit, onAuthFailed: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun startScanningBarCode(onCodeReady: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun startImageUpload(onUploadDone: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun startNotificationService(onTokenReady: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun sendOrderBookingNotification(
        customerName: String,
        vendorLogoUrl: String,
        fcmToken: String
    ) {
        TODO("Not yet implemented")
    }

    override fun sendMeetingBookingNotification(
        customerName: String,
        vendorLogoUrl: String,
        businessName: String,
        meetingDay: String,
        meetingMonth: String,
        meetingYear: String,
        meetingTime: String,
        fcmToken: String
    ) {
        TODO("Not yet implemented")
    }

    override fun sendAppointmentBookingNotification(
        customerName: String,
        vendorLogoUrl: String,
        businessName: String,
        appointmentDay: String,
        appointmentMonth: String,
        appointmentYear: String,
        appointmentTime: String,
        serviceType: String,
        fcmToken: String
    ) {
        TODO("Not yet implemented")
    }

    override fun sendPostponedAppointmentNotification(
        customerName: String,
        vendorLogoUrl: String,
        businessName: String,
        appointmentDay: String,
        appointmentMonth: String,
        appointmentYear: String,
        appointmentTime: String,
        serviceType: String,
        fcmToken: String
    ) {
        TODO("Not yet implemented")
    }

    override fun sendConnectVendorNotification(
        customerName: String,
        vendorLogoUrl: String,
        fcmToken: String
    ) {
        TODO("Not yet implemented")
    }

    override fun sendCustomerExitNotification(
        exitReason: String,
        vendorLogoUrl: String,
        fcmToken: String
    ) {
        TODO("Not yet implemented")
    }
}



