package presentation.Products

import UIStates.AppUIStates
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import com.badoo.reaktive.single.subscribe
import domain.Enums.ServerResponse
import domain.Models.Product
import domain.Products.ProductRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.Transient
import presentation.viewmodels.MainViewModel
import utils.getDistanceFromCustomer

class ProductPresenter(apiService: HttpClient): ProductContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: ProductContract.View? = null
    private var favoriteContractView: ProductContract.FavoriteProductView? = null
    private val productRepositoryImpl: ProductRepositoryImpl = ProductRepositoryImpl(apiService)
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null
    private var mainViewModel: MainViewModel? = null
    override fun registerUIContract(view: ProductContract.View?) {
        contractView = view
    }

    override fun registerFavoriteUIContract(view: ProductContract.FavoriteProductView?) {
        favoriteContractView = view
    }

    fun setMainViewModel(mainViewModel: MainViewModel) {
        this.mainViewModel = mainViewModel
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

    override fun addFavoriteProduct(userId: Long, vendorId: Long, productId: Long) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.addFavoriteProduct(userId, vendorId, productId)
                        .subscribe(
                            onSuccess = { _ ->
                                favoriteContractView?.onFavoriteProductChanged() },
                            onError = {},
                        )
                }
                result.dispose()
            } catch(_: Exception) { }
        }
    }

    override fun removeFavoriteProduct(userId: Long, productId: Long) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.removeFavoriteProduct(userId, productId)
                        .subscribe(
                            onSuccess = { _ -> favoriteContractView?.onFavoriteProductChanged() },
                            onError = {},
                        )
                }
                result.dispose()
            } catch(_: Exception) { }
        }
    }

    override fun getFavoriteProducts(userId: Long) {
        favoriteContractView?.showLce(AppUIStates(isLoading = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.getFavoriteProducts(userId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        runBlocking {
                                            val favoriteProductList = arrayListOf<Product>()
                                            result.favoriteProductItems.map {
                                                it.product.isFavorite = true
                                                favoriteProductList.add(it.product)
                                            }
                                            favoriteContractView?.showFavoriteProducts(favoriteProductList)
                                            favoriteContractView?.showLce(AppUIStates(isSuccess = true))
                                        }
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        favoriteContractView?.showLce(AppUIStates(isEmpty = true, emptyMessage = "No Product Added to Favorite"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        favoriteContractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
                                    }
                                }
                            },
                            onError = {
                                favoriteContractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                favoriteContractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
            }
        }
    }

    override fun getFavoriteProductIds(userId: Long) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    productRepositoryImpl.getFavoriteProductIds(userId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        favoriteContractView?.showFavoriteProductIds(result.favoriteProductIds)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {}
                                }
                            },
                            onError = {},
                        )
                }
                result.dispose()
            } catch(_: Exception) {}
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
                                        runBlocking {
                                            val favoriteList = mainViewModel!!.favoriteProductIds.value
                                            val favoriteIdList = arrayListOf<Long>()
                                            favoriteList.map {
                                                favoriteIdList.add(it.productId)
                                            }
                                           val updatedProductList = response.listItem.resources?.map {
                                                if (it.productId in favoriteIdList){
                                                    it.isFavorite = true
                                                }
                                               it
                                            }
                                            response.listItem.resources = updatedProductList
                                            contractView?.showLce(AppUIStates(isSuccess = true))
                                            contractView?.showProducts(
                                                response.listItem,
                                                isFromSearch = false,
                                                isLoadMore = false
                                            )
                                        }
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
                                        runBlocking {
                                            val favoriteList = mainViewModel!!.favoriteProductIds.value
                                            val favoriteIdList = arrayListOf<Long>()
                                            favoriteList.map {
                                                favoriteIdList.add(it.productId)
                                            }
                                            val updatedProductList = response.listItem.resources?.map {
                                                if (it.productId in favoriteIdList){
                                                    it.isFavorite = true
                                                }
                                                it
                                            }
                                            response.listItem.resources = updatedProductList
                                            contractView?.onLoadMoreProductEnded()
                                            contractView?.showProducts(response.listItem, isFromSearch = false, isLoadMore = true)
                                        }
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
