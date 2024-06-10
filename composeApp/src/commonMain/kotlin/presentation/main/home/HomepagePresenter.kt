package presentation.main.home

import domain.home.HomeRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.viewmodels.ScreenUIStates
import com.badoo.reaktive.single.subscribe
import domain.Models.VendorStatusModel


class HomepagePresenter(apiService: HttpClient): HomepageContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: HomepageContract.View? = null
    private val homeRepositoryImpl: HomeRepositoryImpl = HomeRepositoryImpl(apiService)

    override fun registerUIContract(view: HomepageContract.View?) {
        contractView = view
    }

    override fun getUserHomepage(userEmail: String, vendorWhatsAppPhone: String) {
        val filteredStatusList = arrayListOf<VendorStatusModel>()
        contractView?.showLce(ScreenUIStates(loadingVisible = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    homeRepositoryImpl.getUserHomePage(userEmail, vendorWhatsAppPhone)
                        .subscribe(
                            onSuccess = { response ->
                                if (response.status == "success") {
                                    response.vendorStatus.map { it ->
                                        it.apply {
                                            if (this.statusImage != null && this.statusImage.imageUrl != ""){
                                               filteredStatusList.add(it.copy(isValidStatusType = true))
                                            }
                                            else if (this.statusVideo != null && this.statusVideo.videoUrl != ""){
                                                filteredStatusList.add(it.copy(isValidStatusType = true))
                                            }
                                        }
                                    }
                                    contractView?.showLce(ScreenUIStates(contentVisible = true))
                                    contractView?.showHome(response.homepageInfo, filteredStatusList)
                                }
                                else{
                                    contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
                                }
                            },
                            onError = {
                                contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
            }
        }
    }
}