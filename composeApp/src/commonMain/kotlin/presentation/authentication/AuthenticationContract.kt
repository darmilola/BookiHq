package presentation.authentication

class AuthenticationContract {
    interface View {
        fun onAuth0Started()
        fun onAuth0Ended()
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun startAuth0()
        abstract fun endAuth0()
    }
}