package presentation.therapist

import com.badoo.reaktive.single.subscribe
import domain.specialist.SpecialistRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.viewmodels.ActionUIStates
import presentation.viewmodels.ScreenUIStates

class TherapistPresenter(apiService: HttpClient): TherapistContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: TherapistContract.View? = null
    private val specialistRepositoryImpl: SpecialistRepositoryImpl = SpecialistRepositoryImpl(apiService)
    override fun registerUIContract(view: TherapistContract.View?) {
        contractView = view
    }

    override fun getTherapistReviews(specialistId: Int) {
        contractView?.showLce(ScreenUIStates(loadingVisible = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    specialistRepositoryImpl.getReviews(specialistId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(ScreenUIStates(contentVisible = true))
                                    contractView?.showReviews(result.reviews)
                                }
                                else{
                                    contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again")) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
            }
        }
    }

    override fun getTherapistAvailability(specialistId: Int, day: Int, month: Int, year: Int) {
        contractView?.showActionLce(ActionUIStates(isLoading = true, loadingMessage = "Getting Availability"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    specialistRepositoryImpl.getTherapistAvailability(specialistId, day, month, year)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showTherapistAvailability(result.availableTimes, result.timeOffs)
                                    contractView?.showActionLce(ActionUIStates(isSuccess = true))
                                }
                                else{
                                    contractView?.showActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
                                }
                            },
                            onError = {
                                contractView?.showActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
            }
        }
    }

    override fun addTimeOff(specialistId: Int, timeId: Int, day: Int,
                            month: Int,
                            year: Int) {
        contractView?.showActionLce(ActionUIStates(isLoading = true, errorMessage = "Adding New TimeOff"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    specialistRepositoryImpl.addTimeOff(specialistId, timeId, day, month, year)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showActionLce(ActionUIStates(isSuccess = true, successMessage = "TimeOff Added Successfully"))
                                }
                                else{
                                    contractView?.showActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
                                }
                            },
                            onError = {
                                contractView?.showActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
            }
        }
    }

    override fun removeTimeOff(
        specialistId: Int,
        timeId: Int,
        day: Int,
        month: Int,
        year: Int
    ) {
        contractView?.showActionLce(ActionUIStates(isLoading = true, loadingMessage = "Removing TimeOff"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    specialistRepositoryImpl.removeTimeOff(specialistId, timeId, day, month, year)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showActionLce(ActionUIStates(isSuccess = true, successMessage = "TimeOff Removed Successfully"))
                                }
                                else{
                                    contractView?.showActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
                                }
                            },
                            onError = {
                                contractView?.showActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Occurred Please Try Again"))
            }
        }
    }

}