package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class VendorPackage(@SerialName("id") val packageId: Long = -1, @SerialName("vendor_id") val vendorId: Long = -1,
                         @SerialName("title") val title: String = "", @SerialName("description") val description: String = "",
                         @SerialName("price") val price: Int = 0, @SerialName("isMobileServiceAvailable") val isMobileServiceAvailable: Boolean = false,
                         @SerialName("mobileServicePrice") val mobileServicePrice: Int = 0, @SerialName("therapists") val packageTherapists: ArrayList<PackageTherapists> = arrayListOf(),
                         @SerialName("products") val packageProducts: ArrayList<PackageProducts> = arrayListOf(), @SerialName("packageBookings") val packageBooking: Long = 0,
                         @SerialName("images") val packageImages: ArrayList<PackageImages> = arrayListOf(), @SerialName("services") val packageServices: ArrayList<PackageServices> = arrayListOf(),
                         @SerialName("reviews") val packageReviews: ArrayList<PackageReviews> = arrayListOf(), var isSelected: Boolean = false): Parcelable

data class VendorPackageItemUIModel(
    val selectedPackage: VendorPackage?,
    val packageList: List<VendorPackage>
)