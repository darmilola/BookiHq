package domain.bookings

import com.badoo.reaktive.single.toSingle
import domain.Models.PendingAppointmentResponse
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
            contentType(ContentType.Application.Json)
            setBody(getTherapistsRequest)
        }.body<ServiceTherapistsResponse>().toSingle()

    suspend fun createAppointment(createAppointmentRequest: CreateAppointmentRequest) =
        apiService.post {
            url("/services/appointment/create")
            contentType(ContentType.Application.Json)
            setBody(createAppointmentRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun createPendingAppointment(createPendingAppointmentRequest: CreatePendingAppointmentRequest) =
        apiService.post {
            url("/services/appointment/pending/create")
            contentType(ContentType.Application.Json)
            setBody(createPendingAppointmentRequest)
        }.body<PendingAppointmentResponse>().toSingle()

    suspend fun getPendingAppointment(getPendingAppointmentRequest: GetPendingAppointmentRequest) =
        apiService.post {
            url("/services/appointment/pending/get")
            contentType(ContentType.Application.Json)
            setBody(getPendingAppointmentRequest)
        }.body<PendingAppointmentResponse>().toSingle()

    suspend fun deletePendingAppointment(deletePendingAppointmentRequest: DeletePendingAppointmentRequest) =
        apiService.post {
            url("/services/appointment/pending/delete")
            contentType(ContentType.Application.Json)
            setBody(deletePendingAppointmentRequest)
        }.body<ServerResponse>().toSingle()

}