package presentation.Orders

import domain.Models.Appointment
import domain.Models.AppointmentResourceListEnvelope
import domain.Models.AvailableTime
import domain.Models.OrderResourceListEnvelope
import domain.Models.TimeOffs
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.UIStates

interface OrderContract {
    interface View {
        fun showLce(uiState: UIStates, message: String = "")
        fun showAsyncLce(uiState: AsyncUIStates, message: String = "")
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
