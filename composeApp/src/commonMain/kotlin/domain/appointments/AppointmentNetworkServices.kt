package domain.appointments

import com.badoo.reaktive.single.toSingle
import domain.Models.Appointment
import domain.Models.ListDataResponse
import domain.Models.Product
import domain.Models.ServerResponse
import domain.Models.SpecialistAvailabilityResponse
import domain.Products.GetProductsInCategoryRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class AppointmentNetworkService(private val apiService: HttpClient) {

    suspend fun getAppointments(getAppointmentRequest: GetAppointmentRequest, nextPage: Int = 1) =
        apiService.post {
            url("/api/v1/appointments?page=$nextPage")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getAppointmentRequest)
        }.body<ListDataResponse<Appointment>>().toSingle()

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

    suspend fun getSpecialistAppointments(getSpecialistAppointmentRequest: GetSpecialistAppointmentRequest, nextPage: Int = 1) =
        apiService.post {
            url("/api/v1/specialist/appointments/get?page=$nextPage")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getSpecialistAppointmentRequest)
        }.body<ListDataResponse<Appointment>>().toSingle()

}