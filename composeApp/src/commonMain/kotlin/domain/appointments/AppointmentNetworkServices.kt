package domain.appointments

import com.badoo.reaktive.single.toSingle
import com.russhwolf.settings.Settings
import domain.Enums.SharedPreferenceEnum
import domain.Models.AppointmentListDataResponse
import domain.Models.JoinMeetingResponse
import domain.Models.ServerResponse
import domain.Models.TherapistAvailabilityResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class AppointmentNetworkService(private val apiService: HttpClient) {
    val preferenceSettings = Settings()
    val apiKey = preferenceSettings.getString(SharedPreferenceEnum.API_KEY.toPath(), "")
    suspend fun getAppointments(getAppointmentRequest: GetAppointmentRequest, nextPage: Int = 1) =
        apiService.post {
            url("/appointments?page=$nextPage")
            contentType(ContentType.Application.Json)
            setBody(getAppointmentRequest)
            header("Authorization", apiKey)
        }.body<AppointmentListDataResponse>().toSingle()

    suspend fun postponeAppointment(postponeAppointmentRequest: PostponeAppointmentRequest) =
        apiService.post {
            url("/services/appointment/postpone")
            contentType(ContentType.Application.Json)
            setBody(postponeAppointmentRequest)
            header("Authorization", apiKey)
        }.body<ServerResponse>().toSingle()

    suspend fun deleteAppointment(deleteAppointmentRequest: DeleteAppointmentRequest) =
        apiService.post {
            url("services/appointment/delete")
            contentType(ContentType.Application.Json)
            setBody(deleteAppointmentRequest)
            header("Authorization", apiKey)
        }.body<ServerResponse>().toSingle()

    suspend fun joinMeeting(joinMeetingRequest: JoinMeetingRequest) =
        apiService.post {
            url("/appointment/meeting/join")
            contentType(ContentType.Application.Json)
            setBody(joinMeetingRequest)
            header("Authorization", apiKey)
        }.body<JoinMeetingResponse>().toSingle()

    suspend fun addAppointmentReview(addAppointmentReviewRequest: AddAppointmentReviewRequest) =
        apiService.post {
            url("/services/appointment/review/add")
            contentType(ContentType.Application.Json)
            setBody(addAppointmentReviewRequest)
            header("Authorization", apiKey)
        }.body<ServerResponse>().toSingle()

    suspend fun addPackageAppointmentReview(addPackageAppointmentReviewRequest: AddPackageAppointmentReviewRequest) =
        apiService.post {
            url("/services/appointment/package/review/add")
            contentType(ContentType.Application.Json)
            setBody(addPackageAppointmentReviewRequest)
            header("Authorization", apiKey)
        }.body<ServerResponse>().toSingle()

    suspend fun getTherapistAvailability(getTherapistAvailabilityRequest: GetTherapistAvailabilityRequest) =
        apiService.post {
            url("/services/therapist/availability")
            contentType(ContentType.Application.Json)
            setBody(getTherapistAvailabilityRequest)
            header("Authorization", apiKey)
        }.body<TherapistAvailabilityResponse>().toSingle()

}