package presentation.authentication

import UIStates.AppUIStates
import domain.authentication.AuthenticationRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.badoo.reaktive.single.subscribe
import domain.Models.User
import domain.Enums.ProfileStatus
import domain.Enums.ServerResponse
import utils.makeValidPhone

class AuthenticationPresenter(apiService: HttpClient): AuthenticationContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: AuthenticationContract.View? = null
    private val authenticationRepositoryImpl: AuthenticationRepositoryImpl = AuthenticationRepositoryImpl(apiService)

    override fun completeProfile(
        firstname: String, lastname: String, userEmail: String, authPhone: String,
        address: String, contactPhone: String, country: String,
        signupType: String, gender: String, profileImageUrl: String
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onCompleteProfileStarted()
                    authenticationRepositoryImpl.completeProfile(firstname, lastname, userEmail, authPhone,address,contactPhone,country, signupType,gender, profileImageUrl)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.onCompleteProfileDone(country,result.profileId, result.apiKey)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.onCompleteProfileError()
                                    }
                                    else -> {
                                        contractView?.onCompleteProfileError()
                                    }
                                }
                            },
                            onError = {
                                contractView?.onCompleteProfileError()
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onCompleteProfileError()
            }
        }
    }

    override fun updateProfile(
        userId: Long,
        firstname: String,
        lastname: String,
        address: String,
        contactPhone: String,
        country: String,
        gender: String,
        profileImageUrl: String
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onProfileUpdateStarted()
                    authenticationRepositoryImpl.updateProfile(userId, firstname, lastname, address, contactPhone, country, gender, profileImageUrl)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.onProfileUpdateEnded(isSuccessful = true)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.onProfileUpdateEnded(isSuccessful = false)
                                    }
                                    else -> {
                                        contractView?.onProfileUpdateEnded(isSuccessful = false)
                                    }
                                }
                            },
                            onError = {
                                contractView?.onProfileUpdateEnded(isSuccessful = false)
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onProfileUpdateEnded(isSuccessful = false)
            }
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
                                if (result.status == ServerResponse.SUCCESS.toPath()){
                                    when (result.profileStatus) {
                                        ProfileStatus.DONE.toPath() -> {
                                            contractView?.goToMainScreen(result.userInfo, result.whatsAppPhone)
                                        }
                                        ProfileStatus.CONNECT_VENDOR.toPath() -> {
                                            contractView?.goToConnectVendor(result.userInfo)
                                        }
                                        ProfileStatus.COMPLETE_PROFILE.toPath() -> {
                                            contractView?.onProfileValidationEnded()
                                            contractView?.goToCompleteProfileWithEmail(userEmail)
                                        }
                                    }
                                }
                                else{
                                    contractView?.onProfileValidationError()
                                }
                            },
                            onError = {
                                contractView?.onProfileValidationError()
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onProfileValidationError()
            }
        }
    }

    override fun updateFcmToken(userId: Long, fcmToken: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    authenticationRepositoryImpl.updateFcmToken(userId, fcmToken)
                        .subscribe(onSuccess = { result -> }, onError = {})
                }
                result.dispose()
            } catch(_: Exception) {}
        }
    }

    override fun validatePhone(phone: String, requireValidation: Boolean) {
       var validPhone = ""
        validPhone = if (requireValidation) {
            makeValidPhone(phone)
        } else{
            phone
        }
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onProfileValidationStarted()
                    authenticationRepositoryImpl.validatePhone(validPhone)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == ServerResponse.SUCCESS.toPath()){
                                    when (result.profileStatus) {
                                        ProfileStatus.DONE.toPath() -> {
                                            contractView?.onProfileValidationEnded()
                                            contractView?.goToMainScreen(result.userInfo,  result.whatsAppPhone)
                                        }
                                        ProfileStatus.CONNECT_VENDOR.toPath() -> {
                                            contractView?.onProfileValidationEnded()
                                            contractView?.goToConnectVendor(result.userInfo)
                                        }
                                        ProfileStatus.COMPLETE_PROFILE.toPath() -> {
                                            contractView?.onProfileValidationEnded()
                                            contractView?.goToCompleteProfileWithPhone(phone)
                                        }
                                    }
                                }
                                else{
                                    contractView?.onProfileValidationError()
                                }
                            },
                            onError = {
                                contractView?.onProfileValidationError()
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onProfileValidationError()
            }
        }
    }

    override fun registerUIContract(view: AuthenticationContract.View?) {
        contractView = view
    }
}