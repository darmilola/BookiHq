package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ListDataResponse<T : Any>(@SerialName("response") var listItem: ResourceListEnvelope<T>, @SerialName("status")  var status: String)

@Serializable
class AppointmentListDataResponse(@SerialName("response") var listItem: AppointmentResourceListEnvelope = AppointmentResourceListEnvelope(), @SerialName("status")  var status: String)

@Serializable
class VendorPackageListDataResponse(@SerialName("response") var listItem: VendorPackageResourceListEnvelope = VendorPackageResourceListEnvelope(), @SerialName("status")  var status: String)

@Serializable
class TherapistAppointmentListDataResponse(@SerialName("response") var listItem: TherapistAppointmentResourceListEnvelope = TherapistAppointmentResourceListEnvelope(), @SerialName("status")  var status: String)

@Serializable
class ProductListDataResponse(@SerialName("response") var listItem: ProductResourceListEnvelope = ProductResourceListEnvelope(), @SerialName("status")  var status: String)

@Serializable
class OrderListDataResponse(@SerialName("response") var listItem: OrderResourceListEnvelope = OrderResourceListEnvelope(), @SerialName("status")  var status: String)

@Serializable
class VendorListDataResponse(@SerialName("response") var listItem: VendorResourceListEnvelope = VendorResourceListEnvelope(), @SerialName("status")  var status: String)