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
import presentation.Orders.OrderModule
import presentation.Packages.PackageModule
import presentation.appointmentBookings.BookingModule
import presentation.Products.ProductModule
import presentation.connectVendor.ConnectVendorModule
import presentation.profile.ProfileModule
import presentation.appointments.AppointmentModule
import presentation.authentication.AuthenticationModule.AuthenticationModule
import presentation.home.HomepageModule
import presentation.payment.PaymentModule
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
        modules(PackageModule.PackageModule)
        modules(PaymentModule.PaymentModule)
        modules(AppointmentModule.AppointmentModule)
        modules(TherapistModule.TherapistModule)
        modules(OrderModule.OrderModule)
    }
}

private val KtorModule = module {

    single {
        httpClient {
            defaultRequest {
                url {
                    host = "/yesbeauty.onrender.com/api/v1"
                    protocol = URLProtocol.HTTPS
                    port = 443
                }
            }
            install(Logging) {
                level = LogLevel.BODY
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 45_000
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