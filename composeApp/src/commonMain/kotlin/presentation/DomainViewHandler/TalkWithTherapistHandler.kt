package presentation.DomainViewHandler

import UIStates.AppUIStates
import domain.Models.PlatformTime
import domain.Models.VendorTime
import presentation.profile.ProfileContract
import presentation.profile.ProfilePresenter
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel

class TalkWithTherapistHandler(
    private val profilePresenter: ProfilePresenter,
    private val onAvailableTimesReady:(List<VendorTime>, List<PlatformTime>)-> Unit,
    private val performedActionUIStateViewModel: PerformedActionUIStateViewModel,
    private val loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel,
) : ProfileContract.MeetingViewContract {
    fun init() {
        profilePresenter.registerTalkWithTherapistContract(this)
    }

    override fun showScreenLce(appUIStates: AppUIStates, message: String) {
        loadingScreenUiStateViewModel.switchScreenUIState(appUIStates)
    }

    override fun showActionLce(appUIStates: AppUIStates, message: String) {
        performedActionUIStateViewModel.switchActionUIState(appUIStates)
    }

    override fun showAvailability(vendorTimes: List<VendorTime>, platformTimes: List<PlatformTime>) {
        onAvailableTimesReady(vendorTimes,platformTimes)
    }
}