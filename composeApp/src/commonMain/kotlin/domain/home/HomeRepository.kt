package domain.home

import com.badoo.reaktive.single.Single
import domain.Models.HomePageResponse

interface HomeRepository {
    suspend fun getUserHomePage(userId: Long, vendorPhone: String): Single<HomePageResponse>

}