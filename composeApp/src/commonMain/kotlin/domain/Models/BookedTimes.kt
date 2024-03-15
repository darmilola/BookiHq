package domain.Models

import domain.appointments.ConsultationMedium
import domain.appointments.ServiceLocation
import domain.appointments.ServiceStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookedTimes(@SerialName("time") val serviceTime: ServiceTime? = null)