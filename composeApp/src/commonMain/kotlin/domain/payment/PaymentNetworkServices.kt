package domain.payment

import com.badoo.reaktive.single.toSingle
import com.russhwolf.settings.Settings
import domain.Enums.SharedPreferenceEnum
import domain.Models.InitCheckoutResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class PaymentNetworkService(private val apiService: HttpClient) {

    val preferenceSettings = Settings()
    val apiKey = preferenceSettings.getString(SharedPreferenceEnum.API_KEY.toPath(), "")

    suspend fun initCheckout(initCheckoutRequest: InitCheckoutRequest) =
        apiService.post {
            url("/checkout/init")
            contentType(ContentType.Application.Json)
            setBody(initCheckoutRequest)
            header("Authorization", apiKey)
        }.body<InitCheckoutResponse>().toSingle()
}