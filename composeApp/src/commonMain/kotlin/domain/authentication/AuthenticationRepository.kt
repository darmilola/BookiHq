package domain.authentication

import com.badoo.reaktive.single.Single
import dev.jordond.compass.Location
import dev.jordond.compass.Place
import domain.Models.AuthenticationResponse
import domain.Models.CompleteProfileResponse
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.Vendor

interface AuthenticationRepository {
    suspend fun completeProfile(
        firstname: String,
        lastname: String,
        userEmail: String,
        authPhone: String,
        signupType: String,
        country: String,
        city: String,
        gender: String,
        profileImageUrl: String
    ): Single<CompleteProfileResponse>
    suspend fun reverseGeocode(lat: Double, lng: Double): Single<Place?>
    suspend fun validateUserProfile(userEmail: String): Single<AuthenticationResponse>
    suspend fun validateEmail(userEmail: String): Single<AuthenticationResponse>
    suspend fun validatePhone(authPhone: String): Single<AuthenticationResponse>

}



