package domain.Orders

import com.badoo.reaktive.single.toSingle
import com.russhwolf.settings.Settings
import domain.Enums.SharedPreferenceEnum
import domain.Models.OrderListDataResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class OrderNetworkService(private val apiService: HttpClient) {

    val preferenceSettings = Settings()
    val apiKey = preferenceSettings.getString(SharedPreferenceEnum.API_KEY.toPath(), "")

    suspend fun getUserOrders(getOrderRequest: GetOrderRequest, nextPage: Int = 1) =
        apiService.post {
            url("/orders/get?page=$nextPage")
            contentType(ContentType.Application.Json)
            setBody(getOrderRequest)
            header("Authorization", apiKey)
        }.body<OrderListDataResponse>().toSingle()


}