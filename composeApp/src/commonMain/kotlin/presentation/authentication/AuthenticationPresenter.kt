package presentation.authentication

import infrastructure.authentication.AuthenticationRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.viewmodels.UIStates
import com.badoo.reaktive.single.subscribe
import domain.Models.User


class AuthenticationPresenter(apiService: HttpClient): AuthenticationContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: AuthenticationContract.View? = null
    private val authenticationRepositoryImpl: AuthenticationRepositoryImpl = AuthenticationRepositoryImpl(apiService)

    override fun completeProfile(
        firstname: String,
        lastname: String,
        userEmail: String,
        address: String,
        contactPhone: String,
        country: String,
        gender: String,
        profileImageUrl: String
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(UIStates(loadingVisible = true))
                    authenticationRepositoryImpl.completeProfile(firstname, lastname, userEmail, address, contactPhone, country, gender, profileImageUrl)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(UIStates(contentVisible = true))
                                    contractView?.goToConnectVendor(userEmail)
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

    override fun updateProfile(
        firstname: String,
        lastname: String,
        userEmail: String,
        address: String,
        contactPhone: String,
        country: String,
        gender: String,
        profileImageUrl: String
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(UIStates(loadingVisible = true))
                    authenticationRepositoryImpl.updateProfile(firstname, lastname, userEmail, address, contactPhone, country, gender,profileImageUrl)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(UIStates(contentVisible = true))
                                    contractView?.onProfileUpdated()
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

    override fun connectVendor(userEmail: String, vendorId: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(UIStates(loadingVisible = true))
                    authenticationRepositoryImpl.connectVendor(userEmail,vendorId)
                        .subscribe(
                            onSuccess = { result ->
                                println(result)
                                if (result.status == "success"){
                                    contractView?.showLce(UIStates(contentVisible = true))
                                    contractView?.goToMainScreen(userEmail)
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

    override fun getUserProfile(userEmail: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(UIStates(loadingVisible = true))
                    authenticationRepositoryImpl.getUserProfile(userEmail)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(UIStates(contentVisible = true))
                                    directUser(result.userInfo)
                                }
                                else{
                                    contractView?.showLce(UIStates(errorOccurred = true))
                                    contractView?.goToCompleteProfile(userEmail)
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

    private fun directUser(user: User){
        if (user.connectedVendor != -1){
            contractView?.goToMainScreen(user.userEmail!!)
        }
        else if (user.connectedVendor == -1){
            contractView?.goToConnectVendor(user.userEmail!!)
        }
    }

    override fun deleteProfile(userEmail: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(UIStates(loadingVisible = true))
                    authenticationRepositoryImpl.deleteProfile(userEmail)
                        .subscribe(
                            onSuccess = { result ->
                                println(result)
                                if (result.status == "success"){
                                    contractView?.showLce(UIStates(contentVisible = true))
                                    contractView?.onProfileDeleted()
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

    override fun registerUIContract(view: AuthenticationContract.View?) {
        contractView = view
    }

    override fun startAuth0() {
        contractView?.onAuth0Started()
    }

    override fun endAuth0() {
        contractView?.onAuth0Ended()
    }

}