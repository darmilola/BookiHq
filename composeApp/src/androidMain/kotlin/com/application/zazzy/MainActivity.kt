package com.application.zazzy

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.OpenableColumns
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.core.app.ActivityCompat
import applications.room.getAppDatabase
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import co.paystack.android.Paystack.TransactionCallback
import co.paystack.android.PaystackSdk
import co.paystack.android.Transaction
import co.paystack.android.model.Card
import co.paystack.android.model.Charge
import com.application.zazzy.firebase.NotificationMessage
import com.application.zazzy.firebase.NotificationService
import com.application.zazzy.firebase.NotificationType
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.storage
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Enums.Currency
import domain.Models.PaymentCard
import domain.Models.PlatformNavigator
import kotlinx.parcelize.Parcelize
import presentation.Screens.SplashScreen
import presentation.viewmodels.MainViewModel
import java.io.IOException
import java.util.Locale
import java.util.concurrent.TimeUnit


@Parcelize
class MainActivity : ComponentActivity(), PlatformNavigator, Parcelable {

    @Transient var firebaseAuth: FirebaseAuth? = null
    @Transient private var mainActivityResultLauncher: ActivityResultLauncher<Intent>? = null
    @Transient private var requestPermissionLauncher: ActivityResultLauncher<String>? = null
    @Transient var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    @Transient private var storedVerificationId: String = ""
    @Transient private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    @Transient var imagePickerActivityResult: ActivityResultLauncher<Intent>? = null
    @Transient private var preferences: SharedPreferences? = null
    private var notificationServiceAccessToken: String? = ""
    @Transient private var mainViewModel: MainViewModel? = null
    @Transient private var gmailAuthPreferences: SharedPreferences.Editor? = null
    @Transient private var locationAuthPreferences: SharedPreferences.Editor? = null
    @Transient private var imageUploadPreferences: SharedPreferences.Editor? = null
    @Transient private var paymentPreferences: SharedPreferences.Editor? = null
    @Transient private var sharedPreferenceChangeListener: OnSharedPreferenceChangeListener? = null
    @Transient lateinit var locationManager: LocationManager
    @Transient var networkLocationListener: LocationListener? = null
    private var hasNetwork = false
    private val testPhone = "+16505554567"
    val smsCode = "654321"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PaystackSdk.initialize(getApplicationContext());
        val database = getAppDatabase(applicationContext)

