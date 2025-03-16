package presentation.therapist

import UIStates.AppUIStates
import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.subscribe
import domain.Enums.ServerResponse
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
    private var dashboardContractView: TherapistContract.TherapistDashboardView? = null
    private val therapistRepositoryImpl: TherapistRepositoryImpl = TherapistRepositoryImpl(apiService)
    override fun registerUIContract(view: TherapistContract.View?) {
        contractView = view
    }

    override fun registerTherapistDashboardUIContract(view: TherapistContract.TherapistDashboardView?) {
        dashboardContractView = view
    }

    override fun getTherapistReviews(therapistId: Long) {
        dashboardContractView?.showScreenLce(AppUIStates(isLoading = true, loadingMessage = "Loading Reviews"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    therapistRepositoryImpl.getReviews(therapistId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        dashboardContractView?.showScreenLce(AppUIStates(isSuccess = true))
                                        dashboardContractView?.showReviews(result.reviews)
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        dashboardContractView?.showScreenLce(AppUIStates(isEmpty = true, errorMessage = "No Reviews Available"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        dashboardContractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred"))
                                    }
                                }
                            },
                            onError = {
                                dashboardContractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                dashboardContractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred"))
            }
        }
    }

    override fun refreshTherapistAppointments(therapistId: Long) {
        contractView?.showRefreshing(AppUIStates(isLoading = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    therapistRepositoryImpl.getTherapistAppointments(therapistId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showRefreshing(AppUIStates(isSuccess = true))
                                        contractView?.showAppointments(result.listItem)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showRefreshing(AppUIStates(isFailed = true, errorMessage = "Error Getting Appointments"))
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.showRefreshing(AppUIStates(isEmpty = true, emptyMessage = "No Appointments"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showRefreshing(AppUIStates(isFailed = true, errorMessage = "Error Getting Appointments"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showRefreshing(AppUIStates(isFailed = true, errorMessage = "Error Getting Appointments"))
            }
        }
    }

    override fun getTherapistAppointments(therapistId: Long) {
        contractView?.showScreenLce(AppUIStates(isLoading = true, loadingMessage = "Loading Appointments"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    therapistRepositoryImpl.getTherapistAppointments(therapistId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showScreenLce(AppUIStates(isSuccess = true))
                                        contractView?.showAppointments(result.listItem)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Appointments"))
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.showScreenLce(AppUIStates(isEmpty = true, emptyMessage = "No Appointments"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Appointments"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Getting Appointments"))
            }
        }
    }

    override fun getFilteredTherapistAppointments(therapistId: Long, filter: String) {
        contractView?.showRefreshing(AppUIStates(isLoading = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    therapistRepositoryImpl.getFilteredTherapistAppointments(therapistId,filter)
                        .subscribe(
                            onSuccess = { response ->
                                when (response.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showRefreshing(AppUIStates(isSuccess = true))
                                        contractView?.showAppointments(response.listItem)
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.showRefreshing(AppUIStates(isEmpty = true, emptyMessage = "No Appointment"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showRefreshing(AppUIStates(isFailed = true, errorMessage = "Error Occurred"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showRefreshing(AppUIStates(isFailed = true, errorMessage = "Error Occurred"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showRefreshing(AppUIStates(isFailed = true, errorMessage = "Error Occurred"))
            }
        }
    }

    override fun getMoreFilteredTherapistAppointments(
        therapistId: Long,
        filter: String,
        nextPage: Int
    ) {
        contractView?.onLoadMoreAppointmentStarted()
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    therapistRepositoryImpl.getFilteredTherapistAppointments(therapistId,filter,nextPage)
                        .subscribe(
                            onSuccess = { response ->
                                when (response.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.onLoadMoreAppointmentEnded()
                                        contractView?.showAppointments(response.listItem)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
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

    override fun getMoreTherapistAppointments(therapistId: Long, nextPage: Int) {
        contractView?.onLoadMoreAppointmentStarted()
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    therapistRepositoryImpl.getTherapistAppointments(therapistId, nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.onLoadMoreAppointmentEnded()
                                        contractView?.showAppointments(result.listItem)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
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

    override fun archiveAppointment(appointmentId: Long) {
        contractView?.showActionLce(AppUIStates(isLoading = true, loadingMessage = "Updating Appointment"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    therapistRepositoryImpl.archiveAppointment(appointmentId)
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

    override fun doneAppointment(appointmentId: Long) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showActionLce(AppUIStates(isLoading = true, loadingMessage = "Updating Appointment"))
                    therapistRepositoryImpl.doneAppointment(appointmentId)
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

    override fun updateAvailability(
        therapistId: Long,
        isMobileServiceAvailable: Boolean,
        isAvailable: Boolean
    ) {
        dashboardContractView?.showUpdateScreenLce(AppUIStates(isLoading = true, loadingMessage = "Updating Availability"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    therapistRepositoryImpl.updateAvailability(therapistId, isMobileServiceAvailable, isAvailable)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        dashboardContractView?.showUpdateScreenLce(AppUIStates(isSuccess = true))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        dashboardContractView?.showUpdateScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred"))
                                    }
                                }
                            },
                            onError = {
                                dashboardContractView?.showUpdateScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                dashboardContractView?.showUpdateScreenLce(AppUIStates(isFailed = true, errorMessage = "Error Occurred"))
            }
        }
    }

}