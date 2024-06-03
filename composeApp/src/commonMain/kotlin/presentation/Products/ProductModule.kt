package presentation.Products

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object ProductModule {
    val ProductModule = module {
        singleOf(::ProductPresenter)
        singleOf(::CartPresenter)
    }
}