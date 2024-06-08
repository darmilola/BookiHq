package presentation.bookings

import com.badoo.reaktive.single.subscribe
import domain.Models.UnsavedAppointment
import domain.Models.User
import domain.Models.Vendor
import domain.bookings.BookingRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.viewmodels.ActionUIStates
import presentation.viewmodels.ScreenUIStates

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

    override fun getServiceTherapists(serviceTypeId: Int, day: Int, month: Int, year: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showScreenLce(ScreenUIStates(loadingVisible = true))
                    bookingRepositoryImpl.getServiceTherapist(serviceTypeId, day, month, year)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showScreenLce(ScreenUIStates(contentVisible = true))
                                    contractView?.showTherapists(result.serviceSpecialists)
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

    override fun createAppointment(unsavedAppointments: ArrayList<UnsavedAppointment>, currentUser: User, currentVendor: Vendor) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showActionLce(ActionUIStates(isLoading = true))
                    val requestList = getUnSavedAppointment(unsavedAppointments, currentUser, currentVendor)
                    bookingRepositoryImpl.createAppointment(requestList)
                        .subscribe(
                            onSuccess = { result ->
                                println(result)
                                if (result.status == "success"){
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