package domain.specialist

import com.badoo.reaktive.single.Single
import domain.Models.ServerResponse
import domain.Models.SpecialistAvailabilityResponse
import domain.Models.SpecialistReviews
import domain.Models.SpecialistReviewsResponse
import domain.Models.SpecialistTimeAvailabilityResponse
import domain.Profile.DeleteProfileRequest
import domain.Profile.ProfileNetworkService
import domain.Profile.ProfileRepository
import domain.Profile.UpdateProfileRequest
import domain.appointments.GetSpecialistAvailabilityRequest
import io.ktor.client.HttpClient

class SpecialistRepositoryImpl(apiService: HttpClient): SpecialistRepository {

    private val specialistNetworkService: SpecialistNetworkService = SpecialistNetworkService(apiService)
    override suspend fun getReviews(specialistId: Int): Single<SpecialistReviewsResponse> {
        val param = GetReviewsRequest(specialistId)
        return specialistNetworkService.getReviews(param)
    }

    override suspend fun getTherapistAvailability(
        specialistId: Int, day: Int, month: Int, year: Int): Single<SpecialistTimeAvailabilityResponse> {
        val param = GetSpecialistAvailableTimeRequest(specialistId, day, month, year)
        return specialistNetworkService.getSpecialistAvailability(param)
    }

    override suspend fun addTimeOff(
        specialistId: Int,
        timeId: Int,
        day: Int,
        month: Int,
        year: Int
    ): Single<ServerResponse> {
        val param = TimeOffRequest(specialistId, timeId, day, year, month)
        println("Params $param")
        return specialistNetworkService.addTimeOff(param)
    }

    override suspend fun removeTimeOff(
        specialistId: Int,
        timeId: Int,
        day: Int,
        month: Int,
        year: Int
    ): Single<ServerResponse> {
        val param = TimeOffRequest(specialistId, timeId, day, year, month)
        return specialistNetworkService.removeTimeOff(param)
    }

}