package screens.main

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class MainViewModel: ViewModel() {
    private var _screenTitle = MutableLiveData("")

    private var _screenId = MutableLiveData(0)
    val screenTitle: LiveData<String>
        get() = _screenTitle

    val screenId: LiveData<Int>
        get() = _screenId


    fun setId(newId: Int) {
        _screenId.value = newId
    }

    fun setTitle(newTitle: String) {
        _screenTitle.value = newTitle
    }
}