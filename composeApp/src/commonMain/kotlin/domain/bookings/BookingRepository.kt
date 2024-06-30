package domain.bookings

import com.badoo.reaktive.single.Single
import domain.Models.ServerResponse
import domain.Models.ServiceTherapistsResponse

interface BookingRepository {
    suspend fun getServiceTherapist(
        serviceTypeId: Int,
        day: Int,
        month: Int,
        year: Int): Single<ServiceTherapistsResponse>
    suspend fun createAppointment(appointmentRequests: ArrayList<CreateAppointmentRequest>): Single<ServerResponse>
}

