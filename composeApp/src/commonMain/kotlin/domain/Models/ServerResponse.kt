package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse(@SerialName("status") val status: String = "", @SerialName("message") val message: String = "")

@Serializable
data class AuthenticationResponse(@SerialName("status") val status: String = "",  @SerialName("data") val userInfo: User = User())

@Serializable
data class HomePageResponse(@SerialName("status") val status: String = "", @SerialName("homePage") val homepageModel: HomepageModel = HomepageModel())

@Serializable
data class ServiceSpecialistsResponse(@SerialName("status") val status: String = "", @SerialName("specialist") val serviceSpecialists: List<ServiceTypeSpecialist>)