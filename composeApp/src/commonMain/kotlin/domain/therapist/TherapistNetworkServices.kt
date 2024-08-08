package domain.therapist

import com.badoo.reaktive.single.toSingle
import domain.Models.AppointmentListDataResponse
import domain.Models.ServerResponse
import domain.Models.TherapistAppointmentListDataResponse
import domain.Models.TherapistReviewsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class TherapistNetworkService(private val apiService: HttpClient) {

    suspend fun getReviews(getReviewsRequest: GetReviewsRequest) =
        apiService.post {
            url("/therapist/reviews/get")
            contentType(ContentType.Application.Json)
            setBody(getReviewsRequest)
        }.body<TherapistReviewsResponse>().toSingle()


    suspend fun getTherapistAppointments(getTherapistAppointmentRequest: GetTherapistAppointmentRequest, nextPage: Int = 1) =
        apiService.post {
            url("/therapist/appointments/get?page=$nextPage")
            contentType(ContentType.Application.Json)
            setBody(getTherapistAppointmentRequest)
        }.body<TherapistAppointmentListDataResponse>().toSingle()

    suspend fun doneAppointment(doneAppointmentRequest: DoneAppointmentRequest) =
        apiService.post {
            url("/therapist/appointment/done")
            contentType(ContentType.Application.Json)
            setBody(doneAppointmentRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun archiveAppointment(archiveAppointmentRequest: ArchiveAppointmentRequest) =
        apiService.post {
            url("/therapist/appointment/archive")
            contentType(ContentType.Application.Json)
            setBody(archiveAppointmentRequest)
        }.body<ServerResponse>().toSingle()

}