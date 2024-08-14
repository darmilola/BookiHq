package presentation.DomainViewHandler

import UIStates.AppUIStates
import domain.Models.VendorResourceListEnvelope
import presentation.connectVendor.ConnectVendorContract
import presentation.connectVendor.ConnectVendorPresenter
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.VendorsResourceListEnvelopeViewModel

class ConnectPageHandler(
    private val vendorResourceListEnvelopeViewModel: VendorsResourceListEnvelopeViewModel,
    private val loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel,
    private val actionUIStateViewModel: PerformedActionUIStateViewModel,
    private val connectVendorPresenter: ConnectVendorPresenter) : ConnectVendorContract.View {
    fun init() {
        connectVendorPresenter.registerUIContract(this)
    }

    override fun showScreenLce(appUIStates: AppUIStates) {
        loadingScreenUiStateViewModel.switchScreenUIState(appUIStates)
    }

    override fun showActionLce(appUIStates: AppUIStates, message: String) {
        actionUIStateViewModel.switchActionUIState(appUIStates)
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
            vendors.prevPageUrl?.let { vendorResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            vendors.nextPageUrl?.let { vendorResourceListEnvelopeViewModel.setNextPageUrl(it) }
            vendors.currentPage?.let { vendorResourceListEnvelopeViewModel.setCurrentPage(it) }
            vendors.totalItemCount?.let { vendorResourceListEnvelopeViewModel.setTotalItemCount(it) }
            vendors.displayedItemCount?.let { vendorResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
    }

    override fun onLoadMoreVendorStarted(isSuccess: Boolean) {
        vendorResourceListEnvelopeViewModel.setLoadingMore(true)
    }

    override fun onLoadMoreVendorEnded(isSuccess: Boolean) {
        vendorResourceListEnvelopeViewModel.setLoadingMore(false)
    }

}