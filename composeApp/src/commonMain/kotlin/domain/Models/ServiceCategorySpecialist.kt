package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ServiceCategorySpecialist(@SerialName("id") val id: Int? = null, @SerialName("specialist_id") val specialistId: Int? = null,
                                     @SerialName("service_type_id") val serviceTypeId: Int? = null, @SerialName("specialist_info") val specialistInfo: SpecialistInfo? = null,
                                     val isSelected: Boolean = false)
data class ServiceCategoryTherapistUIModel(
    val selectedTherapist: ServiceCategorySpecialist?,
    val visibleTherapist: List<ServiceCategorySpecialist>)