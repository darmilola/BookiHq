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
import domain.Models.PlatformNavigator
import domain.Models.User
import domain.Models.Vendor
import utils.getUnSavedOrders

class CartPresenter(apiService: HttpClient): CartContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: CartContract.View? = null
    private val productRepositoryImpl: ProductRepositoryImpl = ProductRepositoryImpl(apiService)
    override fun registerUIContract(view: CartContract.View?) {
        contractView = view
    }

    override fun createOrder(
        orderItemList: List<OrderItem>,
        vendorId: Long,
        userId: Long,
        deliveryMethod: String,
        paymentMethod: String,
        day: Int,
        month: Int,
        year: Int, user: User, vendor: Vendor, platformNavigator: PlatformNavigator
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(ActionUIStates(isLoading = true, loadingMessage = "Creating Order"))
                    val orderItemsJson = getUnSavedOrders(orderItemList)
                    productRepositoryImpl.createOrder(vendorId,userId,deliveryMethod,paymentMethod,day, month, year, orderItemsJson)
                        .subscribe(
                            onSuccess = { result ->
                                println(result)
                                if (result.status == "success"){
                                    contractView?.showLce(ActionUIStates(isSuccess = true, successMessage = "Order Created Successfully"))
                                    platformNavigator.sendOrderBookingNotification(customerName = user.firstname!!, vendorLogoUrl = vendor.businessLogo!!, fcmToken = vendor.fcmToken!!)
                                }
                                else{
                                    contractView?.showLce(ActionUIStates(isFailed = true, errorMessage = "Error Creating Order"))
                                }
                            },
                            onError = {
                                println("Error 1 ${it.message}")
                                contractView?.showLce(ActionUIStates(isFailed = true, errorMessage = "Error Creating Order"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Error 2 ${e.message}")
                contractView?.showLce(ActionUIStates(isFailed = true, errorMessage = "Error Creating Order"))
            }
        }
    }

}
