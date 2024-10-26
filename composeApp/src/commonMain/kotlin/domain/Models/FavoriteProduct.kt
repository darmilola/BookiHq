package domain.Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
@Parcelize
data class FavoriteProduct(@PrimaryKey @SerialName("id") val favoriteId: Int = -1, @SerialName("product_id") val productId: Int = -1,
                           @SerialName("vendor_id") val vendorId: Int = -1, @SerialName("user_id") val userId: Int = -1): Parcelable