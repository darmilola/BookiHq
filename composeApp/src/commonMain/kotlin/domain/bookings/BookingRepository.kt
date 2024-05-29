package domain.bookings

import com.badoo.reaktive.single.Single
import domain.Models.Appointment
import domain.Models.AppointmentItem
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.ServiceSpecialistsResponse
import domain.Models.SpecialistAvailabilityResponse

interface BookingRepository {
    suspend fun getServiceTherapist(
        serviceTypeId: Int,
        day: Int,
        month: Int,
        year: Int): Single<ServiceSpecialistsResponse>
    suspend fun createAppointment(appointmentRequests: ArrayList<CreateAppointmentRequest>): Single<ServerResponse>
}

