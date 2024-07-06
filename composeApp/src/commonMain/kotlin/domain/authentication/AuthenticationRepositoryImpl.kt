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
    private val geocoder: Geocoder = createGeocoder()


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


    override suspend fun completeProfile(
        firstname: String,
        lastname: String,
        userEmail: String,
        authPhone: String,
        signupType: String,
        country: String,
        city: String,
        gender: String,
        profileImageUrl: String
    ): Single<CompleteProfileResponse> {
        val param = CompleteProfileRequest(firstname = firstname,lastname =  lastname,userEmail =  userEmail, authPhone = authPhone, signupType = signupType, country = country, city = city, gender = gender, profileImageUrl = profileImageUrl)
        return authenticationNetworkService.completeProfile(param)
    }

    override suspend fun reverseGeocode(lat: Double, lng: Double): Single<Place?> {
        return geocoder.reverse(lat, lng).getFirstOrNull().toSingle()
    }

}