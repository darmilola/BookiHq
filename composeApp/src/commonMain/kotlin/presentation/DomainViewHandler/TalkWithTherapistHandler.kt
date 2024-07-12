package presentation.DomainViewHandler

import UIStates.ActionUIStates
import UIStates.ScreenUIStates
import domain.Models.PlatformTime
import domain.Models.VendorTime
import presentation.profile.ProfileContract
import presentation.profile.ProfilePresenter
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.UIStateViewModel

class TalkWithTherapistHandler(
    private val profilePresenter: ProfilePresenter,
    private val onAvailableTimesReady:(List<VendorTime>, List<PlatformTime>)-> Unit,
    private val actionUIStateViewModel: ActionUIStateViewModel,
    private val uiStateViewModel: UIStateViewModel,
) : ProfileContract.MeetingViewContract {
    fun init() {
        profilePresenter.registerTalkWithTherapistContract(this)
    }

    override fun showLce(screenUIStates: ScreenUIStates, message: String) {
        uiStateViewModel.switchScreenUIState(screenUIStates)
    }

    override fun showActionLce(actionUIStates: ActionUIStates, message: String) {
        actionUIStateViewModel.switchActionUIState(actionUIStates)
    }

    override fun showAvailability(vendorTimes: List<VendorTime>, platformTimes: List<PlatformTime>) {
        onAvailableTimesReady(vendorTimes,platformTimes)
    }
}