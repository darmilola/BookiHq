package presentation.DomainViewHandler

import UIStates.AppUIStates
import domain.Models.FavoriteProductIdModel
import domain.Models.FavoriteProductModel
import domain.Models.Product
import domain.Models.ProductResourceListEnvelope
import kotlinx.coroutines.runBlocking
import presentation.Products.ProductContract
import presentation.Products.ProductPresenter
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.ProductResourceListEnvelopeViewModel

class FavoriteProductsHandler(
    private val loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null,
    private val onFavoriteProductReady:(List<Product>) -> Unit,
    private val onFavoriteProductIdsReady:(List<FavoriteProductIdModel>) -> Unit,
    private val productPresenter: ProductPresenter
) : ProductContract.FavoriteProductView {
    fun init() {
        productPresenter.registerFavoriteUIContract(this)
    }

    override fun showLce(appUIStates: AppUIStates) {
        loadingScreenUiStateViewModel!!.switchScreenUIState(appUIStates)
    }

    override fun showFavoriteProducts(favoriteProducts: List<Product>) {
        onFavoriteProductReady(favoriteProducts)
    }

    override fun showFavoriteProductIds(favoriteProductIds: List<FavoriteProductIdModel>) {
        onFavoriteProductIdsReady(favoriteProductIds)
    }
}