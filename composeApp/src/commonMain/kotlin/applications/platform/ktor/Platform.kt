package applications.platform.ktor

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig


expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient