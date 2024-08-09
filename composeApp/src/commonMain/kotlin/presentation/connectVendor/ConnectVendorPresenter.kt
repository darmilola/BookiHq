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
import com.badoo.reaktive.single.doOnAfterSuccess
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import domain.Enums.SharedPreferenceEnum
import domain.Models.City
import domain.Models.PlatformNavigator
import domain.Models.Vendor
import kotlinx.serialization.Transient
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
    override fun connectVendor(userId: Long, vendorId: Long, action: String, userFirstname: String, vendor: Vendor, platformNavigator: PlatformNavigator) {
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
                                print("Response 2 is ${it.message}")
                                it.message?.let { it1 -> contractView?.showLce(ScreenUIStates(errorOccurred = true)) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                print("Response is ${e.message}")
                contractView?.showLce(ScreenUIStates(errorOccurred = true))
            }
        }
    }

    override fun getVendor(country: String, city: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(ScreenUIStates(loadingVisible = true))
                    connectVendorRepositoryImpl.getVendor(country, city)
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
                                    result.listItem.resources = updatedVendorDistance
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

    override fun getMoreVendor(country: String, city: String, nextPage: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onLoadMoreVendorStarted(true)
                    connectVendorRepositoryImpl.getVendor(country,city,nextPage)
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
                                    result.listItem.resources = updatedVendorDistance
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

    override fun searchVendor(country: String, city: String, searchQuery: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(ScreenUIStates(loadingVisible = true))
                    connectVendorRepositoryImpl.searchVendor(country,city,searchQuery)
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
                                    result.listItem.resources = updatedVendorDistance
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

    override fun searchMoreVendors(country: String, city: String, searchQuery: String, nextPage: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onLoadMoreVendorStarted(true)
                    connectVendorRepositoryImpl.searchVendor(country,city,searchQuery,nextPage)
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
                                    result.listItem.resources = updatedVendorDistance
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