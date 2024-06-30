package presentation.Orders

import domain.Models.OrderResourceListEnvelope
import UIStates.ActionUIStates
import UIStates.ScreenUIStates

interface OrderContract {
    interface View {
        fun showLce(uiState: ScreenUIStates, message: String = "")
        fun showAsyncLce(uiState: ActionUIStates, message: String = "")
        fun showUserOrders(orders: OrderResourceListEnvelope)
        fun onLoadMoreOrderStarted(isSuccess: Boolean = false)
        fun onLoadMoreOrderEnded(isSuccess: Boolean = false)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUserOrders(userId: Int)
        abstract fun getMoreUserOrders(userId: Int, nextPage: Int = 1)
        abstract fun deleteOrder(userId: Int)
    }
}
