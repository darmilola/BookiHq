package presentation.DomainViewHandler

import domain.Models.OrderResourceListEnvelope
import presentation.Orders.OrderContract
import presentation.Orders.OrderPresenter
import UIStates.AppUIStates
import presentation.viewmodels.OrdersResourceListEnvelopeViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel

class OrderHandler(
    private val ordersResourceListEnvelopeViewModel: OrdersResourceListEnvelopeViewModel,
    private val loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel,
    private val addReviewPerformedActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val orderPresenter: OrderPresenter
) : OrderContract.View {
    fun init() {
        orderPresenter.registerUIContract(this)
    }

    override fun showLce(appUIStates: AppUIStates, message: String) {
        loadingScreenUiStateViewModel.switchScreenUIState(appUIStates)
    }

    override fun showAsyncLce(uiState: AppUIStates, message: String) {}

    override fun showReviewsActionLce(uiState: AppUIStates, message: String) {
        addReviewPerformedActionUIStateViewModel.switchAddProductReviewUiState(uiState)
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