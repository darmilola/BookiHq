package domain.home

import com.badoo.reaktive.single.Single
import domain.Models.HomePageResponse
import infrastructure.authentication.AuthenticationNetworkService
import infrastructure.authentication.AuthenticationRepository
import infrastructure.authentication.GetProfileRequest
import io.ktor.client.HttpClient

class HomeRepositoryImpl(apiService: HttpClient):
    HomeRepository {

    private val homeNetworkService: HomeNetworkService = HomeNetworkService(apiService)
    override suspend fun getUserHomePage(userEmail: String, connectedVendor: Int): Single<HomePageResponse> {
        val param = GetHomeRequest(userEmail, connectedVendor)
        return homeNetworkService.getHomePage(param)
    }

}