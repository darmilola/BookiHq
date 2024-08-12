package presentation.home

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
import domain.Models.VendorStatusModel


class HomepagePresenter(apiService: HttpClient): HomepageContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: HomepageContract.View? = null
    private val homeRepositoryImpl: HomeRepositoryImpl = HomeRepositoryImpl(apiService)

    override fun registerUIContract(view: HomepageContract.View?) {
        contractView = view
    }

    override fun getUserHomepageWithStatus(userId: Long, vendorWhatsAppPhone: String) {
        println("User ID $userId")
        println(vendorWhatsAppPhone)
        contractView?.showLce(ScreenUIStates(loadingVisible = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    homeRepositoryImpl.getUserHomePageWithStatus(userId, vendorWhatsAppPhone)
                        .subscribe(
                            onSuccess = { response ->
                                println("My response ${response}")
                                if (response.status == "success") {
                                    contractView?.showLce(ScreenUIStates(contentVisible = true))
                                    contractView?.showHomeWithStatus(response.homepageInfo, response.vendorStatusList)
                                }
                                else{
                                    contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
                                }
                            },
                            onError = {
                                println("Response 2 ${it.message}")
                                contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Response 3 ${e.message}")
                contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
            }
        }
    }

    override fun getUserHomepage(userId: Long) {
        contractView?.showLce(ScreenUIStates(loadingVisible = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    homeRepositoryImpl.getUserHomePage(userId)
                        .subscribe(
                            onSuccess = { response ->
                                println("Response $response")
                                if (response.status == "success") {
                                    contractView?.showLce(ScreenUIStates(contentVisible = true))
                                    contractView?.showHome(response.homepageInfo)
                                }
                                else{
                                    contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
                                }
                            },
                            onError = {
                                println("Response 2 ${it.message}")
                                contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Response 3 ${e.message}")
                contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
            }
        }
    }
}