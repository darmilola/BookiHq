package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class CountryCities(@SerialName("id") val cityId: Int = -1, @SerialName("name") val countryName: String = "",
                         @SerialName("cities") val cities: ArrayList<City> = arrayListOf()): Parcelable