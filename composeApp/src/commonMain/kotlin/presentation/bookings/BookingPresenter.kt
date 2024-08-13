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
import UIStates.ActionUIStates
import UIStates.ScreenUIStates

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

    override fun getServiceTherapists(serviceTypeId: Int, vendorId: Long) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.getTherapistActionLce(ActionUIStates(isLoading  = true))
                    bookingRepositoryImpl.getServiceTherapist(serviceTypeId, vendorId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.getTherapistActionLce(ActionUIStates(isSuccess  = true))
                                    contractView?.showTherapists(result.serviceTherapists, result.platformTimes!!, result.vendorTimes!!)
                                }
                                else{
                                    contractView?.getTherapistActionLce(ActionUIStates(isFailed  = true))
                                }
                            },
                            onError = {
                                contractView?.getTherapistActionLce(ActionUIStates(isFailed  = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.getTherapistActionLce(ActionUIStates(isFailed  = true))
            }
        }
    }

    override fun createAppointment(
        userId: Long,
        vendorId: Long,
        paymentAmount: Double,
        paymentMethod: String,
        bookingStatus: String,
        day: Int,
        month: Int,
        year: Int
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showCreateAppointmentActionLce(ActionUIStates(isLoading = true))
                    bookingRepositoryImpl.createAppointment(userId, vendorId, paymentAmount, paymentMethod, bookingStatus, day, month, year)
                        .subscribe(
                            onSuccess = { result ->
                                println("Result $result")
                                if (result.status == "success"){
                                    contractView?.showCreateAppointmentActionLce(ActionUIStates(isSuccess = true))
                                }
                                else{
                                    contractView?.showCreateAppointmentActionLce(ActionUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                println("Result 2 ${it.message}")
                                contractView?.showCreateAppointmentActionLce(ActionUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Result 3 ${e.message}")
                contractView?.showCreateAppointmentActionLce(ActionUIStates(isFailed = true))
            }
        }
    }

    override fun getPendingBookingAppointment(userId: Long, bookingStatus: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLoadPendingAppointmentLce(ActionUIStates(isLoading  = true))
                    bookingRepositoryImpl.getPendingBookingAppointment(userId, bookingStatus)
                        .subscribe(
                            onSuccess = { result ->
                                println("Response $result")
                                if (result.status == "success"){
                                    contractView?.showPendingBookingAppointment(result.appointments!!)
                                    contractView?.showLoadPendingAppointmentLce(ActionUIStates(isSuccess = true))
                                }
                                else{
                                    contractView?.showLoadPendingAppointmentLce(ActionUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                println("Response 0 ${it.message}")
                                contractView?.showLoadPendingAppointmentLce(ActionUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Response 1 ${e.message}")
                contractView?.showLoadPendingAppointmentLce(ActionUIStates(isFailed = true))
            }
        }
    }

    override fun deletePendingBookingAppointment(pendingAppointmentId: Long) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showDeleteActionLce(ActionUIStates(isLoading = true))
                    bookingRepositoryImpl.deletePendingBookingAppointment(pendingAppointmentId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showDeleteActionLce(ActionUIStates(isSuccess = true))
                                }
                                else{
                                    contractView?.showDeleteActionLce(ActionUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                contractView?.showDeleteActionLce(ActionUIStates(isFailed = true))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showDeleteActionLce(ActionUIStates(isFailed = true))
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


    override fun createPendingBookingAppointment(userId: Long, vendorId: Long, serviceId: Int, serviceTypeId: Int, therapistId: Int,
                                                 appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String,
                                                 serviceStatus: String, appointmentType: String,
                                                 paymentAmount: Double, paymentMethod: String, bookingStatus: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLoadPendingAppointmentLce(ActionUIStates(isLoading = true))
                    bookingRepositoryImpl.createPendingBookingAppointment(userId, vendorId, serviceId, serviceTypeId, therapistId, appointmentTime,
                        day, month, year, serviceLocation, serviceStatus, appointmentType, paymentAmount, paymentMethod, bookingStatus)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLoadPendingAppointmentLce(ActionUIStates(isSuccess = true))
                                    contractView?.showPendingBookingAppointment(result.appointments!!)
                                }
                                else{
                                    contractView?.showLoadPendingAppointmentLce(ActionUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                println("Result 1 ${it.message}")
                                contractView?.showLoadPendingAppointmentLce(ActionUIStates(isFailed = true))
                            },
                        )
                   }
                result.dispose()
            } catch(e: Exception) {
                println("Result 2 ${e.message}")
                contractView?.showLoadPendingAppointmentLce(ActionUIStates(isFailed = true))
            }
        }
    }
}