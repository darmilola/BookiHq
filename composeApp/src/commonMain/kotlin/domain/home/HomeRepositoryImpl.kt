package domain.home

import com.badoo.reaktive.single.Single
import domain.Models.HomePageResponse
import io.ktor.client.HttpClient

class HomeRepositoryImpl(apiService: HttpClient):
    HomeRepository {

    private val homeNetworkService: HomeNetworkService = HomeNetworkService(apiService)
    override suspend fun getUserHomePage(userId: Long, vendorPhone: String): Single<HomePageResponse> {
        val param = GetHomeRequest(userId, vendorPhone)
        return homeNetworkService.getHomePage(param)
    }

}