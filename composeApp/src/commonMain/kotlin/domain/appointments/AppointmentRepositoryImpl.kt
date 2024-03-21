package domain.appointments

import com.badoo.reaktive.single.Single
import domain.Models.Appointment
import domain.Models.ListDataResponse
import domain.Models.Product
import domain.Models.ProductCategoryResponse
import domain.Models.ServerResponse
import domain.Models.ServiceStatus
import domain.Models.SpecialistAvailabilityResponse
import domain.Products.GetProductCategoryRequest
import domain.Products.GetProductsInCategoryRequest
import domain.Products.ProductNetworkService
import domain.Products.ProductRepository
import io.ktor.client.HttpClient

class AppointmentRepositoryImpl(apiService: HttpClient): AppointmentRepository {
    private val appointmentNetworkService: AppointmentNetworkService = AppointmentNetworkService(apiService)

    override suspend fun getAppointments(
        userId: Int,
        nextPage: Int
    ): Single<ListDataResponse<Appointment>> {
        val param = GetAppointmentRequest(userId)
        return appointmentNetworkService.getAppointments(param, nextPage)
    }

    override suspend fun postponeAppointment(
        appointment: Appointment,
        appointmentTime: Int,
        appointmentDate: String
    ): Single<ServerResponse> {

        val param = PostponeAppointmentRequest(userId = appointment.userId!!, vendorId = appointment.vendorId, serviceId = appointment.serviceId,
            serviceTypeId = appointment.serviceTypeId!!, specialistId = appointment.specialistId, recommendationId = appointment.recommendationId, appointmentTime = appointmentTime,
            appointmentDate = appointmentDate, serviceLocation = appointment.serviceLocation, serviceStatus = ServiceStatus.Pending.toPath(),
            isRecommendedAppointment = appointment.isRecommendedAppointment, appointmentId = appointment.appointmentId!!)
        return appointmentNetworkService.postponeAppointment(param)
    }

    override suspend fun deleteAppointment(appointmentId: Int): Single<ServerResponse> {
        val param = DeleteAppointmentRequest(appointmentId)
        return appointmentNetworkService.deleteAppointment(param)
    }

    override suspend fun getTherapistAvailability(
        specialistId: Int,
        selectedDate: String
    ): Single<SpecialistAvailabilityResponse> {
        val param = GetSpecialistAvailabilityRequest(specialistId, selectedDate)
        return appointmentNetworkService.getSpecialistAvailability(param)
    }


}