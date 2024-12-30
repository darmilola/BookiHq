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
import domain.Enums.ServerResponse
import domain.Models.PlatformNavigator
import domain.Models.User
import domain.Models.Vendor
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
        day: Int,
        month: Int,
        year: Int, user: User, vendor: Vendor,paymentAmount: Long, platformNavigator: PlatformNavigator
    ) {
        contractView?.showLce(AppUIStates(isLoading = true, loadingMessage = "Creating Order"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    val orderItemsJson = getUnSavedOrdersRequestJson(orderItemList)
                    productRepositoryImpl.createOrder(vendorId,userId,deliveryMethod,day, month, year, orderItemsJson,paymentAmount)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showLce(AppUIStates(isSuccess = true, successMessage = "Order Created Successfully"))
                                        platformNavigator.sendOrderBookingNotification(customerName = user.firstname!!, vendorLogoUrl = vendor.businessLogo!!, fcmToken = vendor.fcmToken!!)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Creating Order"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Creating Order"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Creating Order"))
            }
        }
    }


}
