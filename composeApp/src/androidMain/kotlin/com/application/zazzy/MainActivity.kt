package com.application.zazzy

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import cafe.adriel.voyager.navigator.Navigator
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import domain.Models.PlatformNavigator
import presentation.Splashscreen.SplashScreen
import presentation.authentication.WelcomeScreen
import presentation.main.MainScreen
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity(), PlatformNavigator {

    @Transient var firebaseAuth: FirebaseAuth? = null
    @Transient private var mainActivityResultLauncher: ActivityResultLauncher<Intent>? = null
    @Transient var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    @Transient private var storedVerificationId: String = ""
    @Transient private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

          firebaseAuth = FirebaseAuth.getInstance()
          setContent {
                Navigator(SplashScreen(this))
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
                        Navigator(WelcomeScreen(this, googleAuthEmail = it))
                    }
                }, onAuthFailed = {

                })
            } catch (e: ApiException) {}
        }

    }

    override fun startVideoCall(authToken: String) {
        val intent = Intent(this, MeetWithTherapistActivity::class.java)
        intent.putExtra("authToken", authToken)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun startImageUpload(imageByteArray: ByteArray) {
      /*  authScreen?.setImageUploadProcessing(isDone = false)

        try {
            MediaManager.get()
        } catch (e: IllegalStateException) {
            MediaManager.init(this)
        }

        val requestId = MediaManager.get().upload(imageByteArray)
            .unsigned("UserUploads")
            .callback(object : UploadCallback {
                override fun onStart(requestId: String) {}
                override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {}
                override fun onSuccess(requestId: String, resultData: Map<*, *>?) {
                    authScreen?.setImageUploadProcessing(isDone = true)
                    mainScreen.setImageUploadProcessing(isDone = true)
                    authScreen?.setImageUploadResponse(resultData?.get("secure_url") as String)
                    mainScreen.setImageUploadResponse(resultData?.get("secure_url") as String)
                }
                override fun onError(requestId: String?, error: ErrorInfo?) {
                    authScreen?.setImageUploadProcessing(isDone = true)
                    mainScreen.setImageUploadProcessing(isDone = true)
                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                    authScreen?.setImageUploadProcessing(isDone = true)
                    mainScreen?.setImageUploadProcessing(isDone = true)
                }
            }).dispatch()*/
    }




    override fun getUserLocation() {}

    override fun startGoogleSSO(onAuthSuccessful: (String) -> Unit, onAuthFailed: () -> Unit) {
        // web client id
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
            .setPhoneNumber(phone) // Phone number to verify
            .setTimeout(30L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks!!) // OnVerificationStateChangedCallbacks
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

    override fun writeToParcel(p0: Parcel, p1: Int) {}
    override fun startScanningBarCode(onCodeReady: (String) -> Unit) {
        println("You called me")
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

}