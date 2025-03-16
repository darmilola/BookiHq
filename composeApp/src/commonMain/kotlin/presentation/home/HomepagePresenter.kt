package presentation.home

import UIStates.AppUIStates
import domain.home.HomeRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.badoo.reaktive.single.subscribe
import domain.Enums.ServerResponse
import utils.calculateServicesGridList


class HomepagePresenter(apiService: HttpClient): HomepageContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: HomepageContract.View? = null
    private var recommendationsView: HomepageContract.RecommendationsView? = null
    private val homeRepositoryImpl: HomeRepositoryImpl = HomeRepositoryImpl(apiService)

    override fun registerUIContract(view: HomepageContract.View?) {
        contractView = view
    }

    override fun registerRecommendationsUIContract(view: HomepageContract.RecommendationsView?) {
        recommendationsView = view
    }

    override fun getUserHomepage(userId: Long) {
        contractView?.showLoadHomePageLce(AppUIStates(isLoading = true, loadingMessage = "Loading Home"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    homeRepositoryImpl.getUserHomePage(userId)
                        .subscribe(
                            onSuccess = { response ->
                                println("Error 0 $response")
                                when (response.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        val servicesGridList = calculateServicesGridList(response.homepageInfo.vendorServices!!)
                                        response.homepageInfo.servicesGridList = servicesGridList
                                        val dayAvailabilityString = arrayListOf<String>()
                                        val vendorDayAvailability = response.homepageInfo.dayAvailability
                                        for (item in vendorDayAvailability!!){
                                            dayAvailabilityString.add(item.platformDay!!.day!!)
                                        }
                                        contractView?.showHome(response.homepageInfo)
                                        contractView?.showVendorDayAvailability(dayAvailabilityString)
                                        contractView?.showLoadHomePageLce(AppUIStates(isSuccess = true))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showLoadHomePageLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Home"))
                                    }
                                }
                            },
                            onError = {
                                println("Error 1 ${it.message}")
                                contractView?.showLoadHomePageLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Home"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Error 2 ${e.message}")
                contractView?.showLoadHomePageLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Home"))
            }
        }
    }

    override fun getRecommendations(vendorId: Long, nextPage: Int) {
        recommendationsView?.showLce(AppUIStates(isLoading = true, loadingMessage = "Getting Recommendations"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    homeRepositoryImpl.getRecommendations(vendorId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        recommendationsView?.showLce(AppUIStates(isSuccess = true))
                                        recommendationsView?.showRecommendations(result.listItem)
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        recommendationsView?.showLce(AppUIStates(isEmpty = true, emptyMessage = "No Recommendations Available"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        recommendationsView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Recommendations"))
                                    }
                                }
                            },
                            onError = {
                                recommendationsView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Recommendations"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                recommendationsView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Recommendations"))
            }
        }
    }

    override fun getMoreRecommendations(vendorId: Long, nextPage: Int) {
        recommendationsView?.onLoadMoreRecommendationsStarted(true)
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    homeRepositoryImpl.getRecommendations(vendorId, nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        recommendationsView?.onLoadMoreRecommendationsEnded(true)
                                        recommendationsView?.showRecommendations(result.listItem)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        recommendationsView?.onLoadMoreRecommendationsEnded(true)
                                    }
                                }
                            },
                            onError = {
                                recommendationsView?.onLoadMoreRecommendationsEnded(true)
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                recommendationsView?.onLoadMoreRecommendationsEnded(true)
            }
        }
    }
}