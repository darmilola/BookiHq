package models

class WorkingHoursDataSource {


    fun getWorkingHours(lastSelectedSlot: Pair<Pair<String, String>, Boolean>): AvailableSlotsUIModel {
        val visibleHours = getWorkHours()
        return toUiModel(visibleHours, lastSelectedSlot, isSlotAvailable = true)
    }

    private fun getWorkHours(): List<Pair<Pair<String, String>, Boolean>> {
        val slotList = mutableListOf<Pair<Pair<String, String>, Boolean>>()
        val timePair1 = Pair(Pair("8:00","10:00"), false)
        val timePair2 = Pair(Pair("10:00","11:00"), false)
        val timePair3 = Pair(Pair("10:00","12:00"), false)
        val timePair4 = Pair(Pair("12:00","2:00"), false)
        val timePair5 = Pair(Pair("7:00","8:00"), true)
        val timePair6 = Pair(Pair("8:00","10:00"), true)
        slotList.add(timePair1)
        slotList.add(timePair2)
        slotList.add(timePair3)
        slotList.add(timePair4)
        slotList.add(timePair5)
        slotList.add(timePair6)


        return slotList
    }

    private fun toUiModel(
        slotList: List<Pair<Pair<String, String>, Boolean>>,
        lastSelectedSlot: Pair<Pair<String, String>, Boolean>,
        isSlotAvailable: Boolean
    ): AvailableSlotsUIModel {
        return AvailableSlotsUIModel(
            selectedSlot = toItemUiModel(lastSelectedSlot, true, true),
            visibleSlots = slotList.map {
                toItemUiModel(it, it == lastSelectedSlot, isSlotAvailable)
            },
        )
    }

    private fun toItemUiModel(timeSlot: Pair<Pair<String, String>, Boolean> , isSelectedSlot: Boolean, isAvailable: Boolean = true) = AvailableSlotsUIModel.AvailableSlot(
        timeSlot = timeSlot,
        isAvailable = isAvailable,
        isSelected = isSelectedSlot)
}