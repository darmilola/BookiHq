package presentation.appointments

import com.badoo.reaktive.single.subscribe
import domain.appointments.AppointmentRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import UIStates.AppUIStates
import domain.Enums.ServerResponseEnum
import domain.Models.PlatformNavigator
import domain.Models.PlatformTime
import domain.Models.User
import domain.Models.UserAppointment
import domain.Models.Vendor

class AppointmentPresenter(apiService: HttpClient): AppointmentContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: AppointmentContract.View? = null
    private val appointmentRepositoryImpl: AppointmentRepositoryImpl = AppointmentRepositoryImpl(apiService)
    override fun registerUIContract(view: AppointmentContract.View?) {
        contractView = view
    }

    override fun getUserAppointments(userId: Long) {
        contractView?.showLce(AppUIStates(isLoading = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.getAppointments(userId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponseEnum.SUCCESS.toPath() -> {
                                        println("Response 0 $result")
                                        contractView?.showLce(AppUIStates(isSuccess = true))
                                        contractView?.showAppointments(result.listItem, isRefresh = false)
                                    }
                                    ServerResponseEnum.EMPTY.toPath() -> {
                                        println("Response 1 $result")
                                        contractView?.showLce(AppUIStates(isFailed = true))
                                    }
                                    else -> {
                                        println("Response 2 $result")
                                        contractView?.showLce(AppUIStates(isFailed = true))
                                    }
                                }
                            },
                            onError = {
                                println("Result 3 ${it.message}")
                                contractView?.showLce(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Result 4 ${e.message}")
                contractView?.showLce(AppUIStates(isFailed = true))
            }
        }
    }

    override fun refreshUserAppointments(userId: Long) {
        contractView?.showRefreshing(AppUIStates(isLoading = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.getAppointments(userId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponseEnum.SUCCESS.toPath() -> {
                                        contractView?.showRefreshing(AppUIStates(isSuccess = true))
                                        contractView?.showAppointments(result.listItem, isRefresh = true)
                                    }
                                    ServerResponseEnum.EMPTY.toPath() -> {
                                        contractView?.showRefreshing(AppUIStates(isFailed = true))
                                    }
                                    else -> {
                                        contractView?.showRefreshing(AppUIStates(isFailed = true))
                                    }
                                }
                            },
                            onError = {
                                println("Result 2 ${it.message}")
                                contractView?.showRefreshing(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Result 3 ${e.message}")
                contractView?.showRefreshing(AppUIStates(isFailed = true))
            }
        }
    }

    override fun getMoreAppointments(userId: Long, nextPage: Int) {
        contractView?.onLoadMoreAppointmentStarted()
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.getAppointments(userId, nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.onLoadMoreAppointmentEnded()
                                    contractView?.showAppointments(result.listItem, isRefresh = false)
                                }
                                else{
                                    contractView?.onLoadMoreAppointmentEnded()
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.onLoadMoreAppointmentEnded() }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onLoadMoreAppointmentEnded()
            }
        }
    }
    override fun postponeAppointment(
        userAppointment: UserAppointment,
        newAppointmentTime: Int,
        day: Int,
        month: Int,
        year: Int,
        vendor: Vendor, user: User, monthName: String, platformNavigator: PlatformNavigator, platformTime: PlatformTime) {
        contractView?.showPostponeActionLce(AppUIStates(isLoading = true, loadingMessage = "Postponing Your Appointment"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.postponeAppointment(userAppointment, newAppointmentTime, day,month,year)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    val time = if (platformTime.isAm) platformTime.time+"AM" else platformTime.time+"PM"
                                    contractView?.showPostponeActionLce(AppUIStates(isSuccess = true, successMessage = "Appointment Postponed"))
                                    platformNavigator.sendPostponedAppointmentNotification(customerName = user.firstname!!, vendorLogoUrl = vendor.businessLogo!!, businessName = vendor.businessName!!, appointmentDay = day.toString(), appointmentMonth = monthName, appointmentYear = year.toString(),
                                        appointmentTime = time, fcmToken = vendor.fcmToken!!, serviceType = userAppointment.resources?.serviceTypeItem!!.title)
                                }
                                else{
                                    contractView?.showPostponeActionLce(AppUIStates(isFailed = true, errorMessage = "Error Postponing Appointment Please Try Again"))
                                }
                            },
                            onError = {
                                println("Error 2 ${it.message}")
                                contractView?.showPostponeActionLce(AppUIStates(isFailed = true, errorMessage = "Error Postponing Appointment Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Error 2 ${e.message}")
                contractView?.showPostponeActionLce(AppUIStates(isFailed = true, errorMessage = "Error Postponing Appointment Please Try Again"))
            }
        }
    }

    override fun deleteAppointment(appointmentId: Long) {
        contractView?.showDeleteActionLce(AppUIStates(isLoading = true, loadingMessage = "Deleting Appointment"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.deleteAppointment(appointmentId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showDeleteActionLce(AppUIStates(isSuccess = true, successMessage = "Appointment Deleted"))
                                }
                                else{
                                    contractView?.showDeleteActionLce(AppUIStates(isFailed = true, errorMessage = "Error Deleting Appointment Please Try Again"))
                                }
                            },
                            onError = {
                                contractView?.showDeleteActionLce(AppUIStates(isFailed = true, errorMessage = "Error Deleting Appointment Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showDeleteActionLce(AppUIStates(isFailed = true, errorMessage = "Error Deleting Appointment Please Try Again"))
            }
        }
    }

    override fun joinMeeting(customParticipantId: String, presetName: String, meetingId: String) {
        contractView?.showJoinMeetingActionLce(AppUIStates(isLoading = true, loadingMessage = "Joining Meeting"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.joinMeeting(customParticipantId, presetName, meetingId)
                        .subscribe(
                            onSuccess = { result ->
                                println("Error 0 $result")
                                if (result.status == "success"){
                                    contractView?.showJoinMeetingActionLce(AppUIStates(isSuccess = true, successMessage = "Meeting Ready to be joined"))
                                    contractView?.onJoinMeetingTokenReady(result.token)
                                }
                                else{
                                    contractView?.showJoinMeetingActionLce(AppUIStates(isFailed = true, errorMessage = "Error Joining Meeting please try again"))
                                }
                            },
                            onError = {
                                println("Error 1 ${it.message}")
                                contractView?.showJoinMeetingActionLce(AppUIStates(isFailed = true, errorMessage = "Error Joining Meeting please try again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Error 2 ${e.message}")
                contractView?.showJoinMeetingActionLce(AppUIStates(isFailed = true, errorMessage = "Error Joining Meeting please try again"))
            }
        }
    }

    override fun getTherapistAvailability(therapistId: Long, vendorId: Long, day: Int, month: Int, year: Int) {
        contractView?.showGetAvailabilityActionLce(AppUIStates(isLoading = true, loadingMessage = "Getting Availability"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.getTherapistAvailability(therapistId,vendorId, day, month, year)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showTherapistAvailability(result.bookedAppointment, result.platformTimes, result.vendorTimes)
                                    contractView?.showGetAvailabilityActionLce(AppUIStates(isSuccess = true, successMessage = "Availability Ready"))
                                }
                                else{
                                    contractView?.showGetAvailabilityActionLce(AppUIStates(isFailed = true, errorMessage = "Error Fetching Availability, Please Try Again"))
                                }
                            },
                            onError = {
                                contractView?.showGetAvailabilityActionLce(AppUIStates(isFailed = true, errorMessage = "Error Fetching Availability, Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showGetAvailabilityActionLce(AppUIStates(isFailed = true, errorMessage = "Error Fetching Availability, Please Try Again"))
            }
        }
    }

    override fun addAppointmentReviews(userId: Long, appointmentId: Long, vendorId: Long, serviceTypeId: Long, reviewText: String) {
        contractView?.showReviewsActionLce(AppUIStates(isLoading = true, loadingMessage = "Adding Reviews"))
        println("Called ")
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.addAppointmentReviews(userId, appointmentId, vendorId, serviceTypeId, reviewText)
                        .subscribe(
                            onSuccess = { result ->
                                println("Called 2 $result")
                                if (result.status == "success"){
                                    contractView?.showReviewsActionLce(AppUIStates(isSuccess = true, successMessage = "Review Added Successfully"))
                                }
                                else{
                                    contractView?.showReviewsActionLce(AppUIStates(isFailed = true, errorMessage = "Error Adding Review, Please Try Again"))
                                }
                            },
                            onError = {
                                println("Called 3 ${it.message}")
                                contractView?.showReviewsActionLce(AppUIStates(isFailed = true, errorMessage = "Error Adding Review, Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Called 4 ${e.message}")
                contractView?.showReviewsActionLce(AppUIStates(isFailed = true, errorMessage = "Error Adding Review, Please Try Again"))
            }
        }
    }

}