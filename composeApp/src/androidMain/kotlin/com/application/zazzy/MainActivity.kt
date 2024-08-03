package com.application.zazzy

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
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
import domain.Models.PlatformNavigator
import kotlinx.parcelize.Parcelize
import presentation.Screens.MainScreen
import presentation.Screens.SplashScreen
import presentation.Screens.WelcomeScreen
import presentation.viewmodels.MainViewModel
import java.io.IOException
import java.util.concurrent.TimeUnit


@Parcelize
class MainActivity : ComponentActivity(), PlatformNavigator, Parcelable {

    @Transient var firebaseAuth: FirebaseAuth? = null
    @Transient private var mainActivityResultLauncher: ActivityResultLauncher<Intent>? = null
    @Transient var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    @Transient private var storedVerificationId: String = ""
    @Transient private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    @Transient var imagePickerActivityResult: ActivityResultLauncher<Intent>? = null
    @Transient private var preferences: SharedPreferences? = null
    private var notificationServiceAccessToken: String? = ""
    @Transient private var mainViewModel: MainViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                mainViewModel!!.setOnBackPressed(true)
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)


          preferences = getSharedPreferences("TokenSharedPref", MODE_PRIVATE);
          firebaseAuth = FirebaseAuth.getInstance()

          setContent {
              if (mainViewModel == null) {
                  mainViewModel = kmpViewModel(
                      factory = viewModelFactory {
                          MainViewModel(savedStateHandle = createSavedStateHandle())
                      },
                  )
                  Navigator(SplashScreen(this,mainViewModel!!))
               }
               val isFinished = mainViewModel!!.exitApp.collectAsState()
               if (isFinished.value){
                  finish()
              }

              val goToMainScreen = mainViewModel!!.goToMainScreen.collectAsState()
              if (goToMainScreen.value) {
                  mainViewModel!!.setGoToMainScreen(false)
                  val mainScreen = MainScreen(this)
                  mainScreen.setMainViewModel(mainViewModel!!)
                  val navigator = LocalNavigator.currentOrThrow
                  navigator.replaceAll(mainScreen)
              }

              val restartApp = mainViewModel!!.restartApp.collectAsState()
              if (restartApp.value) {
                  mainViewModel!!.setRestartApp(false)
                  val navigator = LocalNavigator.currentOrThrow
                  navigator.replaceAll(SplashScreen(this, mainViewModel!!))
              }
            }


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {}
            override fun onVerificationFailed(e: FirebaseException) {}
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                storedVerificationId = verificationId
                resendToken = token
            }
        }


        mainActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!, onAuthSuccessful = {
                    setContent {
                        val welcomeScreen = WelcomeScreen(this, googleAuthEmail = it)
                        welcomeScreen.setMainViewModel(mainViewModel!!)
                        Navigator(welcomeScreen)
                    }
                }, onAuthFailed = {

                })
            } catch (e: ApiException) {}
        }

         imagePickerActivityResult =
            registerForActivityResult( ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.data?.data != null) {
                    val storageRef = Firebase.storage.reference;
                    val imageUri: Uri? = result.data?.data
                    val imageName = getFileName(applicationContext, imageUri!!)

                    val uploadTask = storageRef.child("file/$imageName").putFile(imageUri)
                    uploadTask.addOnSuccessListener {
                        storageRef.child("file/$imageName").downloadUrl.addOnSuccessListener {
                            val myEdit: SharedPreferences.Editor = preferences!!.edit()
                            myEdit.putString("imageUrl",it.toString())
                            myEdit.apply()

                        }.addOnFailureListener {
                            Log.e("Firebase", "Failed in downloading")
                        }
                    }.addOnFailureListener {
                        Log.e("Firebase", "Image Upload fail")
                    }
                }
            }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
            val myEdit: SharedPreferences.Editor = preferences!!.edit()
            myEdit.putString("fcmToken",token)
            myEdit.apply()
        })

        Thread {
           notificationServiceAccessToken =  getAccessToken()
            runOnUiThread {
                if (!notificationServiceAccessToken.isNullOrEmpty()){
                    val myEdit: SharedPreferences.Editor = preferences!!.edit()
                    myEdit.putString("accessToken",notificationServiceAccessToken)
                    myEdit.apply()
                }
            }
        }.start()
    }

    override fun startVideoCall(authToken: String) {
        val intent = Intent(this, MeetWithTherapistActivity::class.java)
        intent.putExtra("authToken", authToken)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun getUserLocation() {}

    override fun startGoogleSSO(onAuthSuccessful: (String) -> Unit, onAuthFailed: () -> Unit) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("114387254114-knn5thuj39m2hgnns9vnl8ic35f15nhp.apps.googleusercontent.com")
            .requestEmail()
            .requestProfile()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = mGoogleSignInClient.signInIntent

        mainActivityResultLauncher!!.launch(signInIntent)
    }

    override fun startPhoneSS0(phone: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth!!)
            .setPhoneNumber(phone)
            .setTimeout(30L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks!!)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun verifyOTP(verificationCode: String, onVerificationSuccessful: (String) -> Unit,
                           onVerificationFailed: () -> Unit) {
        val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, verificationCode)
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
                    onAuthSuccessful(it.user?.email!!)
                }
                .addOnFailureListener {
                    onAuthFailed()
                }
        } else {

            firebaseAuth!!
                .startActivityForSignInWithProvider(this, provider.build())
                .addOnSuccessListener {
                    onAuthSuccessful(it.user?.email!!)
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
        .enableAutoZoom() // available on 16.1.0 and higher
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
        preferences!!.registerOnSharedPreferenceChangeListener { sharedPreferences, s ->
          val imageUrl = sharedPreferences.getString("imageUrl","")
          onUploadDone(imageUrl!!)
        }
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
        mainViewModel!!.setExitApp(true)
    }

    override fun goToMainScreen() {
       mainViewModel!!.setGoToMainScreen(true)
    }

    override fun restartApp() {
        mainViewModel!!.setRestartApp(true)
    }


}