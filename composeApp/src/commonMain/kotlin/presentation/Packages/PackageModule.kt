package presentation.Packages

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object PackageModule {
    val PackageModule  = module {
        singleOf(::PackagePresenter)
    }
}