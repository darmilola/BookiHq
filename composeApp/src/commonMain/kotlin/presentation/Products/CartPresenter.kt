package presentation.Products

import com.badoo.reaktive.single.subscribe
import domain.Models.OrderItem
import domain.Products.ProductRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import UIStates.ActionUIStates
import utils.getUnSavedOrders

class CartPresenter(apiService: HttpClient): CartContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: CartContract.View? = null
    private val productRepositoryImpl: ProductRepositoryImpl = ProductRepositoryImpl(apiService)
    override fun registerUIContract(view: CartContract.View?) {
        contractView = view
    }

    override fun createOrder(
        orderItemList: MutableList<OrderItem>,
        vendorId: Int,
        userId: Int,
        orderReference: Int,
        deliveryMethod: String,
        paymentMethod: String
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(ActionUIStates(isLoading = true, loadingMessage = "Creating Order"))
                    val orderItems = getUnSavedOrders(orderItemList,userId,orderReference)
                    productRepositoryImpl.createOrder(vendorId,userId,orderReference,deliveryMethod,paymentMethod,orderItems)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(ActionUIStates(isSuccess = true, successMessage = "Order Created Successfully"))
                                }
                                else{
                                    contractView?.showLce(ActionUIStates(isFailed = true, errorMessage = "Error Creating Order"))
                                }
                            },
                            onError = {
                                contractView?.showLce(ActionUIStates(isFailed = true, errorMessage = "Error Creating Order"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(ActionUIStates(isFailed = true, errorMessage = "Error Creating Order"))
            }
        }
    }

}
