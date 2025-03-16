package presentation.DomainViewHandler

import domain.Models.State
import presentation.profile.ProfileContract
import presentation.profile.ProfilePresenter
import presentation.viewmodels.StatesViewModel

class PlatformHandler(
    private val profilePresenter: ProfilePresenter,
    private val statesViewModel: StatesViewModel
) : ProfileContract.PlatformContract {
    fun init() {
        profilePresenter.registerPlatformContract(this)
    }

    override fun showStates(states: ArrayList<State>) {
        statesViewModel.setStates(states)
    }

}