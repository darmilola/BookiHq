package domain.home

import com.badoo.reaktive.single.Single
import domain.Models.HomePageResponse
import io.ktor.client.HttpClient

class HomeRepositoryImpl(apiService: HttpClient):
    HomeRepository {

    private val homeNetworkService: HomeNetworkService = HomeNetworkService(apiService)
    override suspend fun getUserHomePage(userId: Long): Single<HomePageResponse> {
        val param = GetHomeRequest(userId)
        return homeNetworkService.getHomePage(param)
    }

}