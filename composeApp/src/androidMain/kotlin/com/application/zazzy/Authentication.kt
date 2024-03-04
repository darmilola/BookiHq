package com.application.zazzy

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials

class Authentication : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val twitterAuth = loginWithTwitter()

        if (twitterAuth){
            finish()
            onDestroy()
        }
        else{
            finish()
            onDestroy()
        }
    }
    private fun loginWithTwitter(): Boolean {
        val account = Auth0("msZpuVAzJvHN6HI7ZLLjHp4e8HjTwe1H", "dev-6s0tarpbfr017qxp.us.auth0.com")
        var isSuccess: Boolean = false
        // Setup the WebAuthProvider, using the custom scheme and scope.
        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid profile email")
            // Launch the authentication passing the callback where the results will be received
            .start(this, object : Callback<Credentials, AuthenticationException> {
                // Called when there is an authentication failure
                override fun onFailure(exception: AuthenticationException) {
                    isSuccess = false
                }
                // Called when authentication completed successfully
                override fun onSuccess(credentials: Credentials) {
                    // Get the access token from the credentials object.
                    // This can be used to call APIs
                    val accessToken = credentials.accessToken
                    isSuccess = true

                }
            })
        return isSuccess
    }
}
