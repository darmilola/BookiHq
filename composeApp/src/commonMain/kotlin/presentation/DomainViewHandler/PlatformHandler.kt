package presentation.DomainViewHandler

import presentation.profile.ProfileContract
import presentation.profile.ProfilePresenter
import presentation.viewmodels.CityViewModel

class PlatformHandler(
    private val profilePresenter: ProfilePresenter,
    private val cityViewModel: CityViewModel
) : ProfileContract.PlatformContract {
    fun init() {
        profilePresenter.registerPlatformContract(this)
    }

    override fun showCities(cities: ArrayList<String>) {
        cityViewModel.setCities(cities)
    }

}