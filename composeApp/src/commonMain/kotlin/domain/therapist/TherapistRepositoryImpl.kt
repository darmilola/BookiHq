package domain.therapist

import com.badoo.reaktive.single.Single
import domain.Models.AppointmentListDataResponse
import domain.Models.ServerResponse
import domain.Models.TherapistAppointmentListDataResponse
import domain.Models.TherapistReviewsResponse
import io.ktor.client.HttpClient

class TherapistRepositoryImpl(apiService: HttpClient): TherapistRepository {

    private val therapistNetworkService: TherapistNetworkService = TherapistNetworkService(apiService)
    override suspend fun getReviews(therapistId: Long): Single<TherapistReviewsResponse> {
        val param = GetReviewsRequest(therapistId)
        return therapistNetworkService.getReviews(param)
    }

    override suspend fun updateAvailability(
        therapistId: Long,
        isMobileServiceAvailable: Boolean,
        isAvailable: Boolean
    ): Single<ServerResponse> {
        val param = UpdateTherapistAvailabilityRequest(therapistId, isAvailable, isMobileServiceAvailable)
        return therapistNetworkService.updateTherapistAvailability(param)
    }

    override suspend fun archiveAppointment(appointmentId: Long): Single<ServerResponse> {
        val param = ArchiveAppointmentRequest(appointmentId)
        return therapistNetworkService.archiveAppointment(param)
    }

    override suspend fun doneAppointment(appointmentId: Long): Single<ServerResponse> {
        val param = DoneAppointmentRequest(appointmentId)
        return therapistNetworkService.doneAppointment(param)
    }

    override suspend fun getTherapistAppointments(
        therapistId: Long,
        nextPage: Int
    ): Single<TherapistAppointmentListDataResponse> {
        val param = GetTherapistAppointmentRequest(therapistId)
        return therapistNetworkService.getTherapistAppointments(param, nextPage)
    }

}