package domain.bookings

import com.badoo.reaktive.single.Single
import domain.Models.Appointment
import domain.Models.ServerResponse
import domain.Models.ServiceSpecialistsResponse
import domain.Models.SpecialistAvailabilityResponse
import domain.Profile.DeleteProfileRequest
import domain.Profile.ProfileNetworkService
import domain.Profile.ProfileRepository
import domain.Profile.UpdateProfileRequest
import io.ktor.client.HttpClient
import kotlinx.serialization.SerialName

class BookingRepositoryImpl(apiService: HttpClient): BookingRepository {

    private val bookingNetworkService: BookingNetworkService = BookingNetworkService(apiService)

    override suspend fun getServiceTherapist(
        serviceTypeId: Int,
        selectedDate: String
    ): Single<ServiceSpecialistsResponse> {
        val param = GetSpecialistsRequest(serviceTypeId, selectedDate)
        return bookingNetworkService.getSpecialists(param)
    }

    override suspend fun createAppointment(appointmentRequests: ArrayList<CreateAppointmentRequest>): Single<ServerResponse> {
        val param = CreateAppointmentRequestArray(appointmentRequests)
        return bookingNetworkService.createAppointment(param)
    }

    override suspend fun postponeAppointment(
        appointment: Appointment,
        appointmentTime: Int,
        appointmentDate: String
    ): Single<ServerResponse> {

        val param = PostponeAppointmentRequest(userId = appointment.userId!!, vendorId = appointment.vendorId, serviceId = appointment.serviceId,
            serviceTypeId = appointment.serviceTypeId!!, specialistId = appointment.specialistId, recommendationId = appointment.recommendationId, appointmentTime = appointmentTime,
            appointmentDate = appointmentDate, serviceLocation = appointment.serviceLocation, serviceStatus = appointment.serviceStatus,
            isRecommendedAppointment = appointment.isRecommendedAppointment, appointmentId = appointment.appointmentId!!)
          return bookingNetworkService.postponeAppointment(param)
    }

    override suspend fun deleteAppointment(appointmentId: Int): Single<ServerResponse> {
        val param = DeleteAppointmentRequest(appointmentId)
        return bookingNetworkService.deleteAppointment(param)
    }

    override suspend fun getTherapistAvailability(
        specialistId: Int,
        selectedDate: String
    ): Single<SpecialistAvailabilityResponse> {
        val param = GetSpecialistAvailabilityRequest(specialistId, selectedDate)
        return bookingNetworkService.getSpecialistAvailability(param)
    }


}