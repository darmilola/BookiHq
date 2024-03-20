package domain.Models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class UnsavedAppointment(val bookingId: Int = -1, var isHomeService: Boolean = false, var serviceId: Int = -1,
                              var serviceTypeId: Int? = -1, var appointmentTime: ServiceTime? = null,
                              var appointmentDate: LocalDate? = null, var isRecommendedAppointment: Boolean = false,
                              var recommendationId: Int? = null, var serviceTypeSpecialist: ServiceTypeSpecialist? = null,
                              var serviceTypeItem: ServiceTypeItem? = null, var services: Services? = null, val serviceStatus: String = ServiceStatus.Pending.toPath())