package presentation.Orders

import domain.Models.OrderResourceListEnvelope
import UIStates.AppUIStates

interface OrderContract {
    interface View {
        fun showLce(appUIStates: AppUIStates, message: String = "")
        fun showAsyncLce(uiState: AppUIStates, message: String = "")
        fun showReviewsActionLce(uiState: AppUIStates, message: String = "")
        fun showUserOrders(orders: OrderResourceListEnvelope)
        fun onLoadMoreOrderStarted(isSuccess: Boolean = false)
        fun onLoadMoreOrderEnded(isSuccess: Boolean = false)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUserOrders(userId: Long)
        abstract fun getMoreUserOrders(userId: Long, nextPage: Int = 1)
        abstract fun deleteOrder(userId: Int)
        abstract fun addProductReviews(userId: Long, productId: Long, reviewText: String)
    }
}
