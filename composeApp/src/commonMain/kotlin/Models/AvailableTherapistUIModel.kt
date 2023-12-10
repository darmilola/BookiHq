package Models

data class AvailableTherapistUIModel(
    val selectedTherapist: AvailableTherapistUIModel.AvailableTherapist,
    val visibleTherapist: List<AvailableTherapistUIModel.AvailableTherapist>
) {
    data class AvailableTherapist(
        val therapistId: Int,
        val isSelected: Boolean,
        val isAvailable: Boolean
    )
}