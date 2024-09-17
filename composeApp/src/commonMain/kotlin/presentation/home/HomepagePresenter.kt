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


class HomepagePresenter(apiService: HttpClient): HomepageContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: HomepageContract.View? = null
    private val homeRepositoryImpl: HomeRepositoryImpl = HomeRepositoryImpl(apiService)

    override fun registerUIContract(view: HomepageContract.View?) {
        contractView = view
    }

    override fun getUserHomepageWithStatus(userId: Long, vendorWhatsAppPhone: String) {
        println("Called Again Here...")
        contractView?.showLoadHomePageLce(AppUIStates(isLoading = true, loadingMessage = "Loading Home"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    homeRepositoryImpl.getUserHomePageWithStatus(userId, vendorWhatsAppPhone)
                        .subscribe(
                            onSuccess = { response ->
                                when (response.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showLoadHomePageLce(AppUIStates(isSuccess = true))
                                        contractView?.showHomeWithStatus(response.homepageInfo, response.vendorStatusList)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showLoadHomePageLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Home"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showLoadHomePageLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Home"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLoadHomePageLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Home"))
            }
        }
    }

    override fun getUserHomepage(userId: Long) {
        contractView?.showLoadHomePageLce(AppUIStates(isLoading = true, loadingMessage = "Loading Home"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    homeRepositoryImpl.getUserHomePage(userId)
                        .subscribe(
                            onSuccess = { response ->
                                when (response.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showLoadHomePageLce(AppUIStates(isSuccess = true))
                                        contractView?.showHome(response.homepageInfo)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showLoadHomePageLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Home"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showLoadHomePageLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Home"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLoadHomePageLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Home"))
            }
        }
    }
}