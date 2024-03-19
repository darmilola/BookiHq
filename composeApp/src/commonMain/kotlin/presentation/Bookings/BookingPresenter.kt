package presentation.Bookings

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.badoo.reaktive.single.subscribe
import domain.Models.UnsavedAppointment
import domain.Models.User
import domain.Models.Vendor
import domain.bookings.BookingRepositoryImpl
import domain.bookings.CreateAppointmentRequest
import infrastructure.authentication.AuthenticationRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.authentication.AuthenticationContract
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.UIStates

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

    override fun getServiceTherapists(serviceTypeId: Int, selectedDate: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(UIStates(loadingVisible = true))
                    bookingRepositoryImpl.getServiceTherapist(serviceTypeId, selectedDate)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(UIStates(contentVisible = true))
                                    contractView?.showTherapists(result.serviceSpecialists)
                                }
                                else{
                                    contractView?.showLce(UIStates(errorOccurred = true))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showLce(UIStates(errorOccurred = true), message = it1) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(UIStates(errorOccurred = true))
            }
        }
    }

    override fun createAppointment(unsavedAppointments: SnapshotStateList<UnsavedAppointment>, currentUser: User, currentVendor: Vendor) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showBookingLce(AsyncUIStates(isLoading = true))
                    val requestList = getUnSavedAppointment(unsavedAppointments, currentUser, currentVendor)
                    bookingRepositoryImpl.createAppointment(requestList)
                        .subscribe(
                            onSuccess = { result ->
                                println(result)
                                if (result.status == "success"){
                                    contractView?.showBookingLce(AsyncUIStates(isSuccess = true))
                                }
                                else{
                                    contractView?.showBookingLce(AsyncUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                println(it)
                                it.message?.let { it1 -> contractView?.showBookingLce(AsyncUIStates(isFailed = true))}
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println(e.message)
                contractView?.showBookingLce(AsyncUIStates(isFailed = true))
            }
        }
    }
}