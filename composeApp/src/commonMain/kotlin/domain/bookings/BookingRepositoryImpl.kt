package domain.bookings

import com.badoo.reaktive.single.Single
import domain.Models.ServerResponse
import domain.Models.ServiceTherapistsResponse
import io.ktor.client.HttpClient

class BookingRepositoryImpl(apiService: HttpClient): BookingRepository {

    private val bookingNetworkService: BookingNetworkService = BookingNetworkService(apiService)

    override suspend fun getServiceTherapist(
        serviceTypeId: Int,
        day: Int,
        month: Int,
        year: Int,
    ): Single<ServiceTherapistsResponse> {
        val param = GetTherapistsRequest(serviceTypeId, day, month, year)
        return bookingNetworkService.getTherapists(param)
    }

    override suspend fun createAppointment(appointmentRequests: ArrayList<CreateAppointmentRequest>): Single<ServerResponse> {
        val param = CreateAppointmentRequestArray(appointmentRequests)
        return bookingNetworkService.createAppointment(param)
    }

}