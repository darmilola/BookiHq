package presentation.appointmentBookings

import com.badoo.reaktive.single.subscribe
import domain.bookings.BookingRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import UIStates.AppUIStates
import domain.Enums.AppointmentType
import domain.Enums.ServerResponse
import domain.packages.PackageRepositoryImpl

class BookingPresenter(apiService: HttpClient): BookingContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: BookingContract.View? = null
    private val bookingRepositoryImpl: BookingRepositoryImpl = BookingRepositoryImpl(apiService)
    private val packageRepositoryImpl: PackageRepositoryImpl = PackageRepositoryImpl(apiService)
    override fun registerUIContract(view: BookingContract.View?) {
        contractView = view
    }
    override fun getUnSavedAppointment() {
        contractView?.showUnsavedAppointment()
    }

    override fun getServiceTherapists(serviceTypeId: Long, vendorId: Long, day: Int, month: Int, year: Int) {
        contractView?.getTherapistActionLce(AppUIStates(isLoading  = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    bookingRepositoryImpl.getServiceTherapist(serviceTypeId, vendorId, day, month, year)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.getTherapistActionLce(AppUIStates(isSuccess  = true))
                                        contractView?.showTherapists(result.serviceTherapists, result.platformTimes!!, result.vendorTimes!!)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.getTherapistActionLce(AppUIStates(isFailed  = true, errorMessage = "Error Getting Therapist, Please Try Again"))
                                    }
                                    else -> {
                                        contractView?.getTherapistActionLce(AppUIStates(isFailed  = true, errorMessage = "Error Getting Therapist, Please Try Again"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.getTherapistActionLce(AppUIStates(isFailed  = true, errorMessage = "Error Getting Therapist, Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.getTherapistActionLce(AppUIStates(isFailed  = true, errorMessage = "Error Getting Therapist, Please Try Again"))
            }
        }
    }

    override fun createAppointment(
        userId: Long,
        vendorId: Long,
        paymentAmount: Int,
        paymentMethod: String,
        bookingStatus: String,
        day: Int,
        month: Int,
        year: Int
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showCreateAppointmentActionLce(AppUIStates(isLoading = true))
                    bookingRepositoryImpl.createAppointment(userId, vendorId, paymentAmount, paymentMethod, bookingStatus, day, month, year)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showCreateAppointmentActionLce(AppUIStates(isSuccess = true))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showCreateAppointmentActionLce(AppUIStates(isFailed = true))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showCreateAppointmentActionLce(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showCreateAppointmentActionLce(AppUIStates(isFailed = true))
            }
        }
    }

    override fun getPendingBookingAppointment(userId: Long, bookingStatus: String) {
        contractView?.showLoadPendingAppointmentLce(AppUIStates(isLoading  = true, loadingMessage = "Loading Pending Appointment"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    bookingRepositoryImpl.getPendingBookingAppointment(userId, bookingStatus)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showPendingBookingAppointment(result.appointments!!)
                                        contractView?.showLoadPendingAppointmentLce(AppUIStates(isSuccess = true))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Appointment"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Appointment"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Appointment"))
            }
        }
    }

    override fun deletePendingBookingAppointment(pendingAppointmentId: Long) {
        contractView?.showDeleteActionLce(AppUIStates(isLoading = true, loadingMessage = "Deleting Appointment"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    bookingRepositoryImpl.deletePendingBookingAppointment(pendingAppointmentId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showDeleteActionLce(AppUIStates(isSuccess = true, successMessage = "Delete Successful"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showDeleteActionLce(AppUIStates(isFailed = true, errorMessage = "Error Deleting Appointment"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showDeleteActionLce(AppUIStates(isFailed = true, errorMessage = "Error Deleting Appointment"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showDeleteActionLce(AppUIStates(isFailed = true, errorMessage = "Error Deleting Appointment"))
            }
        }
    }

    override fun getTimeAvailability(vendorId: Long) {
        contractView?.getTimesActionLce(AppUIStates(isLoading  = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    packageRepositoryImpl.getTimeAvailability(vendorId = vendorId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.getTimesActionLce(AppUIStates(isSuccess  = true))
                                        contractView?.showTimes(result.platformTimes!!, result.vendorTimes!!)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.getTimesActionLce(AppUIStates(isFailed  = true, errorMessage = "Error Getting Available Times, Please Try Again"))
                                    }
                                    else -> {
                                        contractView?.getTimesActionLce(AppUIStates(isFailed  = true, errorMessage = "Error Getting Available Times, Please Try Again"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.getTimesActionLce(AppUIStates(isFailed  = true, errorMessage = "Error Getting Available Times, Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.getTimesActionLce(AppUIStates(isFailed  = true, errorMessage = "Error Getting Available Times, Please Try Again"))
            }
        }
    }


    override fun silentDeletePendingBookingAppointment(pendingAppointmentId: Long) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    bookingRepositoryImpl.deletePendingBookingAppointment(pendingAppointmentId)
                        .subscribe(onSuccess = { result -> }, onError = {},)
                }
                result.dispose()
            } catch(e: Exception) {}
        }
    }


    override fun createPendingBookingAppointment(userId: Long, vendorId: Long, serviceId: Long, serviceTypeId: Long, therapistId: Long,
                                                 appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String,
                                                 serviceStatus: String, bookingStatus: String) {
        contractView?.showLoadPendingAppointmentLce(AppUIStates(isLoading = true, loadingMessage = "Creating Pending Appointment"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    bookingRepositoryImpl.createPendingBookingAppointment(userId, vendorId, serviceId, serviceTypeId, therapistId, appointmentTime,
                        day, month, year, serviceLocation, serviceStatus,bookingStatus, appointmentType = AppointmentType.SINGLE.toPath())
                        .subscribe(
                            onSuccess = { result ->
                                println("Result is $result")
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showLoadPendingAppointmentLce(AppUIStates(isSuccess = true, successMessage = "Creation Successful"))
                                        contractView?.showPendingBookingAppointment(result.appointments!!)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Appointment"))
                                    }
                                }
                            },
                            onError = {
                                println("Result 2 is ${it.message}")
                                contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true, errorMessage = "Error Creating Appointment"))
                            },
                        )
                   }
                result.dispose()
            } catch(e: Exception) {
                println("Result 3 is ${e.message}")
                contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true, errorMessage = "Error Creating Appointment"))
            }
        }
    }

    override fun createPendingPackageBookingAppointment(
        userId: Long,
        vendorId: Long,
        packageId: Long,
        appointmentTime: Int,
        day: Int,
        month: Int,
        year: Int,
        serviceLocation: String,
        serviceStatus: String,
        bookingStatus: String
    ) {
        contractView?.showLoadPendingAppointmentLce(AppUIStates(isLoading = true, loadingMessage = "Creating Pending Appointment"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    bookingRepositoryImpl.createPendingPackageBookingAppointment(userId, vendorId, packageId, appointmentTime,
                        day, month, year, serviceLocation, serviceStatus, bookingStatus, appointmentType = AppointmentType.PACKAGE.toPath())
                        .subscribe(
                            onSuccess = { result ->
                                println("Result is $result")
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.showLoadPendingAppointmentLce(AppUIStates(isSuccess = true, successMessage = "Creation Successful"))
                                        contractView?.showPendingBookingAppointment(result.appointments!!)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Appointment"))
                                    }
                                }
                            },
                            onError = {
                                println("Result 2 is ${it.message}")
                                contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true, errorMessage = "Error Creating Appointment"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Result 3 is ${e.message}")
                contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true, errorMessage = "Error Creating Appointment"))
            }
        }
    }
}