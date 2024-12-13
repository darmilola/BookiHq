package presentation.connectVendor


import UIStates.AppUIStates
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.badoo.reaktive.single.subscribe
import domain.connectVendor.ConnectVendorRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import domain.Enums.ServerResponse
import domain.Enums.SharedPreferenceEnum
import kotlinx.coroutines.runBlocking
import utils.getDistanceFromCustomer
import utils.getMinuteDrive

class ConnectVendorPresenter(apiService: HttpClient): ConnectVendorContract.Presenter() {
    private val scope: CoroutineScope = MainScope()
    private var contractView: ConnectVendorContract.View? = null
    private var connectVendorRepositoryImpl: ConnectVendorRepositoryImpl = ConnectVendorRepositoryImpl(apiService)
    override fun registerUIContract(view: ConnectVendorContract.View?) {
         contractView = view
    }
    override fun connectVendor(userId: Long, vendorId: Long, action: String) {
        contractView?.showActionLce(AppUIStates(isLoading = true, loadingMessage = "Connecting Vendor"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    connectVendorRepositoryImpl.connectVendor(userId,vendorId,action)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showActionLce(AppUIStates(isSuccess = true))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showActionLce(AppUIStates(isFailed = true, errorMessage = "Error Connecting Vendor"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showActionLce(AppUIStates(isFailed = true, errorMessage = "Error Connecting Vendor"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showActionLce(AppUIStates(isFailed = true, errorMessage = "Error Connecting Vendor"))
            }
        }
    }

    override fun getVendor(country: String, city: String, connectedVendor: Long) {
        contractView?.showScreenLce(AppUIStates(isLoading = true, loadingMessage = "Getting Vendors"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    connectVendorRepositoryImpl.getVendor(country, city, connectedVendor,1)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showScreenLce(AppUIStates(isSuccess = true))
                                        contractView?.showVendors(result.listItem,isFromSearch = false, isLoadMore = false)
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.showScreenLce(AppUIStates(isEmpty = true, emptyMessage = "No Vendor Available In Your Area"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Vendors"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Vendors"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Vendors"))
            }
        }
    }

    override fun getMoreVendor(country: String, city: String, connectedVendor: Long, nextPage: Int) {
        contractView?.onLoadMoreVendorStarted(true)
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    connectVendorRepositoryImpl.getVendor(country,city,connectedVendor,nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.onLoadMoreVendorEnded(true)
                                        contractView?.showVendors(result.listItem, isFromSearch = false, isLoadMore = true)

                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Vendors"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Vendors"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Vendors"))
            }
        }
    }

    override fun searchVendor(country: String, connectedVendor: Long, searchQuery: String) {
        contractView?.showScreenLce(AppUIStates(isLoading = true, loadingMessage = "Searching Vendor"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    connectVendorRepositoryImpl.searchVendor(country,connectedVendor,searchQuery)
                        .subscribe(
                            onSuccess = { result ->
                                    when (result.status) {
                                        ServerResponse.SUCCESS.toPath() -> {
                                            contractView?.showScreenLce(AppUIStates(isSuccess = true))
                                            contractView?.showVendors(result.listItem, isFromSearch = true, isLoadMore = false)

                                        }
                                        ServerResponse.EMPTY.toPath() -> {
                                            contractView?.showScreenLce(AppUIStates(isEmpty = true, emptyMessage = "Vendor Not Found"))
                                        }
                                        ServerResponse.FAILURE.toPath() -> {
                                            contractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Vendors"))
                                        }
                                    }
                            },
                            onError = {
                                contractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Vendors"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Vendors"))
            }
        }
    }

    override fun searchMoreVendors(country: String,connectedVendor: Long, searchQuery: String, nextPage: Int) {
        contractView?.onLoadMoreVendorStarted(true)
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    connectVendorRepositoryImpl.searchVendor(country,connectedVendor,searchQuery,nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.onLoadMoreVendorEnded(true)
                                        contractView?.showVendors(
                                            result.listItem,
                                            isFromSearch = true,
                                            isLoadMore = true
                                        )

                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.onLoadMoreVendorEnded(false)
                                    }
                                }
                            },
                            onError = {
                                contractView?.onLoadMoreVendorEnded(false)
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onLoadMoreVendorEnded(false)
            }
        }
    }

    override fun viewVendors(country: String, city: String, connectedVendor: Long) {
        contractView?.showScreenLce(AppUIStates(isLoading = true, loadingMessage = "Loading Parlors"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    connectVendorRepositoryImpl.viewVendors(country,city,connectedVendor)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showScreenLce(AppUIStates(isSuccess = true))
                                        contractView?.showVendorsView(result.nearbyVendors, result.newVendors)

                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.showScreenLce(AppUIStates(isEmpty = true, emptyMessage = "Vendor Not Found"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Vendors"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Vendors"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Vendors"))
            }
        }
    }
}