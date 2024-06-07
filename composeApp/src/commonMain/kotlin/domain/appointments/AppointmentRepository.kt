package domain.appointments

import com.badoo.reaktive.single.Single
import domain.Models.Appointment
import domain.Models.AppointmentListDataResponse
import domain.Models.AppointmentResourceListEnvelope
import domain.Models.JoinMeetingResponse
import domain.Models.ListDataResponse
import domain.Models.Product
import domain.Models.ServerResponse
import domain.Models.SpecialistAvailabilityResponse

interface AppointmentRepository {
    suspend fun getAppointments(userId: Int, nextPage: Int = 1): Single<AppointmentListDataResponse>
    suspend fun getSpecialistAppointments(specialistId: Int, nextPage: Int = 1): Single<AppointmentListDataResponse>
    suspend fun postponeAppointment(appointment: Appointment, appointmentTime: Int,  day: Int, month: Int, year: Int): Single<ServerResponse>
    suspend fun deleteAppointment(appointmentId: Int): Single<ServerResponse>
    suspend fun joinMeeting(customParticipantId: String, presetName: String, meetingId: String): Single<JoinMeetingResponse>
    suspend fun getTherapistAvailability(specialistId: Int, day: Int, month: Int, year: Int): Single<SpecialistAvailabilityResponse>
}