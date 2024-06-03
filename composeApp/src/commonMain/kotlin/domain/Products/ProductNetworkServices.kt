package domain.Products

import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.toSingle
import domain.Models.ListDataResponse
import domain.Models.Product
import domain.Models.ProductCategoryResponse
import domain.Models.ProductListDataResponse
import domain.Models.ServerResponse
import domain.Models.ServiceSpecialistsResponse
import domain.bookings.GetSpecialistsRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class ProductNetworkService(private val apiService: HttpClient) {

    suspend fun getAllProducts(getAllProductsRequest: GetAllProductsRequest, nextPage: Int = 1) =
        apiService.post {
            url("/api/v1/products/get?page=$nextPage")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getAllProductsRequest)
        }.body<ProductListDataResponse>().toSingle()

    suspend fun searchProduct(searchProductRequest: SearchProductRequest, nextPage: Int = 1) =
        apiService.post {
            url("/api/v1/products/search?page=$nextPage")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(searchProductRequest)
        }.body<ProductListDataResponse>().toSingle()

    suspend fun createOrder(createOrderRequest: CreateOrderRequest) =
        apiService.post {
            url("/api/v1/orders/create")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(createOrderRequest)
        }.body<ServerResponse>().toSingle()

}