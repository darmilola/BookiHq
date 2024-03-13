package presentation.UserProfile

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object ProfileModule {
    val ProfileModule = module {
        singleOf(::ProfilePresenter)
    }
}