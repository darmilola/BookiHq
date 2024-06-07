package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ListDataResponse<T : Any>(@SerialName("response") var listItem: ResourceListEnvelope<T>, @SerialName("status")  var status: String)

@Serializable
class AppointmentListDataResponse(@SerialName("response") var listItem: AppointmentResourceListEnvelope, @SerialName("status")  var status: String)
@Serializable
class ProductListDataResponse(@SerialName("response") var listItem: ProductResourceListEnvelope, @SerialName("status")  var status: String)

@Serializable
class OrderListDataResponse(@SerialName("response") var listItem: OrderResourceListEnvelope, @SerialName("status")  var status: String)