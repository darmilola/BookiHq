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
import UIStates.AppUIStates
import domain.Models.PaymentAuthorizationResult
import domain.Models.PlatformNavigator
import domain.Models.User
import domain.Models.Vendor
import kotlinx.serialization.json.Json
import utils.getUnSavedOrdersRequestJson

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
        year: Int, user: User, vendor: Vendor,paymentAmount: Long, platformNavigator: PlatformNavigator
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(AppUIStates(isLoading = true, loadingMessage = "Creating Order"))
                    val orderItemsJson = getUnSavedOrdersRequestJson(orderItemList)
                    productRepositoryImpl.createOrder(vendorId,userId,deliveryMethod,paymentMethod,day, month, year, orderItemsJson,paymentAmount)
                        .subscribe(
                            onSuccess = { result ->
                                println(result)
                                if (result.status == "success"){
                                    contractView?.showLce(AppUIStates(isSuccess = true, successMessage = "Order Created Successfully"))
                                    platformNavigator.sendOrderBookingNotification(customerName = user.firstname!!, vendorLogoUrl = vendor.businessLogo!!, fcmToken = vendor.fcmToken!!)
                                }
                                else{
                                    contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Creating Order"))
                                }
                            },
                            onError = {
                                println("Error 1 ${it.message}")
                                contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Creating Order"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Error 2 ${e.message}")
                contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Creating Order"))
            }
        }
    }

    override fun initCheckOut(customerEmail: String, amount: String) {
        println("Amount $amount email $customerEmail")
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(AppUIStates(isLoading = true, loadingMessage = "Processing..."))
                    productRepositoryImpl.initCheckout(paymentAmount = amount, customerEmail = customerEmail)
                        .subscribe(
                            onSuccess = { result ->
                                val authorizationResult = Json.decodeFromString<PaymentAuthorizationResult>(result.authorizationResultJsonString)
                                if (result.status == "success"){
                                    contractView?.showLce(AppUIStates(isSuccess = true, successMessage = "Processing Successful"))
                                    contractView?.showAuthorizationResult(authorizationResult)
                                }
                                else{
                                    contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Processing Error"))
                                }
                            },
                            onError = {
                                println("Error 1 ${it.message}")
                                contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Processing Error"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Error 1 ${e.message}")
                contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Processing Error"))
            }
        }
    }

}
