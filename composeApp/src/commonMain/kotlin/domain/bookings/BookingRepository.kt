package domain.bookings

import com.badoo.reaktive.single.Single
import domain.Models.PendingBookingAppointmentResponse
import domain.Models.ServerResponse
import domain.Models.ServiceTherapistsResponse
import domain.Models.TimeAvailabilityResponse

interface BookingRepository {
    suspend fun getServiceTherapist(serviceTypeId: Long, vendorId: Long, day: Int, month: Int, year: Int): Single<ServiceTherapistsResponse>
    suspend fun createPendingBookingAppointment(userId: Long, vendorId: Long, serviceId: Long, serviceTypeId: Long, therapistId: Long,
                                                appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String, serviceStatus: String, bookingStatus: String): Single<PendingBookingAppointmentResponse>
    suspend fun createAppointment(userId: Long, vendorId: Long, paymentAmount: Int, paymentMethod: String, bookingStatus: String, day: Int, month: Int, year: Int) : Single<ServerResponse>
    suspend fun getPendingBookingAppointment(userId: Long, bookingStatus: String): Single<PendingBookingAppointmentResponse>
    suspend fun deletePendingBookingAppointment(pendingAppointmentId: Long) : Single<ServerResponse>
}

