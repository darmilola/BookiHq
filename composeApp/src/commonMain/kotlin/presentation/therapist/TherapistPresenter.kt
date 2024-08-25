package presentation.therapist

import UIStates.AppUIStates
import com.badoo.reaktive.single.subscribe
import domain.therapist.TherapistRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TherapistPresenter(apiService: HttpClient): TherapistContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: TherapistContract.View? = null
    private val therapistRepositoryImpl: TherapistRepositoryImpl = TherapistRepositoryImpl(apiService)
    override fun registerUIContract(view: TherapistContract.View?) {
        contractView = view
    }

    override fun getTherapistReviews(therapistId: Long) {
        contractView?.showScreenLce(AppUIStates(isLoading = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    therapistRepositoryImpl.getReviews(therapistId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showScreenLce(AppUIStates(isSuccess = true))
                                    contractView?.showReviews(result.reviews)
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

    override fun getTherapistAppointments(therapistId: Long) {
        println("Therapist ID $therapistId")
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showScreenLce(AppUIStates(isLoading = true))
                    therapistRepositoryImpl.getTherapistAppointments(therapistId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    println("Success 1")
                                    contractView?.showScreenLce(AppUIStates(isSuccess = true))
                                    contractView?.showAppointments(result.listItem)
                                }
                                if (result.status == "empty"){
                                    println("Success 2")
                                    contractView?.showScreenLce(AppUIStates(isFailed = true))
                                }
                                else if(result.status == "failure"){
                                    println("Success 3")
                                    contractView?.showScreenLce(AppUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                println("Success 4")
                                println("Result2 is ${it.message}")
                                contractView?.showScreenLce(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Result3 is ${e.message}")
                contractView?.showScreenLce(AppUIStates(isFailed = true))
            }
        }
    }

    override fun getMoreTherapistAppointments(therapistId: Long, nextPage: Int) {
        contractView?.onLoadMoreAppointmentStarted()
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    therapistRepositoryImpl.getTherapistAppointments(therapistId, nextPage)
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

    override fun archiveAppointment(appointmentId: Long) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showActionLce(AppUIStates(isLoading = true, loadingMessage = "Updating Appointment"))
                    therapistRepositoryImpl.archiveAppointment(appointmentId)
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

    override fun doneAppointment(appointmentId: Long) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showActionLce(AppUIStates(isLoading = true, loadingMessage = "Updating Appointment"))
                    therapistRepositoryImpl.doneAppointment(appointmentId)
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

}