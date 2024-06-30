package presentation.home

import domain.Models.HomepageInfo
import domain.Models.VendorStatusModel
import UIStates.ScreenUIStates

class HomepageContract {
    interface View {
        fun showLce(uiState: ScreenUIStates)
        fun showHome(homePageInfo: HomepageInfo, vendorStatus: ArrayList<VendorStatusModel>)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUserHomepage(userEmail: String, vendorWhatsAppPhone: String)
    }
}