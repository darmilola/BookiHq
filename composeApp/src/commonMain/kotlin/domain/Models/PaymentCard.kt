package domain.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.PaymentMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
@Parcelize
data class PaymentCard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "cardNumber") val cardNumber: String = "",
    @ColumnInfo(name = "expiryMonth") val expiryMonth: String = "",
    @ColumnInfo(name = "expiryYear") val expiryYear: String = "",
    @ColumnInfo(name = "cvv") val cvv: String = "",
    val isSelected: Boolean = false): Parcelable


data class PaymentCardUIModel(
    val selectedCard: PaymentCard?,
    val visibleCards: List<PaymentCard>)