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
import domain.Enums.SharedPreferenceEnum
import kotlinx.coroutines.runBlocking
import utils.getDistanceFromCustomer

class ConnectVendorPresenter(apiService: HttpClient): ConnectVendorContract.Presenter() {
    private val scope: CoroutineScope = MainScope()
    private var contractView: ConnectVendorContract.View? = null
    private val preferenceSettings: Settings = Settings()
    private var userLatitude: Double = 0.0
    private var userLongitude: Double = 0.0
    private var connectVendorRepositoryImpl: ConnectVendorRepositoryImpl = ConnectVendorRepositoryImpl(apiService)
    override fun registerUIContract(view: ConnectVendorContract.View?) {
         contractView = view
    }
    override fun connectVendor(userId: Long, vendorId: Long, action: String, userFirstname: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showActionLce(AppUIStates(isLoading = true))
                    connectVendorRepositoryImpl.connectVendor(userId,vendorId,action)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showActionLce(AppUIStates(isSuccess = true))
                                }
                                else{
                                    contractView?.showActionLce(AppUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                contractView?.showActionLce(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showActionLce(AppUIStates(isFailed = true))
            }
        }
    }

    override fun getVendor(country: String) {
        println("Country is $country")
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showScreenLce(AppUIStates(isLoading = true))
                    connectVendorRepositoryImpl.getVendor(country)
                        .subscribe(
                            onSuccess = { result ->
                               userLatitude =  preferenceSettings[SharedPreferenceEnum.LATITUDE.toPath(), "0.0"].toDouble()
                               userLongitude =  preferenceSettings[SharedPreferenceEnum.LONGITUDE.toPath(), "0.0"].toDouble()
                                if (result.status == "success"){
                                    val updatedVendorDistance = result.listItem.resources!!.map { vendor ->
                                        val distance = getDistanceFromCustomer(userLat = userLatitude, userLong = userLongitude, vendorLat = vendor.latitude, vendorLong = vendor.longitude)
                                        vendor.distanceFromCustomer = distance
                                        vendor
                                    }
                                    val updatedSortedDistance = updatedVendorDistance.sortedBy{ it.distanceFromCustomer }
                                    result.listItem.resources = updatedSortedDistance
                                    contractView?.showScreenLce(AppUIStates(isSuccess = true))
                                    contractView?.showVendors(result.listItem,isFromSearch = false, isLoadMore = false)
                                }
                                else{
                                    println("Response 3 $result")
                                    contractView?.showScreenLce(AppUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                println("Response 4 ${it.message}")
                                contractView?.showScreenLce(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Response 5 ${e.message}")
                contractView?.showScreenLce(AppUIStates(isFailed = true))
            }
        }
    }

    override fun getMoreVendor(country: String, nextPage: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onLoadMoreVendorStarted(true)
                    connectVendorRepositoryImpl.getVendor(country, nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                userLatitude =  preferenceSettings[SharedPreferenceEnum.LATITUDE.toPath(), "0.0"].toDouble()
                                userLongitude =  preferenceSettings[SharedPreferenceEnum.LONGITUDE.toPath(), "0.0"].toDouble()
                                if (result.status == "success"){
                                    val updatedVendorDistance = result.listItem.resources!!.map { vendor ->
                                        val distance = getDistanceFromCustomer(userLat = userLatitude, userLong = userLongitude, vendorLat = vendor.latitude, vendorLong = vendor.longitude)
                                        vendor.distanceFromCustomer = distance
                                        vendor
                                    }
                                    val updatedSortedDistance = updatedVendorDistance.sortedBy{ it.distanceFromCustomer }
                                    result.listItem.resources = updatedSortedDistance
                                    contractView?.onLoadMoreVendorEnded(true)
                                    contractView?.showVendors(result.listItem, isFromSearch = false, isLoadMore = true)
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
                    contractView?.showScreenLce(AppUIStates(isLoading = true))
                    connectVendorRepositoryImpl.searchVendor(country, searchQuery)
                        .subscribe(
                            onSuccess = { result ->
                                userLatitude =  preferenceSettings[SharedPreferenceEnum.LATITUDE.toPath(), "0.0"].toDouble()
                                userLongitude =  preferenceSettings[SharedPreferenceEnum.LONGITUDE.toPath(), "0.0"].toDouble()
                                if (result.status == "success"){
                                    val updatedVendorDistance = result.listItem.resources!!.map { vendor ->
                                        val distance = getDistanceFromCustomer(userLat = userLatitude, userLong = userLongitude, vendorLat = vendor.latitude, vendorLong = vendor.longitude)
                                        vendor.distanceFromCustomer = distance
                                        vendor
                                    }
                                    val updatedSortedDistance = updatedVendorDistance.sortedBy{ it.distanceFromCustomer }
                                    result.listItem.resources = updatedSortedDistance
                                    contractView?.showScreenLce(AppUIStates(isSuccess = true))
                                    contractView?.showVendors(result.listItem, isFromSearch = true, isLoadMore = false)
                                }
                                else{
                                    contractView?.showScreenLce(AppUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                contractView?.showScreenLce(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showScreenLce(AppUIStates(isFailed = true))
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
                                runBlocking {
                                    userLatitude =
                                        preferenceSettings[SharedPreferenceEnum.LATITUDE.toPath(), "0.0"].toDouble()
                                    userLongitude =
                                        preferenceSettings[SharedPreferenceEnum.LONGITUDE.toPath(), "0.0"].toDouble()
                                    if (result.status == "success") {
                                        val updatedVendorDistance =
                                            result.listItem.resources!!.map { vendor ->
                                                val distance = getDistanceFromCustomer(
                                                    userLat = userLatitude,
                                                    userLong = userLongitude,
                                                    vendorLat = vendor.latitude,
                                                    vendorLong = vendor.longitude
                                                )
                                                vendor.distanceFromCustomer = distance
                                                vendor
                                            }
                                        val updatedSortedDistance = updatedVendorDistance.sortedBy{ it.distanceFromCustomer }
                                        result.listItem.resources = updatedSortedDistance
                                        contractView?.onLoadMoreVendorEnded(true)
                                        contractView?.showVendors(
                                            result.listItem,
                                            isFromSearch = true,
                                            isLoadMore = true
                                        )
                                    } else {
                                        contractView?.onLoadMoreVendorEnded(false)
                                    }
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