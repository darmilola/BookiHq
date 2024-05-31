package domain.appointments

import com.badoo.reaktive.single.Single
import domain.Models.Appointment
import domain.Models.AppointmentListDataResponse
import domain.Models.AppointmentResourceListEnvelope
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.ServiceStatus
import domain.Models.SpecialistAvailabilityResponse
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

    override suspend fun getSpecialistAppointments(
        specialistId: Int,
        nextPage: Int
    ): Single<AppointmentListDataResponse> {
        val param = GetSpecialistAppointmentRequest(specialistId)
        return appointmentNetworkService.getSpecialistAppointments(param, nextPage)
    }

    override suspend fun postponeAppointment(
        appointment: Appointment,
        appointmentTime: Int,
        day: Int,
        month: Int,
        year: Int
    ): Single<ServerResponse> {

        val param = PostponeAppointmentRequest(userId = appointment.userId!!, vendorId = appointment.vendorId, serviceId = appointment.serviceId,
            serviceTypeId = appointment.serviceTypeId!!, specialistId = appointment.specialistId, recommendationId = appointment.recommendationId, appointmentTime = appointmentTime,
            day = day, month = month, year = year, serviceLocation = appointment.serviceLocation, serviceStatus = ServiceStatus.Pending.toPath(),
            isRecommendedAppointment = appointment.isRecommendedAppointment, appointmentId = appointment.appointmentId!!)
        return appointmentNetworkService.postponeAppointment(param)
    }

    override suspend fun deleteAppointment(appointmentId: Int): Single<ServerResponse> {
        val param = DeleteAppointmentRequest(appointmentId)
        return appointmentNetworkService.deleteAppointment(param)
    }

    override suspend fun getTherapistAvailability(
        specialistId: Int,
        day: Int, month: Int, year: Int
    ): Single<SpecialistAvailabilityResponse> {
        val param = GetSpecialistAvailabilityRequest(specialistId, day, month, year)
        return appointmentNetworkService.getSpecialistAvailability(param)
    }


}