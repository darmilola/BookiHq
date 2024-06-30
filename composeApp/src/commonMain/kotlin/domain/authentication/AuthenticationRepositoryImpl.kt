package domain.authentication

import applications.location.createGeocoder
import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.toSingle
import dev.jordond.compass.Location
import dev.jordond.compass.Place
import dev.jordond.compass.geocoder.Geocoder
import domain.Models.AuthenticationResponse
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.Vendor
import io.ktor.client.HttpClient

class AuthenticationRepositoryImpl(apiService: HttpClient):
    AuthenticationRepository {

    private val authenticationNetworkService: AuthenticationNetworkService = AuthenticationNetworkService(apiService)
    private val geocoder: Geocoder = createGeocoder()


    override suspend fun validateUserProfile(userEmail: String): Single<AuthenticationResponse> {
        val param = ValidateProfileRequest(userEmail)
        return authenticationNetworkService.validateProfile(param)
    }
    override suspend fun completeProfile(
        firstname: String,
        lastname: String,
        userEmail: String,
        address: String,
        contactPhone: String,
        countryId: Int,
        cityId: Int,
        gender: String,
        profileImageUrl: String
    ): Single<ServerResponse> {
        val param = CompleteProfileRequest(firstname, lastname, userEmail, address, contactPhone, countryId, cityId, gender, profileImageUrl)
        return authenticationNetworkService.completeProfile(param)
    }

    override suspend fun reverseGeocode(lat: Double, lng: Double): Single<Place?> {
        return geocoder.reverse(lat, lng).getFirstOrNull().toSingle()
    }

}