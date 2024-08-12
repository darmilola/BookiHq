package domain.bookings

import com.badoo.reaktive.single.toSingle
import domain.Models.PendingBookingAppointmentResponse
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

    suspend fun createPendingBookingAppointment(createPendingBookingAppointmentRequest: CreatePendingBookingAppointmentRequest) =
        apiService.post {
            url("/services/appointment/pending/booking/create")
            contentType(ContentType.Application.Json)
            setBody(createPendingBookingAppointmentRequest)
        }.body<PendingBookingAppointmentResponse>().toSingle()

    suspend fun getPendingBookingAppointment(getPendingBookingAppointmentRequest: GetPendingBookingAppointmentRequest) =
        apiService.post {
            url("/services/appointment/pending/booking/get")
            contentType(ContentType.Application.Json)
            setBody(getPendingBookingAppointmentRequest)
        }.body<PendingBookingAppointmentResponse>().toSingle()

    suspend fun deletePendingBookingAppointment(deletePendingBookingAppointmentRequest: DeletePendingBookingAppointmentRequest) =
        apiService.post {
            url("/services/appointment/pending/booking/delete")
            contentType(ContentType.Application.Json)
            setBody(deletePendingBookingAppointmentRequest)
        }.body<ServerResponse>().toSingle()

}