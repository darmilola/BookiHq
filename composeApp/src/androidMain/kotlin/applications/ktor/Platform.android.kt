package applications.ktor

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp) {
    config(this)
    engine {
        config {
            followRedirects(false)
        }
    }
}