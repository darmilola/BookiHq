package domain.Products

import com.badoo.reaktive.single.toSingle
import domain.Models.ProductListDataResponse
import domain.Models.ServerResponse
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
            url("/products/get?page=$nextPage")
            contentType(ContentType.Application.Json)
            setBody(getAllProductsRequest)
        }.body<ProductListDataResponse>().toSingle()

    suspend fun searchProduct(searchProductRequest: SearchProductRequest, nextPage: Int = 1) =
        apiService.post {
            url("/products/search?page=$nextPage")
            contentType(ContentType.Application.Json)
            setBody(searchProductRequest)
        }.body<ProductListDataResponse>().toSingle()

    suspend fun createOrder(createOrderRequest: CreateOrderRequest) =
        apiService.post {
            url("/orders/create")
            contentType(ContentType.Application.Json)
            setBody(createOrderRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun getProductType(getProductTypeRequest: GetProductTypeRequest,nextPage: Int = 1) =
        apiService.post {
            url("/products?page=$nextPage")
            contentType(ContentType.Application.Json)
            setBody(getProductTypeRequest)
        }.body<ProductListDataResponse>().toSingle()

}