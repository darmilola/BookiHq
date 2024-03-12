package presentation.main.home

import domain.Models.HomePageResponse
import domain.Models.User
import presentation.authentication.AuthenticationContract
import presentation.viewmodels.UIStates

class HomepageContract {
    interface View {
        fun showLce(uiState: UIStates, message: String = "")
        fun showHome(homePageResponse: HomePageResponse)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: HomepageContract.View?)
        abstract fun getUserHomepage(userEmail: String, connectedVendor: Int)
    }
}