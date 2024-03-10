package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ListDataResponse<T : Any>(@SerialName("response") var listItem: ResourceListEnvelope<T>, @SerialName("status")  var status: String)