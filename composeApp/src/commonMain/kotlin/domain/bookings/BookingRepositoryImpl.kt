package domain.bookings

import com.badoo.reaktive.single.Single
import domain.Models.PendingAppointmentResponse
import domain.Models.ServerResponse
import domain.Models.ServiceTherapistsResponse
import io.ktor.client.HttpClient

class BookingRepositoryImpl(apiService: HttpClient): BookingRepository {

    private val bookingNetworkService: BookingNetworkService = BookingNetworkService(apiService)

    override suspend fun getServiceTherapist(
        serviceTypeId: Int,
        vendorId: Long
    ): Single<ServiceTherapistsResponse> {
        val param = GetTherapistsRequest(serviceTypeId, vendorId)
        return bookingNetworkService.getTherapists(param)
    }

    override suspend fun createPendingAppointment(userId: Long, vendorId: Long, service_id: Int, serviceTypeId: Int, therapist_id: Int,
                                                  appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String, serviceStatus: String,
                                                  appointmentType: String, paymentAmount: Double, paymentMethod: String, bookingStatus: String): Single<PendingAppointmentResponse> {
        val param = CreatePendingAppointmentRequest(userId, vendorId, service_id, serviceTypeId, therapist_id,
            appointmentTime, day, month, year, serviceLocation, serviceStatus, appointmentType, bookingStatus, paymentMethod)
        return bookingNetworkService.createPendingAppointment(param)
    }

    override suspend fun createAppointment(
        userId: Long,
        vendorId: Long,
        paymentAmount: Double,
        paymentMethod: String,
        bookingStatus: String,
        day: Int,
        month: Int,
        year: Int
    ): Single<ServerResponse> {
        val param = CreateAppointmentRequest(userId, vendorId, day, month, year, bookingStatus, paymentAmount, paymentMethod)
        return bookingNetworkService.createAppointment(param)
    }

    override suspend fun getPendingAppointment(userId: Long): Single<PendingAppointmentResponse> {
        val param = GetPendingAppointmentRequest(userId)
        return bookingNetworkService.getPendingAppointment(param)
    }

    override suspend fun deletePendingAppointment(pendingAppointmentId: Long): Single<ServerResponse> {
        val param = DeletePendingAppointmentRequest(pendingAppointmentId)
        return bookingNetworkService.deletePendingAppointment(param)
    }

}