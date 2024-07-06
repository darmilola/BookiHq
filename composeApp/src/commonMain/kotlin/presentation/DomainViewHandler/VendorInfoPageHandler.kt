package presentation.DomainViewHandler

import UIStates.ScreenUIStates
import domain.Models.Vendor
import domain.Models.VendorResourceListEnvelope
import presentation.connectVendor.ConnectVendorContract
import presentation.connectVendor.ConnectVendorPresenter
import presentation.viewmodels.ResourceListEnvelopeViewModel
import presentation.viewmodels.ScreenUIStateViewModel

class VendorInfoPageHandler(
    private val vendorResourceListEnvelopeViewModel: ResourceListEnvelopeViewModel<Vendor>? = null,
    private val screenUiStateViewModel: ScreenUIStateViewModel,
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
        screenUiStateViewModel.switchScreenUIState(uiState)
        uiState.let {
            when {
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

    override fun showVendors(vendors: VendorResourceListEnvelope?, isFromSearch: Boolean) {}

    override fun onLoadMoreVendorStarted(isSuccess: Boolean) {}

    override fun onLoadMoreVendorEnded(isSuccess: Boolean) {}

}
