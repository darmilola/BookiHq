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

    override suspend fun createAppointment(appointmentRequests: ArrayList<CreateAppointmentRequest>): Single<ServerResponse> {
        val param = CreateAppointmentRequestArray(appointmentRequests)
        return bookingNetworkService.createAppointment(param)
    }

}