package presentation.DomainViewHandler

import UIStates.ScreenUIStates
import domain.Models.VendorResourceListEnvelope
import presentation.connectVendor.ConnectVendorContract
import presentation.connectVendor.ConnectVendorPresenter
import presentation.viewmodels.UIStateViewModel
import presentation.viewmodels.VendorsResourceListEnvelopeViewModel

class ConnectPageHandler(
    private val vendorResourceListEnvelopeViewModel: VendorsResourceListEnvelopeViewModel,
    private val uiStateViewModel: UIStateViewModel,
    private val connectVendorPresenter: ConnectVendorPresenter,
    private val onPageLoading: () -> Unit,
    private val onContentVisible: () -> Unit,
    private val onConnected: (userId: Long) -> Unit,
    private val onErrorVisible: () -> Unit
) : ConnectVendorContract.View {
    fun init() {
        connectVendorPresenter.registerUIContract(this)
    }

    override fun showLce(uiState: ScreenUIStates) {
        uiStateViewModel.switchScreenUIState(uiState)
        uiState.let {
            when{
                it.loadingVisible -> {
                    onPageLoading()
                }

                it.contentVisible -> {
                    onContentVisible()
                }

                it.errorOccurred -> {
                    onErrorVisible()
                }
            }
        }
    }

    override fun onVendorConnected(userId: Long) {
        onConnected(userId)
    }

    override fun showVendors(vendors: VendorResourceListEnvelope?, isFromSearch: Boolean) {
        if (vendorResourceListEnvelopeViewModel.resources.value.isNotEmpty()) {
            val vendorList = vendorResourceListEnvelopeViewModel.resources.value
            vendorList.addAll(vendors?.resources!!)
            vendorResourceListEnvelopeViewModel.setResources(vendorList)
            vendors.prevPageUrl?.let { vendorResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            vendors.nextPageUrl?.let { vendorResourceListEnvelopeViewModel.setNextPageUrl(it) }
            vendors.currentPage?.let { vendorResourceListEnvelopeViewModel.setCurrentPage(it) }
            vendors.totalItemCount?.let { vendorResourceListEnvelopeViewModel.setTotalItemCount(it) }
            vendors.displayedItemCount?.let { vendorResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        } else {
            vendorResourceListEnvelopeViewModel.setResources(vendors?.resources!!.toMutableList())
            vendors?.prevPageUrl?.let { vendorResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            vendors?.nextPageUrl?.let { vendorResourceListEnvelopeViewModel.setNextPageUrl(it) }
            vendors?.currentPage?.let { vendorResourceListEnvelopeViewModel.setCurrentPage(it) }
            vendors?.totalItemCount?.let { vendorResourceListEnvelopeViewModel.setTotalItemCount(it) }
            vendors?.displayedItemCount?.let { vendorResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
    }

    override fun onLoadMoreVendorStarted(isSuccess: Boolean) {
        vendorResourceListEnvelopeViewModel.setLoadingMore(true)
    }

    override fun onLoadMoreVendorEnded(isSuccess: Boolean) {
        vendorResourceListEnvelopeViewModel.setLoadingMore(false)
    }

}