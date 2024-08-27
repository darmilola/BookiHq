package domain.home

import com.badoo.reaktive.single.toSingle
import com.russhwolf.settings.Settings
import domain.Enums.SharedPreferenceEnum
import domain.Models.HomePageResponse
import domain.Models.HomePageWithStatusResponse
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

    suspend fun getHomePageWithStatus(getHomeRequestWithStatus: GetHomeRequestWithStatus) =
        apiService.post {
            url("/home/status")
            contentType(ContentType.Application.Json)
            setBody(getHomeRequestWithStatus)
            header("Authorization", apiKey)
        }.body<HomePageWithStatusResponse>().toSingle()

}