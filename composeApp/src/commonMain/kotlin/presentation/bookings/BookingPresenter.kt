package presentation.bookings

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

class BookingPresenter(apiService: HttpClient): BookingContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: BookingContract.View? = null
    private val bookingRepositoryImpl: BookingRepositoryImpl = BookingRepositoryImpl(apiService)
    override fun registerUIContract(view: BookingContract.View?) {
        contractView = view
    }

    override fun getUnSavedAppointment() {
        contractView?.showUnsavedAppointment()
    }

    override fun getServiceTherapists(serviceTypeId: Long, vendorId: Long, day: Int, month: Int, year: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.getTherapistActionLce(AppUIStates(isLoading  = true))
                    bookingRepositoryImpl.getServiceTherapist(serviceTypeId, vendorId, day, month, year)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.getTherapistActionLce(AppUIStates(isSuccess  = true))
                                    contractView?.showTherapists(result.serviceTherapists, result.platformTimes!!, result.vendorTimes!!, result.reviews!!)
                                }
                                else{
                                    contractView?.getTherapistActionLce(AppUIStates(isFailed  = true))
                                }
                            },
                            onError = {
                                contractView?.getTherapistActionLce(AppUIStates(isFailed  = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.getTherapistActionLce(AppUIStates(isFailed  = true))
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
                                println("Result $result")
                                if (result.status == "success"){
                                    contractView?.showCreateAppointmentActionLce(AppUIStates(isSuccess = true))
                                }
                                else{
                                    contractView?.showCreateAppointmentActionLce(AppUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                println("Result 2 ${it.message}")
                                contractView?.showCreateAppointmentActionLce(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Result 3 ${e.message}")
                contractView?.showCreateAppointmentActionLce(AppUIStates(isFailed = true))
            }
        }
    }

    override fun getPendingBookingAppointment(userId: Long, bookingStatus: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLoadPendingAppointmentLce(AppUIStates(isLoading  = true))
                    bookingRepositoryImpl.getPendingBookingAppointment(userId, bookingStatus)
                        .subscribe(
                            onSuccess = { result ->
                                println("Response $result")
                                if (result.status == "success"){
                                    contractView?.showPendingBookingAppointment(result.appointments!!)
                                    contractView?.showLoadPendingAppointmentLce(AppUIStates(isSuccess = true))
                                }
                                else{
                                    contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                println("Response 0 ${it.message}")
                                contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Response 1 ${e.message}")
                contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true))
            }
        }
    }

    override fun deletePendingBookingAppointment(pendingAppointmentId: Long) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showDeleteActionLce(AppUIStates(isLoading = true))
                    bookingRepositoryImpl.deletePendingBookingAppointment(pendingAppointmentId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showDeleteActionLce(AppUIStates(isSuccess = true))
                                }
                                else{
                                    contractView?.showDeleteActionLce(AppUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                contractView?.showDeleteActionLce(AppUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showDeleteActionLce(AppUIStates(isFailed = true))
            }
        }
    }

    override fun silentDeletePendingBookingAppointment(pendingAppointmentId: Long) {
        println(pendingAppointmentId)
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    bookingRepositoryImpl.deletePendingBookingAppointment(pendingAppointmentId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){}
                                else{}
                            },
                            onError = {},
                        )
                }
                result.dispose()
            } catch(e: Exception) {}
        }
    }


    override fun createPendingBookingAppointment(userId: Long, vendorId: Long, serviceId: Long, serviceTypeId: Long, therapistId: Long,
                                                 appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String,
                                                 serviceStatus: String,
                                                 paymentAmount: Double, paymentMethod: String, bookingStatus: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLoadPendingAppointmentLce(AppUIStates(isLoading = true))
                    bookingRepositoryImpl.createPendingBookingAppointment(userId, vendorId, serviceId, serviceTypeId, therapistId, appointmentTime,
                        day, month, year, serviceLocation, serviceStatus, paymentAmount, paymentMethod, bookingStatus)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLoadPendingAppointmentLce(AppUIStates(isSuccess = true))
                                    contractView?.showPendingBookingAppointment(result.appointments!!)
                                }
                                else{
                                    contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                println("Result 1 ${it.message}")
                                contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true))
                            },
                        )
                   }
                result.dispose()
            } catch(e: Exception) {
                println("Result 2 ${e.message}")
                contractView?.showLoadPendingAppointmentLce(AppUIStates(isFailed = true))
            }
        }
    }
}