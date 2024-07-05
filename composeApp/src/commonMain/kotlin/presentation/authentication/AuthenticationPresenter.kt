package presentation.authentication

import domain.authentication.AuthenticationRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import UIStates.ScreenUIStates
import com.badoo.reaktive.single.subscribe
import domain.Models.User
import UIStates.ActionUIStates
import domain.Enums.ProfileStatus
import kotlinx.serialization.Serializable
import utils.makeValidPhone

class AuthenticationPresenter(apiService: HttpClient): AuthenticationContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: AuthenticationContract.View? = null
    private val authenticationRepositoryImpl: AuthenticationRepositoryImpl = AuthenticationRepositoryImpl(apiService)

    override fun completeProfile(
        firstname: String,
        lastname: String,
        userEmail: String,
        authPhone: String,
        countryId: Int,
        cityId: Int,
        gender: String,
        profileImageUrl: String
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onProfileValidationStarted()
                    authenticationRepositoryImpl.completeProfile(firstname, lastname, userEmail, authPhone, countryId, cityId, gender, profileImageUrl)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.onCompleteProfileEnded(isSuccessful = true)
                                    contractView?.goToConnectVendor(userEmail)
                                }
                                else{
                                    contractView?.onCompleteProfileEnded(isSuccessful = false)
                                }
                            },
                            onError = {
                                contractView?.onCompleteProfileEnded(isSuccessful = false)
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onCompleteProfileEnded(isSuccessful = false)
            }
        }
    }

    override fun validateUserProfile(userEmail: String) {
      /*  scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(ScreenUIStates(loadingVisible = true))
                    authenticationRepositoryImpl.validateUserProfile(userEmail)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(ScreenUIStates(contentVisible = true))
                                    directUser(result.userInfo)
                                }
                                else{
                                    contractView?.showLce(ScreenUIStates(errorOccurred = true))
                                    contractView?.goToCompleteProfileWithEmail(userEmail)
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showLce(ScreenUIStates(errorOccurred = true), message = it1) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(ScreenUIStates(errorOccurred = true))
            }
        }*/
    }
    private fun directUser(user: User){
        if (user.connectedVendor != -1){

        }
        else if (user.connectedVendor == -1){
        }
    }
    override fun validateEmail(userEmail: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onProfileValidationStarted()
                    authenticationRepositoryImpl.validateEmail(userEmail)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    if (result.profileStatus == ProfileStatus.DONE.toPath()) {
                                        contractView?.onProfileValidationEnded()
                                        contractView?.goToMainScreen(result.userInfo.userEmail!!)
                                    }
                                    else if(result.profileStatus == ProfileStatus.CONNECT_VENDOR.toPath()){
                                        contractView?.onProfileValidationEnded()
                                        contractView?.goToConnectVendor(result.userInfo.userEmail!!)
                                    }
                                    else if(result.profileStatus == ProfileStatus.COMPLETE_PROFILE.toPath()){
                                        contractView?.onProfileValidationEnded()
                                        contractView?.goToCompleteProfileWithEmail(userEmail)
                                    }
                                }
                                else{
                                    contractView?.onProfileValidationEnded()

                                }
                            },
                            onError = {
                                contractView?.onProfileValidationEnded()
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onProfileValidationEnded()
            }
        }
    }

    override fun validatePhone(phone: String) {
        val validPhone = makeValidPhone(phone)
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onProfileValidationStarted()
                    authenticationRepositoryImpl.validatePhone(validPhone)
                        .subscribe(
                            onSuccess = { result ->
                                println(result)
                                if (result.status == "success"){
                                    if (result.profileStatus == ProfileStatus.DONE.toPath()) {
                                        contractView?.onProfileValidationEnded()
                                        contractView?.goToMainScreenWithPhone(phone)
                                    }
                                    else if(result.profileStatus == ProfileStatus.CONNECT_VENDOR.toPath()){
                                        contractView?.onProfileValidationEnded()
                                        contractView?.goToConnectVendorWithPhone(phone)
                                    }
                                    else if(result.profileStatus == ProfileStatus.COMPLETE_PROFILE.toPath()){
                                        contractView?.onProfileValidationEnded()
                                        contractView?.goToCompleteProfileWithPhone(phone)
                                    }
                                }
                                else{
                                    contractView?.onProfileValidationEnded()

                                }
                            },
                            onError = {
                                contractView?.onProfileValidationEnded()
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onProfileValidationEnded()
            }
        }
    }

    override fun getUserLocation(lat: Double, lng: Double) {
        /*scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showAsyncLce(ActionUIStates(isLoading = true))
                    authenticationRepositoryImpl.reverseGeocode(lat, lng)
                        .subscribe(
                            onSuccess = { result ->
                                if (result?.country?.isNotEmpty() == true){
                                    contractView?.showAsyncLce(ActionUIStates(isSuccess = true))
                                    contractView?.showUserLocation(result)
                                }
                                else{
                                    contractView?.showAsyncLce(ActionUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showAsyncLce(ActionUIStates(isFailed = true)) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showAsyncLce(ActionUIStates(isFailed = true))
            }
        }*/
    }

    override fun registerUIContract(view: AuthenticationContract.View?) {
        contractView = view
    }



}