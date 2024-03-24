package di

import applications.ktor.httpClient
import org.koin.core.context.startKoin
import org.koin.dsl.module
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.stopKoin
import presentation.bookings.BookingModule
import presentation.Products.ProductModule
import presentation.UserProfile.ConnectVendor.ConnectVendorModule
import presentation.UserProfile.ProfileModule
import presentation.appointments.AppointmentModule
import presentation.authentication.AuthenticationModule.AuthenticationModule
import presentation.main.home.HomepageModule
import presentation.therapist.TherapistModule

fun initKoin(){
    stopKoin()
    startKoin {
        modules(KtorModule)
        modules(AuthenticationModule)
        modules(ConnectVendorModule.ConnectVendorModule)
        modules(HomepageModule.HomepageModule)
        modules(ProfileModule.ProfileModule)
        modules(BookingModule.BookingModule)
        modules(ProductModule.ProductModule)
        modules(AppointmentModule.AppointmentModule)
        modules(TherapistModule.TherapistModule)
    }
}

private val KtorModule = module {
    single {
        httpClient {
            defaultRequest {
                host =  "devprocess-dd1566e93331.herokuapp.com"
                url {
                    protocol = URLProtocol.HTTPS
                }
            }
            install(Logging) {
                level = LogLevel.BODY
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }
}
