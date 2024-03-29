package infrastructure.authentication

import com.badoo.reaktive.single.Single
import dev.jordond.compass.Location
import dev.jordond.compass.Place
import domain.Models.AuthenticationResponse
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.Vendor

interface AuthenticationRepository {
    suspend fun completeProfile(
        firstname: String,
        lastname: String,
        userEmail: String,
        address: String,
        contactPhone: String,
        countryId: Int,
        cityId: Int,
        gender: String,
        profileImageUrl: String
    ): Single<ServerResponse>
    suspend fun reverseGeocode(lat: Double, lng: Double): Single<Place?>
    suspend fun validateUserProfile(userEmail: String): Single<AuthenticationResponse>

}



