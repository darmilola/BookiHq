package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class ServiceTypeItem(@SerialName("id") val serviceTypeId: Long = -1, @SerialName("service_id") val serviceId: Long = -1, @SerialName("title") val title: String = "",
                           @SerialName("price") val price: Int = 0, @SerialName("mobileServicePrice") val mobileServicePrice: Int = 0,@SerialName("length") val length: Int = 0, @SerialName("description") val description: String = "",
                           @SerialName("isMobileServicesAvailable") val mobileServiceAvailable: Boolean = false, @SerialName("isAvailable") val isAvailable: Boolean  = false,
                           @SerialName("service_details") val serviceDetails: Services = Services(), val isSelected: Boolean = false): Parcelable