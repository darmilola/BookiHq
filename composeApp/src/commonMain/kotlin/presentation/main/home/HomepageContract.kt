package presentation.main.home

import domain.Models.HomepageInfo
import domain.Models.VendorStatusModel
import presentation.viewmodels.UIStates

class HomepageContract {
    interface View {
        fun showLce(uiState: UIStates, message: String = "")
        fun showHome(homePageInfo: HomepageInfo, vendorStatus: ArrayList<VendorStatusModel>)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUserHomepage(userEmail: String)
    }
}