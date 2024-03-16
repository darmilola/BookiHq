package domain.Models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class UnsavedAppointment(val bookingId: Int = -1, var isHomeService: Boolean = false, var serviceId: Int = -1, var serviceTypeId: Int? = -1, var appointmentTime: ServiceTime? = null,
                              var appointmentDate: LocalDate? = null, val isRecommendedAppointment: Boolean = false, var serviceTypeSpecialist: ServiceTypeSpecialist? = null,
                              var serviceTypeItem: ServiceTypeItem? = null, var services: Services? = null)