        setContent {
            if (mainViewModel == null) {
                mainViewModel = kmpViewModel(
                    factory = viewModelFactory {
                        MainViewModel(savedStateHandle = createSavedStateHandle())
                    },
                )
            }
            val splashScreen = SplashScreen(platformNavigator = this)
            splashScreen.setDatabaseBuilder(database)
            splashScreen.setMainViewModel(mainViewModel!!)

            Navigator(splashScreen) { navigator ->
                SlideTransition(navigator)
            }
        }

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                mainViewModel!!.setOnBackPressed(true)
            }
        }

          onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
          preferences = getSharedPreferences("PlatformSharedPref", MODE_PRIVATE)
          gmailAuthPreferences = preferences!!.edit()
          imageUploadPreferences = preferences!!.edit()
          locationAuthPreferences = preferences!!.edit()
          paymentPreferences = preferences!!.edit()

        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseSettings = firebaseAuth!!.firebaseAuthSettings

           //for testing
           //firebaseSettings.setAppVerificationDisabledForTesting(true)
           //firebaseSettings.setAutoRetrievedSmsCodeForPhoneNumber(testPhone, smsCode)

        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        networkLocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {}
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                println("Completed")
            }
            override fun onVerificationFailed(e: FirebaseException) {
                println("Failed")
            }
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                storedVerificationId = verificationId
                resendToken = token
                println("Sent $storedVerificationId $resendToken")
            }
        }


        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return@registerForActivityResult
                }
                else {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        5000,
                        0F,
                        networkLocationListener!!
                    )
                    val lastKnownLocationByNetwork =
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                    lastKnownLocationByNetwork?.let {
                        locationAuthPreferences!!.putString("latitude",lastKnownLocationByNetwork.latitude.toString())
                        locationAuthPreferences!!.putString("longitude",lastKnownLocationByNetwork.longitude.toString())
                        locationAuthPreferences!!.apply()
                    }
                }
            }
        }

        mainActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result ->
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!, onAuthSuccessful = {
                    gmailAuthPreferences!!.putString("gmailAccount",it)
                    gmailAuthPreferences!!.apply()

                }, onAuthFailed = {})
            } catch (e: ApiException) {}
        }

         imagePickerActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.data?.data != null) {
                    val storageRef = Firebase.storage.reference;
                    val imageUri: Uri? = result.data?.data
                    val imageName = getFileName(applicationContext, imageUri!!)

                    val uploadTask = storageRef.child("file/$imageName").putFile(imageUri)
                    uploadTask.addOnSuccessListener {
                        storageRef.child("file/$imageName").downloadUrl.addOnSuccessListener {
                            imageUploadPreferences!!.putString("imageUrl",it.toString())
                            imageUploadPreferences!!.apply()

                        }.addOnFailureListener {}
                    }.addOnFailureListener {}
                }
            }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
            val tokenEdit: SharedPreferences.Editor = preferences!!.edit()
            tokenEdit.putString("fcmToken",token)
            tokenEdit.apply()
        })

        Thread {
           notificationServiceAccessToken =  getAccessToken()
            runOnUiThread {
                if (!notificationServiceAccessToken.isNullOrEmpty()){
                    val accessTokenPreferences: SharedPreferences.Editor = preferences!!.edit()
                    accessTokenPreferences.putString("accessToken",notificationServiceAccessToken)
                    accessTokenPreferences.apply()
                }
            }
        }.start()
    }
    override fun startGoogleSSO(onAuthSuccessful: (String) -> Unit, onAuthFailed: () -> Unit) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("114387254114-knn5thuj39m2hgnns9vnl8ic35f15nhp.apps.googleusercontent.com")
            .requestEmail()
            .requestProfile()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = mGoogleSignInClient.signInIntent
        mainActivityResultLauncher!!.launch(signInIntent)

        sharedPreferenceChangeListener = OnSharedPreferenceChangeListener { sharedPreferences, s ->
            val gmailAccount = sharedPreferences.getString("gmailAccount","")
            if (gmailAccount!!.isNotEmpty()) {
                gmailAuthPreferences!!.clear().apply()
                onAuthSuccessful(gmailAccount)
            }
        }
        preferences!!.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)
    }

    override fun startPhoneSS0(phone: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth!!)
            .setPhoneNumber(testPhone)
            .setTimeout(30L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks!!)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun verifyOTP(verificationCode: String, onVerificationSuccessful: (String) -> Unit,
                           onVerificationFailed: () -> Unit) {
        //sessionInfo + smsCode or temporary proof + phoneNumber
        println("My ID $storedVerificationId")
        println("MY CODE $verificationCode")
        val credential = PhoneAuthProvider.getCredential(storedVerificationId, verificationCode)
        signInWithPhoneAuthCredential(credential, onVerificationSuccessful = {
            onVerificationSuccessful(it)
        }, onVerificationFailed = {
            onVerificationFailed()
        })
    }

    override fun startXSSO(onAuthSuccessful: (String) -> Unit, onAuthFailed: () -> Unit) {
        val provider = OAuthProvider.newBuilder("twitter.com")
        val pendingResultTask = firebaseAuth!!.pendingAuthResult
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                .addOnSuccessListener {
                    if (it.user?.email != null){
                        onAuthSuccessful(it.user?.email!!)
                    }
                    else{
                        onAuthFailed()
                    }

                }
                .addOnFailureListener {
                    onAuthFailed()
                }
        } else {
            firebaseAuth!!
                .startActivityForSignInWithProvider(this, provider.build())
                .addOnSuccessListener {
                    if (it.user?.email != null){
                        onAuthSuccessful(it.user?.email!!)
                    }
                    else{
                        onAuthFailed()
                    }
                }
                .addOnFailureListener {
                    onAuthFailed()
                }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, onVerificationSuccessful: (String) -> Unit,
                                              onVerificationFailed: () -> Unit) {
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    onVerificationSuccessful(user?.phoneNumber!!)
                } else {
                     onVerificationFailed()
                }
            }
     }


    private fun firebaseAuthWithGoogle(idToken: String,onAuthSuccessful: (String) -> Unit, onAuthFailed: () -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth!!.currentUser
                    onAuthSuccessful(user?.email!!)
                } else {
                    onAuthFailed()
                }
            }
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun startScanningBarCode(onCodeReady: (String) -> Unit) {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC)
        .enableAutoZoom()
            .build()

        val scanner = GmsBarcodeScanning.getClient(this, options)

        scanner.startScan()
            .addOnSuccessListener { barcode ->
                val rawValue: String? = barcode.rawValue
                onCodeReady(rawValue.toString())
            }
            .addOnCanceledListener {
                // Task canceled
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
            }
    }

    override fun startImageUpload(onUploadDone: (String) -> Unit) {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        imagePickerActivityResult!!.launch(galleryIntent)

        sharedPreferenceChangeListener = OnSharedPreferenceChangeListener { sharedPreferences, s ->
            val imageUrl = sharedPreferences.getString("imageUrl","")
            if (imageUrl!!.isNotEmpty()) {
                imageUploadPreferences!!.clear().apply()
                onUploadDone(imageUrl)
            }
        }
        preferences!!.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)
    }



    @SuppressLint("Range")
    private fun getFileName(context: Context, uri: Uri): String? {
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (cursor != null) {
                    if(cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }
            }
        }
        return uri.path?.lastIndexOf('/')?.let { uri.path?.substring(it) }
    }

    override fun startNotificationService(onTokenReady: (String) -> Unit) {
        val fcmToken = preferences!!.getString("fcmToken","")
        onTokenReady(fcmToken!!)
    }

    @Throws(IOException::class)
    fun getAccessToken(): String? {
        val googleCredentials: GoogleCredentials = GoogleCredentials
            .fromStream(assets.open("cloud_message.json"))
            .createScoped("https://www.googleapis.com/auth/firebase.messaging")
        googleCredentials.refresh()
        return googleCredentials.accessToken.tokenValue
    }

    override fun sendOrderBookingNotification(
        customerName: String,
        vendorLogoUrl: String,
        fcmToken: String
    ) {
        val data = NotificationMessage.data(customerName = customerName, vendorLogoUrl = vendorLogoUrl,
            type = NotificationType.ORDER_BOOKING.toPath())
        NotificationService().sendAppNotification(fcmToken = fcmToken,
            accessToken = notificationServiceAccessToken!!, data = data)
    }

    override fun sendMeetingBookingNotification(
        customerName: String,
        vendorLogoUrl: String,
        meetingDay: String,
        meetingMonth: String,
        meetingYear: String,
        meetingTime: String,
        fcmToken: String
    ) {
        val data = NotificationMessage.data(customerName = customerName, vendorLogoUrl = vendorLogoUrl,
            type = NotificationType.MEETING_BOOKING.toPath(), meetingDay = meetingDay,
            meetingMonth = meetingMonth, meetingYear = meetingYear, meetingTime = meetingTime)
        NotificationService().sendAppNotification(fcmToken = fcmToken,
            accessToken = notificationServiceAccessToken!!, data = data)
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
        val data = NotificationMessage.data(customerName = customerName, vendorLogoUrl = vendorLogoUrl,
            type = NotificationType.APPOINTMENT_BOOKING.toPath(), businessName = businessName, appointmentDay = appointmentDay,
            appointmentMonth = appointmentMonth, appointmentYear = appointmentYear, appointmentTime = appointmentTime, serviceType = serviceType)
        NotificationService().sendAppNotification(fcmToken = fcmToken,
            accessToken = notificationServiceAccessToken!!, data = data)
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
        val data = NotificationMessage.data(customerName = customerName, vendorLogoUrl = vendorLogoUrl,
            type = NotificationType.APPOINTMENT_POSTPONED.toPath(), businessName = businessName, appointmentDay = appointmentDay,
            appointmentMonth = appointmentMonth, appointmentYear = appointmentYear, appointmentTime = appointmentTime, serviceType = serviceType)
        NotificationService().sendAppNotification(fcmToken = fcmToken,
            accessToken = notificationServiceAccessToken!!, data = data)
    }

    override fun sendConnectVendorNotification(
        customerName: String,
        vendorLogoUrl: String,
        fcmToken: String
    ) {
        val data = NotificationMessage.data(customerName = customerName, vendorLogoUrl = vendorLogoUrl,
            type = NotificationType.CONNECT_BUSINESS.toPath())
        NotificationService().sendAppNotification(fcmToken = fcmToken,
            accessToken = notificationServiceAccessToken!!, data = data)
    }

    override fun sendCustomerExitNotification(
        exitReason: String,
        vendorLogoUrl: String,
        fcmToken: String
    ) {
        val data = NotificationMessage.data(exitReason = exitReason, vendorLogoUrl = vendorLogoUrl,
            type = NotificationType.CONNECT_BUSINESS.toPath())
        NotificationService().sendAppNotification(fcmToken = fcmToken,
            accessToken = notificationServiceAccessToken!!, data = data)
    }

    override fun exitApp() {
        finish()
    }
    override fun restartApp() {
        // No Android Implementation
    }

    override fun goToMainScreen() {
        // No Android Implementation
    }

    override fun getUserLocation(onLocationReady: (String, String, String, String) -> Unit) {
        if (hasNetwork) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher!!.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            else {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    5000,
                    0F,
                    networkLocationListener!!
                )
                val lastKnownLocationByNetwork =
                    locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                val lat =  6.465422
                val long = 3.406448

                val locationInfo = getLocationInfo(this, lat, long)

                if (locationInfo != null) {
                    onLocationReady(
                        lastKnownLocationByNetwork!!.latitude.toString(),
                        lastKnownLocationByNetwork.longitude.toString(),
                        locationInfo.first,
                        locationInfo.second
                    )
                }
            }
         }
        sharedPreferenceChangeListener = OnSharedPreferenceChangeListener { sharedPreferences, s ->
            //val latitude = sharedPreferences.getString("latitude","")
            //val longitude = sharedPreferences.getString("longitude","")
            val latitude =  6.465422.toString()
            val longitude = 3.406448.toString()
            if (latitude.isNotEmpty() && longitude.isNotEmpty()) {
               // locationAuthPreferences!!.clear().apply()
                val locationInfo = getLocationInfo(this, latitude.toDouble(), longitude.toDouble())

                if (locationInfo != null) {
                    onLocationReady(latitude,longitude, locationInfo.first,
                        locationInfo.second)
                }
            }
        }
        preferences!!.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)
    }


    private fun getLocationInfo(context: Context?, latitude: Double, longitude: Double): Pair<String,String>? {
        val geocoder = Geocoder(context!!, Locale.getDefault())
        var addresses: List<Address>? = null
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1)
            return if (!addresses.isNullOrEmpty()) {
               Pair(addresses[0].countryName, addresses[0].adminArea)
            } else null
        } catch (ignored: IOException) {
            //do something
        }
        return null
    }

    override fun startPaymentProcess(
        paymentAmount: String,
        accessCode: String,
        currency: String,
        paymentCard: PaymentCard,
        customerEmail: String,
        onPaymentLoading: () -> Unit,
        onPaymentSuccessful: () -> Unit,
        onPaymentFailed: () -> Unit
    ) {
        val card = Card(paymentCard.cardNumber, paymentCard.expiryMonth.toInt(), paymentCard.expiryYear.toInt(), paymentCard.cvv)
        if (card.isValid) {
            val charge = Charge()
            charge.setCard(card)
            charge.setAccessCode(accessCode)
            charge.setEmail(customerEmail)
            charge.setAmount(paymentAmount.toInt())

            PaystackSdk.chargeCard(this@MainActivity, charge, object : TransactionCallback {
                override fun onSuccess(transaction: Transaction?) {
                    onPaymentSuccessful()
                }

                override fun beforeValidate(transaction: Transaction?) {}

                fun showLoading(isProcessing: Boolean?) {}

                override fun onError(error: Throwable?, transaction: Transaction?) {
                    onPaymentFailed()
                }
            })
        } else {
            onPaymentFailed()
        }
    }

}