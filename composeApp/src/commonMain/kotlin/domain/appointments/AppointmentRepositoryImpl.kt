package domain.appointments

import com.badoo.reaktive.single.Single
import domain.Enums.ServiceStatusEnum
import domain.Models.AppointmentListDataResponse
import domain.Models.JoinMeetingResponse
import domain.Models.ServerResponse
import domain.Models.TherapistAvailabilityResponse
import domain.Models.UserAppointment
import io.ktor.client.HttpClient
import kotlinx.serialization.SerialName

class AppointmentRepositoryImpl(apiService: HttpClient): AppointmentRepository {
    private val appointmentNetworkService: AppointmentNetworkService = AppointmentNetworkService(apiService)

    override suspend fun getAppointments(
        userId: Long,
        nextPage: Int
    ): Single<AppointmentListDataResponse> {
        println(userId)
        val param = GetAppointmentRequest(userId)
        return appointmentNetworkService.getAppointments(param, nextPage)
    }
    override suspend fun postponeAppointment(
        userAppointment: UserAppointment,
        appointmentTime: Int,
        day: Int,
        month: Int,
        year: Int
    ): Single<ServerResponse> {
        println("Info are ${userAppointment.userId}, ${userAppointment.appointmentId}, ${userAppointment.bookingStatus}")
        val appointment = userAppointment.resources!!
        val param = PostponeAppointmentRequest(userId = appointment.userId!!, vendorId = appointment.vendorId, serviceId = appointment.serviceId,
            serviceTypeId = appointment.serviceTypeId!!, therapistId = appointment.therapistId, appointmentTime = appointmentTime,
            day = day, month = month, year = year, serviceLocation = appointment.serviceLocation, serviceStatus = ServiceStatusEnum.PENDING.toPath(),
            appointmentId = appointment.appointmentId!!, bookingStatus = userAppointment.bookingStatus!!, paymentMethod = userAppointment.paymentMethod!!)
        return appointmentNetworkService.postponeAppointment(param)
    }

    override suspend fun deleteAppointment(appointmentId: Long): Single<ServerResponse> {
        val param = DeleteAppointmentRequest(appointmentId)
        return appointmentNetworkService.deleteAppointment(param)
    }

    override suspend fun addAppointmentReviews(
        userId: Long,
        appointmentId: Long,
        vendorId: Long,
        serviceTypeId: Long,
        therapistId: Long,
        reviewText: String
    ): Single<ServerResponse> {
        val param = AddAppointmentReviewRequest(userId, appointmentId, vendorId, serviceTypeId,therapistId, reviewText)
        return appointmentNetworkService.addAppointmentReview(param)
    }

    override suspend fun joinMeeting(
        customParticipantId: String,
        presetName: String,
        meetingId: String
    ): Single<JoinMeetingResponse> {
        val param = JoinMeetingRequest(customParticipantId, presetName, meetingId)
        return appointmentNetworkService.joinMeeting(param)
    }
    override suspend fun getTherapistAvailability(therapistId: Long,vendorId: Long,day: Int, month: Int, year: Int): Single<TherapistAvailabilityResponse> {
        val param = GetTherapistAvailabilityRequest(therapistId,vendorId, day, month, year)
        return appointmentNetworkService.getTherapistAvailability(param)
    }


}