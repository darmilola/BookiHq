package domain.appointments

import com.badoo.reaktive.single.Single
import domain.Models.AppointmentListDataResponse
import domain.Models.JoinMeetingResponse
import domain.Models.ServerResponse
import domain.Models.TherapistAvailabilityResponse
import domain.Models.UserAppointment

interface AppointmentRepository {
    suspend fun getAppointments(userId: Long, nextPage: Int = 1): Single<AppointmentListDataResponse>
    suspend fun postponeAppointment(userAppointment: UserAppointment, appointmentTime: Int, day: Int, month: Int, year: Int): Single<ServerResponse>
    suspend fun deleteAppointment(appointmentId: Long): Single<ServerResponse>
    suspend fun addAppointmentReviews(userId: Long, appointmentId: Long, vendorId: Long, serviceTypeId: Long, therapistId: Long, reviewText: String): Single<ServerResponse>
    suspend fun addPackageAppointmentReviews(userId: Long, appointmentId: Long, vendorId: Long, packageId: Long, reviewText: String): Single<ServerResponse>
    suspend fun joinMeeting(customParticipantId: String, presetName: String, meetingId: String): Single<JoinMeetingResponse>
    suspend fun getTherapistAvailability(therapistId: Long,vendorId: Long,day: Int, month: Int, year: Int): Single<TherapistAvailabilityResponse>
}