package presentation.therapist

import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.subscribe
import domain.Models.ServerResponse
import domain.appointments.AppointmentRepositoryImpl
import domain.specialist.SpecialistRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.appointments.AppointmentContract
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.UIStates

class TherapistPresenter(apiService: HttpClient): TherapistContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: TherapistContract.View? = null
    private val specialistRepositoryImpl: SpecialistRepositoryImpl = SpecialistRepositoryImpl(apiService)
    override fun registerUIContract(view: TherapistContract.View?) {
        contractView = view
    }

    override fun getTherapistReviews(specialistId: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(UIStates(loadingVisible = true))
                    specialistRepositoryImpl.getReviews(specialistId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(UIStates(contentVisible = true))
                                    contractView?.showReviews(result.reviews)
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

    override fun getTherapistAvailability(specialistId: Int, selectedDate: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(UIStates(loadingVisible = true))
                    specialistRepositoryImpl.getTherapistAvailability(specialistId, selectedDate)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showTherapistAvailability(result.platformTimes, result.bookedAppointment, result.timeOffs)
                                    contractView?.showLce(UIStates(contentVisible = true))
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

    override fun addTimeOff(specialistId: Int, timeId: Int, date: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showAsyncLce(AsyncUIStates(isLoading = true))
                    specialistRepositoryImpl.addTimeOff(specialistId, timeId, date)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
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

    override fun removeTimeOff(
        specialistId: Int,
        timeId: Int,
        date: String
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showAsyncLce(AsyncUIStates(isLoading = true))
                    specialistRepositoryImpl.removeTimeOff(specialistId, timeId, date)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
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