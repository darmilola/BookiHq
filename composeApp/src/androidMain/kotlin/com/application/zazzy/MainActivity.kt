package com.application.zazzy

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import applications.auth0.AndroidAuth0ConnectionResponse
import cafe.adriel.voyager.navigator.Navigator
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import domain.Enums.AuthSSOScreenNav
import domain.Enums.AuthenticationAction
import domain.Models.Auth0ConnectionResponse
import domain.Models.PlatformNavigator
import presentation.Splashscreen.SplashScreen
import presentation.authentication.AuthenticationScreen
import presentation.authentication.WelcomeScreen
import presentation.main.MainScreen
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity(), PlatformNavigator {
    private var authScreen: AuthenticationScreen? = null
    private val mainScreen = MainScreen(platformNavigator = this)
    private val RC_SIGN_IN = 1
    private var firebaseAuth: FirebaseAuth? = null
    private var mainActivityResultLauncher: ActivityResultLauncher<Intent>? = null
    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var storedVerificationId: String = ""
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

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
                        Navigator(WelcomeScreen(this, userEmail = it))
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
        authScreen?.setImageUploadProcessing(isDone = false)

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
            }).dispatch()
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

    override fun startFacebookSSO(onAuthSuccessful: (String) -> Unit, onAuthFailed: () -> Unit) {}
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
}