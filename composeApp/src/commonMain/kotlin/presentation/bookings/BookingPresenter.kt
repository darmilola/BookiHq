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
import applications.date.getMonth
import domain.Models.PlatformNavigator
import domain.Models.PlatformTime
import domain.Models.ServiceTypeItem
import domain.Models.User
import domain.Models.Vendor

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
                    contractView?.showScreenLce(ScreenUIStates(loadingVisible = true))
                    bookingRepositoryImpl.getServiceTherapist(serviceTypeId, vendorId)
                        .subscribe(
                            onSuccess = { result ->
                                println("Result is $result")
                                if (result.status == "success"){
                                    contractView?.showScreenLce(ScreenUIStates(contentVisible = true))
                                    contractView?.showTherapists(result.serviceTherapists, result.platformTimes!!, result.vendorTimes!!)
                                }
                                else{
                                    contractView?.showScreenLce(ScreenUIStates(errorOccurred = true))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showScreenLce(ScreenUIStates(errorOccurred = true), message = it1) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showScreenLce(ScreenUIStates(errorOccurred = true))
            }
        }
    }

    override fun createAppointment(userId: Long, vendorId: Long, service_id: Int, serviceTypeId: Int, therapist_id: Int,
                                   appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String, serviceStatus: String,
                                   appointmentType: String, platformNavigator: PlatformNavigator, user: User, vendor: Vendor, monthName: String,platformTime: PlatformTime
                                   ,serviceType: ServiceTypeItem) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showActionLce(ActionUIStates(isLoading = true))
                    bookingRepositoryImpl.createAppointment(userId, vendorId, service_id, serviceTypeId, therapist_id, appointmentTime,
                        day, month, year, serviceLocation, serviceStatus, appointmentType)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    val time = if (platformTime.isAm) platformTime.time+"AM" else platformTime.time+"PM"
                                    platformNavigator.sendAppointmentBookingNotification(customerName = user.firstname!!, vendorLogoUrl = vendor.businessLogo!!, businessName = vendor.businessName!!, appointmentDay = day.toString(),
                                        appointmentMonth = monthName, appointmentYear = year.toString(), appointmentTime = time, fcmToken = vendor.fcmToken!!, serviceType = serviceType.title)
                                    contractView?.showActionLce(ActionUIStates(isSuccess = true))
                                }
                                else{
                                    contractView?.showActionLce(ActionUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                println(it)
                                it.message?.let { it1 -> contractView?.showActionLce(ActionUIStates(isFailed = true))}
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println(e.message)
                contractView?.showActionLce(ActionUIStates(isFailed = true))
            }
        }
    }
}