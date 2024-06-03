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
import presentation.viewmodels.UIStates

class ProductPresenter(apiService: HttpClient): ProductContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: ProductContract.View? = null
    private val productRepositoryImpl: ProductRepositoryImpl = ProductRepositoryImpl(apiService)
    override fun registerUIContract(view: ProductContract.View?) {
        contractView = view
    }

    override fun getProducts(vendorId: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(UIStates(loadingVisible = true))
                    productRepositoryImpl.getAllProducts(vendorId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(UIStates(contentVisible = true))
                                    contractView?.showProducts(result.listItem, isFromSearch = false)
                                }
                                else{
                                    contractView?.showLce(UIStates(errorOccurred = true))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showLce(UIStates(errorOccurred = true)) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(UIStates(errorOccurred = true))
            }
        }
    }

    override fun getMoreProducts(vendorId: Int, nextPage: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onLoadMoreProductStarted(true)
                    productRepositoryImpl.getAllProducts(vendorId, nextPage = nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.onLoadMoreProductEnded(true)
                                    contractView?.showProducts(result.listItem, isFromSearch = false)
                                }
                                else{
                                    contractView?.onLoadMoreProductEnded(false)
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.onLoadMoreProductEnded(false) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onLoadMoreProductEnded(false)
            }
        }
    }

    override fun searchProducts(vendorId: Int, searchQuery: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(UIStates(loadingVisible = true))
                    productRepositoryImpl.searchProducts(vendorId, searchQuery)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(UIStates(contentVisible = true))
                                    contractView?.showProducts(result.listItem, isFromSearch = true)
                                }
                                else{
                                    contractView?.showLce(UIStates(errorOccurred = true))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showLce(UIStates(errorOccurred = true)) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(UIStates(errorOccurred = true))
            }
        }
    }

    override fun searchMoreProducts(vendorId: Int, searchQuery: String, nextPage: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onLoadMoreProductStarted(true)
                    productRepositoryImpl.searchProducts(vendorId, searchQuery,nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.onLoadMoreProductEnded(true)
                                    contractView?.showProducts(result.listItem, isFromSearch = true)
                                }
                                else{
                                    contractView?.onLoadMoreProductEnded(false)
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.onLoadMoreProductEnded(false) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onLoadMoreProductEnded(false)
            }
        }
    }
}
