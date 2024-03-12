package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.HomePageResponse
import kotlinx.coroutines.flow.StateFlow

class HomePageViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

        private var _homePageInfo =  savedStateHandle.getStateFlow("homePageInfo",HomePageResponse())
        private var _homePageUiState =  savedStateHandle.getStateFlow("homePageUiState", AsyncUIStates())

        val homePageInfo: StateFlow<HomePageResponse>
            get() = _homePageInfo

       val homePageUIState: StateFlow<AsyncUIStates>
         get() = _homePageUiState

        fun setHomePageInfo(homePageResponse: HomePageResponse) {
            savedStateHandle["homePageInfo"] = homePageResponse
        }

      fun setHomePageUIState(asyncUIStates: AsyncUIStates) {
         savedStateHandle["homePageUiState"] = asyncUIStates
      }
}