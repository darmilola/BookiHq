package presentation.main.home

import domain.Models.HomepageInfo
import presentation.viewmodels.UIStates

class HomepageContract {
    interface View {
        fun showLce(uiState: UIStates, message: String = "")
        fun showHome(homePageInfo: HomepageInfo)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUserHomepage(userEmail: String)
    }
}