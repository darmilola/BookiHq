package presentation.DomainViewHandler

import presentation.profile.ProfileContract
import presentation.profile.ProfilePresenter
import presentation.viewmodels.PlatformViewModel

class PlatformHandler(
    private val profilePresenter: ProfilePresenter,
    private val platformViewModel: PlatformViewModel
) : ProfileContract.PlatformContract {
    fun init() {
        profilePresenter.registerPlatformContract(this)
    }

    override fun showPlatformCities(cities: ArrayList<String>) {
        platformViewModel.setPlatformCities(cities)
    }

}