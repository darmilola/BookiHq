package presentation.Products

import com.badoo.reaktive.single.subscribe
import domain.Products.ProductRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import UIStates.ScreenUIStates

class ProductPresenter(apiService: HttpClient): ProductContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: ProductContract.View? = null
    private val productRepositoryImpl: ProductRepositoryImpl = ProductRepositoryImpl(apiService)
    override fun registerUIContract(view: ProductContract.View?) {
        contractView = view
    }

    override fun getProducts(vendorId: Int) {
        contractView?.showLce(ScreenUIStates(loadingVisible = true))

        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.getAllProducts(vendorId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(ScreenUIStates(contentVisible = true))
                                    contractView?.showProducts(result.listItem, isFromSearch = false)
                                }
                                else{
                                    contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Loading Products Please Try Again"))
                                }
                            },
                            onError = {
                                contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Loading Products Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Loading Products Please Try Again"))
            }
        }
    }

    override fun getMoreProducts(vendorId: Int, nextPage: Int) {
        contractView?.onLoadMoreProductStarted()

        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.getAllProducts(vendorId, nextPage = nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.onLoadMoreProductEnded()
                                    contractView?.showProducts(result.listItem, isFromSearch = false)
                                }
                                else{
                                    contractView?.onLoadMoreProductEnded()
                                }
                            },
                            onError = {
                                contractView?.onLoadMoreProductEnded()
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onLoadMoreProductEnded()
            }
        }
    }

    override fun searchProducts(vendorId: Int, searchQuery: String) {
        contractView?.showLce(ScreenUIStates(loadingVisible = true))

        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.searchProducts(vendorId, searchQuery)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(ScreenUIStates(contentVisible = true))
                                    contractView?.showProducts(result.listItem, isFromSearch = true)
                                }
                                else{
                                    contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
                                }
                            },
                            onError = {
                                contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
            }
        }
    }

    override fun searchMoreProducts(vendorId: Int, searchQuery: String, nextPage: Int) {
        contractView?.onLoadMoreProductStarted()

        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.searchProducts(vendorId, searchQuery,nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.onLoadMoreProductEnded()
                                    contractView?.showProducts(result.listItem, isFromSearch = true)
                                }
                                else{
                                    contractView?.onLoadMoreProductEnded()
                                }
                            },
                            onError = {
                                contractView?.onLoadMoreProductEnded()
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onLoadMoreProductEnded()
            }
        }
    }
}
