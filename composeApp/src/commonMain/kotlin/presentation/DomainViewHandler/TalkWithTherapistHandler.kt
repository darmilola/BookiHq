package presentation.DomainViewHandler

import UIStates.ActionUIStates
import domain.Models.PlatformTime
import domain.Models.VendorTime
import presentation.profile.ProfileContract
import presentation.profile.ProfilePresenter
import presentation.viewmodels.ActionUIStateViewModel

class TalkWithTherapistHandler(
    private val profilePresenter: ProfilePresenter,
    private val onAvailableTimesReady:(List<VendorTime>, List<PlatformTime>)-> Unit,
    private val actionUIStateViewModel: ActionUIStateViewModel,
    private val isLoading: () -> Unit,
    private val isDone: () -> Unit,
    private val isSuccess: () -> Unit,
) : ProfileContract.MeetingViewContract {
    fun init() {
        profilePresenter.registerTalkWithTherapistContract(this)
    }

    override fun showLce(actionUIStates: ActionUIStates, message: String) {
        actionUIStates.let {
            when{
                it.isLoading -> {
                    isLoading()
                }

                it.isSuccess -> {
                    isSuccess()
                }
            }
        }
    }

    override fun showActionLce(actionUIStates: ActionUIStates, message: String) {
        actionUIStateViewModel.switchActionUIState(actionUIStates)
    }

    override fun showAvailability(vendorTimes: List<VendorTime>, platformTimes: List<PlatformTime>) {
        onAvailableTimesReady(vendorTimes,platformTimes)
    }
}