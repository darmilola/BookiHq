package domain.Orders

import com.badoo.reaktive.single.Single
import domain.Models.OrderListDataResponse
import domain.Models.ServerResponse

interface OrderRepository {
    suspend fun getUserOrders(userId: Long, nextPage: Int = 1): Single<OrderListDataResponse>
    suspend fun addProductReviews(userId: Long, productId: Long, reviewText: String): Single<ServerResponse>
}