package domain.bookings

import com.badoo.reaktive.single.toSingle
import domain.Models.ServerResponse
import domain.Models.ServiceTherapistsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class BookingNetworkService(private val apiService: HttpClient) {

    suspend fun getTherapists(getTherapistsRequest: GetTherapistsRequest) =
        apiService.post {
            url("/services/therapists")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getTherapistsRequest)
        }.body<ServiceTherapistsResponse>().toSingle()

    suspend fun createAppointment(createAppointmentRequestArray: CreateAppointmentRequestArray) =
        apiService.post {
            url("/services/appointment/create")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(createAppointmentRequestArray)
        }.body<ServerResponse>().toSingle()

}