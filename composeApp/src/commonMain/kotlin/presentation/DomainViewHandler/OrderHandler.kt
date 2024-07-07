package presentation.DomainViewHandler

import domain.Models.OrderResourceListEnvelope
import presentation.Orders.OrderContract
import presentation.Orders.OrderPresenter
import UIStates.ActionUIStates
import presentation.viewmodels.OrdersResourceListEnvelopeViewModel
import presentation.viewmodels.UIStateViewModel
import UIStates.ScreenUIStates

class OrderHandler(
    private val ordersResourceListEnvelopeViewModel: OrdersResourceListEnvelopeViewModel,
    private val uiStateViewModel: UIStateViewModel,
    private val orderPresenter: OrderPresenter
) : OrderContract.View {
    fun init() {
        orderPresenter.registerUIContract(this)
    }

    override fun showLce(uiState: ScreenUIStates, message: String) {
        ordersResourceListEnvelopeViewModel.clearData(mutableListOf())
        uiStateViewModel.switchScreenUIState(uiState)
    }

    override fun showAsyncLce(uiState: ActionUIStates, message: String) {
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