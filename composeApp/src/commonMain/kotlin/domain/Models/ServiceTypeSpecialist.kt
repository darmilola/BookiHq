package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable @Parcelize
data class ServiceTypeSpecialist(@SerialName("id") val id: Int? = null, @SerialName("specialist_id") val specialistId: Int? = null,
                                 @SerialName("service_type_id") val serviceTypeId: Int? = null, @SerialName("specialist_info") val specialistInfo: SpecialistInfo? = null,
                                 val isSelected: Boolean = false): Parcelable
data class ServiceTypeTherapistUIModel(
    val selectedTherapist: ServiceTypeSpecialist?,
    val visibleTherapist: List<ServiceTypeSpecialist>)