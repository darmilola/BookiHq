package presentation.appointments

import com.badoo.reaktive.single.subscribe
import domain.Models.Appointment
import domain.appointments.AppointmentRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.viewmodels.ActionUIStates
import presentation.viewmodels.ScreenUIStates

class AppointmentPresenter(apiService: HttpClient): AppointmentContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: AppointmentContract.View? = null
    private val appointmentRepositoryImpl: AppointmentRepositoryImpl = AppointmentRepositoryImpl(apiService)
    override fun registerUIContract(view: AppointmentContract.View?) {
        contractView = view
    }

    override fun getUserAppointments(userId: Int) {
        contractView?.showLce(ScreenUIStates(loadingVisible = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.getAppointments(userId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(ScreenUIStates(contentVisible = true))
                                    contractView?.showAppointments(result.listItem)
                                }
                                else{
                                    contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
                                }
                            },
                            onError = {
                                contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
            }
        }
    }

    override fun getMoreAppointments(userId: Int, nextPage: Int) {
        contractView?.onLoadMoreAppointmentStarted()
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.getAppointments(userId, nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.onLoadMoreAppointmentEnded()
                                    contractView?.showAppointments(result.listItem)
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
        appointment: Appointment,
        newAppointmentTime: Int,
        day: Int,
        month: Int,
        year: Int
    ) {
        contractView?.showPostponeActionLce(ActionUIStates(isLoading = true, loadingMessage = "Postponing Your Appointment"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.postponeAppointment(appointment, newAppointmentTime, day,month,year)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showPostponeActionLce(ActionUIStates(isSuccess = true, successMessage = "Appointment Postponed"))
                                }
                                else{
                                    contractView?.showPostponeActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Postponing Appointment Please Try Again"))
                                }
                            },
                            onError = {
                                contractView?.showPostponeActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Postponing Appointment Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showPostponeActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Postponing Appointment Please Try Again"))
            }
        }
    }

    override fun deleteAppointment(appointmentId: Int) {
        contractView?.showDeleteActionLce(ActionUIStates(isLoading = true, loadingMessage = "Deleting Appointment"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.deleteAppointment(appointmentId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showDeleteActionLce(ActionUIStates(isSuccess = true, successMessage = "Appointment Deleted"))
                                }
                                else{
                                    contractView?.showDeleteActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Deleting Appointment Please Try Again"))
                                }
                            },
                            onError = {
                                contractView?.showDeleteActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Deleting Appointment Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showDeleteActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Deleting Appointment Please Try Again"))
            }
        }
    }

    override fun joinMeeting(customParticipantId: String, presetName: String, meetingId: String) {
        println("Inside Presenter1")
        contractView?.showJoinMeetingActionLce(ActionUIStates(isLoading = true, loadingMessage = "Joining Meeting"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.joinMeeting(customParticipantId, presetName, meetingId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    println("Inside Presenter2")
                                    contractView?.showJoinMeetingActionLce(ActionUIStates(isSuccess = true, successMessage = "Meeting Ready to be joined"))
                                    contractView?.onJoinMeetingTokenReady(result.token)
                                }
                                else{
                                    println("Inside Presenter Erroe")
                                    contractView?.showJoinMeetingActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Joining Meeting please try again"))
                                }
                            },
                            onError = {
                                println("Inside Presenter ${it.message}")
                                contractView?.showJoinMeetingActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Joining Meeting please try again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Inside Presenter ${e.message}")
                contractView?.showJoinMeetingActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Joining Meeting please try again"))
            }
        }
    }

    override fun getTherapistAvailability(therapistId: Int, day: Int, month: Int, year: Int) {
        contractView?.showGetAvailabilityActionLce(ActionUIStates(isLoading = true, loadingMessage = "Getting Availability"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.getTherapistAvailability(therapistId, day, month, year)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showTherapistAvailability(result.bookedAppointment)
                                    contractView?.showGetAvailabilityActionLce(ActionUIStates(isSuccess = true, successMessage = "Availability Ready"))
                                }
                                else{
                                    contractView?.showGetAvailabilityActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Fetching Availability, Please Try Again"))
                                }
                            },
                            onError = {
                                contractView?.showGetAvailabilityActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Fetching Availability, Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showGetAvailabilityActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Fetching Availability, Please Try Again"))
            }
        }
    }

}