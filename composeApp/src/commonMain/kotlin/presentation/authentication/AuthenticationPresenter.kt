package presentation.authentication

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
                                println("Response 0 $result")
                                if (result.status == "success"){
                                    contractView?.onCompleteProfileDone(country,result.profileId, result.apiKey)
                                }
                                else{
                                    println("Response 1 $result")
                                    contractView?.onCompleteProfileError()
                                }
                            },
                            onError = {
                                println("Response 2 ${it.message}")
                                contractView?.onCompleteProfileError()
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Response 2 ${e.message}")
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
                                println("Error 1 $result")
                                if (result.status == "success"){
                                    contractView?.onProfileUpdateEnded(isSuccessful = true)
                                }
                                else{
                                    contractView?.onProfileUpdateEnded(isSuccessful = false)
                                }
                            },
                            onError = {
                                println("Error 2 ${it.message}")
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
                                println(result.toString())
                                if (result.status == "success"){
                                    when (result.profileStatus) {
                                        ProfileStatus.DONE.toPath() -> {
                                            contractView?.onProfileValidationEnded()
                                            contractView?.goToMainScreen(result.userInfo, result.whatsAppPhone)
                                        }
                                        ProfileStatus.CONNECT_VENDOR.toPath() -> {
                                            contractView?.onProfileValidationEnded()
                                            contractView?.goToConnectVendor(result.userInfo)
                                        }
                                        ProfileStatus.COMPLETE_PROFILE.toPath() -> {
                                            contractView?.onProfileValidationEnded()
                                            contractView?.goToCompleteProfileWithEmail(userEmail)
                                        }
                                    }
                                }
                                else{
                                    contractView?.onProfileValidationEnded()
                                }
                            },
                            onError = {
                                println("Here 2 ${it.message}")
                                contractView?.onProfileValidationEnded()
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Here 2 ${e.message}")
                contractView?.onProfileValidationEnded()
            }
        }
    }

    override fun updateFcmToken(userId: Long, fcmToken: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    authenticationRepositoryImpl.updateFcmToken(userId, fcmToken)
                        .subscribe(
                            onSuccess = { result -> },
                            onError = {},
                        )
                }
                result.dispose()
            } catch(e: Exception) {}
        }
    }

    override fun validatePhone(phone: String, requireValidation: Boolean) {
       var validPhone = ""
        if (requireValidation) {
            validPhone = makeValidPhone(phone)
       }
        else{
            validPhone = phone
        }
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onProfileValidationStarted()
                    authenticationRepositoryImpl.validatePhone(validPhone)
                        .subscribe(
                            onSuccess = { result ->
                                println("Error 3 $result")
                                if (result.status == "success"){
                                    if (result.profileStatus == ProfileStatus.DONE.toPath()) {
                                        contractView?.onProfileValidationEnded()
                                        contractView?.goToMainScreen(result.userInfo,  result.whatsAppPhone)
                                    }
                                    else if(result.profileStatus == ProfileStatus.CONNECT_VENDOR.toPath()){
                                        contractView?.onProfileValidationEnded()
                                        contractView?.goToConnectVendor(result.userInfo)
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
                                println("Error 1 ${it.message}")
                                contractView?.onProfileValidationEnded()
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Error 2 ${e.message}")
                contractView?.onProfileValidationEnded()
            }
        }
    }

    override fun registerUIContract(view: AuthenticationContract.View?) {
        contractView = view
    }
}