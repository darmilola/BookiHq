package domain.authentication

import applications.location.createGeocoder
import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.toSingle
import dev.jordond.compass.Place
import dev.jordond.compass.geocoder.Geocoder
import domain.Models.AuthenticationResponse
import domain.Models.CompleteProfileResponse
import domain.Models.ServerResponse
import io.ktor.client.HttpClient

class AuthenticationRepositoryImpl(apiService: HttpClient):
    AuthenticationRepository {

    private val authenticationNetworkService: AuthenticationNetworkService = AuthenticationNetworkService(apiService)

    override suspend fun validateUserProfile(userEmail: String): Single<AuthenticationResponse> {
        val param = ValidateProfileRequest(userEmail)
        return authenticationNetworkService.validateProfile(param)
    }

    override suspend fun validateEmail(userEmail: String): Single<AuthenticationResponse> {
        val param = ValidateProfileRequest(userEmail)
        return authenticationNetworkService.validateEmail(param)
    }

    override suspend fun validatePhone(authPhone: String): Single<AuthenticationResponse> {
        val param = PhoneValidateProfileRequest(authPhone)
        return authenticationNetworkService.validatePhone(param)
    }

    override suspend fun updateProfile(
        userId: Long,
        firstname: String,
        lastname: String,
        address: String,
        contactPhone: String,
        country: String,
        state: Long,
        gender: String,
        profileImageUrl: String
    ): Single<ServerResponse> {
        val param = UpdateProfileRequest(userId, firstname, lastname, contactPhone, address,country,state, gender, profileImageUrl)
        return authenticationNetworkService.updateProfile(param)
    }

    override suspend fun updateFcmToken(userId: Long, fcmToken: String): Single<ServerResponse> {
        val param = UpdateFcmRequest(userId = userId, fcmToken = fcmToken)
        return authenticationNetworkService.updateFcmToken(param)
    }


    override suspend fun completeProfile(
        firstname: String,
        lastname: String,
        userEmail: String,
        authPhone: String,
        country: String,
        state: Long,
        signupType: String,
        gender: String,
        profileImageUrl: String
    ): Single<CompleteProfileResponse> {
        val param = CompleteProfileRequest(firstname = firstname,lastname =  lastname,userEmail =  userEmail, authPhone = authPhone, signupType = signupType, gender = gender, country = country, state = state, profileImageUrl = profileImageUrl)
        return authenticationNetworkService.completeProfile(param)
    }

}