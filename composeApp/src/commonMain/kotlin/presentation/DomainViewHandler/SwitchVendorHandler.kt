package presentation.DomainViewHandler

import UIStates.AppUIStates
import presentation.profile.ProfileContract
import presentation.profile.ProfilePresenter
import presentation.viewmodels.PerformedActionUIStateViewModel

class SwitchVendorHandler (
    private val profilePresenter: ProfilePresenter,
    private val performedActionUIStateViewModel: PerformedActionUIStateViewModel
) : ProfileContract.SwitchVendorContract {
    fun init() {
        profilePresenter.registerSwitchVendorContract(this)
    }

    override fun showActionLce(appUIStates: AppUIStates, message: String) {
        performedActionUIStateViewModel.switchVendorActionUIState(appUIStates)
    }


}