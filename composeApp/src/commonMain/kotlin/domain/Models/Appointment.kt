package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Appointment(
    @SerialName("id") val appointmentId: Int? = -1, @SerialName("user_id") val userId: Int? = -1, @SerialName("vendor_id") val vendorId: Int = -1,
    @SerialName("serviceLocation") val serviceLocation: String = ServiceLocation.Spa.toPath(), @SerialName("serviceStatus") val serviceStatus: String = ServiceStatus.Pending.toPath(),
    @SerialName("consultationMedium") val consultationMedium: String = ConsultationMedium.Spa.toPath(), @SerialName("service_id") val serviceId: Int = -1,
    @SerialName("specialist_id") val specialistId: Int = -1, @SerialName("service_type_id") val serviceTypeId: Int? = -1, @SerialName("appointmentTime") val appointmentTime: Int? = -1,
    @SerialName("day") val appointmentDay: Int? = -1, @SerialName("month") val appointmentMonth: Int? = -1,
    @SerialName("year") val appointmentYear: Int? = -1, @SerialName("isRecommendedAppointment") val isRecommendedAppointment: Boolean = false,
    @SerialName("recommendation_id") val recommendationId: Int? = -1, @SerialName("specialist_info") val specialistInfo: SpecialistInfo? = null,
    @SerialName("service_type") val serviceTypeItem: ServiceTypeItem? = null, @SerialName("service") val services: Services? = null, @SerialName("time") val serviceTime: ServiceTime? = null,
    @SerialName("vendor") val vendor: Vendor? = null, @SerialName("customer_info") val customerInfo: User? = null, val isSelected: Boolean = false)

data class AppointmentItemUIModel(
    val selectedAppointment: Appointment?,
    val appointmentList: List<Appointment>
)