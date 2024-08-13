package presentation.DomainViewHandler

import UIStates.ActionUIStates
import domain.Models.HomepageInfo
import domain.Models.VendorStatusModel
import presentation.home.HomepageContract
import presentation.home.HomepagePresenter
import presentation.viewmodels.UIStateViewModel
import UIStates.ScreenUIStates
import presentation.viewmodels.ActionUIStateViewModel

class HomepageHandler(
    private val actionUIStateViewModel: ActionUIStateViewModel,
    private val homepagePresenter: HomepagePresenter,
    private val onHomeInfoAvailable: (HomepageInfo, List<VendorStatusModel>) -> Unit) : HomepageContract.View {
    fun init() {
        homepagePresenter.registerUIContract(this)
    }

    override fun showLoadHomePageLce(actionUIStates: ActionUIStates) {
        actionUIStateViewModel.switchActionLoadHomepageUiState(actionUIStates)
    }


    override fun showHome(homePageInfo: HomepageInfo) {
        val emptyStatusList = arrayListOf<VendorStatusModel>()
        onHomeInfoAvailable(homePageInfo, emptyStatusList)
    }

    override fun showHomeWithStatus(homePageInfo: HomepageInfo, vendorStatus: List<VendorStatusModel>) {
        val filteredStatusList = arrayListOf<VendorStatusModel>()
        vendorStatus.map {
            if (it.statusText != null){
                filteredStatusList.add(it)
            }
            else if (it.statusVideoModel != null && it.statusVideoModel.videoUrl.isNotEmpty()){
                filteredStatusList.add(it)
            }
            else if (it.statusImage != null && it.statusImage.imageUrl.isNotEmpty()){
                filteredStatusList.add(it)
            } else {

            }
        }
        onHomeInfoAvailable(homePageInfo, filteredStatusList)
    }
}
