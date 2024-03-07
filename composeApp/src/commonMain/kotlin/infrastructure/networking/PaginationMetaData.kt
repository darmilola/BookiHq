package infrastructure.networking

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationMetadata(@SerialName("next_page_url") val nextPageUrl: String? = null, @SerialName("prev_page_url") val prevPageUrl: String? = null,
                              @SerialName("per_page") val perPage: String? = null, @SerialName("to") var to: String? = null,
                              @SerialName("total") var total: String? = null, @SerialName("path") var path: String? = null)

