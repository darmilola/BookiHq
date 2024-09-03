package presentation.Products

import UIStates.AppUIStates
import com.badoo.reaktive.single.subscribe
import domain.Enums.ServerResponse
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
        contractView?.showLce(AppUIStates(isLoading = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.getAllProducts(vendorId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showLce(AppUIStates(isSuccess = true))
                                        contractView?.showProducts(result.listItem, isFromSearch = false, isLoadMore = false)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showLce(AppUIStates(isFailed = true))
                                    }
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
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.onLoadMoreProductEnded()
                                        contractView?.showProducts(result.listItem)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.onLoadMoreProductEnded()
                                    }
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
        contractView?.showLce(AppUIStates(isLoading = true, loadingMessage = "Searching Product..."))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.searchProducts(vendorId, searchQuery)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showLce(AppUIStates(isSuccess = true))
                                        contractView?.showProducts(result.listItem, isLoadMore = false, isFromSearch = true)
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.showLce(AppUIStates(isEmpty = true, emptyMessage = "Product Not Found"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showLce(AppUIStates(isEmpty = true, emptyMessage = "Product Not Found"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showLce(AppUIStates(isEmpty = true, emptyMessage = "Product Not Found"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(AppUIStates(isEmpty = true, emptyMessage = "Product Not Found"))
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
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.onLoadMoreProductEnded()
                                        contractView?.showProducts(result.listItem, isLoadMore = true, isFromSearch = true)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.onLoadMoreProductEnded()
                                    }
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
        contractView?.showLce(AppUIStates(isLoading = true, loadingMessage = "Loading Products"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.getProductsByType(vendorId, productType)
                        .subscribe(
                            onSuccess = { response ->
                                when (response.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showLce(AppUIStates(isSuccess = true))
                                        contractView?.showProducts(response.listItem, isFromSearch = false, isLoadMore = false)
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Product is Empty"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Product"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Product"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Product"))
            }
        }
    }

    override fun onProductTypeChanged(vendorId: Long, productType: String) {
        contractView?.onProductTypeChangeStarted()
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.getProductsByType(vendorId, productType)
                        .subscribe(
                            onSuccess = { response ->
                                when (response.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.onProductTypeChangeEnded(isSuccess = true)
                                        contractView?.showProducts(response.listItem, isFromSearch = false, isLoadMore = false)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.onProductTypeChangeEnded(isSuccess = false)
                                    }
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
                                when (response.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.onLoadMoreProductEnded()
                                        contractView?.showProducts(response.listItem, isFromSearch = false, isLoadMore = true)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.onLoadMoreProductEnded()
                                    }
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
