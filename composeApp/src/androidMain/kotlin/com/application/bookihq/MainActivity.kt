package com.application.bookihq

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.OpenableColumns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import applications.room.getAppDatabase
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import co.paystack.android.Paystack.TransactionCallback
import co.paystack.android.PaystackSdk
import co.paystack.android.Transaction
import co.paystack.android.model.Card
import co.paystack.android.model.Charge
import com.application.bookihq.firebase.FirebaseTopic
import com.application.bookihq.firebase.NotificationMessage
import com.application.bookihq.firebase.NotificationService
import com.application.bookihq.firebase.NotificationType
import com.application.bookihq.viewmodels.MainViewModel
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
import domain.Models.PlatformNavigator
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import presentation.Screens.SplashScreen
import java.io.IOException
import java.util.Calendar
import java.util.concurrent.TimeUnit


@Parcelize
class MainActivity : ComponentActivity(), PlatformNavigator, Parcelable {

    @Transient
    var firebaseAuth: FirebaseAuth? = null
    @Transient
    private var mainActivityResultLauncher: ActivityResultLauncher<Intent>? = null
    @Transient
    var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    @Transient
    private var storedVerificationId: String = ""
    @Transient
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    @Transient
    var imagePickerActivityResult: ActivityResultLauncher<Intent>? = null
    private var notificationServiceAccessToken: String? = ""
    @Transient
    lateinit var locationManager: LocationManager
    private val mainViewModel: MainViewModel by viewModels()

    private val pushNotificationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()
    ) { granted -> }

    private val cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()
    ) { granted -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PaystackSdk.initialize(applicationContext);
        val database = getAppDatabase(applicationContext)



        setContent {
            val splashScreen = SplashScreen(platformNavigator = this)
            splashScreen.setDatabaseBuilder(database)
            Navigator(splashScreen) { navigator ->
                SlideTransition(navigator)
            }
        }

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        firebaseAuth = FirebaseAuth.getInstance()
        FirebaseMessaging.getInstance().subscribeToTopic(FirebaseTopic.CUSTOMER_TOPIC.toPath())
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    println("Failed")
                }
                else{
                    println("Successful")
                }
            }


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                println("Open Completed")
            }
            override fun onVerificationFailed(e: FirebaseException) {
                println("Open Failed ${e.message}")
            }
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                println("Open Code Sent $verificationId $token")
                storedVerificationId = verificationId
                resendToken = token
            }
        }


        mainActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result ->
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                println(task)
                val account = task.getResult(ApiException::class.java)
                println("My Account $account")
                firebaseAuthWithGoogle(account.idToken!!, onAuthSuccessful = {
                    mainViewModel.setGoogleAuth(it)

                }, onAuthFailed = {})
            } catch (e: ApiException) {
                println("Exception ${e.status} ${e.message}")
            }
        }

         imagePickerActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.data?.data != null) {
                    val storageRef = Firebase.storage.reference;
                    val imageUri: Uri? = result.data?.data
                    val imageName = getFileName(applicationContext, imageUri!!)

                    val uploadTask = storageRef.child("images/$imageName").putFile(imageUri)
                    uploadTask.addOnSuccessListener {
                        storageRef.child("images/$imageName").downloadUrl.addOnSuccessListener {
                            mainViewModel.setImageUrl(it.toString())
                        }.addOnFailureListener {
                            println("Error 1 ${it.message}")
                        }
                    }.addOnFailureListener {
                        println("Error 2 ${it.message}")
                    }
                }
            }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
            mainViewModel.setFcmToken(token)
        })

        Thread {
           notificationServiceAccessToken =  getAccessToken()
        }.start()
    }
    override fun startGoogleSSO(onAuthSuccessful: (String) -> Unit, onAuthFailed: () -> Unit) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("180139598970-n3t73sb3qcq08hc8timdm3849k6tggvu.apps.googleusercontent.com")
            .requestEmail()
            .requestProfile()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = mGoogleSignInClient.signInIntent
        mainActivityResultLauncher!!.launch(signInIntent)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.googleAuthUiState.collect {
                    if (it.trim().isNotEmpty()) {
                        print(it)
                        onAuthSuccessful(it)
                    }
                }
            }
        }

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
        if (storedVerificationId.isNotEmpty()) {
            val credential = PhoneAuthProvider.getCredential(storedVerificationId, verificationCode)
            signInWithPhoneAuthCredential(credential, onVerificationSuccessful = {
                onVerificationSuccessful(it)
            }, onVerificationFailed = {
                onVerificationFailed()
            })
        }
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
                        println("Open ${pendingResultTask.exception!!.message}")
                        onAuthFailed()
                    }

                }
                .addOnFailureListener {
                    println("Open 2 ${pendingResultTask.exception!!.message}")
                    onAuthFailed()
                }
        } else {
            firebaseAuth!!
                .startActivityForSignInWithProvider(this, provider.build())
                .addOnSuccessListener {
                    println("Open 3 ${it}")
                    if (it.user?.email != null){
                        onAuthSuccessful(it.user?.email!!)
                    }
                    else{
                        onAuthFailed()
                    }
                }
                .addOnFailureListener {
                    println("Open 4 ${it.message}")
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
                print(task.toString())
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.imageUrlUiState.collect {
                    if (it.trim().isNotEmpty()) {
                        onUploadDone(it)
                    }
                }
            }
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.fcmTokenUiState.collect {
                    if (it.trim().isNotEmpty()) {
                        onTokenReady(it)
                    }
                }
            }
        }
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

    override fun sendAppointmentBookingNotification(
        vendorLogoUrl: String,
        fcmToken: String
    ) {
        val data = NotificationMessage.data(vendorLogoUrl = vendorLogoUrl, type = NotificationType.APPOINTMENT_BOOKING.toPath())
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
    override fun exitApp() {
        finish()
    }
    override fun restartApp() {
        // No Android Implementation
    }

    override fun goToMainScreen() {
        // No Android Implementation
    }

    override fun startPaymentProcess(
        paymentAmount: String,
        accessCode: String,
        currency: String,
        cardNumber: String,
        expiryMonth: String,
        expiryYear: String,
        cvv: String,
        customerEmail: String,
        onPaymentLoading: () -> Unit,
        onPaymentSuccessful: () -> Unit,
        onPaymentFailed: () -> Unit
    ) {
        val card = Card(cardNumber, expiryMonth.toInt(), expiryYear.toInt(), cvv)
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

                override fun beforeValidate(transaction: Transaction?) {
                    onPaymentLoading()
                }

                fun showLoading(isProcessing: Boolean?) {
                    onPaymentLoading()
                }

                override fun onError(error: Throwable?, transaction: Transaction?) {
                    onPaymentFailed()
                }
            })
        } else {
            onPaymentFailed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun requestNotificationPermission() {
        pushNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    override fun requestCameraPermission() {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    override fun getHourOfDay(): Int {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        return hour
    }

    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

}