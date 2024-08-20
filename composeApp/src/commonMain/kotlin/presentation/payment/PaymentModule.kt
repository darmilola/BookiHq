package presentation.payment

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object PaymentModule {
    val PaymentModule = module {
        singleOf(::PaymentPresenter)
    }
}