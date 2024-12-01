package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class PackageServices(@SerialName("id") val id: Long = -1, @SerialName("package_id") val packageId: Long = -1,
                           @SerialName("service_type_id") val serviceId: Long = -1, @SerialName("service_info") val serviceInfo: ServiceTypeItem = ServiceTypeItem(),
                           var isSelected: Boolean = false): Parcelable