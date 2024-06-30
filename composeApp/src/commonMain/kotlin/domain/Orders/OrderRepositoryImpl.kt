package domain.Orders

import com.badoo.reaktive.single.Single
import domain.Models.OrderListDataResponse
import io.ktor.client.HttpClient


class OrderRepositoryImpl(apiService: HttpClient): OrderRepository {
    private val orderNetworkService: OrderNetworkService = OrderNetworkService(apiService)

    override suspend fun getUserOrders(userId: Int, nextPage: Int): Single<OrderListDataResponse> {
        val param = GetOrderRequest(userId)
        return orderNetworkService.getUserOrders(param, nextPage)
    }


}