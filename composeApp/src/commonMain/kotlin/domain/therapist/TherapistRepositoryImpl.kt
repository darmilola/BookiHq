package domain.therapist

import com.badoo.reaktive.single.Single
import domain.Models.AppointmentListDataResponse
import domain.Models.ServerResponse
import domain.Models.TherapistReviewsResponse
import domain.Models.TherapistTimeAvailabilityResponse
import domain.appointments.GetTherapistAppointmentRequest
import io.ktor.client.HttpClient

class TherapistRepositoryImpl(apiService: HttpClient): TherapistRepository {

    private val therapistNetworkService: TherapistNetworkService = TherapistNetworkService(apiService)
    override suspend fun getReviews(therapistId: Int): Single<TherapistReviewsResponse> {
        val param = GetReviewsRequest(therapistId)
        return therapistNetworkService.getReviews(param)
    }

    override suspend fun getTherapistAppointments(
        therapistId: Int,
        nextPage: Int
    ): Single<AppointmentListDataResponse> {
        val param = GetTherapistAppointmentRequest(therapistId)
        return therapistNetworkService.getTherapistAppointments(param, nextPage)
    }

}