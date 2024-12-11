package presentation.DomainViewHandler

import UIStates.AppUIStates
import domain.Models.OrderResourceListEnvelope
import domain.Models.RecommendationResourceListEnvelope
import presentation.Orders.OrderContract
import presentation.Orders.OrderPresenter
import presentation.home.HomepageContract
import presentation.home.HomepagePresenter
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.OrdersResourceListEnvelopeViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.RecommendationsResourceListEnvelopeViewModel

class RecommendationsHandler(
    private val recommendationsResourceListEnvelopeViewModel: RecommendationsResourceListEnvelopeViewModel,
    private val loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel,
    private val homepagePresenter: HomepagePresenter
) : HomepageContract.RecommendationsView {
    fun init() {
        homepagePresenter.registerRecommendationsUIContract(this)
    }

    override fun showRecommendations(recommendations: RecommendationResourceListEnvelope) {
        if (recommendationsResourceListEnvelopeViewModel.resources.value.isNotEmpty()) {
            val recommendationsList = recommendationsResourceListEnvelopeViewModel.resources.value
            recommendationsList.addAll(recommendations.resources!!)
            recommendations.prevPageUrl?.let { recommendationsResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            recommendations.nextPageUrl?.let { recommendationsResourceListEnvelopeViewModel.setNextPageUrl(it) }
            recommendations.currentPage?.let { recommendationsResourceListEnvelopeViewModel.setCurrentPage(it) }
            recommendations.totalItemCount?.let { recommendationsResourceListEnvelopeViewModel.setTotalItemCount(it) }
            recommendations.displayedItemCount?.let { recommendationsResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
            recommendationsResourceListEnvelopeViewModel.setResources(recommendationsList)
        } else {
            recommendationsResourceListEnvelopeViewModel.setResources(recommendations.resources)
            recommendations.prevPageUrl?.let { recommendationsResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            recommendations.nextPageUrl?.let { recommendationsResourceListEnvelopeViewModel.setNextPageUrl(it) }
            recommendations.currentPage?.let { recommendationsResourceListEnvelopeViewModel.setCurrentPage(it) }
            recommendations.totalItemCount?.let { recommendationsResourceListEnvelopeViewModel.setTotalItemCount(it) }
            recommendations.displayedItemCount?.let { recommendationsResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
    }

    override fun onLoadMoreRecommendationsStarted(isSuccess: Boolean) {
        recommendationsResourceListEnvelopeViewModel.setLoadingMore(true)
    }

    override fun onLoadMoreRecommendationsEnded(isSuccess: Boolean) {
        recommendationsResourceListEnvelopeViewModel.setLoadingMore(false)
    }

    override fun showLce(appUIStates: AppUIStates, message: String) {
        loadingScreenUiStateViewModel.switchScreenUIState(appUIStates)
    }

}