package presentation.Products

import com.badoo.reaktive.single.subscribe
import domain.Products.OrderItemRequest
import domain.Products.ProductRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.viewmodels.AsyncUIStates

class CartPresenter(apiService: HttpClient): CartContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: CartContract.View? = null
    private val productRepositoryImpl: ProductRepositoryImpl = ProductRepositoryImpl(apiService)
    override fun registerUIContract(view: CartContract.View?) {
        contractView = view
    }

    override fun createOrder(
        vendorId: Int,
        userId: Int,
        orderReference: Int,
        deliveryMethod: String,
        paymentMethod: String,
        orderItems: ArrayList<OrderItemRequest>
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(AsyncUIStates(isLoading = true))
                    productRepositoryImpl.getProductCategories(vendorId, userId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(AsyncUIStates(isSuccess = true))
                                }
                                else{
                                    contractView?.showLce(AsyncUIStates(isSuccess = false))
                                }
                            },
                            onError = {
                                it.message?.let { it1 ->  contractView?.showLce(AsyncUIStates(isSuccess = false), message = it1) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(AsyncUIStates(isSuccess = false))
            }
        }
    }

}
