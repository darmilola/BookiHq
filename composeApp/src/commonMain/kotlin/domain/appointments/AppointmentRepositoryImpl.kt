package domain.appointments

import com.badoo.reaktive.single.Single
import domain.Enums.ServiceStatusEnum
import domain.Models.Appointment
import domain.Models.AppointmentListDataResponse
import domain.Models.JoinMeetingResponse
import domain.Models.ServerResponse
import domain.Models.TherapistAvailabilityResponse
import io.ktor.client.HttpClient

class AppointmentRepositoryImpl(apiService: HttpClient): AppointmentRepository {
    private val appointmentNetworkService: AppointmentNetworkService = AppointmentNetworkService(apiService)

    override suspend fun getAppointments(
        userId: Int,
        nextPage: Int
    ): Single<AppointmentListDataResponse> {
        val param = GetAppointmentRequest(userId)
        return appointmentNetworkService.getAppointments(param, nextPage)
    }
    override suspend fun postponeAppointment(
        appointment: Appointment,
        appointmentTime: Int,
        day: Int,
        month: Int,
        year: Int
    ): Single<ServerResponse> {

        val param = PostponeAppointmentRequest(userId = appointment.userId!!, vendorId = appointment.vendorId, serviceId = appointment.serviceId,
            serviceTypeId = appointment.serviceTypeId!!, therapistId = appointment.therapistId, recommendationId = appointment.recommendationId, appointmentTime = appointmentTime,
            day = day, month = month, year = year, serviceLocation = appointment.serviceLocation, serviceStatus = ServiceStatusEnum.PENDING.toPath(),
            isRecommendedAppointment = appointment.isRecommendedAppointment, appointmentId = appointment.appointmentId!!)
        return appointmentNetworkService.postponeAppointment(param)
    }

    override suspend fun deleteAppointment(appointmentId: Int): Single<ServerResponse> {
        val param = DeleteAppointmentRequest(appointmentId)
        return appointmentNetworkService.deleteAppointment(param)
    }

    override suspend fun joinMeeting(
        customParticipantId: String,
        presetName: String,
        meetingId: String
    ): Single<JoinMeetingResponse> {
        val param = JoinMeetingRequest(customParticipantId, presetName, meetingId)
        return appointmentNetworkService.joinMeeting(param)
    }

    override suspend fun getTherapistAvailability(
        specialistId: Int,
        day: Int, month: Int, year: Int
    ): Single<TherapistAvailabilityResponse> {
        val param = GetTherapistAvailabilityRequest(specialistId, day, month, year)
        return appointmentNetworkService.getTherapistAvailability(param)
    }


}