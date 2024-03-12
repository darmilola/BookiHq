package presentation.main.home

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object HomepageModule {
    val HomepageModule  = module {
        singleOf(::HomepagePresenter)
    }
}