package domain.home

import com.badoo.reaktive.single.Single
import domain.Models.HomePageResponse

interface HomeRepository {
    suspend fun getUserHomePage(userEmail: String): Single<HomePageResponse>

}