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
import domain.Models.PlatformNavigator
import domain.Models.ServiceTypeTherapists
import domain.Models.Vendor
import domain.packages.PackageRepositoryImpl

class BookingPresenter(apiService: HttpClient): BookingContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: BookingContract.View? = null
    private var cancelContractView: BookingContract.CancelContractView? = null
    private val bookingRepositoryImpl: BookingRepositoryImpl = BookingRepositoryImpl(apiService)
    private val packageRepositoryImpl: PackageRepositoryImpl = PackageRepositoryImpl(apiService)
    override fun registerUIContract(view: BookingContract.View?) {
        contractView = view
    }

    override fun registerCancelUIContract(view: BookingContract.CancelContractView?) {
        cancelContractView = view
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
                                // This is needed since therapistInfo can be null from the server
                                val filteredTherapist = arrayListOf<ServiceTypeTherapists>()
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        result.serviceTherapists.map {
                                            if (it.therapistInfo != null){
                                                filteredTherapist.add(it)
                                            }
                                        }
                                        if (filteredTherapist.size > 0){
                                            contractView?.getTherapistActionLce(AppUIStates(isSuccess  = true))
                                            contractView?.showTherapists(filteredTherapist, result.platformTimes!!, result.vendorTimes!!)
                                        }
                                        else{
                                            contractView?.getTherapistActionLce(AppUIStates(isEmpty = true, emptyMessage = "No Therapist is Available"))
                                        }
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.getTherapistActionLce(AppUIStates(isFailed  = true, errorMessage = "Error Getting Therapist, Please Try Again"))
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.getTherapistActionLce(AppUIStates(isEmpty = true, emptyMessage = "No Therapist is Available"))
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

    override fun getMobileServiceTherapists(
        serviceTypeId: Long,
        vendorId: Long,
        day: Int,
        month: Int,
        year: Int
    ) {
        contractView?.getTherapistActionLce(AppUIStates(isLoading  = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    bookingRepositoryImpl.getMobileServiceTherapist(serviceTypeId, vendorId, day, month, year)
                        .subscribe(
                            onSuccess = { result ->
                                // This is needed since therapistInfo can be null from the server
                                val filteredTherapist = arrayListOf<ServiceTypeTherapists>()
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        result.serviceTherapists.map {
                                            if (it.therapistInfo != null){
                                                filteredTherapist.add(it)
                                            }
                                        }
                                        if (filteredTherapist.size > 0){
                                            contractView?.getTherapistActionLce(AppUIStates(isSuccess  = true))
                                            contractView?.showTherapists(filteredTherapist, result.platformTimes!!, result.vendorTimes!!)
                                        }
                                        else{
                                            contractView?.getTherapistActionLce(AppUIStates(isEmpty = true, emptyMessage = "No Therapist is Available"))
                                        }

                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.getTherapistActionLce(AppUIStates(isFailed  = true, errorMessage = "Error Getting Therapist, Please Try Again"))
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.getTherapistActionLce(AppUIStates(isEmpty = true, emptyMessage = "No Therapist is Available"))
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

    override fun getServiceData(serviceId: Long) {
        contractView?.getServiceTypesLce(AppUIStates(isLoading  = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    bookingRepositoryImpl.getServiceData(serviceId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.getServiceTypesLce(AppUIStates(isSuccess  = true))
                                        contractView?.showServiceTypes(result.serviceTypes!!, result.serviceImages!!)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.getServiceTypesLce(AppUIStates(isFailed  = true, errorMessage = "Error Getting Services, Please Try Again"))
                                    }
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.getServiceTypesLce(AppUIStates(isEmpty = true, emptyMessage = "No Service is Available"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.getServiceTypesLce(AppUIStates(isFailed  = true, errorMessage = "Error Getting Services, Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.getServiceTypesLce(AppUIStates(isFailed  = true, errorMessage = "Error Getting Services, Please Try Again"))
            }
        }
    }

    override fun createAppointment(
        userId: Long,
        vendorId: Long,
        paymentAmount: Int,
        bookingStatus: String,
        day: Int,
        month: Int,
        year: Int,
        platformNavigator: PlatformNavigator,
        vendor: Vendor
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showCreateAppointmentActionLce(AppUIStates(isLoading = true))
                    bookingRepositoryImpl.createAppointment(userId, vendorId, paymentAmount, bookingStatus, day, month, year)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        platformNavigator.sendAppointmentBookingNotification(vendorLogoUrl = vendor.businessLogo!!, fcmToken = vendor.fcmToken!!)
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
                                    ServerResponse.EMPTY.toPath() -> {
                                        contractView?.showLoadPendingAppointmentLce(AppUIStates(isEmpty = true, errorMessage = "No Appointment Available"))
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
        cancelContractView?.showCancelActionLce(AppUIStates(isLoading = true, loadingMessage = "Cancelling Appointment"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    bookingRepositoryImpl.deletePendingBookingAppointment(pendingAppointmentId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        cancelContractView?.showCancelActionLce(AppUIStates(isSuccess = true, successMessage = "Cancel Successful"))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        cancelContractView?.showCancelActionLce(AppUIStates(isFailed = true, errorMessage = "Error Cancelling Appointment"))
                                    }
                                }
                            },
                            onError = {
                                cancelContractView?.showCancelActionLce(AppUIStates(isFailed = true, errorMessage = "Error Cancelling Appointment"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                cancelContractView?.showCancelActionLce(AppUIStates(isFailed = true, errorMessage = "Error Cancelling Appointment"))
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

    override fun deleteAllPendingAppointment(userId: Long, bookingStatus: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    bookingRepositoryImpl.deleteAllPendingAppointment(userId, bookingStatus)
                        .subscribe(onSuccess = { result -> }, onError = {},)
                }
                result.dispose()
            } catch(e: Exception) {}
        }
    }


    override fun createPendingBookingAppointment(userId: Long, vendorId: Long, serviceId: Long, serviceTypeId: Long, therapistId: Long,
                                                 appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String, bookingStatus: String) {
        contractView?.showLoadPendingAppointmentLce(AppUIStates(isLoading = true, loadingMessage = "Creating Pending Appointment"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    bookingRepositoryImpl.createPendingBookingAppointment(userId, vendorId, serviceId, serviceTypeId, therapistId, appointmentTime,
                        day, month, year, serviceLocation,bookingStatus, appointmentType = AppointmentType.SINGLE.toPath())
                        .subscribe(
                            onSuccess = { result ->
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
                                contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true, errorMessage = "Error Creating Appointment"))
                            },
                        )
                   }
                result.dispose()
            } catch(e: Exception) {
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
        bookingStatus: String
    ) {
        contractView?.showLoadPendingAppointmentLce(AppUIStates(isLoading = true, loadingMessage = "Creating Pending Appointment"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    bookingRepositoryImpl.createPendingPackageBookingAppointment(userId, vendorId, packageId, appointmentTime,
                        day, month, year, serviceLocation, bookingStatus, appointmentType = AppointmentType.PACKAGE.toPath())
                        .subscribe(
                            onSuccess = { result ->
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
                                contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true, errorMessage = "Error Creating Appointment"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true, errorMessage = "Error Creating Appointment"))
            }
        }
    }
}