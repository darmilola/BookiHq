package presentation.payment

import UIStates.AppUIStates
import domain.Enums.ActionType
import domain.Models.OrderItem
import domain.Models.PaymentAuthorizationResult
import domain.Models.PlatformNavigator
import domain.Models.User
import domain.Models.Vendor
import domain.Products.ProductRepositoryImpl
import domain.payment.PaymentRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import presentation.Products.CartContract
import com.badoo.reaktive.single.subscribe
import domain.Enums.ServerResponse
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaymentPresenter(apiService: HttpClient): PaymentContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: PaymentContract.View? = null
    private val paymentRepositoryImpl: PaymentRepositoryImpl = PaymentRepositoryImpl(apiService)
    override fun registerUIContract(view: PaymentContract.View?) {
        contractView = view
    }

    override fun initCheckOut(customerEmail: String, amount: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(AppUIStates(isLoading = true, loadingMessage = "Processing..."))
                    paymentRepositoryImpl.initCheckout(paymentAmount = amount, customerEmail = customerEmail)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        val authorizationResult = Json.decodeFromString<PaymentAuthorizationResult>(result.authorizationResultJsonString)
                                        contractView?.showLce(AppUIStates(isSuccess = true, successMessage = "Processing Successful"))
                                        contractView?.showAuthorizationResult(authorizationResult)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Processing Error"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Processing Error"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Processing Error"))
            }
        }
    }




}