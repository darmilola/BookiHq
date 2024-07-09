package domain.bookings

import com.badoo.reaktive.single.Single
import domain.Models.ServerResponse
import domain.Models.ServiceTherapistsResponse

interface BookingRepository {
    suspend fun getServiceTherapist(serviceTypeId: Int, vendorId: Long): Single<ServiceTherapistsResponse>
    suspend fun createAppointment(appointmentRequests: ArrayList<CreateAppointmentRequest>): Single<ServerResponse>
}

