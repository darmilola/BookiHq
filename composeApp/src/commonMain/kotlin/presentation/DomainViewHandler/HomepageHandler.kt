package presentation.DomainViewHandler

import UIStates.AppUIStates
import domain.Models.HomepageInfo
import presentation.home.HomepageContract
import presentation.home.HomepagePresenter
import presentation.viewmodels.LoadingScreenUIStateViewModel

class HomepageHandler(
    private val loadingScreenUIStateViewModel: LoadingScreenUIStateViewModel,
    private val homepagePresenter: HomepagePresenter,
    private val onHomeInfoAvailable: (HomepageInfo) -> Unit) : HomepageContract.View {
    fun init() {
        homepagePresenter.registerUIContract(this)
    }

    override fun showLoadHomePageLce(appUIStates: AppUIStates) {
        loadingScreenUIStateViewModel.switchScreenUIState(appUIStates)
    }
    override fun showHome(homePageInfo: HomepageInfo) {
        onHomeInfoAvailable(homePageInfo)
    }
}
