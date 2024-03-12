package domain.Models

import domain.appointments.ServiceLocation
import domain.appointments.ConsultationMedium
import domain.appointments.ServiceStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Appointment(
    @SerialName("id") val appointmentId: Int? = -1, @SerialName("user_id") val userId: Int? = -1, @SerialName("vendor_id") val vendorId: Int = -1,
    @SerialName("serviceLocation") val serviceLocation: String = ServiceLocation.Spa.toPath(),
    @SerialName("isConsultation") val isConsultation: Boolean = false, @SerialName("serviceStatus") val serviceStatus: String = ServiceStatus.Pending.toPath(),
    @SerialName("consultationMedium") val consultationMedium: String = ConsultationMedium.Spa.toPath(), @SerialName("service_id") val serviceId: Int = -1,
    @SerialName("service_type_id") val serviceTypeId: Int? = -1, @SerialName("appointmentTime") val appointmentTime: Int? = -1,
    @SerialName("appointmentDate") val appointmentDate: String? = null, @SerialName("isRecommendedAppointment") val isRecommendedAppointment: Boolean = false,
    @SerialName("recommendation_id") val recommendationId: Int? = -1, @SerialName("specialist") val specialistInfo: SpecialistInfo? = null,
    @SerialName("service_type") val serviceCategoryItem: ServiceCategoryItem? = null, @SerialName("service") val services: Services? = null,
    @SerialName("time") val serviceTime: ServiceTime? = null, @SerialName("consultationReason") val consultationReason: String? = null, val isSelected: Boolean = false)

data class AppointmentItemUIModel(
    val selectedAppointment: Appointment?,
    val vendorAppointments: List<Appointment>)