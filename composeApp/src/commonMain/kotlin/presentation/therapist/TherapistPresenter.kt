package presentation.therapist

import com.badoo.reaktive.single.subscribe
import domain.therapist.TherapistRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import UIStates.ScreenUIStates

class TherapistPresenter(apiService: HttpClient): TherapistContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: TherapistContract.View? = null
    private val therapistRepositoryImpl: TherapistRepositoryImpl = TherapistRepositoryImpl(apiService)
    override fun registerUIContract(view: TherapistContract.View?) {
        contractView = view
    }

    override fun getTherapistReviews(therapistId: Int) {
        contractView?.showScreenLce(ScreenUIStates(loadingVisible = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    therapistRepositoryImpl.getReviews(therapistId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showScreenLce(ScreenUIStates(contentVisible = true))
                                    contractView?.showReviews(result.reviews)
                                }
                                else{
                                    contractView?.showScreenLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showScreenLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again")) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showScreenLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
            }
        }
    }

    override fun getTherapistAppointments(therapistId: Int) {
        contractView?.showScreenLce(ScreenUIStates(loadingVisible = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    therapistRepositoryImpl.getTherapistAppointments(therapistId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showScreenLce(ScreenUIStates(contentVisible = true))
                                    contractView?.showAppointments(result.listItem)
                                }
                                else{
                                    contractView?.showScreenLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
                                }
                            },
                            onError = {
                                contractView?.showScreenLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showScreenLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
            }
        }
    }

    override fun getMoreTherapistAppointments(therapistId: Int, nextPage: Int) {
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

}