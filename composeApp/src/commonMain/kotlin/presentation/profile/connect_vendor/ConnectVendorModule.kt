package presentation.profile.connect_vendor

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object ConnectVendorModule {
    val ConnectVendorModule = module {
        singleOf(::ConnectVendorPresenter)
    }
}