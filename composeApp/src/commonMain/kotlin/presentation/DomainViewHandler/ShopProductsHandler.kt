package presentation.DomainViewHandler

import UIStates.AppUIStates
import domain.Models.ProductResourceListEnvelope
import kotlinx.coroutines.runBlocking
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

    override fun showProducts(products: ProductResourceListEnvelope?, isFromSearch: Boolean, isLoadMore: Boolean) {
        runBlocking {
            productResourceListEnvelopeViewModel.setIsRefreshing(false)
            if (isFromSearch && !isLoadMore) {
                productResourceListEnvelopeViewModel.clearData(mutableListOf())
                productResourceListEnvelopeViewModel.setResources(products!!.resources)
                products.prevPageUrl?.let { productResourceListEnvelopeViewModel.setPrevPageUrl(it) }
                products.nextPageUrl?.let { productResourceListEnvelopeViewModel.setNextPageUrl(it) }
                products.currentPage?.let { productResourceListEnvelopeViewModel.setCurrentPage(it) }
                products.totalItemCount?.let {
                    productResourceListEnvelopeViewModel.setTotalItemCount(
                        it
                    )
                }
                products.displayedItemCount?.let {
                    productResourceListEnvelopeViewModel.setDisplayedItemCount(
                        it
                    )
                }
            }
            else if (isLoadMore) {
                products!!.prevPageUrl?.let { productResourceListEnvelopeViewModel.setPrevPageUrl(it) }
                products.nextPageUrl?.let { productResourceListEnvelopeViewModel.setNextPageUrl(it) }
                products.currentPage?.let { productResourceListEnvelopeViewModel.setCurrentPage(it) }
                products.totalItemCount?.let {
                    productResourceListEnvelopeViewModel.setTotalItemCount(
                        it
                    )
                }
                products.displayedItemCount?.let {
                    productResourceListEnvelopeViewModel.setDisplayedItemCount(
                        it
                    )
                }
                val productList = productResourceListEnvelopeViewModel.resources.value
                productList.addAll(products.resources!!.distinct())
                productResourceListEnvelopeViewModel.setResources(productList)

            }
            else {
                products!!.prevPageUrl?.let { productResourceListEnvelopeViewModel.setPrevPageUrl(it) }
                products.nextPageUrl?.let { productResourceListEnvelopeViewModel.setNextPageUrl(it) }
                products.currentPage?.let { productResourceListEnvelopeViewModel.setCurrentPage(it) }
                products.totalItemCount?.let {
                    productResourceListEnvelopeViewModel.setTotalItemCount(
                        it
                    )
                }
                products.displayedItemCount?.let {
                    productResourceListEnvelopeViewModel.setDisplayedItemCount(
                        it
                    )
                }
                productResourceListEnvelopeViewModel.setResources(products.resources)
            }
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