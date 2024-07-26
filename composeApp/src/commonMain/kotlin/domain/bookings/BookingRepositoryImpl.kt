package domain.bookings

import com.badoo.reaktive.single.Single
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

    override suspend fun createAppointment(userId: Long, vendorId: Long, service_id: Int, serviceTypeId: Int, therapist_id: Int,
                                           appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String, serviceStatus: String,
                                           appointmentType: String, paymentAmount: Double, paymentMethod: String): Single<ServerResponse> {
        val param = CreateAppointmentRequest(userId, vendorId, service_id, serviceTypeId, therapist_id,
            appointmentTime, day, month, year, serviceLocation, serviceStatus, appointmentType,paymentAmount, paymentMethod)
        return bookingNetworkService.createAppointment(param)
    }

}