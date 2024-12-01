package presentation.DomainViewHandler

import dev.jordond.compass.Place
import presentation.profile.ProfileContract
import presentation.profile.ProfilePresenter
import presentation.viewmodels.PerformedActionUIStateViewModel
import UIStates.AppUIStates
import domain.Models.Vendor

class ProfileHandler(
    private val profilePresenter: ProfilePresenter,
    private val onUserLocationReady:(Place)-> Unit,
    private val onVendorInfoReady:(Vendor)-> Unit,
    private val performedActionUIStateViewModel: PerformedActionUIStateViewModel
) : ProfileContract.View {
    fun init() {
        profilePresenter.registerUIContract(this)
    }
    override fun onProfileDeleted() {}

    override fun onProfileUpdated() {}
    override fun showUserLocation(place: Place) {
        onUserLocationReady(place)
    }

    override fun showVendorInfo(vendor: Vendor) {
        onVendorInfoReady(vendor)
    }

    override fun showActionLce(appUIStates: AppUIStates) {
        performedActionUIStateViewModel.switchActionUIState(appUIStates)
    }

}
