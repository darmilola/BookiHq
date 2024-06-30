package domain.home

import com.badoo.reaktive.single.toSingle
import domain.Models.HomePageResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType


open class HomeNetworkService(private val apiService: HttpClient) {
    suspend fun getHomePage(getHomeRequest: GetHomeRequest) =
        apiService.post {
            url("/home")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getHomeRequest)
        }.body<HomePageResponse>().toSingle()

}