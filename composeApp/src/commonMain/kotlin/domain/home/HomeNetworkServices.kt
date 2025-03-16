package domain.home

import com.badoo.reaktive.single.toSingle
import com.russhwolf.settings.Settings
import domain.Enums.SharedPreferenceEnum
import domain.Models.HomePageResponse
import domain.Models.OrderListDataResponse
import domain.Models.RecommendationListDataResponse
import domain.Orders.GetOrderRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType


open class HomeNetworkService(private val apiService: HttpClient) {
    val preferenceSettings = Settings()
    val apiKey = preferenceSettings.getString(SharedPreferenceEnum.API_KEY.toPath(), "")
    suspend fun getHomePage(getHomeRequest: GetHomeRequest) =
        apiService.post {
            url("/home")
            contentType(ContentType.Application.Json)
            setBody(getHomeRequest)
            header("Authorization", apiKey)
        }.body<HomePageResponse>().toSingle()

    suspend fun getRecommendations(getRecommendationRequest: GetRecommendationRequest, nextPage: Int = 1) =
        apiService.post {
            url("/recommendations?page=$nextPage")
            contentType(ContentType.Application.Json)
            setBody(getRecommendationRequest)
            header("Authorization", apiKey)
        }.body<RecommendationListDataResponse>().toSingle()

}