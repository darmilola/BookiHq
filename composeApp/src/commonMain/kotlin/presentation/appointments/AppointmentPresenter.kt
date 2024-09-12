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
import domain.Enums.ServerResponse
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
        contractView?.showLce(AppUIStates(isLoading = true, loadingMessage = "Getting Appointments"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.getAppointments(userId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showLce(AppUIStates(isSuccess = true))
                                        contractView?.showAppointments(result.listItem, isRefresh = false)
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.showLce(AppUIStates(isFailed = true, emptyMessage = "No Appointments Available"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showRefreshing(AppUIStates(isFailed = true))
                                    }
                                    else -> {
                                        contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
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
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showRefreshing(AppUIStates(isSuccess = true))
                                        contractView?.showAppointments(result.listItem, isRefresh = true)
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.showRefreshing(AppUIStates(isFailed = true))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showRefreshing(AppUIStates(isFailed = true))
                                    }
                                    else -> {
                                        contractView?.showRefreshing(AppUIStates(isFailed = true))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showRefreshing(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
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
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.onLoadMoreAppointmentEnded()
                                        contractView?.showAppointments(result.listItem, isRefresh = false)
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.onLoadMoreAppointmentEnded()
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.onLoadMoreAppointmentEnded()
                                    }
                                    else -> {
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
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        val time = if (platformTime.isAm) platformTime.time+"AM" else platformTime.time+"PM"
                                        contractView?.showPostponeActionLce(AppUIStates(isSuccess = true, successMessage = "Appointment Postponed"))
                                        platformNavigator.sendPostponedAppointmentNotification(customerName = user.firstname!!, vendorLogoUrl = vendor.businessLogo!!, businessName = vendor.businessName!!, appointmentDay = day.toString(), appointmentMonth = monthName, appointmentYear = year.toString(),
                                            appointmentTime = time, fcmToken = vendor.fcmToken!!, serviceType = userAppointment.resources?.serviceTypeItem!!.title)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showPostponeActionLce(AppUIStates(isFailed = true, errorMessage = "Error Postponing Appointment Please Try Again"))
                                    }
                                    else -> {
                                        contractView?.showPostponeActionLce(AppUIStates(isFailed = true, errorMessage = "Error Postponing Appointment Please Try Again"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showPostponeActionLce(AppUIStates(isFailed = true, errorMessage = "Error Postponing Appointment Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
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
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showDeleteActionLce(AppUIStates(isSuccess = true, successMessage = "Appointment Deleted"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showDeleteActionLce(AppUIStates(isFailed = true, errorMessage = "Error Deleting Appointment Please Try Again"))
                                    }
                                    else -> {
                                        contractView?.showDeleteActionLce(AppUIStates(isFailed = true, errorMessage = "Error Deleting Appointment Please Try Again"))
                                    }
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
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showJoinMeetingActionLce(AppUIStates(isSuccess = true, successMessage = "Meeting Ready to be joined"))
                                        contractView?.onJoinMeetingTokenReady(result.token)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showJoinMeetingActionLce(AppUIStates(isFailed = true, errorMessage = "Error Joining Meeting please try again"))
                                    }
                                    else -> {
                                        contractView?.showJoinMeetingActionLce(AppUIStates(isFailed = true, errorMessage = "Error Joining Meeting please try again"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showJoinMeetingActionLce(AppUIStates(isFailed = true, errorMessage = "Error Joining Meeting please try again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
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
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showTherapistAvailability(result.bookedAppointment, result.platformTimes, result.vendorTimes)
                                        contractView?.showGetAvailabilityActionLce(AppUIStates(isSuccess = true, successMessage = "Availability Ready"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showGetAvailabilityActionLce(AppUIStates(isFailed = true, errorMessage = "Error Fetching Availability, Please Try Again"))
                                    }
                                    else -> {
                                        contractView?.showGetAvailabilityActionLce(AppUIStates(isFailed = true, errorMessage = "Error Fetching Availability, Please Try Again"))
                                    }
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

    override fun addAppointmentReviews(userId: Long, appointmentId: Long, vendorId: Long, serviceTypeId: Long,therapistId: Long,reviewText: String) {
        contractView?.showReviewsActionLce(AppUIStates(isLoading = true, loadingMessage = "Adding Reviews"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.addAppointmentReviews(userId, appointmentId, vendorId, serviceTypeId,therapistId,reviewText)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showReviewsActionLce(AppUIStates(isSuccess = true, successMessage = "Review Added Successfully"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showReviewsActionLce(AppUIStates(isFailed = true, errorMessage = "Error Adding Review, Please Try Again"))
                                    }
                                    else -> {
                                        contractView?.showReviewsActionLce(AppUIStates(isFailed = true, errorMessage = "Error Adding Review, Please Try Again"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showReviewsActionLce(AppUIStates(isFailed = true, errorMessage = "Error Adding Review, Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showReviewsActionLce(AppUIStates(isFailed = true, errorMessage = "Error Adding Review, Please Try Again"))
            }
        }
    }

}