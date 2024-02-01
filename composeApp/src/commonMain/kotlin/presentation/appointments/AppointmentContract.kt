package presentation.appointments

interface AppointmentContract {
    interface View {
        fun showError()
        fun showLce(
            loadingVisible: Boolean = false,
            contentVisible: Boolean = false,
            emptyVisible: Boolean = false,
            errorVisible: Boolean = false
        )
        fun showAppointment()

    }

    interface Presenter {
        fun onAppointmentPostponed(isPostponed: Boolean)
        fun start()
    }
}
