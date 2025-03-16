package domain.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@Entity
@Parcelize
data class State(@ColumnInfo  @SerialName("id") var id: Long = -1L, @ColumnInfo @SerialName("name") var stateName: String = ""): Parcelable

