package presentation.DomainViewHandler

import domain.Models.ProductResourceListEnvelope
import presentation.Products.ProductContract
import presentation.Products.ProductPresenter
import presentation.viewmodels.ScreenUIStateViewModel
import UIStates.ScreenUIStates

class ShopProductsHandler(
    private val screenUiStateViewModel: ScreenUIStateViewModel,
    private val productPresenter: ProductPresenter,
    private val onProductAvailable: (products: ProductResourceListEnvelope?, isFromSearch: Boolean) -> Unit,
    private val onLoadMoreStarted: () -> Unit,
    private val onLoadMoreEnded: () -> Unit
) : ProductContract.View {
    fun init() {
        productPresenter.registerUIContract(this)
    }

    override fun showLce(uiState: ScreenUIStates) {
        screenUiStateViewModel.switchScreenUIState(uiState)
    }

    override fun showProducts(products: ProductResourceListEnvelope?, isFromSearch: Boolean) {
        onProductAvailable(products, isFromSearch)
    }

    override fun onLoadMoreProductStarted() {
        onLoadMoreStarted()
    }

    override fun onLoadMoreProductEnded() {
        onLoadMoreEnded()
    }

}