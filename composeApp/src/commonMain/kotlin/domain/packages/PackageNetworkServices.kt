package domain.packages

import com.badoo.reaktive.single.toSingle
import com.russhwolf.settings.Settings
import domain.Enums.SharedPreferenceEnum
import domain.Models.OrderListDataResponse
import domain.Models.ServerResponse
import domain.Models.TimeAvailabilityResponse
import domain.Models.VendorPackageListDataResponse
import domain.Orders.AddProductReviewRequest
import domain.Orders.GetOrderRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class PackageNetworkService(private val apiService: HttpClient) {

    val preferenceSettings = Settings()
    val apiKey = preferenceSettings.getString(SharedPreferenceEnum.API_KEY.toPath(), "")

    suspend fun getVendorPackages(getVendorPackageRequest: GetVendorPackageRequest, nextPage: Int = 1) =
        apiService.post {
            url("/packages?page=$nextPage")
            contentType(ContentType.Application.Json)
            setBody(getVendorPackageRequest)
            header("Authorization", apiKey)
        }.body<VendorPackageListDataResponse>().toSingle()

    suspend fun getTimeAvailability(timeAvailabilityRequest: TimeAvailabilityRequest) =
        apiService.post {
            url("/services/time")
            contentType(ContentType.Application.Json)
            setBody(timeAvailabilityRequest)
            header("Authorization", apiKey)
        }.body<TimeAvailabilityResponse>().toSingle()

}