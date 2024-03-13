package domain.Profile

import com.badoo.reaktive.single.Single
import domain.Models.AuthenticationResponse
import domain.Models.ServerResponse
import infrastructure.authentication.AuthenticationNetworkService
import infrastructure.authentication.AuthenticationRepository
import infrastructure.authentication.CompleteProfileRequest
import io.ktor.client.HttpClient

class ProfileRepositoryImpl(apiService: HttpClient): ProfileRepository {

    private val profileNetworkService: ProfileNetworkService = ProfileNetworkService((apiService))
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

}