package di

import applications.platform.ktor.httpClient
import org.koin.core.context.startKoin
import org.koin.dsl.module
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


fun initKoin() = startKoin {
    modules(KtorModule)
}

private val KtorModule = module {
    single {
        httpClient {
            defaultRequest {
                url.takeFrom(URLBuilder().takeFrom("/baseurl/is_here"))
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }
}