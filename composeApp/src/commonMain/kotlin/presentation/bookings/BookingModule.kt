package presentation.bookings

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object BookingModule {
    val BookingModule = module {
        singleOf(::BookingPresenter)
    }
}