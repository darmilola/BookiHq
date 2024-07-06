package domain.Products

import com.badoo.reaktive.single.Single
import domain.Models.ProductListDataResponse
import domain.Models.ServerResponse

interface ProductRepository {
    suspend fun getAllProducts(vendorId: Long, nextPage: Int = 1): Single<ProductListDataResponse>
    suspend fun searchProducts(vendorId: Long, searchQuery: String, nextPage: Int = 1): Single<ProductListDataResponse>
    suspend fun createOrder(vendorId: Long,userId: Long,orderReference: Int,deliveryMethod: String,paymentMethod: String,orderItems: ArrayList<OrderItemRequest>): Single<ServerResponse>
}
