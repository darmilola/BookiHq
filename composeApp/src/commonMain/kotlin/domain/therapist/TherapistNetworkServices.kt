package domain.therapist

import com.badoo.reaktive.single.toSingle
import com.russhwolf.settings.Settings
import domain.Enums.SharedPreferenceEnum
import domain.Models.AppointmentListDataResponse
import domain.Models.ServerResponse
import domain.Models.TherapistAppointmentListDataResponse
import domain.Models.TherapistReviewsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class TherapistNetworkService(private val apiService: HttpClient) {
    val preferenceSettings = Settings()
    val apiKey = preferenceSettings.getString(SharedPreferenceEnum.API_KEY.toPath(), "")

    suspend fun getReviews(getReviewsRequest: GetReviewsRequest) =
        apiService.post {
            url("/therapist/reviews/get")
            contentType(ContentType.Application.Json)
            setBody(getReviewsRequest)
            header("Authorization", apiKey)
        }.body<TherapistReviewsResponse>().toSingle()


    suspend fun getTherapistAppointments(getTherapistAppointmentRequest: GetTherapistAppointmentRequest, nextPage: Int = 1) =
        apiService.post {
            url("/therapist/appointments/get?page=$nextPage")
            contentType(ContentType.Application.Json)
            setBody(getTherapistAppointmentRequest)
            header("Authorization", apiKey)
        }.body<TherapistAppointmentListDataResponse>().toSingle()

    suspend fun doneAppointment(doneAppointmentRequest: DoneAppointmentRequest) =
        apiService.post {
            url("/therapist/appointment/done")
            contentType(ContentType.Application.Json)
            setBody(doneAppointmentRequest)
            header("Authorization", apiKey)
        }.body<ServerResponse>().toSingle()

    suspend fun archiveAppointment(archiveAppointmentRequest: ArchiveAppointmentRequest) =
        apiService.post {
            url("/therapist/appointment/archive")
            contentType(ContentType.Application.Json)
            setBody(archiveAppointmentRequest)
            header("Authorization", apiKey)
        }.body<ServerResponse>().toSingle()

    suspend fun updateTherapistAvailability(updateTherapistAvailabilityRequest: UpdateTherapistAvailabilityRequest) =
        apiService.post {
            url("/therapist/availability/update")
            contentType(ContentType.Application.Json)
            setBody(updateTherapistAvailabilityRequest)
            header("Authorization", apiKey)
        }.body<ServerResponse>().toSingle()

}