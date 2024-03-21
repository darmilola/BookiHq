package domain.bookings

import com.badoo.reaktive.single.toSingle
import domain.Models.ServerResponse
import domain.Models.ServiceSpecialistsResponse
import domain.Models.SpecialistAvailabilityResponse
import domain.Profile.UpdateProfileRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class BookingNetworkService(private val apiService: HttpClient) {

    suspend fun getSpecialists(getSpecialistsRequest: GetSpecialistsRequest) =
        apiService.post {
            url("/api/v1/services/specialist")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getSpecialistsRequest)
        }.body<ServiceSpecialistsResponse>().toSingle()

    suspend fun createAppointment(createAppointmentRequestArray: CreateAppointmentRequestArray) =
        apiService.post {
            url("/api/v1/services/appointment/create")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(createAppointmentRequestArray)
        }.body<ServerResponse>().toSingle()

    suspend fun postponeAppointment(postponeAppointmentRequest: PostponeAppointmentRequest) =
        apiService.post {
            url("/api/v1/services/appointment/postpone")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(postponeAppointmentRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun deleteAppointment(deleteAppointmentRequest: DeleteAppointmentRequest) =
        apiService.post {
            url("/api/v1/services/appointment/delete")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(deleteAppointmentRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun getSpecialistAvailability(getSpecialistAvailabilityRequest: GetSpecialistAvailabilityRequest) =
        apiService.post {
            url("/api/v1/services/specialist/availability")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getSpecialistAvailabilityRequest)
        }.body<SpecialistAvailabilityResponse>().toSingle()

}