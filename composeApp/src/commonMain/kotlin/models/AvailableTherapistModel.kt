package models

data class AvailableTherapistUIModel(
    val selectedTherapist: AvailableTherapist,
    val visibleTherapist: List<AvailableTherapist>)

data class AvailableTherapist(
    val therapistId: Int,
    val isSelected: Boolean,
    val isAvailable: Boolean
)