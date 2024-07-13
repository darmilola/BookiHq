package presentation.DomainViewHandler

import UIStates.ActionUIStates
import presentation.profile.ProfileContract
import presentation.profile.ProfilePresenter
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.PlatformViewModel

class SwitchVendorHandler (
    private val profilePresenter: ProfilePresenter,
    private val actionUIStateViewModel: ActionUIStateViewModel
) : ProfileContract.SwitchVendorContract {
    fun init() {
        profilePresenter.registerSwitchVendorContract(this)
    }

    override fun showActionLce(actionUIStates: ActionUIStates, message: String) {
        println("Arena 2")
        actionUIStateViewModel.switchVendorActionUIState(actionUIStates)
    }


}