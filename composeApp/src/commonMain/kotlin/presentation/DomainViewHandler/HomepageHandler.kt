package presentation.DomainViewHandler

import domain.Models.HomepageInfo
import domain.Models.VendorStatusModel
import presentation.home.HomepageContract
import presentation.home.HomepagePresenter
import presentation.viewmodels.UIStateViewModel
import UIStates.ScreenUIStates

class HomepageHandler(
    private val uiStateViewModel: UIStateViewModel,
    private val homepagePresenter: HomepagePresenter,
    private val onHomeInfoAvailable: (HomepageInfo, List<VendorStatusModel>) -> Unit) : HomepageContract.View {
    fun init() {
        homepagePresenter.registerUIContract(this)
    }

    override fun showLce(uiState: ScreenUIStates) {
        uiStateViewModel.switchScreenUIState(uiState)
    }


    override fun showHome(homePageInfo: HomepageInfo) {
        val emptyStatusList = arrayListOf<VendorStatusModel>()
        onHomeInfoAvailable(homePageInfo, emptyStatusList)
    }

    override fun showHomeWithStatus(homePageInfo: HomepageInfo, vendorStatus: List<VendorStatusModel>) {
        val filteredStatusList = arrayListOf<VendorStatusModel>()
        vendorStatus.map {
            if (it.statusText != null || it.statusVideoModel != null || it.statusImage != null){
                filteredStatusList.add(it)
            }
        }
        onHomeInfoAvailable(homePageInfo, filteredStatusList)
    }
}
