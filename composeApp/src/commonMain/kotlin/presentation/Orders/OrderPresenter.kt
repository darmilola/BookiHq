package presentation.Orders

import UIStates.AppUIStates
import com.badoo.reaktive.single.subscribe
import domain.Enums.ServerResponse
import domain.Orders.OrderRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderPresenter(apiService: HttpClient): OrderContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: OrderContract.View? = null
    private val orderRepositoryImpl: OrderRepositoryImpl = OrderRepositoryImpl(apiService)
    override fun registerUIContract(view: OrderContract.View?) {
        contractView = view
    }

    override fun getUserOrders(userId: Long) {
        contractView?.showLce(AppUIStates(isLoading = true, loadingMessage = "Getting Orders"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    orderRepositoryImpl.getUserOrders(userId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showLce(AppUIStates(isSuccess = true))
                                        contractView?.showUserOrders(result.listItem)
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.showLce(AppUIStates(isEmpty = true, emptyMessage = "No Order Available"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Orders"))
                                    }
                                }
                             },
                            onError = {
                                contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Orders"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Orders"))
            }
        }
    }

    override fun getMoreUserOrders(userId: Long, nextPage: Int) {
        contractView?.onLoadMoreOrderStarted(true)
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    orderRepositoryImpl.getUserOrders(userId, nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.onLoadMoreOrderEnded(true)
                                        contractView?.showUserOrders(result.listItem)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                       contractView?.onLoadMoreOrderEnded(true)
                                    }
                                }
                            },
                            onError = {
                                contractView?.onLoadMoreOrderEnded(true)
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onLoadMoreOrderEnded(true)
            }
        }
    }

    override fun deleteOrder(userId: Int) {}
    override fun addProductReviews(userId: Long, productId: Long, reviewText: String) {
        contractView?.showReviewsActionLce(AppUIStates(isLoading = true, loadingMessage = "Adding Reviews"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    orderRepositoryImpl.addProductReviews(userId, productId, reviewText)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showReviewsActionLce(AppUIStates(isSuccess = true, successMessage = "Reviews Added Successfully"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showReviewsActionLce(AppUIStates(isFailed = true, errorMessage = "Error Adding Review, Please Try Again"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showReviewsActionLce(AppUIStates(isFailed = true, errorMessage = "Error Adding Review, Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showReviewsActionLce(AppUIStates(isFailed = true, errorMessage = "Error Adding Review, Please Try Again"))
            }
        }
    }

}