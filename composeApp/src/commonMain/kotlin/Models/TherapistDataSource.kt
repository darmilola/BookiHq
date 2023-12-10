package Models

class TherapistDataSource {

    fun getAvailableTherapist(lastSelectedTherapist: AvailableTherapistUIModel.AvailableTherapist): AvailableTherapistUIModel {
        val visibleTherapist = getTherapist()
        return toUiModel(visibleTherapist, lastSelectedTherapist, isTherapistAvailable = true)
    }

    private fun getTherapist(): List<AvailableTherapistUIModel.AvailableTherapist> {
        val therapistList = mutableListOf<AvailableTherapistUIModel.AvailableTherapist>()
        val t1 = AvailableTherapistUIModel.AvailableTherapist(0, true, true)
        val t2 = AvailableTherapistUIModel.AvailableTherapist(1, true, true)
        val t3 = AvailableTherapistUIModel.AvailableTherapist(2, true, true)
        val t4 = AvailableTherapistUIModel.AvailableTherapist(3, true, true)
        val t5 = AvailableTherapistUIModel.AvailableTherapist(4, true, true)
        val t6 = AvailableTherapistUIModel.AvailableTherapist(5, true, true)

        therapistList.add(t1)
        therapistList.add(t2)
        therapistList.add(t3)
        therapistList.add(t4)
        therapistList.add(t5)
        therapistList.add(t6)
        return therapistList
    }

    private fun toUiModel(
        therapistList: List<AvailableTherapistUIModel.AvailableTherapist>,
        lastSelectedTherapist: AvailableTherapistUIModel.AvailableTherapist,
        isTherapistAvailable: Boolean
    ): AvailableTherapistUIModel {
        return AvailableTherapistUIModel(
            selectedTherapist = toItemUiModel(0, true, true),
            visibleTherapist = therapistList.map {
                toItemUiModel(it.therapistId, it == lastSelectedTherapist, isTherapistAvailable)
            },
        )
    }

    private fun toItemUiModel(therapistId: Int, isSelectedTherapist: Boolean, isAvailable: Boolean = true) = AvailableTherapistUIModel.AvailableTherapist(
        therapistId = therapistId,
        isAvailable = isAvailable,
        isSelected = isSelectedTherapist)
}