package domain.appointments

import com.badoo.reaktive.single.Single
import domain.Models.AppointmentItem


interface AppointmentRepository {
    suspend fun get(id: Long): Single<AppointmentItem>
    suspend fun create()
    suspend fun update(id: Long)
}