package domain.specialist

import com.badoo.reaktive.single.Single
import domain.Models.ServerResponse
import domain.Models.SpecialistReviews

interface SpecialistRepository {

    suspend fun getReviews(specialistId: Int): Single<List<SpecialistReviews>>
}