package domain.home

import com.badoo.reaktive.single.Single
import domain.Models.HomePageResponse

interface HomeRepository {
    suspend fun getUserHomePage(userId: Long): Single<HomePageResponse>

    suspend fun getUserHomePageWithStatus(userId: Long, vendorPhone: String): Single<HomePageResponse>

}