package presentation.Orders

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object OrderModule {
    val OrderModule = module {
        singleOf(::OrderPresenter)
    }
}