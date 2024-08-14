package presentation.home

import UIStates.AppUIStates
import domain.Models.HomepageInfo
import domain.Models.VendorStatusModel

class HomepageContract {
    interface View {
        fun showLoadHomePageLce(appUIStates: AppUIStates)
        fun showHomeWithStatus(homePageInfo: HomepageInfo, vendorStatus: List<VendorStatusModel>)
        fun showHome(homePageInfo: HomepageInfo)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUserHomepage(userId: Long)
        abstract fun getUserHomepageWithStatus(userId: Long, vendorWhatsAppPhone: String)
    }
}