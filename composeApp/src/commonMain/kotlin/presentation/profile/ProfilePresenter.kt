package presentation.profile

import domain.Profile.ProfileRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.badoo.reaktive.single.subscribe
import presentation.viewmodels.ActionUIStates


class ProfilePresenter(apiService: HttpClient): ProfileContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: ProfileContract.View? = null
    private var videoView: ProfileContract.VideoView? = null
    private val profileRepositoryImpl: ProfileRepositoryImpl = ProfileRepositoryImpl(apiService)
    override fun registerUIContract(view: ProfileContract.View?) {
       contractView = view
    }

    override fun registerTalkWithTherapistContract(view: ProfileContract.VideoView?) {
        videoView = view
    }

    override fun updateProfile(
        firstname: String,
        lastname: String,
        userEmail: String,
        address: String,
        contactPhone: String,
        countryId: Int,
        cityId: Int,
        gender: String,
        profileImageUrl: String
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(ActionUIStates(isLoading = true))
                    profileRepositoryImpl.updateProfile(firstname, lastname, userEmail, address, contactPhone, countryId, cityId, gender,profileImageUrl)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(ActionUIStates(isSuccess = true))
                                    //contractView?.onProfileUpdated()
                                }
                                else{
                                    contractView?.showLce(ActionUIStates(isDone = true, isSuccess = false))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showLce(ActionUIStates(isDone = true, isSuccess = false), message = it1) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(ActionUIStates(isDone = true, isSuccess = false))
            }
        }
    }

    override fun deleteProfile(userEmail: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(ActionUIStates(isLoading = true))
                    profileRepositoryImpl.deleteProfile(userEmail)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(ActionUIStates(isDone = true, isSuccess = true))
                                    contractView?.onProfileDeleted()
                                }
                                else{
                                    contractView?.showLce(ActionUIStates(isDone = true, isSuccess = false))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showLce(ActionUIStates(isDone = true, isSuccess = false), message = it1) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(ActionUIStates(isDone = true, isSuccess = false))
            }
        }
    }

    override fun getVendorAvailability(vendorId: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    videoView?.showLce(ActionUIStates(isLoading = true))
                    profileRepositoryImpl.getVendorAvailableTimes(vendorId = vendorId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    println("Success ${result.availableTimes}")
                                    videoView?.showLce(ActionUIStates(isDone = true, isSuccess = true))
                                    videoView?.showAvailability(result.availableTimes)
                                }
                                else{
                                    println("Error 1")
                                    videoView?.showLce(ActionUIStates(isDone = true, isSuccess = false))
                                }
                            },
                            onError = {
                                println("Error 2")
                                it.message?.let { it1 -> videoView?.showLce(ActionUIStates(isDone = true, isSuccess = false), message = it1) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Error 3 ${e.message}")
                videoView?.showLce(ActionUIStates(isDone = true, isSuccess = false))
            }
        }
    }

    override fun getUserLocation(lat: Double, lng: Double) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(ActionUIStates(isLoading = true))
                    profileRepositoryImpl.reverseGeocode(lat, lng)
                        .subscribe(
                            onSuccess = { result ->
                                if (result?.country?.isNotEmpty() == true){
                                    contractView?.showLce(ActionUIStates(isSuccess = true))
                                    contractView?.showUserLocation(result)
                                }
                                else{
                                    contractView?.showLce(ActionUIStates(isDone = true))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showLce(ActionUIStates(isDone = true)) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(ActionUIStates(isDone =  true))
            }
        }
    }
}