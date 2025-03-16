package domain.home

import com.badoo.reaktive.single.Single
import domain.Models.HomePageResponse
import domain.Models.OrderListDataResponse
import domain.Models.RecommendationListDataResponse

interface HomeRepository {
    suspend fun getUserHomePage(userId: Long): Single<HomePageResponse>
    suspend fun getRecommendations(vendorId: Long, nextPage: Int = 1): Single<RecommendationListDataResponse>
}