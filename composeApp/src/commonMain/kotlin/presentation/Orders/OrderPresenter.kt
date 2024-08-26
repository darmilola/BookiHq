package presentation.Orders

import UIStates.AppUIStates
import com.badoo.reaktive.single.subscribe
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
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(AppUIStates(isLoading = true))
                    orderRepositoryImpl.getUserOrders(userId, 1)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(AppUIStates(isSuccess = true))
                                    contractView?.showUserOrders(result.listItem)
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

    override fun getMoreUserOrders(userId: Long, nextPage: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onLoadMoreOrderStarted(true)
                    orderRepositoryImpl.getUserOrders(userId, nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.onLoadMoreOrderEnded(true)
                                    contractView?.showUserOrders(result.listItem)
                                }
                                else{
                                    contractView?.onLoadMoreOrderEnded(true)
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.onLoadMoreOrderEnded(true) }
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
        println("Started $userId, $productId, $reviewText")
        contractView?.showReviewsActionLce(AppUIStates(isLoading = true, loadingMessage = "Adding Reviews"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    orderRepositoryImpl.addProductReviews(userId, productId, reviewText)
                        .subscribe(
                            onSuccess = { result ->
                                println("Success $result")
                                if (result.status == "success"){
                                    contractView?.showReviewsActionLce(AppUIStates(isSuccess = true, successMessage = "Reviews Added Successfully"))
                                }
                                else{
                                    contractView?.showReviewsActionLce(AppUIStates(isFailed = true, errorMessage = "Error Adding Review, Please Try Again"))
                                }
                            },
                            onError = {
                                println("Failure ${it.message}")
                                contractView?.showReviewsActionLce(AppUIStates(isFailed = true, errorMessage = "Error Adding Review, Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Failure ${e.message}")
                contractView?.onLoadMoreOrderEnded(true)
            }
        }
    }

}