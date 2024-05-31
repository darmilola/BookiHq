package domain.specialist

import com.badoo.reaktive.single.Single
import domain.Models.ServerResponse
import domain.Models.SpecialistAvailabilityResponse
import domain.Models.SpecialistReviews
import domain.Models.SpecialistReviewsResponse

interface SpecialistRepository {

    suspend fun getReviews(specialistId: Int): Single<SpecialistReviewsResponse>
    suspend fun getTherapistAvailability(specialistId: Int, day: Int, month: Int, year: Int): Single<SpecialistAvailabilityResponse>
    suspend fun addTimeOff(specialistId: Int, timeId: Int, date: String): Single<ServerResponse>
    suspend fun removeTimeOff(specialistId: Int, timeId: Int, date: String): Single<ServerResponse>
}