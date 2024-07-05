package presentation.DomainViewHandler

import UIStates.ActionUIStates
import domain.Models.VendorTime
import presentation.profile.ProfileContract
import presentation.profile.ProfilePresenter

class TalkWithTherapistHandler(
    private val profilePresenter: ProfilePresenter,
    private val onAvailableTimesReady:(List<VendorTime>)-> Unit,
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

    override fun showAvailability(availableTimes: List<VendorTime>) {
        onAvailableTimesReady(availableTimes)
    }
}