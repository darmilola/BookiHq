package presentation.main.home

import domain.home.HomeRepositoryImpl
import infrastructure.authentication.AuthenticationRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.authentication.AuthenticationContract
import presentation.viewmodels.UIStates
import com.badoo.reaktive.single.subscribe
import domain.Models.User


class HomepagePresenter(apiService: HttpClient): HomepageContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: HomepageContract.View? = null
    private val homeRepositoryImpl: HomeRepositoryImpl = HomeRepositoryImpl(apiService)

    override fun registerUIContract(view: HomepageContract.View?) {
        contractView = view
    }

    override fun getUserHomepage(userEmail: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(UIStates(loadingVisible = true))
                    homeRepositoryImpl.getUserHomePage(userEmail, "2348111343996")
                        .subscribe(
                            onSuccess = { response ->
                                if (response.status == "success") {
                                    contractView?.showHome(response.homepageInfo, response.vendorStatus)
                                }
                                else{
                                    contractView?.showLce(UIStates(errorOccurred = true, errorMessage = "Unknown"))
                                }
                            },
                            onError = {
                                println("Error ${it.message}")
                                contractView?.showLce(UIStates(errorOccurred = true, errorMessage = it.message.toString()))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Error2 $e")
                contractView?.showLce(UIStates(errorOccurred = true, errorMessage = e.message.toString()))
            }
        }
    }


}