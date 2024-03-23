package domain.specialist

import com.badoo.reaktive.single.Single
import domain.Models.ServerResponse
import domain.Models.SpecialistReviews
import domain.Models.SpecialistReviewsResponse
import domain.Profile.DeleteProfileRequest
import domain.Profile.ProfileNetworkService
import domain.Profile.ProfileRepository
import domain.Profile.UpdateProfileRequest
import io.ktor.client.HttpClient

class SpecialistRepositoryImpl(apiService: HttpClient): SpecialistRepository {

    private val specialistNetworkService: SpecialistNetworkService = SpecialistNetworkService(apiService)
    override suspend fun getReviews(specialistId: Int): Single<SpecialistReviewsResponse> {
        val param = GetReviewsRequest(specialistId)
        return specialistNetworkService.getReviews(param)
    }

}