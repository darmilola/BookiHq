package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse(@SerialName("status") val status: String = "", @SerialName("message") val message: String = "")

@Serializable
data class AuthenticationResponse(@SerialName("status") val status: String = "",  @SerialName("data") val userInfo: User = User())