package models

data class AvailableSlotsUIModel(
    val selectedSlot: AvailableSlotsUIModel.AvailableSlot,
    val visibleSlots: List<AvailableSlotsUIModel.AvailableSlot>
) {
    data class AvailableSlot(
        val timeSlot: Pair<Pair<String, String>, Boolean>,
        val isSelected: Boolean,
        val isAvailable: Boolean
    )

}