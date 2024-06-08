package presentation.viewmodels


import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize

@Parcelize
class ScreenUIStates(
   val loadingVisible: Boolean = false,
   val contentVisible: Boolean = false,
   val errorOccurred: Boolean = false,
   val errorMessage: String = ""): Parcelable

@Parcelize
class ActionUIStates(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isFailed: Boolean = false,
    val loadingMessage: String = "",
    val successMessage: String = "",
    val errorMessage: String = ""): Parcelable
