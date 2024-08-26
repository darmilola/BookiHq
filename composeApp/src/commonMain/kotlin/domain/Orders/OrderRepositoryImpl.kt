package domain.Orders

import com.badoo.reaktive.single.Single
import domain.Models.OrderListDataResponse
import domain.Models.ServerResponse
import io.ktor.client.HttpClient


class OrderRepositoryImpl(apiService: HttpClient): OrderRepository {
    private val orderNetworkService: OrderNetworkService = OrderNetworkService(apiService)

    override suspend fun getUserOrders(userId: Long, nextPage: Int): Single<OrderListDataResponse> {
        val param = GetOrderRequest(userId)
        return orderNetworkService.getUserOrders(param, nextPage)
    }

    override suspend fun addProductReviews(
        userId: Long,
        productId: Long,
        reviewText: String
    ): Single<ServerResponse> {
        val param = AddProductReviewRequest(userId, productId, reviewText)
        return orderNetworkService.addProductReview(param)
    }


}