package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class Services(@SerialName("id") val serviceId: Int = -1, @SerialName("vendor_id") val vendorId: Int = -1,
                    @SerialName("isAvailable") val isAvailable: Boolean = false, @SerialName("service_info") val serviceInfo: PlatformServices? = PlatformServices(),
                    @SerialName("service_images") val serviceImages: ArrayList<ServiceImages> = arrayListOf(),
                    @SerialName("service_types") val serviceTypes:  ArrayList<ServiceTypeItem> = arrayListOf(), var isSelected: Boolean = false): Parcelable