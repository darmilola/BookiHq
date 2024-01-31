package presentation.dataModeller

import domain.Models.AvailableTherapist
import domain.Models.AvailableTherapistUIModel

class TherapistDataSource {

    fun getAvailableTherapist(lastSelectedTherapist: AvailableTherapist): AvailableTherapistUIModel {
        val visibleTherapist = getTherapist()
        return toUiModel(visibleTherapist, lastSelectedTherapist, isTherapistAvailable = true)
    }

    private fun getTherapist(): List<AvailableTherapist> {
        val therapistList = mutableListOf<AvailableTherapist>()
        val t1 = AvailableTherapist(0, true, true)
        val t2 = AvailableTherapist(1, true, true)
        val t3 = AvailableTherapist(2, true, true)
        val t4 = AvailableTherapist(3, true, true)
        val t5 = AvailableTherapist(4, true, true)
        val t6 = AvailableTherapist(5, true, true)

        therapistList.add(t1)
        therapistList.add(t2)
        therapistList.add(t3)
        therapistList.add(t4)
        therapistList.add(t5)
        therapistList.add(t6)
        return therapistList
    }

    private fun toUiModel(
        therapistList: List<AvailableTherapist>,
        lastSelectedTherapist: AvailableTherapist,
        isTherapistAvailable: Boolean
    ): AvailableTherapistUIModel {
        return AvailableTherapistUIModel(
            selectedTherapist = toItemUiModel(0, true, true),
            visibleTherapist = therapistList.map {
                toItemUiModel(it.therapistId, it == lastSelectedTherapist, isTherapistAvailable)
            },
        )
    }

    private fun toItemUiModel(therapistId: Int, isSelectedTherapist: Boolean, isAvailable: Boolean = true) =
        AvailableTherapist(
            therapistId = therapistId,
            isAvailable = isAvailable,
            isSelected = isSelectedTherapist
        )
}