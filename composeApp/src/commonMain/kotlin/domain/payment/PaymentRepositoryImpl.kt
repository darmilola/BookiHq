package domain.payment

import com.badoo.reaktive.single.Single
import domain.Models.InitCheckoutResponse
import domain.Models.ProductListDataResponse
import domain.Products.GetAllProductsRequest
import domain.Products.ProductNetworkService
import domain.Products.ProductRepository
import io.ktor.client.HttpClient

class PaymentRepositoryImpl(apiService: HttpClient): PaymentRepository {
    private val paymentNetworkService: PaymentNetworkService = PaymentNetworkService(apiService)
    override suspend fun initCheckout(
        paymentAmount: String,
        customerEmail: String,
        currency: String
    ): Single<InitCheckoutResponse> {
        val param = InitCheckoutRequest(customerEmail = customerEmail, amount = paymentAmount, currency = currency)
        return paymentNetworkService.initCheckout(param)
    }
}