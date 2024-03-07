package com.application.zazzy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.russhwolf.settings.Settings
import domain.Models.AuthenticationAction
import domain.Models.AuthenticationStatus
import applications.auth0.AndroidAuth0ConnectionResponse


class Authentication : AppCompatActivity() {

    private var connectionType: String? = ""
    private var authAction: String? = ""
    private val preferenceSettings: Settings = Settings()
    private val account = Auth0("msZpuVAzJvHN6HI7ZLLjHp4e8HjTwe1H", "dev-6s0tarpbfr017qxp.us.auth0.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionType = intent.getStringExtra("connectionType")
        authAction = intent.getStringExtra("authAction")

        if (authAction == AuthenticationAction.SIGNUP.toPath()){
             startSSOSignup(connectionType.toString())
        }
        else if (authAction == AuthenticationAction.LOGIN.toPath()){
            startSSOLogin(connectionType.toString())
        }
    }

    private fun startSSOLogin(connectionType: String) {
        WebAuthProvider.login(account)
            .withConnection(connectionType)
            .withScheme("demo")
            .withRedirectUri("dev-6s0tarpbfr017qxp.us.auth0.com/android/com.application.zazzy.Zazzy/callback")
            .start(this, object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    val intent = Intent(this@Authentication, MainActivity::class.java)
                    val response = AndroidAuth0ConnectionResponse(connectionType = connectionType, email = "", action = authAction!!, status = AuthenticationStatus.FAILURE.toPath())
                    intent.putExtra("authResponse", response)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
                override fun onSuccess(result: Credentials) {
                    val accessToken = result.accessToken
                    val client = AuthenticationAPIClient(auth0 = account)
                    client.userInfo(accessToken)
                        .start(object : Callback<UserProfile, AuthenticationException> {
                            override fun onFailure(error: AuthenticationException) {
                                val intent = Intent(this@Authentication, MainActivity::class.java)
                                val response = AndroidAuth0ConnectionResponse(connectionType = connectionType, email = "", action = authAction!!, status = AuthenticationStatus.FAILURE.toPath())
                                intent.putExtra("authResponse", response)
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                            }
                            override fun onSuccess(result: UserProfile) {
                                println(result)
                            }
                        })
                }
            })
       }

    private fun startSSOSignup(connectionType: String) {
        WebAuthProvider.login(account)
            .withConnection(connectionType)
            .withScheme("demo")
            .withScope("openid profile email")
            .withRedirectUri("dev-6s0tarpbfr017qxp.us.auth0.com/android/com.application.zazzy.Zazzy/callback")
            .start(this, object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    val intent = Intent(this@Authentication, MainActivity::class.java)
                    val response = AndroidAuth0ConnectionResponse(connectionType = connectionType, email = "", action = authAction!!, status = AuthenticationStatus.FAILURE.toPath())
                    intent.putExtra("authResponse", response)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
                override fun onSuccess(result: Credentials) {
                    val accessToken = result.accessToken
                    val client = AuthenticationAPIClient(auth0 = account)
                    client.userInfo(accessToken)
                        .start(object : Callback<UserProfile, AuthenticationException> {
                            override fun onFailure(error: AuthenticationException) {
                                val intent = Intent(this@Authentication, MainActivity::class.java)
                                val response = AndroidAuth0ConnectionResponse(connectionType = connectionType, email = "", action = authAction!!, status = AuthenticationStatus.FAILURE.toPath())
                                intent.putExtra("authResponse", response)
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                            }
                            override fun onSuccess(result: UserProfile) {
                                println(result)
                            }
                        })
                }
            })
    }
    private fun logout() {
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .withReturnToUrl("demo://dev-6s0tarpbfr017qxp.us.auth0.com/android/com.application.zazzy.Zazzy/callback")
            .start(this, object: Callback<Void?, AuthenticationException> {
                override fun onSuccess(result: Void?) {
                    preferenceSettings.clear()
                }
                override fun onFailure(error: AuthenticationException) {}})
    }

}
