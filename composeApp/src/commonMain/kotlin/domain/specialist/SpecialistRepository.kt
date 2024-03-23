package domain.specialist

import com.badoo.reaktive.single.Single
import domain.Models.ServerResponse
import domain.Models.SpecialistReviews
import domain.Models.SpecialistReviewsResponse

interface SpecialistRepository {

    suspend fun getReviews(specialistId: Int): Single<SpecialistReviewsResponse>
}