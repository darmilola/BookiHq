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
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.UIStates

class AppointmentPresenter(apiService: HttpClient): AppointmentContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: AppointmentContract.View? = null
    private val appointmentRepositoryImpl: AppointmentRepositoryImpl = AppointmentRepositoryImpl(apiService)
    override fun registerUIContract(view: AppointmentContract.View?) {
        contractView = view
    }

    override fun getUserAppointments(userId: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(UIStates(loadingVisible = true))
                    appointmentRepositoryImpl.getAppointments(userId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(UIStates(contentVisible = true))
                                    contractView?.showAppointments(result.listItem)
                                }
                                else{
                                    contractView?.showLce(UIStates(errorOccurred = true))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showLce(UIStates(errorOccurred = true)) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(UIStates(errorOccurred = true))
            }
        }
    }

    override fun getMoreAppointments(userId: Int, nextPage: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onLoadMoreAppointmentStarted(true)
                    appointmentRepositoryImpl.getAppointments(userId, nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.onLoadMoreAppointmentEnded(true)
                                    contractView?.showAppointments(result.listItem)
                                }
                                else{
                                    contractView?.onLoadMoreAppointmentEnded(false)
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.onLoadMoreAppointmentEnded(false) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onLoadMoreAppointmentEnded(false)
            }
        }
    }

    override fun postponeAppointment(
        appointment: Appointment,
        newAppointmentTime: Int,
        newAppointmentDate: String
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onPostponeAppointmentStarted()
                    appointmentRepositoryImpl.postponeAppointment(appointment, newAppointmentTime, newAppointmentDate)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.onPostponeAppointmentSuccess()
                                }
                                else{
                                    contractView?.onPostponeAppointmentFailed()
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.onPostponeAppointmentFailed() }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onPostponeAppointmentFailed()
            }
        }
    }

    override fun deleteAppointment(appointmentId: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onDeleteAppointmentStarted()
                    appointmentRepositoryImpl.deleteAppointment(appointmentId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.onDeleteAppointmentSuccess()
                                }
                                else{
                                    contractView?.onDeleteAppointmentFailed()
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.onDeleteAppointmentFailed() }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onDeleteAppointmentFailed()
            }
        }
    }

    override fun getTherapistAvailability(specialistId: Int, selectedDate: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showAsyncLce(AsyncUIStates(isLoading = true))
                    appointmentRepositoryImpl.getTherapistAvailability(specialistId, selectedDate)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showTherapistAvailability(result.availableTimes, result.bookedAppointment)
                                    contractView?.showAsyncLce(AsyncUIStates(isSuccess = true))
                                }
                                else{
                                    contractView?.showAsyncLce(AsyncUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showAsyncLce(AsyncUIStates(isFailed = true)) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showAsyncLce(AsyncUIStates(isFailed = true))
            }
        }
    }

}