package domain.bookings

import com.badoo.reaktive.single.Single
import domain.Models.PendingBookingAppointmentResponse
import domain.Models.ServerResponse
import domain.Models.ServiceTherapistsResponse
import io.ktor.client.HttpClient

class BookingRepositoryImpl(apiService: HttpClient): BookingRepository {

    private val bookingNetworkService: BookingNetworkService = BookingNetworkService(apiService)

    override suspend fun getServiceTherapist(serviceTypeId: Long, vendorId: Long, day: Int, month: Int, year: Int): Single<ServiceTherapistsResponse> {
        val param = GetTherapistsRequest(serviceTypeId, vendorId, day, month, year)
        return bookingNetworkService.getTherapists(param)
    }

    override suspend fun createPendingBookingAppointment(userId: Long, vendorId: Long, serviceId: Long, serviceTypeId: Long, therapistId: Long,
                                                         appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String, serviceStatus: String,
                                                          paymentAmount: Double, paymentMethod: String, bookingStatus: String): Single<PendingBookingAppointmentResponse> {
        val param = CreatePendingBookingAppointmentRequest(userId, vendorId, serviceId, serviceTypeId, therapistId,
            appointmentTime, day, month, year, serviceLocation, serviceStatus, bookingStatus, paymentMethod)
        return bookingNetworkService.createPendingBookingAppointment(param)
    }

    override suspend fun createAppointment(
        userId: Long,
        vendorId: Long,
        paymentAmount: Int,
        paymentMethod: String,
        bookingStatus: String,
        day: Int,
        month: Int,
        year: Int
    ): Single<ServerResponse> {
        val param = CreateAppointmentRequest(userId, vendorId, day, month, year, bookingStatus, paymentAmount, paymentMethod)
        return bookingNetworkService.createAppointment(param)
    }

    override suspend fun getPendingBookingAppointment(userId: Long, bookingStatus: String): Single<PendingBookingAppointmentResponse> {
        val param = GetPendingBookingAppointmentRequest(userId,bookingStatus)
        return bookingNetworkService.getPendingBookingAppointment(param)
    }

    override suspend fun deletePendingBookingAppointment(pendingAppointmentId: Long): Single<ServerResponse> {
        val param = DeletePendingBookingAppointmentRequest(pendingAppointmentId)
        return bookingNetworkService.deletePendingBookingAppointment(param)
    }

}