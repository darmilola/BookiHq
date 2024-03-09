package presentation.viewmodels


 class UIStates(
   val loadingVisible: Boolean = false,
   val contentVisible: Boolean = false,
   val errorOccurred: Boolean = false,
   val errorMessage: String = "")

class AsyncUIStates(
    val isLoading: Boolean = false,
    val isDone: Boolean = false,
    val isSuccess: Boolean = false)
