package domain.Orders

import com.badoo.reaktive.single.Single
import domain.Models.OrderListDataResponse

interface OrderRepository {
    suspend fun getUserOrders(userId: Long, nextPage: Int = 1): Single<OrderListDataResponse>
}