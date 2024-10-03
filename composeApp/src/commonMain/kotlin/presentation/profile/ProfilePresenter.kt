package presentation.profile

import domain.Profile.ProfileRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.badoo.reaktive.single.subscribe
import UIStates.AppUIStates
import domain.Enums.ServerResponse
import domain.Models.PlatformNavigator
import domain.Models.PlatformTime
import domain.Models.User
import domain.Models.Vendor


class ProfilePresenter(apiService: HttpClient): ProfileContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: ProfileContract.View? = null
    private var platformContractView: ProfileContract.PlatformContract? = null
    private var meetingViewContract: ProfileContract.MeetingViewContract? = null
    private var switchVendorContract: ProfileContract.SwitchVendorContract? = null
    private val profileRepositoryImpl: ProfileRepositoryImpl = ProfileRepositoryImpl(apiService)
    override fun registerUIContract(view: ProfileContract.View?) {
       contractView = view
    }

    override fun registerTalkWithTherapistContract(view: ProfileContract.MeetingViewContract?) {
        meetingViewContract = view
    }

    override fun registerSwitchVendorContract(view: ProfileContract.SwitchVendorContract?) {
        switchVendorContract = view
    }

    override fun registerPlatformContract(view: ProfileContract.PlatformContract?) {
        platformContractView = view
    }



    override fun updateProfile(
        firstname: String,
        lastname: String,
        userEmail: String,
        address: String,
        contactPhone: String,
        countryId: Int,
        cityId: Int,
        gender: String,
        profileImageUrl: String
    ) {
        contractView?.showActionLce(AppUIStates(isLoading = true, loadingMessage = "Updating Profile"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    profileRepositoryImpl.updateProfile(firstname, lastname, userEmail, address, contactPhone, countryId, cityId, gender,profileImageUrl)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showActionLce(AppUIStates(isSuccess = true))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showActionLce(AppUIStates(isSuccess = false, errorMessage = "Error Updating Profile"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showActionLce(AppUIStates(isSuccess = false, errorMessage = "Error Updating Profile"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showActionLce(AppUIStates(isSuccess = false, errorMessage = "Error Updating Profile"))
            }
        }
    }

    override fun deleteProfile(userEmail: String) {
        contractView?.showActionLce(AppUIStates(isLoading = true, loadingMessage = "Deleting Profile"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    profileRepositoryImpl.deleteProfile(userEmail)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showActionLce(AppUIStates(isSuccess = true))
                                        contractView?.onProfileDeleted()
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showActionLce(AppUIStates(isSuccess = false, errorMessage = "Error Deleting Profile"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showActionLce(AppUIStates(isSuccess = false, errorMessage = "Error Deleting Profile"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showActionLce(AppUIStates(isSuccess = false, errorMessage = "Error Deleting Profile"))
            }
        }
    }

    override fun getCities(country: String) {
        val cityList: ArrayList<String> = arrayListOf()
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    profileRepositoryImpl.getCountryCities(country)
                        .subscribe(
                            onSuccess = { response ->
                                when (response.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        response.countryCities.cities.map {
                                            cityList.add(it)
                                        }
                                        platformContractView?.showCities(cityList)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        platformContractView?.showCities(cityList)
                                    }
                                }
                            },
                            onError = {
                                platformContractView?.showCities(cityList)
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                platformContractView?.showCities(cityList)
            }
        }
    }

    override fun getVendorAvailability(vendorId: Long) {
        meetingViewContract?.showScreenLce(AppUIStates(isLoading = true, loadingMessage = "Loading Vendor Availability"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    profileRepositoryImpl.getVendorAvailableTimes(vendorId = vendorId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        meetingViewContract?.showScreenLce(AppUIStates(isSuccess = true))
                                        meetingViewContract?.showAvailability(result.vendorTimes, result.platformTimes)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        meetingViewContract?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Vendor Availability"))
                                    }
                                }
                            },
                            onError = {
                                meetingViewContract?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Vendor Availability"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                meetingViewContract?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Vendor Availability"))
            }
        }
    }

    override fun switchVendor(userId: Long, vendorId: Long, action: String, exitReason: String, vendor: Vendor, platformNavigator: PlatformNavigator) {
        switchVendorContract?.showActionLce(AppUIStates(isLoading = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    profileRepositoryImpl.switchVendor(userId, vendorId, action, exitReason)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        platformNavigator.sendCustomerExitNotification(exitReason = exitReason, vendorLogoUrl = vendor.businessLogo!!, fcmToken = vendor.fcmToken!!)
                                        switchVendorContract?.showActionLce(AppUIStates(isSuccess = true))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        switchVendorContract?.showActionLce(AppUIStates(isFailed = true))
                                    }
                                }
                            },
                            onError = {
                                switchVendorContract?.showActionLce(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                switchVendorContract?.showActionLce(AppUIStates(isFailed = true))
            }
        }
    }

    override fun getVendorAccountInfo(vendorId: Long) {
        contractView?.showActionLce(AppUIStates(isLoading = true, loadingMessage = "Loading Vendor"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    profileRepositoryImpl.getVendorAccountInfo(vendorId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showActionLce(AppUIStates(isSuccess = true))
                                        contractView?.showVendorInfo(result.vendorInfo)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showActionLce(AppUIStates(isFailed = true))
                                    }
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

    override fun joinSpa(vendorId: Long, therapistId: Long) {
        contractView?.showActionLce(AppUIStates(isLoading = true, loadingMessage = "Joining Spa"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    profileRepositoryImpl.joinSpa(vendorId, therapistId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showActionLce(AppUIStates(isSuccess = true))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showActionLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showActionLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showActionLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred"))
            }
        }
    }

    override fun getUserLocation(lat: Double, lng: Double) {
        contractView?.showActionLce(AppUIStates(isLoading = true, loadingMessage = "Getting User Location"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    profileRepositoryImpl.reverseGeocode(lat, lng)
                        .subscribe(
                            onSuccess = { result ->
                                if (result?.country?.isNotEmpty() == true){
                                    contractView?.showActionLce(AppUIStates(isSuccess = true))
                                    contractView?.showUserLocation(result)
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


    override fun createMeeting(
        meetingTitle: String,
        userId: Long,
        vendorId: Long,
        serviceStatus: String,
        bookingStatus: String,
        appointmentType: String,
        appointmentTime: Int,
        day: Int,
        month: Int,
        year: Int,
        meetingDescription: String,
        user: User, vendor: Vendor, platformTime: PlatformTime, monthName: String, platformNavigator: PlatformNavigator,
        paymentAmount: Double, paymentMethod: String
    ) {
        meetingViewContract?.showActionLce(AppUIStates(isLoading = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    profileRepositoryImpl.createMeetingAppointment(meetingTitle, userId, vendorId, serviceStatus,bookingStatus, appointmentType,
                        appointmentTime, day, month, year, meetingDescription, paymentAmount, paymentMethod)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        val time = if (platformTime.isAm) platformTime.time+"AM" else platformTime.time+"PM"
                                        platformNavigator.sendMeetingBookingNotification(customerName = user.firstname!!, vendorLogoUrl = vendor.businessLogo!!,
                                            meetingDay = day.toString(), meetingMonth = monthName, meetingYear = year.toString(), meetingTime = time, fcmToken = vendor.fcmToken!!)
                                        meetingViewContract?.showActionLce(AppUIStates(isSuccess = true))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        meetingViewContract?.showActionLce(AppUIStates(isFailed = true))
                                    }
                                }
                            },
                            onError = {
                                meetingViewContract?.showActionLce(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                meetingViewContract?.showActionLce(AppUIStates(isFailed = true))
            }
        }
    }
}