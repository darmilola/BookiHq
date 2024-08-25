package domain.therapist

import com.badoo.reaktive.single.Single
import domain.Models.AppointmentListDataResponse
import domain.Models.ServerResponse
import domain.Models.TherapistAppointmentListDataResponse
import domain.Models.TherapistReviewsResponse
import domain.Models.TherapistTimeAvailabilityResponse

interface TherapistRepository {

    suspend fun getReviews(therapistId: Long): Single<TherapistReviewsResponse>
    suspend fun updateAvailability(therapistId: Long, isMobileServiceAvailable: Boolean, isAvailable: Boolean): Single<ServerResponse>
    suspend fun archiveAppointment(appointmentId: Long): Single<ServerResponse>
    suspend fun doneAppointment(appointmentId: Long): Single<ServerResponse>
    suspend fun getTherapistAppointments(therapistId: Long, nextPage: Int = 1): Single<TherapistAppointmentListDataResponse>
}