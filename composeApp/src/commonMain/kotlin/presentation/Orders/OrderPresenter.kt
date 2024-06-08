package presentation.Orders

import com.badoo.reaktive.single.subscribe
import domain.Orders.OrderRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.viewmodels.ScreenUIStates

class OrderPresenter(apiService: HttpClient): OrderContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: OrderContract.View? = null
    private val orderRepositoryImpl: OrderRepositoryImpl = OrderRepositoryImpl(apiService)
    override fun registerUIContract(view: OrderContract.View?) {
        contractView = view
    }

    override fun getUserOrders(userId: Int) {
        println("Called")
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(ScreenUIStates(loadingVisible = true))
                    orderRepositoryImpl.getUserOrders(userId, 1)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(ScreenUIStates(contentVisible = true))
                                    println("My Result")
                                    contractView?.showUserOrders(result.listItem)
                                }
                                else{
                                    println("Error3")
                                    contractView?.showLce(ScreenUIStates(errorOccurred = true))
                                }
                            },
                            onError = {
                                println("Error3 ${it.message}")
                                it.message?.let { it1 -> contractView?.showLce(ScreenUIStates(errorOccurred = true)) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Error1 ${e.message}")
                contractView?.showLce(ScreenUIStates(errorOccurred = true))
            }
        }
    }

    override fun getMoreUserOrders(userId: Int, nextPage: Int) {
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

    override fun deleteOrder(userId: Int) {
        TODO("Not yet implemented")
    }

}