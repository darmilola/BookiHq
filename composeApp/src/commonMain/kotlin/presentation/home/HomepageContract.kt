package presentation.home

import UIStates.AppUIStates
import domain.Models.HomepageInfo
import domain.Models.OrderResourceListEnvelope
import domain.Models.RecommendationResourceListEnvelope

class HomepageContract {
    interface View {
        fun showLoadHomePageLce(appUIStates: AppUIStates)
        fun showHome(homePageInfo: HomepageInfo)
        fun showVendorDayAvailability(dayAvailability: ArrayList<String>)
    }

    interface RecommendationsView {
        fun showRecommendations(recommendations: RecommendationResourceListEnvelope)
        fun onLoadMoreRecommendationsStarted(isSuccess: Boolean = false)
        fun onLoadMoreRecommendationsEnded(isSuccess: Boolean = false)
        fun showLce(appUIStates: AppUIStates, message: String = "")
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun registerRecommendationsUIContract(view: RecommendationsView?)
        abstract fun getUserHomepage(userId: Long)
        abstract fun getRecommendations(vendorId: Long, nextPage: Int = 1)
        abstract fun getMoreRecommendations(vendorId: Long, nextPage: Int = 1)
    }
}