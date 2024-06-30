package domain.Profile

import applications.location.createGeocoder
import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.toSingle
import dev.jordond.compass.Place
import dev.jordond.compass.geocoder.Geocoder
import domain.Models.AuthenticationResponse
import domain.Models.ServerResponse
import domain.Models.VendorAvailabilityResponse
import domain.authentication.AuthenticationNetworkService
import domain.authentication.AuthenticationRepository
import domain.authentication.CompleteProfileRequest
import io.ktor.client.HttpClient

class ProfileRepositoryImpl(apiService: HttpClient): ProfileRepository {

    private val profileNetworkService: ProfileNetworkService = ProfileNetworkService(apiService)
    private val geocoder: Geocoder = createGeocoder()

    override suspend fun updateProfile(
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
        val param = UpdateProfileRequest(firstname, lastname, userEmail, address, contactPhone, countryId,cityId, gender, profileImageUrl)
        return profileNetworkService.updateProfile(param)
    }

    override suspend fun deleteProfile(userEmail: String): Single<ServerResponse> {
        val param = DeleteProfileRequest(userEmail)
        return profileNetworkService.deleteProfile(param)
    }

    override suspend fun getVendorAvailableTimes(vendorId: Int): Single<VendorAvailabilityResponse> {
        val param = GetVendorAvailabilityRequest(vendorId)
        return profileNetworkService.getVendorAvailableTimes(param)
    }

    override suspend fun reverseGeocode(lat: Double, lng: Double): Single<Place?> {
        return geocoder.reverse(lat, lng).getFirstOrNull().toSingle()
    }

}