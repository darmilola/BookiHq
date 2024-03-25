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
data class ServiceSpecialistsResponse(@SerialName("status") val status: String = "", @SerialName("specialist") val serviceSpecialists: List<ServiceTypeSpecialist>,
                                      @SerialName("platformAvailability") val platformAvailability: List<ServiceTime>)


@Serializable
data class SpecialistReviewsResponse(@SerialName("status") val status: String = "", @SerialName("reviews") val reviews: List<SpecialistReviews>)


@Serializable
data class ProductCategoryResponse(@SerialName("status") val status: String = "",
                                   @SerialName("categories") val productCategories: List<ProductCategory>,
                                   @SerialName("favoriteProducts") val favoriteProducts: List<FavoriteProduct>)


@Serializable
data class SpecialistAvailabilityResponse(@SerialName("status") val status: String = "",
                                   @SerialName("availability") val availableTimes: List<ServiceTime>,
                                   @SerialName("bookings") val bookedAppointment: List<Appointment>)