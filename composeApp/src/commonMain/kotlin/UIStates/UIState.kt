package UIStates


import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.ActionType

@Parcelize
class AppUIStates(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isFailed: Boolean = false,
    val isEmpty: Boolean = false,
    val isDefault: Boolean = false,
    val loadingMessage: String = "",
    val successMessage: String = "",
    val emptyMessage: String = "",
    val errorMessage: String = "",
    val defaultMessage: String = ""): Parcelable
