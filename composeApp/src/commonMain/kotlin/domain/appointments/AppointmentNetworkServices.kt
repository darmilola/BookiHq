package domain.appointments

import com.badoo.reaktive.single.toSingle
import domain.Models.AppointmentListDataResponse
import domain.Models.JoinMeetingResponse
import domain.Models.ServerResponse
import domain.Models.TherapistAvailabilityResponse
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
            url("/appointments?page=$nextPage")
            contentType(ContentType.Application.Json)
            setBody(getAppointmentRequest)
        }.body<AppointmentListDataResponse>().toSingle()

    suspend fun postponeAppointment(postponeAppointmentRequest: PostponeAppointmentRequest) =
        apiService.post {
            url("/services/appointment/postpone")
            contentType(ContentType.Application.Json)
            setBody(postponeAppointmentRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun deleteAppointment(deleteAppointmentRequest: DeleteAppointmentRequest) =
        apiService.post {
            url("/services/appointment/delete")
            contentType(ContentType.Application.Json)
            setBody(deleteAppointmentRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun joinMeeting(joinMeetingRequest: JoinMeetingRequest) =
        apiService.post {
            url("/appointment/meeting/join")
            contentType(ContentType.Application.Json)
            setBody(joinMeetingRequest)
        }.body<JoinMeetingResponse>().toSingle()

    suspend fun getTherapistAvailability(getTherapistAvailabilityRequest: GetTherapistAvailabilityRequest) =
        apiService.post {
            url("/services/therapist/availability")
            contentType(ContentType.Application.Json)
            setBody(getTherapistAvailabilityRequest)
        }.body<TherapistAvailabilityResponse>().toSingle()

}