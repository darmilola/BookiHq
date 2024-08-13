package presentation.home

import UIStates.ActionUIStates
import domain.home.HomeRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import UIStates.ScreenUIStates
import com.badoo.reaktive.single.subscribe


class HomepagePresenter(apiService: HttpClient): HomepageContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: HomepageContract.View? = null
    private val homeRepositoryImpl: HomeRepositoryImpl = HomeRepositoryImpl(apiService)

    override fun registerUIContract(view: HomepageContract.View?) {
        contractView = view
    }

    override fun getUserHomepageWithStatus(userId: Long, vendorWhatsAppPhone: String) {
        contractView?.showLoadHomePageLce(ActionUIStates(isLoading = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    homeRepositoryImpl.getUserHomePageWithStatus(userId, vendorWhatsAppPhone)
                        .subscribe(
                            onSuccess = { response ->
                                if (response.status == "success") {
                                    contractView?.showLoadHomePageLce(ActionUIStates(isSuccess = true))
                                    contractView?.showHomeWithStatus(response.homepageInfo, response.vendorStatusList)
                                }
                                else{
                                    contractView?.showLoadHomePageLce(ActionUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                contractView?.showLoadHomePageLce(ActionUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLoadHomePageLce(ActionUIStates(isFailed = true))
            }
        }
    }

    override fun getUserHomepage(userId: Long) {
        contractView?.showLoadHomePageLce(ActionUIStates(isLoading = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    homeRepositoryImpl.getUserHomePage(userId)
                        .subscribe(
                            onSuccess = { response ->
                                if (response.status == "success") {
                                    contractView?.showLoadHomePageLce(ActionUIStates(isSuccess = true))
                                    contractView?.showHome(response.homepageInfo)
                                }
                                else{
                                    contractView?.showLoadHomePageLce(ActionUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                contractView?.showLoadHomePageLce(ActionUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLoadHomePageLce(ActionUIStates(isFailed = true))
            }
        }
    }
}