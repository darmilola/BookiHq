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
        selectedDate: String): Single<ServiceSpecialistsResponse>
    suspend fun createAppointment(appointmentRequests: ArrayList<CreateAppointmentRequest>): Single<ServerResponse>
}

