package domain.appointments

import com.badoo.reaktive.single.Single
import domain.Models.Appointment
import domain.Models.ListDataResponse
import domain.Models.Product

interface AppointmentRepository {
    suspend fun getAppointments(userId: Int, nextPage: Int = 1): Single<ListDataResponse<Appointment>>
}