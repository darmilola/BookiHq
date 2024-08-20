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


class HomepagePresenter(apiService: HttpClient): HomepageContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: HomepageContract.View? = null
    private val homeRepositoryImpl: HomeRepositoryImpl = HomeRepositoryImpl(apiService)

    override fun registerUIContract(view: HomepageContract.View?) {
        contractView = view
    }

    override fun getUserHomepageWithStatus(userId: Long, vendorWhatsAppPhone: String) {
        contractView?.showLoadHomePageLce(AppUIStates(isLoading = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    homeRepositoryImpl.getUserHomePageWithStatus(userId, vendorWhatsAppPhone)
                        .subscribe(
                            onSuccess = { response ->
                                println("Response 0 $response")
                                if (response.status == "success") {
                                    contractView?.showLoadHomePageLce(AppUIStates(isSuccess = true))
                                    contractView?.showHomeWithStatus(response.homepageInfo, response.vendorStatusList)
                                }
                                else{
                                    println("Response 1 $response")
                                    contractView?.showLoadHomePageLce(AppUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                println("Response 2 ${it.message}")
                                contractView?.showLoadHomePageLce(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Response 3 ${e.message}")
                contractView?.showLoadHomePageLce(AppUIStates(isFailed = true))
            }
        }
    }

    override fun getUserHomepage(userId: Long) {
        contractView?.showLoadHomePageLce(AppUIStates(isLoading = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    homeRepositoryImpl.getUserHomePage(userId)
                        .subscribe(
                            onSuccess = { response ->
                                if (response.status == "success") {
                                    contractView?.showLoadHomePageLce(AppUIStates(isSuccess = true))
                                    contractView?.showHome(response.homepageInfo)
                                }
                                else{
                                    contractView?.showLoadHomePageLce(AppUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                contractView?.showLoadHomePageLce(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLoadHomePageLce(AppUIStates(isFailed = true))
            }
        }
    }
}