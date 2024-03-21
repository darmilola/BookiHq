package presentation.appointments

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object AppointmentModule {
    val AppointmentModule = module {
        singleOf(::AppointmentPresenter)
    }
}