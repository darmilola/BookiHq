package domain.Products

import com.badoo.reaktive.single.toSingle
import com.russhwolf.settings.Settings
import domain.Enums.SharedPreferenceEnum
import domain.Models.InitCheckoutResponse
import domain.Models.ProductListDataResponse
import domain.Models.ServerResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class ProductNetworkService(private val apiService: HttpClient) {

    val preferenceSettings = Settings()
    val apiKey = preferenceSettings.getString(SharedPreferenceEnum.API_KEY.toPath(), "")
    suspend fun getAllProducts(getAllProductsRequest: GetAllProductsRequest, nextPage: Int = 1) =
        apiService.post {
            url("/products/get?page=$nextPage")
            contentType(ContentType.Application.Json)
            setBody(getAllProductsRequest)
            header("Authorization", apiKey)
        }.body<ProductListDataResponse>().toSingle()

    suspend fun searchProduct(searchProductRequest: SearchProductRequest, nextPage: Int = 1) =
        apiService.post {
            url("/products/search?page=$nextPage")
            contentType(ContentType.Application.Json)
            setBody(searchProductRequest)
            header("Authorization", apiKey)
        }.body<ProductListDataResponse>().toSingle()

    suspend fun createOrder(createOrderRequest: CreateOrderRequest) =
        apiService.post {
            url("/orders/create")
            contentType(ContentType.Application.Json)
            setBody(createOrderRequest)
            header("Authorization", apiKey)
        }.body<ServerResponse>().toSingle()

    suspend fun getProductType(getProductTypeRequest: GetProductTypeRequest,nextPage: Int = 1) =
        apiService.post {
            url("/products?page=$nextPage")
            contentType(ContentType.Application.Json)
            setBody(getProductTypeRequest)
            header("Authorization", apiKey)
        }.body<ProductListDataResponse>().toSingle()

    suspend fun initCheckout(initCheckoutRequest: InitCheckoutRequest) =
        apiService.post {
            url("/orders/checkout/init")
            contentType(ContentType.Application.Json)
            setBody(initCheckoutRequest)
            header("Authorization", apiKey)
        }.body<InitCheckoutResponse>().toSingle()

}