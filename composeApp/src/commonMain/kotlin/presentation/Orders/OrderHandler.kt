package presentation.Orders

import domain.Models.Appointment
import domain.Models.AppointmentResourceListEnvelope
import domain.Models.AvailableTime
import domain.Models.OrderResourceListEnvelope
import domain.Models.TimeOffs
import presentation.appointments.AppointmentContract
import presentation.appointments.AppointmentPresenter
import presentation.viewmodels.AppointmentResourceListEnvelopeViewModel
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.OrdersResourceListEnvelopeViewModel
import presentation.viewmodels.PostponementViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.viewmodels.UIStates

class OrderHandler(
    private val ordersResourceListEnvelopeViewModel: OrdersResourceListEnvelopeViewModel,
    private val uiStateViewModel: UIStateViewModel,
    private val orderPresenter: OrderPresenter) : OrderContract.View {
    fun init() {
        orderPresenter.registerUIContract(this)
    }

    override fun showLce(uiState: UIStates, message: String) {
        ordersResourceListEnvelopeViewModel.clearData(mutableListOf())
        uiStateViewModel.switchState(uiState)
    }

    override fun showAsyncLce(uiState: AsyncUIStates, message: String) {
        TODO("Not yet implemented")
    }

    override fun showUserOrders(orders: OrderResourceListEnvelope) {
        if (ordersResourceListEnvelopeViewModel.resources.value.isNotEmpty()) {
            val orderList = ordersResourceListEnvelopeViewModel.resources.value
            orderList.addAll(orders.resources!!)
            ordersResourceListEnvelopeViewModel.setResources(orderList)
            orders.prevPageUrl?.let { ordersResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            orders.nextPageUrl?.let { ordersResourceListEnvelopeViewModel.setNextPageUrl(it) }
            orders.currentPage?.let { ordersResourceListEnvelopeViewModel.setCurrentPage(it) }
            orders.totalItemCount?.let { ordersResourceListEnvelopeViewModel.setTotalItemCount(it) }
            orders.displayedItemCount?.let { ordersResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        } else {
            ordersResourceListEnvelopeViewModel.setResources(orders.resources)
            orders.prevPageUrl?.let { ordersResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            orders.nextPageUrl?.let { ordersResourceListEnvelopeViewModel.setNextPageUrl(it) }
            orders.currentPage?.let { ordersResourceListEnvelopeViewModel.setCurrentPage(it) }
            orders.totalItemCount?.let { ordersResourceListEnvelopeViewModel.setTotalItemCount(it) }
            orders.displayedItemCount?.let { ordersResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
    }
    override fun onLoadMoreOrderStarted(isSuccess: Boolean) {
        ordersResourceListEnvelopeViewModel.setLoadingMore(true)
    }

    override fun onLoadMoreOrderEnded(isSuccess: Boolean) {
        ordersResourceListEnvelopeViewModel.setLoadingMore(false)
    }

}