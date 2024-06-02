package presentation.therapist

import domain.Models.Appointment
import domain.Models.AvailableTime
import domain.Models.SpecialistReviews
import domain.Models.TimeOffs
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.UIStates

interface TherapistContract {
    interface View {
        fun showLce(uiState: UIStates, message: String = "")
        fun showAsyncLce(uiState: AsyncUIStates, message: String = "")
        fun showReviews(reviews: List<SpecialistReviews>)
        fun showTherapistAvailability(availableTimes: List<AvailableTime>, bookedAppointment: List<Appointment>, timeOffs: List<TimeOffs>)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getTherapistReviews(specialistId: Int)
        abstract fun getTherapistAvailability(specialistId: Int, day: Int, month: Int, year: Int)
        abstract fun addTimeOff(specialistId: Int, timeId: Int, date: String)
        abstract fun removeTimeOff(specialistId: Int, timeId: Int, date: String)
    }
}
