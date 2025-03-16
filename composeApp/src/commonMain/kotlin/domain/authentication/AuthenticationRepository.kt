package domain.authentication

import com.badoo.reaktive.single.Single
import dev.jordond.compass.Location
import dev.jordond.compass.Place
import domain.Models.AuthenticationResponse
import domain.Models.CompleteProfileResponse
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.Vendor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface AuthenticationRepository {
    suspend fun updateProfile(
        userId: Long,
        firstname: String,
        lastname: String,
        address: String,
        contactPhone: String,
        country: String,
        state: Long,
        gender: String,
        profileImageUrl: String
    ): Single<ServerResponse>

    suspend fun updateFcmToken(
        userId: Long,
        fcmToken: String
    ): Single<ServerResponse>

    suspend fun completeProfile(
        firstname: String,
        lastname: String,
        userEmail: String,
        authPhone: String,
        country: String,
        state: Long,
        signupType: String,
        gender: String,
        profileImageUrl: String
    ): Single<CompleteProfileResponse>
    suspend fun validateUserProfile(userEmail: String): Single<AuthenticationResponse>
    suspend fun validateEmail(userEmail: String): Single<AuthenticationResponse>
    suspend fun validatePhone(authPhone: String): Single<AuthenticationResponse>

}



