package com.application.zazzy

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import applications.auth0.AndroidAuth0ConnectionResponse
import cafe.adriel.voyager.navigator.Navigator
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import domain.Models.Auth0ConnectionResponse
import domain.Models.AuthSSOScreenNav
import domain.Models.AuthenticationAction
import domain.Models.PlatformNavigator
import presentation.SplashScreen
import presentation.authentication.AuthenticationScreen


class MainActivity : ComponentActivity(), PlatformNavigator {
    private var auth0ConnectionResponse: AndroidAuth0ConnectionResponse? = null
    private var authScreen: AuthenticationScreen? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth0ConnectionResponse = intent.getParcelableExtra("authResponse")
        enableEdgeToEdge()
        if(auth0ConnectionResponse == null){
            setContent {
                Navigator(SplashScreen(this))
            }
        }

        else if (auth0ConnectionResponse!!.action.toString() == AuthenticationAction.SIGNUP.toPath()) {
            val authResponse = Auth0ConnectionResponse(email = auth0ConnectionResponse!!.email!!,
                connectionType = auth0ConnectionResponse!!.connectionType.toString(),
                status = auth0ConnectionResponse!!.status!!, action = auth0ConnectionResponse!!.action!!)
             authScreen = AuthenticationScreen(currentPosition = AuthSSOScreenNav.AUTH_SIGNUP.toPath(), platformNavigator = this, authResponse)
            setContent {
                Navigator(authScreen!!)
            }

        }

        else if (auth0ConnectionResponse!!.action.toString() == AuthenticationAction.LOGIN.toPath()) {
            val authResponse = Auth0ConnectionResponse(email = auth0ConnectionResponse!!.email!!,
                connectionType = auth0ConnectionResponse!!.connectionType.toString(),
                status = auth0ConnectionResponse!!.status!!, action = auth0ConnectionResponse!!.action!!)
             authScreen = AuthenticationScreen(currentPosition = AuthSSOScreenNav.AUTH_LOGIN.toPath(), platformNavigator = this, authResponse)
            setContent {
                Navigator(authScreen!!)
            }
        }
        else{
            setContent {
                Navigator(SplashScreen(this))
            }
        }
    }
    override fun startAuth0Login(connectionType: String) {
        val intent = Intent(this, Authentication::class.java)
        intent.putExtra("authAction",AuthenticationAction.LOGIN.toPath())
        intent.putExtra("connectionType",connectionType)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
    override fun startAuth0Signup(connectionType: String) {
        val intent = Intent(this, Authentication::class.java)
        intent.putExtra("authAction",AuthenticationAction.SIGNUP.toPath())
        intent.putExtra("connectionType",connectionType)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
    override fun startAuth0Logout(connectionType: String) {
        val intent = Intent(this, Authentication::class.java)
        intent.putExtra("authAction",AuthenticationAction.LOGOUT.toPath())
        intent.putExtra("connectionType",connectionType)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun startImageUpload(imageByteArray: ByteArray) {
        authScreen?.setImageUploadProcessing(isDone = true)

        try {
             MediaManager.get()
        } catch (e: IllegalStateException) {
            MediaManager.init(this)
        }

        val requestId = MediaManager.get().upload(imageByteArray)
            .unsigned("UserUploads")
            .callback(object : UploadCallback {
            override fun onStart(requestId: String) {

            }
          override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {

            }
           override fun onSuccess(requestId: String, resultData: Map<*, *>?) {
                authScreen?.setImageUploadProcessing(isDone = false)
                authScreen?.setImageUploadResponse(resultData?.get("secure_url") as String)
            }

            override fun onError(requestId: String?, error: ErrorInfo?) {
                authScreen?.setImageUploadProcessing(isDone = false)
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                authScreen?.setImageUploadProcessing(isDone = false)
            }
        }).dispatch()


    }

    }