package presentation.DomainViewHandler

import domain.Models.HomepageInfo
import domain.Models.VendorStatusModel
import presentation.home.HomepageContract
import presentation.home.HomepagePresenter
import presentation.viewmodels.ScreenUIStateViewModel
import UIStates.ScreenUIStates

class HomepageHandler(
    private val screenUiStateViewModel: ScreenUIStateViewModel,
    private val homepagePresenter: HomepagePresenter,
    private val onHomeInfoAvailable: (HomepageInfo, ArrayList<VendorStatusModel>) -> Unit) : HomepageContract.View {
    fun init() {
        homepagePresenter.registerUIContract(this)
    }

    override fun showLce(uiState: ScreenUIStates) {
        screenUiStateViewModel.switchScreenUIState(uiState)
    }
    override fun showHome(homePageInfo: HomepageInfo, vendorStatus: ArrayList<VendorStatusModel>) {
        onHomeInfoAvailable(homePageInfo, vendorStatus)
    }
}
