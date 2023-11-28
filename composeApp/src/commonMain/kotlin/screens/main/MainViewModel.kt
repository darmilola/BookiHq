package screens.main

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class MainViewModel: ViewModel() {
    private var _screenTitle = MutableLiveData("")
    val screenTitle: LiveData<String>
        get() = _screenTitle

    fun setTitle(newTitle: String) {
        _screenTitle.value = newTitle
    }
}