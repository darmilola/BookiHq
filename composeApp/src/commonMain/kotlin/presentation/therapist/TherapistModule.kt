package presentation.therapist

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object TherapistModule {
    val TherapistModule = module {
        singleOf(::TherapistPresenter)
    }
}