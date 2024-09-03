package presentation.DomainViewHandler

import UIStates.AppUIStates
import domain.Models.VendorResourceListEnvelope
import presentation.connectVendor.ConnectVendorContract
import presentation.connectVendor.ConnectVendorPresenter
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel

class VendorInfoPageHandler(
    private val loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel,
    private val connectVendorActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val connectVendorPresenter: ConnectVendorPresenter) : ConnectVendorContract.View {
    fun init() {
        connectVendorPresenter.registerUIContract(this)
    }

    override fun showScreenLce(appUIStates: AppUIStates) {
        loadingScreenUiStateViewModel.switchScreenUIState(appUIStates)
    }

    override fun showActionLce(appUIStates: AppUIStates, message: String) {
        connectVendorActionUIStateViewModel.switchActionUIState(appUIStates)
    }

    override fun showVendors(vendors: VendorResourceListEnvelope?, isFromSearch: Boolean, isLoadMore: Boolean) {}

    override fun onLoadMoreVendorStarted(isSuccess: Boolean) {}

    override fun onLoadMoreVendorEnded(isSuccess: Boolean) {}

}
