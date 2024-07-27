package domain.bookings

import com.badoo.reaktive.single.Single
import domain.Models.PendingAppointmentResponse
import domain.Models.ServerResponse
import domain.Models.ServiceTherapistsResponse

interface BookingRepository {
    suspend fun getServiceTherapist(serviceTypeId: Int, vendorId: Long): Single<ServiceTherapistsResponse>

    suspend fun createPendingAppointment(userId: Long, vendorId: Long, serviceId: Int, serviceTypeId: Int, therapistId: Int,
                                         appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String, serviceStatus: String,
                                         appointmentType: String, paymentAmount: Double, paymentMethod: String, bookingStatus: String): Single<PendingAppointmentResponse>
    suspend fun createAppointment(userId: Long, vendorId: Long, paymentAmount: Double, paymentMethod: String, bookingStatus: String, day: Int, month: Int, year: Int) : Single<ServerResponse>
    suspend fun getPendingAppointment(userId: Long): Single<PendingAppointmentResponse>
    suspend fun deletePendingAppointment(pendingAppointmentId: Long) : Single<ServerResponse>
}

