package presentation.DomainViewHandler

import dev.jordond.compass.Place
import presentation.profile.ProfileContract
import presentation.profile.ProfilePresenter
import presentation.viewmodels.ActionUIStateViewModel
import UIStates.ActionUIStates

class ProfileHandler(
    private val profilePresenter: ProfilePresenter,
    private val onUserLocationReady:(Place)-> Unit,
    private val actionUIStateViewModel: ActionUIStateViewModel
) : ProfileContract.View {
    fun init() {
        profilePresenter.registerUIContract(this)
    }
    override fun onProfileDeleted() {}

    override fun onProfileUpdated() {}
    override fun showUserLocation(place: Place) {
        onUserLocationReady(place)
    }

    override fun showActionLce(actionUIStates: ActionUIStates) {
        println("I got here")
        actionUIStateViewModel.switchActionUIState(actionUIStates)
    }

}
