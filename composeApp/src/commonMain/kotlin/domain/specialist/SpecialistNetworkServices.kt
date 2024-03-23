package domain.specialist

import com.badoo.reaktive.single.toSingle
import domain.Models.ServerResponse
import domain.Models.SpecialistReviews
import domain.Models.SpecialistReviewsResponse
import domain.Profile.DeleteProfileRequest
import domain.Profile.UpdateProfileRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class SpecialistNetworkService(private val apiService: HttpClient) {

    suspend fun getReviews(getReviewsRequest: GetReviewsRequest) =
        apiService.post {
            url("/api/v1/specialist/reviews/get")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getReviewsRequest)
        }.body<SpecialistReviewsResponse>().toSingle()

}