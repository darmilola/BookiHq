package presentation.DomainViewHandler

import UIStates.AppUIStates
import domain.Models.ProductResourceListEnvelope
import presentation.Products.ProductContract
import presentation.Products.ProductPresenter
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.ProductResourceListEnvelopeViewModel

class ShopProductsHandler(
    private val loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel,
    private val productResourceListEnvelopeViewModel: ProductResourceListEnvelopeViewModel,
    private val productPresenter: ProductPresenter
) : ProductContract.View {
    fun init() {
        productPresenter.registerUIContract(this)
    }

    override fun showLce(appUIStates: AppUIStates) {
        loadingScreenUiStateViewModel.switchScreenUIState(appUIStates)
    }

    override fun showProducts(products: ProductResourceListEnvelope?) {
        println("Products $products")
        productResourceListEnvelopeViewModel.setIsRefreshing(false)
        if (productResourceListEnvelopeViewModel.resources.value.isNotEmpty()) {
            val productList = productResourceListEnvelopeViewModel.resources.value
            productList.addAll(products?.resources!!)
            productResourceListEnvelopeViewModel.setResources(productList)
            products.prevPageUrl?.let { productResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            products.nextPageUrl?.let { productResourceListEnvelopeViewModel.setNextPageUrl(it) }
            products.currentPage?.let { productResourceListEnvelopeViewModel.setCurrentPage(it) }
            products.totalItemCount?.let { productResourceListEnvelopeViewModel.setTotalItemCount(it) }
            products.displayedItemCount?.let { productResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        } else {
            productResourceListEnvelopeViewModel.setResources(products?.resources)
            products?.prevPageUrl?.let { productResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            products?.nextPageUrl?.let { productResourceListEnvelopeViewModel.setNextPageUrl(it) }
            products?.currentPage?.let { productResourceListEnvelopeViewModel.setCurrentPage(it) }
            products?.totalItemCount?.let { productResourceListEnvelopeViewModel.setTotalItemCount(it) }
            products?.displayedItemCount?.let { productResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
    }

    override fun showSearchProducts(products: ProductResourceListEnvelope?, isLoadMore: Boolean) {
        productResourceListEnvelopeViewModel.setIsRefreshing(false)
        if (isLoadMore) {
            val productList = productResourceListEnvelopeViewModel.resources.value
            productList.addAll(products?.resources!!)
            productResourceListEnvelopeViewModel.setResources(productList)
            products.prevPageUrl?.let { productResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            products.nextPageUrl?.let { productResourceListEnvelopeViewModel.setNextPageUrl(it) }
            products.currentPage?.let { productResourceListEnvelopeViewModel.setCurrentPage(it) }
            products.totalItemCount?.let { productResourceListEnvelopeViewModel.setTotalItemCount(it) }
            products.displayedItemCount?.let { productResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        } else {
            productResourceListEnvelopeViewModel.setResources(products?.resources)
            products?.prevPageUrl?.let { productResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            products?.nextPageUrl?.let { productResourceListEnvelopeViewModel.setNextPageUrl(it) }
            products?.currentPage?.let { productResourceListEnvelopeViewModel.setCurrentPage(it) }
            products?.totalItemCount?.let { productResourceListEnvelopeViewModel.setTotalItemCount(it) }
            products?.displayedItemCount?.let { productResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
    }

    override fun onLoadMoreProductStarted() {
        productResourceListEnvelopeViewModel.setLoadingMore(true)
    }

    override fun onLoadMoreProductEnded() {
        productResourceListEnvelopeViewModel.setLoadingMore(false)
    }

    override fun onProductTypeChangeStarted() {}

    override fun onProductTypeChangeEnded(isSuccess: Boolean) {}
}