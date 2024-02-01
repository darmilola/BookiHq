package applications.platform.ktor

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*


actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp) {
    config(this)
    engine {
        config {
            followRedirects(true)
        }
        addInterceptor(RequestInterceptor())
        addNetworkInterceptor(RequestInterceptor())

    }
}