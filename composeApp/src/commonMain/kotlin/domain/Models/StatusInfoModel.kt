package domain.Models

data class StatusInfoModel (
    val businessName: String? = "",
    val hour: String = "",
    val minute: String = "",
    val isPM: Boolean = false,
    val isToday: Boolean = false,
    val businessLogo: Boolean = false)