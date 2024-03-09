package presentation.authentication

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object AuthenticationModule {
    val AuthenticationModule = module {
        singleOf(::AuthenticationPresenter)
    }
}