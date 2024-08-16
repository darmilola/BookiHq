package presentation.Products

import UIStates.AppUIStates
import com.badoo.reaktive.single.subscribe
import domain.Products.ProductRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductPresenter(apiService: HttpClient): ProductContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: ProductContract.View? = null
    private val productRepositoryImpl: ProductRepositoryImpl = ProductRepositoryImpl(apiService)
    override fun registerUIContract(view: ProductContract.View?) {
        contractView = view
    }

    override fun getProducts(vendorId: Long) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(AppUIStates(isLoading = true))
                    productRepositoryImpl.getAllProducts(vendorId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(AppUIStates(isSuccess = true))
                                    contractView?.showProducts(result.listItem, isFromSearch = false, isLoadMore = false)
                                }
                                else{
                                    contractView?.showLce(AppUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                contractView?.showLce(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(AppUIStates(isFailed = true))
            }
        }
    }

    override fun getMoreProducts(vendorId: Long, nextPage: Int) {
        contractView?.onLoadMoreProductStarted()

        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.getAllProducts(vendorId, nextPage = nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.onLoadMoreProductEnded()
                                    contractView?.showProducts(result.listItem)
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

    override fun searchProducts(vendorId: Long, searchQuery: String) {
        contractView?.showLce(AppUIStates(isLoading = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.searchProducts(vendorId, searchQuery)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(AppUIStates(isSuccess = true))
                                    contractView?.showProducts(result.listItem, isLoadMore = false, isFromSearch = true)
                                }
                                else{
                                    contractView?.showLce(AppUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                contractView?.showLce(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(AppUIStates(isFailed = true))
            }
        }
    }

    override fun searchMoreProducts(vendorId: Long, searchQuery: String, nextPage: Int) {
        contractView?.onLoadMoreProductStarted()

        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.searchProducts(vendorId, searchQuery,nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.onLoadMoreProductEnded()
                                    contractView?.showProducts(result.listItem, isLoadMore = true, isFromSearch = true)
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

    override fun getProductsByType(vendorId: Long, productType: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(AppUIStates(isLoading = true))
                    productRepositoryImpl.getProductsByType(vendorId, productType)
                        .subscribe(
                            onSuccess = { response ->
                                println("Result $response")
                                if (response.status == "success") {
                                    contractView?.showLce(AppUIStates(isSuccess = true))
                                    contractView?.showProducts(response.listItem, isFromSearch = false, isLoadMore = false)
                                }
                                else{
                                    contractView?.showLce(AppUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                println("Result 2 ${it.message}")
                                contractView?.showLce(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Result32 ${e.message}")
                contractView?.showLce(AppUIStates(isFailed = true))
            }
        }
    }

    override fun onProductTypeChanged(vendorId: Long, productType: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onProductTypeChangeStarted()
                    productRepositoryImpl.getProductsByType(vendorId, productType)
                        .subscribe(
                            onSuccess = { response ->
                                if (response.status == "success") {
                                    contractView?.onProductTypeChangeEnded(isSuccess = true)
                                    contractView?.showProducts(response.listItem, isFromSearch = false, isLoadMore = false)
                                }
                                else{
                                    contractView?.onProductTypeChangeEnded(isSuccess = false)
                                }
                            },
                            onError = {
                                contractView?.onProductTypeChangeEnded(isSuccess = false)
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onProductTypeChangeEnded(isSuccess = false)
            }
        }
    }

    override fun getMoreProductsByType(vendorId: Long, productType: String, nextPage: Int) {
        contractView?.onLoadMoreProductStarted()
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.getProductsByType(vendorId, productType, nextPage)
                        .subscribe(
                            onSuccess = { response ->
                                if (response.status == "success") {
                                    contractView?.onLoadMoreProductEnded()
                                    contractView?.showProducts(response.listItem, isFromSearch = false, isLoadMore = true)
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
