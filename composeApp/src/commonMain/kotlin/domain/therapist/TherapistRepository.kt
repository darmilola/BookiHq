package domain.therapist

import com.badoo.reaktive.single.Single
import domain.Models.AppointmentListDataResponse
import domain.Models.ServerResponse
import domain.Models.TherapistReviewsResponse
import domain.Models.TherapistTimeAvailabilityResponse

interface TherapistRepository {

    suspend fun getReviews(therapistId: Int): Single<TherapistReviewsResponse>
    suspend fun getTherapistAppointments(therapistId: Int, nextPage: Int = 1): Single<AppointmentListDataResponse>
}