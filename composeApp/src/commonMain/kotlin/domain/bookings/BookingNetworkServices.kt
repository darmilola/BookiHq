package domain.bookings

import com.badoo.reaktive.single.toSingle
import com.russhwolf.settings.Settings
import domain.Enums.SharedPreferenceEnum
import domain.Models.PendingBookingAppointmentResponse
import domain.Models.ServerResponse
import domain.Models.ServiceTherapistsResponse
import domain.Models.TimeAvailabilityResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class BookingNetworkService(private val apiService: HttpClient) {
    val preferenceSettings = Settings()
    val apiKey = preferenceSettings.getString(SharedPreferenceEnum.API_KEY.toPath(), "")
    suspend fun getTherapists(getTherapistsRequest: GetTherapistsRequest) =
        apiService.post {
            url("/services/therapists")
            contentType(ContentType.Application.Json)
            setBody(getTherapistsRequest)
            header("Authorization", apiKey)
        }.body<ServiceTherapistsResponse>().toSingle()

    suspend fun createAppointment(createAppointmentRequest: CreateAppointmentRequest) =
        apiService.post {
            url("/services/appointment/create")
            contentType(ContentType.Application.Json)
            setBody(createAppointmentRequest)
            header("Authorization", apiKey)
        }.body<ServerResponse>().toSingle()

    suspend fun createPendingBookingAppointment(createPendingBookingAppointmentRequest: CreatePendingBookingAppointmentRequest) =
        apiService.post {
            url("/services/appointment/pending/booking/create")
            contentType(ContentType.Application.Json)
            header("Authorization", apiKey)
            setBody(createPendingBookingAppointmentRequest)
        }.body<PendingBookingAppointmentResponse>().toSingle()

    suspend fun getPendingBookingAppointment(getPendingBookingAppointmentRequest: GetPendingBookingAppointmentRequest) =
        apiService.post {
            url("/services/appointment/pending/booking/get")
            contentType(ContentType.Application.Json)
            setBody(getPendingBookingAppointmentRequest)
            header("Authorization", apiKey)
        }.body<PendingBookingAppointmentResponse>().toSingle()

    suspend fun deletePendingBookingAppointment(deletePendingBookingAppointmentRequest: DeletePendingBookingAppointmentRequest) =
        apiService.post {
            url("/services/appointment/pending/booking/delete")
            contentType(ContentType.Application.Json)
            setBody(deletePendingBookingAppointmentRequest)
            header("Authorization", apiKey)
        }.body<ServerResponse>().toSingle()

}