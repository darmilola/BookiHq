package models

data class AvailableSlotsUIModel(
    val selectedSlot: AvailableSlot,
    val visibleSlots: List<AvailableSlot>
)

data class AvailableSlot(
    val timeSlot: Pair<Pair<String, String>, Boolean>,
    val isSelected: Boolean,
    val isAvailable: Boolean
)