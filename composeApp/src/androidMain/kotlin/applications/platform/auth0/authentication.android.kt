package applications.platform.auth0

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.application.zazzy.Authentication
import com.application.zazzy.R
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import org.jetbrains.compose.resources.resource


@Composable
actual fun StartAuth0() {
    val activity = LocalContext.current as Activity
    activity.startActivity(Intent(activity, Authentication::class.java))
}