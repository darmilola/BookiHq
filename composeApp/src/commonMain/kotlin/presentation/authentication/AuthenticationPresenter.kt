package presentation.authentication

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class AuthenticationPresenter(): AuthenticationContract.Presenter() {

    private var contractView: AuthenticationContract.View? = null
    override fun registerUIContract(view: AuthenticationContract.View?) {
        contractView = view
    }

    override fun startAuth0() {
        contractView?.onAuth0Started()
    }

    override fun endAuth0() {
        contractView?.onAuth0Ended()
    }
}