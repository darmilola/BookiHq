package presentation.home

import UIStates.AppUIStates
import domain.Models.HomepageInfo

class HomepageContract {
    interface View {
        fun showLoadHomePageLce(appUIStates: AppUIStates)
        fun showHome(homePageInfo: HomepageInfo)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUserHomepage(userId: Long)
    }
}