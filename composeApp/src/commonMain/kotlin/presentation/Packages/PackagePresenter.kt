package presentation.Packages

import UIStates.AppUIStates
import com.badoo.reaktive.single.subscribe
import domain.Enums.ServerResponse
import domain.home.HomeRepositoryImpl
import domain.packages.PackageRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.home.HomepageContract

class PackagePresenter(apiService: HttpClient): PackageContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: PackageContract.View? = null
    private val packageRepositoryImpl: PackageRepositoryImpl = PackageRepositoryImpl(apiService)

    override fun registerUIContract(view: PackageContract.View?) {
        contractView = view
    }

    override fun getVendorPackages(vendorId: Long) {
        contractView?.showLoadPackageLce(AppUIStates(isLoading = true, loadingMessage = "Loading Packages"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    packageRepositoryImpl.getVendorPackages(vendorId)
                        .subscribe(
                            onSuccess = { response ->
                                when (response.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showLoadPackageLce(AppUIStates(isSuccess = true))
                                        contractView?.showVendorPackages(response.listItem)
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.showLoadPackageLce(AppUIStates(isEmpty = true, emptyMessage = "Vendor Has No Package"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showLoadPackageLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Packages"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showLoadPackageLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Packages"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLoadPackageLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Packages"))
            }
        }
    }

    override fun getMoreVendorPackages(vendorId: Long, nextPage: Int) {
        contractView?.onLoadMoreAppointmentStarted()
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    packageRepositoryImpl.getVendorPackages(vendorId, nextPage)
                        .subscribe(
                            onSuccess = { response ->
                                when (response.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.onLoadMoreAppointmentEnded()
                                        contractView?.showVendorPackages(response.listItem)
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.onLoadMoreAppointmentEnded()
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.onLoadMoreAppointmentEnded()
                                    }
                                }
                            },
                            onError = {
                                contractView?.onLoadMoreAppointmentEnded()
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onLoadMoreAppointmentEnded()
            }
        }
    }
}