package presentation.payment

import UIStates.AppUIStates
import domain.Models.PaymentAuthorizationResult

class PaymentContract {
    interface View {
        fun showLce(appUIStates: AppUIStates)
        fun showAuthorizationResult(paymentAuthorizationResult: PaymentAuthorizationResult)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun initCheckOut(customerEmail: String, amount: String)

    }
}