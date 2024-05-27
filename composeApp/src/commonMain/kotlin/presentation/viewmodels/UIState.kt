package presentation.viewmodels


import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize

@Parcelize
class UIStates(
   val loadingVisible: Boolean = false,
   val contentVisible: Boolean = false,
   val errorOccurred: Boolean = false,
   val errorMessage: String = ""): Parcelable

@Parcelize
class AsyncUIStates(
    val isLoading: Boolean = false,
    val isDone: Boolean = false,
    val isSuccess: Boolean = false,
    val isFailed: Boolean = false): Parcelable
