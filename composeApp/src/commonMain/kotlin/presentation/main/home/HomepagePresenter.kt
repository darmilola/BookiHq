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

    override fun getUserHomepage(userEmail: String, connectedVendor: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(UIStates(loadingVisible = true))
                    homeRepositoryImpl.getUserHomePage(userEmail, connectedVendor)
                        .subscribe(
                            onSuccess = { response ->
                                if (response.status == "success") {
                                    println(response)
                                    contractView?.showLce(UIStates(contentVisible = true))
                                    contractView?.showHome(response)
                                }
                                else{
                                    contractView?.showLce(UIStates(errorOccurred = true, errorMessage = "Unknown"))
                                }
                            },
                            onError = {
                                println(it.message)
                                contractView?.showLce(UIStates(errorOccurred = true, errorMessage = it.message.toString()))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(UIStates(errorOccurred = true, errorMessage = e.message.toString()))
            }
        }
    }


}