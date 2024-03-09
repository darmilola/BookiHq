package presentation.widgets

import StackedSnackbarDuration
import StackedSnakbarHostState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import rememberStackedSnackbarHostState


fun ShowSnackBar(title: String, description: String, actionLabel: String,
                 duration: StackedSnackbarDuration = StackedSnackbarDuration.Short,
                 snackBarType: SnackBarType = SnackBarType.ERROR,
                 stackedSnackBarHostState: StackedSnakbarHostState,
                 onActionClick: () -> Unit) {

    when (snackBarType) {
        SnackBarType.INFO -> {
            stackedSnackBarHostState.showInfoSnackbar(
                title = title,
                description = description,
                actionTitle = actionLabel,
                action = {
                    onActionClick()
                },
                duration = duration
            )
        }
        SnackBarType.SUCCESS -> {
            stackedSnackBarHostState.showSuccessSnackbar(
                title = title,
                description = description,
                actionTitle = actionLabel,
                action = {
                    onActionClick()
                },
                duration = duration
            )
        }
        SnackBarType.ERROR -> {
            stackedSnackBarHostState.showErrorSnackbar(
                title = title,
                description = description,
                actionTitle = actionLabel,
                action = {
                    onActionClick()
                },
                duration = duration
            )
        }
        SnackBarType.WARNING -> {
            stackedSnackBarHostState.showWarningSnackbar(
                title = title,
                description = description,
                actionTitle = actionLabel,
                action = {
                    onActionClick()
                },
                duration = duration
            )
        }
    }
}

enum class SnackBarType {
    WARNING,
    INFO,
    ERROR,
    SUCCESS
}

