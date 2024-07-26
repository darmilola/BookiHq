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
import UIStates.ActionUIStates
import UIStates.ScreenUIStates
import domain.Enums.ServerResponseEnum
import domain.Models.PlatformNavigator
import domain.Models.PlatformTime
import domain.Models.User
import domain.Models.Vendor


class ProfilePresenter(apiService: HttpClient): ProfileContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: ProfileContract.View? = null
    private var meetingView: ProfileContract.MeetingViewContract? = null
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
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showActionLce(ActionUIStates(isLoading = true))
                    profileRepositoryImpl.updateProfile(firstname, lastname, userEmail, address, contactPhone, countryId, cityId, gender,profileImageUrl)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showActionLce(ActionUIStates(isSuccess = true))
                                    //contractView?.onProfileUpdated()
                                }
                                else{
                                    contractView?.showActionLce(ActionUIStates(isSuccess = false))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showActionLce(ActionUIStates(isSuccess = false)) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showActionLce(ActionUIStates(isSuccess = false))
            }
        }
    }

    override fun deleteProfile(userEmail: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showActionLce(ActionUIStates(isLoading = true))
                    profileRepositoryImpl.deleteProfile(userEmail)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showActionLce(ActionUIStates( isSuccess = true))
                                    contractView?.onProfileDeleted()
                                }
                                else{
                                    contractView?.showActionLce(ActionUIStates(isSuccess = false))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showActionLce(ActionUIStates(isSuccess = false)) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showActionLce(ActionUIStates(isSuccess = false))
            }
        }
    }

    override fun getPlatformCities(country: String) {
        val cityList: ArrayList<String> = arrayListOf()
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    profileRepositoryImpl.getPlatformCities(country)
                        .subscribe(
                            onSuccess = { response ->
                                if (response.status == "success"){
                                    response.countryCities?.get(0)?.cities!!.map {
                                        cityList.add(it.cityName)
                                    }
                                    platformContractView?.showPlatformCities(cityList)
                                }
                                else if (response.status == "failure"){}
                            },
                            onError = {},
                        )
                }
                result.dispose()
            } catch(e: Exception) {}
        }
    }

    override fun getVendorAvailability(vendorId: Long) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    meetingViewContract?.showLce(ScreenUIStates(loadingVisible = true, loadingMessage = "Loading Vendor Availability"))
                    profileRepositoryImpl.getVendorAvailableTimes(vendorId = vendorId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    println("Success ${result.vendorTimes}")
                                    meetingViewContract?.showLce(ScreenUIStates(contentVisible = true))
                                    meetingViewContract?.showAvailability(result.vendorTimes, result.platformTimes)
                                }
                                else{
                                    println("Error 1")
                                    meetingViewContract?.showLce(ScreenUIStates(errorOccurred = true))
                                }
                            },
                            onError = {
                                println("Error 2")
                                meetingViewContract?.showLce(ScreenUIStates(errorOccurred = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Error 3 ${e.message}")
                meetingViewContract?.showLce(ScreenUIStates(errorOccurred = true))
            }
        }
    }

    override fun switchVendor(userId: Long, vendorId: Long, action: String, exitReason: String, vendor: Vendor, platformNavigator: PlatformNavigator) {
        scope.launch(Dispatchers.Main) {
            switchVendorContract?.showActionLce(ActionUIStates(isLoading = true))
            try {
                val result = withContext(Dispatchers.IO) {
                    profileRepositoryImpl.switchVendor(userId, vendorId, action, exitReason)
                        .subscribe(
                            onSuccess = { result ->
                                if (result?.status == ServerResponseEnum.SUCCESS.toPath()){
                                    platformNavigator.sendCustomerExitNotification(exitReason = exitReason, vendorLogoUrl = vendor.businessLogo!!, fcmToken = vendor.fcmToken!!)
                                    switchVendorContract?.showActionLce(ActionUIStates(isSuccess = true))
                                }
                                else{
                                    switchVendorContract?.showActionLce(ActionUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                println("Error 2 ${it.message}")
                                switchVendorContract?.showActionLce(ActionUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Error 2 ${e.message}")
                switchVendorContract?.showActionLce(ActionUIStates(isFailed = true))
            }
        }
    }

    override fun getVendorAccountInfo(vendorId: Long) {
        scope.launch(Dispatchers.Main) {
            contractView?.showActionLce(ActionUIStates(isLoading = true, loadingMessage = "Searching Vendor"))
            try {
                val result = withContext(Dispatchers.IO) {
                    profileRepositoryImpl.getVendorAccountInfo(vendorId)
                        .subscribe(
                            onSuccess = { result ->
                                println("My Result is $result")
                                if (result.status == ServerResponseEnum.SUCCESS.toPath()){
                                    contractView?.showActionLce(ActionUIStates(isSuccess = true))
                                    contractView?.showVendorInfo(result.vendorInfo)
                                }
                                else if (result.status == ServerResponseEnum.FAILURE.toPath()){
                                    contractView?.showActionLce(ActionUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                println("My Result is Error ${it.message}")
                                contractView?.showActionLce(ActionUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("My Result is Error 2 ${e.message}")
                contractView?.showActionLce(ActionUIStates(isFailed = true))
            }
        }
    }

    override fun joinSpa(vendorId: Long, therapistId: Long) {
        scope.launch(Dispatchers.Main) {
            contractView?.showActionLce(ActionUIStates(isLoading = true, loadingMessage = "Joining Spa"))
            try {
                val result = withContext(Dispatchers.IO) {
                    profileRepositoryImpl.joinSpa(vendorId, therapistId)
                        .subscribe(
                            onSuccess = { result ->
                                println("My Result 1 $result")
                                if (result.status == ServerResponseEnum.SUCCESS.toPath()){
                                    contractView?.showActionLce(ActionUIStates(isSuccess = true))
                                }
                                else{
                                    contractView?.showActionLce(ActionUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                println("My Result 2 ${it.message}")
                                contractView?.showActionLce(ActionUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("My Result 3  ${e.message}")
                contractView?.showActionLce(ActionUIStates(isFailed = true))
            }
        }
    }

    override fun getUserLocation(lat: Double, lng: Double) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showActionLce(ActionUIStates(isLoading = true))
                    profileRepositoryImpl.reverseGeocode(lat, lng)
                        .subscribe(
                            onSuccess = { result ->
                                if (result?.country?.isNotEmpty() == true){
                                    contractView?.showActionLce(ActionUIStates(isSuccess = true))
                                    contractView?.showUserLocation(result)
                                }
                                else{
                                   // contractView?.showActionLce(ActionUIStates(isDone = true))
                                }
                            },
                            onError = {
                               // it.message?.let { it1 -> contractView?.showActionLce(ActionUIStates(isDone = true)) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
               // contractView?.showActionLce(ActionUIStates(isDone =  true))
            }
        }
    }


    override fun createMeeting(
        meetingTitle: String,
        userId: Long,
        vendorId: Long,
        serviceStatus: String,
        appointmentType: String,
        appointmentTime: Int,
        day: Int,
        month: Int,
        year: Int,
        meetingDescription: String,
        user: User, vendor: Vendor, platformTime: PlatformTime, monthName: String, platformNavigator: PlatformNavigator,
        paymentAmount: Double, paymentMethod: String
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    meetingViewContract?.showActionLce(ActionUIStates(isLoading = true))
                    profileRepositoryImpl.createMeetingAppointment(meetingTitle, userId, vendorId, serviceStatus, appointmentType,
                        appointmentTime, day, month, year, meetingDescription, paymentAmount, paymentMethod)
                        .subscribe(
                            onSuccess = { result ->
                                println("Result $result")
                                if (result.status == "success") {
                                    val time = if (platformTime.isAm) platformTime.time+"AM" else platformTime.time+"PM"
                                    platformNavigator.sendMeetingBookingNotification(customerName = user.firstname!!, vendorLogoUrl = vendor.businessLogo!!,
                                        meetingDay = day.toString(), meetingMonth = monthName, meetingYear = year.toString(), meetingTime = time, fcmToken = vendor.fcmToken!!)
                                    meetingViewContract?.showActionLce(ActionUIStates(isSuccess = true))
                                }
                                else{
                                    meetingViewContract?.showActionLce(ActionUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                println("Result 2  ${it.message}")
                                it.message?.let { it1 -> meetingViewContract?.showActionLce(ActionUIStates(isFailed = true))}
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Result ${e.message}")
                meetingViewContract?.showActionLce(ActionUIStates(isFailed = true))
            }
        }
    }
}