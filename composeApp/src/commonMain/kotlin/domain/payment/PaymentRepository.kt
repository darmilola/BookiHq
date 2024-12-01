package domain.payment

import com.badoo.reaktive.single.Single
import domain.Models.InitCheckoutResponse

interface PaymentRepository {
    suspend fun initCheckout(
        paymentAmount: String,
        customerEmail: String,
        currency: String
    ): Single<InitCheckoutResponse>
}