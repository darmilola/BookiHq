package domain.bookings

import com.badoo.reaktive.single.Single
import domain.Models.PendingBookingAppointmentResponse
import domain.Models.ServerResponse
import domain.Models.ServiceTherapistsResponse

interface BookingRepository {
    suspend fun getServiceTherapist(serviceTypeId: Int, vendorId: Long): Single<ServiceTherapistsResponse>

    suspend fun createPendingBookingAppointment(userId: Long, vendorId: Long, serviceId: Int, serviceTypeId: Int, therapistId: Int,
                                                appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String, serviceStatus: String,
                                                appointmentType: String, paymentAmount: Double, paymentMethod: String, bookingStatus: String): Single<PendingBookingAppointmentResponse>
    suspend fun createAppointment(userId: Long, vendorId: Long, paymentAmount: Double, paymentMethod: String, bookingStatus: String, day: Int, month: Int, year: Int) : Single<ServerResponse>
    suspend fun getPendingBookingAppointment(userId: Long): Single<PendingBookingAppointmentResponse>
    suspend fun deletePendingBookingAppointment(pendingAppointmentId: Long) : Single<ServerResponse>
}

