package domain.Products

import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.toSingle
import domain.Models.ListDataResponse
import domain.Models.Product
import domain.Models.ProductCategoryResponse
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

    suspend fun getProductCategories(getProductCategoryRequest: GetProductCategoryRequest) =
        apiService.post {
            url("/api/v1/products/category/get")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getProductCategoryRequest)
        }.body<ProductCategoryResponse>().toSingle()


    suspend fun getProductInCategory(getProductsInCategoryRequest: GetProductsInCategoryRequest, nextPage: Int = 1) =
        apiService.post {
            url("/api/v1/products/category/items/get?page=$nextPage")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getProductsInCategoryRequest)
        }.body<ListDataResponse<Product>>().toSingle()

    suspend fun searchProduct(searchProductRequest: SearchProductRequest, nextPage: Int = 1) =
        apiService.post {
            url("/api/v1/products/search?page=$nextPage")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(searchProductRequest)
        }.body<ListDataResponse<Product>>().toSingle()

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