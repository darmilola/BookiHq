package domain.appointments

import com.badoo.reaktive.single.Single
import domain.Models.Appointment
import domain.Models.ListDataResponse
import domain.Models.Product
import domain.Models.ServerResponse
import domain.Models.SpecialistAvailabilityResponse

interface AppointmentRepository {
    suspend fun getAppointments(userId: Int, nextPage: Int = 1): Single<ListDataResponse<Appointment>>
    suspend fun getSpecialistAppointments(specialistId: Int, nextPage: Int = 1): Single<ListDataResponse<Appointment>>
    suspend fun postponeAppointment(appointment: Appointment, appointmentTime: Int, appointmentDate: String): Single<ServerResponse>
    suspend fun deleteAppointment(appointmentId: Int): Single<ServerResponse>
    suspend fun getTherapistAvailability(specialistId: Int, selectedDate: String): Single<SpecialistAvailabilityResponse>
}