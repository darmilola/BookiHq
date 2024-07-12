package presentation.connectVendor


import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.badoo.reaktive.single.subscribe
import domain.connectVendor.ConnectVendorRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import UIStates.ScreenUIStates

class ConnectVendorPresenter(apiService: HttpClient): ConnectVendorContract.Presenter() {
    private val scope: CoroutineScope = MainScope()
    private var contractView: ConnectVendorContract.View? = null
    private var connectVendorRepositoryImpl: ConnectVendorRepositoryImpl = ConnectVendorRepositoryImpl(apiService)
    override fun registerUIContract(view: ConnectVendorContract.View?) {
         contractView = view
    }
    override fun connectVendor(userId: Long, vendorId: Long, action: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(ScreenUIStates(loadingVisible = true))
                    connectVendorRepositoryImpl.connectVendor(userId,vendorId,action)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(ScreenUIStates(contentVisible = true))
                                    contractView?.onVendorConnected(userId)
                                }
                                else{
                                    contractView?.showLce(ScreenUIStates(errorOccurred = true))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showLce(ScreenUIStates(errorOccurred = true)) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(ScreenUIStates(errorOccurred = true))
            }
        }
    }

    override fun getVendor(country: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(ScreenUIStates(loadingVisible = true))
                    connectVendorRepositoryImpl.getVendor(country)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(ScreenUIStates(contentVisible = true))
                                    contractView?.showVendors(result.listItem)
                                }
                                else{
                                    contractView?.showLce(ScreenUIStates(errorOccurred = true))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showLce(ScreenUIStates(errorOccurred = true)) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(ScreenUIStates(errorOccurred = true))
            }
        }
    }

    override fun getMoreVendor(country: String, nextPage: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onLoadMoreVendorStarted(true)
                    connectVendorRepositoryImpl.getVendor(country,nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.onLoadMoreVendorEnded(true)
                                    contractView?.showVendors(result.listItem)
                                }
                                else{
                                    contractView?.onLoadMoreVendorEnded(false)
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.onLoadMoreVendorEnded(false) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onLoadMoreVendorEnded(false)
            }
        }
    }

    override fun searchVendor(country: String, searchQuery: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(ScreenUIStates(loadingVisible = true))
                    connectVendorRepositoryImpl.searchVendor(country,searchQuery)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(ScreenUIStates(contentVisible = true))
                                    contractView?.showVendors(result.listItem, isFromSearch = true)
                                }
                                else{
                                    contractView?.showLce(ScreenUIStates(errorOccurred = true))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showLce(ScreenUIStates(errorOccurred = true)) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(ScreenUIStates(errorOccurred = true))
            }
        }
    }

    override fun searchMoreVendors(country: String, searchQuery: String, nextPage: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onLoadMoreVendorStarted(true)
                    connectVendorRepositoryImpl.searchVendor(country,searchQuery,nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.onLoadMoreVendorEnded(true)
                                    contractView?.showVendors(result.listItem, isFromSearch = true)
                                }
                                else{
                                    contractView?.onLoadMoreVendorEnded(false)
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.onLoadMoreVendorEnded(false) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onLoadMoreVendorEnded(false)
            }
        }
    }


}