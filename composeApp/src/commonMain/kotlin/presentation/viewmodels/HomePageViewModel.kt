package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.HomepageInfo
import kotlinx.coroutines.flow.StateFlow

class HomePageViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

        private var _homePageInfo =  savedStateHandle.getStateFlow("homePageInfo",HomepageInfo())
        private var _homePageViewHeight =  savedStateHandle.getStateFlow("homePageViewHeight",0)
        private var _homePageUiState =  savedStateHandle.getStateFlow("homePageUiState", AsyncUIStates())

        val homePageInfo: StateFlow<HomepageInfo>
            get() = _homePageInfo

      val homePageViewHeight: StateFlow<Int>
        get() = _homePageViewHeight

       val homePageUIState: StateFlow<AsyncUIStates>
         get() = _homePageUiState

        fun setHomePageInfo(homepageInfo: HomepageInfo) {
            savedStateHandle["homePageInfo"] = homepageInfo
        }

      fun setHomePageViewHeight(viewHeight: Int) {
          println("General $viewHeight")
        savedStateHandle["homePageViewHeight"] = viewHeight
     }

        fun setHomePageUIState(asyncUIStates: AsyncUIStates) {
         savedStateHandle["homePageUiState"] = asyncUIStates
        }
}