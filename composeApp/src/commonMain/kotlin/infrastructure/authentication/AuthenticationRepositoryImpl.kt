package infrastructure.authentication

import com.badoo.reaktive.single.Single
import domain.Models.AuthenticationResponse
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.Vendor
import io.ktor.client.HttpClient

class AuthenticationRepositoryImpl(apiService: HttpClient):
    AuthenticationRepository {

    private val authenticationNetworkService: AuthenticationNetworkService = AuthenticationNetworkService(apiService)

    override suspend fun getUserProfile(userEmail: String): Single<AuthenticationResponse> {
        val param = GetProfileRequest(userEmail)
        return authenticationNetworkService.getProfile(param)
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
        return authenticationNetworkService.updateProfile(param)
    }

    override suspend fun deleteProfile(userEmail: String): Single<ServerResponse> {
        val param = DeleteProfileRequest(userEmail)
        return authenticationNetworkService.deleteProfile(param)
    }

}