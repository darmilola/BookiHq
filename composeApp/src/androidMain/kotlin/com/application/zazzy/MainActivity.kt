package com.application.zazzy

import domain.Models.PlatformNavigator
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import domain.Models.Auth0ConnectionResponse
import domain.Models.AuthSSOScreen
import domain.Models.AuthenticationAction
import presentation.SplashScreen
import presentation.authentication.AuthenticationScreen
import applications.auth0.AndroidAuth0ConnectionResponse


class MainActivity : ComponentActivity(), PlatformNavigator {
    private var auth0ConnectionResponse: AndroidAuth0ConnectionResponse? = null
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
            val authScreen = AuthenticationScreen(currentPosition = AuthSSOScreen.AUTH_SIGNUP.toPath(), platformNavigator = this)
            val authResponse = Auth0ConnectionResponse(email = auth0ConnectionResponse!!.email!!,
                connectionType = auth0ConnectionResponse!!.connectionType.toString(),
                status = auth0ConnectionResponse!!.status!!, action = auth0ConnectionResponse!!.action!!)
            authScreen.setSignupAuthResponse(authResponse)
            setContent {
                Navigator(authScreen)
            }
        }

        else if (auth0ConnectionResponse!!.action.toString() == AuthenticationAction.LOGIN.toPath()){
            val authScreen = AuthenticationScreen(currentPosition = AuthSSOScreen.AUTH_LOGIN.toPath(), platformNavigator = this)
            val authResponse = Auth0ConnectionResponse(email = auth0ConnectionResponse!!.email!!,
                connectionType = auth0ConnectionResponse!!.connectionType.toString(),
                status = auth0ConnectionResponse!!.status!!, action = auth0ConnectionResponse!!.action!!)
            authScreen.setLoginAuthResponse(authResponse)
            setContent {
                Navigator(authScreen)
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
}