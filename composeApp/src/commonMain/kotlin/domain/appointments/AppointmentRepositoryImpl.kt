package domain.appointments

import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.toSingle
import domain.Models.AppointmentItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.headers

class AppointmentRepositoryImpl(apiService: HttpClient): AppointmentRepository {

    private val appointmentNetworkService: AppointmentNetworkService  = AppointmentNetworkService(apiService)
    override suspend fun get(id: Long): Single<AppointmentItem> {
        return appointmentNetworkService.getAppointments()
    }

    override suspend fun create() {
        TODO("Not yet implemented")
    }

    override suspend fun update(id: Long) {
        TODO("Not yet implemented")
    }

}

open class AppointmentNetworkService(private val apiService: HttpClient) {
     suspend fun getAppointments() =
            apiService.get {
                url("/api/v1/appointment")
                method = HttpMethod.Get
                headers {
                    append(HttpHeaders.Authorization, "abc123")
                }
                contentType(ContentType.Application.Json)
                // setBody(Customer(3, "Jet", "Brains"))
            }.body<AppointmentItem>().toSingle()

   }

