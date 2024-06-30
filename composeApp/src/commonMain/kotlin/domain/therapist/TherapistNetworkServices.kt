package domain.therapist

import com.badoo.reaktive.single.toSingle
import domain.Models.AppointmentListDataResponse
import domain.Models.TherapistReviewsResponse
import domain.appointments.GetTherapistAppointmentRequest
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
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getReviewsRequest)
        }.body<TherapistReviewsResponse>().toSingle()


    suspend fun getTherapistAppointments(getTherapistAppointmentRequest: GetTherapistAppointmentRequest, nextPage: Int = 1) =
        apiService.post {
            url("/therapist/appointments/get?page=$nextPage")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getTherapistAppointmentRequest)
        }.body<AppointmentListDataResponse>().toSingle()

}