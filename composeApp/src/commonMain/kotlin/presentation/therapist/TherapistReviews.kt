package presentation.therapist

import StackedSnackbarHost
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.TherapistViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.widgets.TherapistReviewScreen
import rememberStackedSnackbarHostState

@Composable
fun TherapistReviews(mainViewModel: MainViewModel, therapistPresenter: TherapistPresenter, therapistViewModel: TherapistViewModel,
                     loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel){
    val uiState = loadingScreenUiStateViewModel.uiStateInfo.collectAsState()
    val reviews = therapistViewModel.therapistReviews.collectAsState()

    val stackedSnackBarHostState = rememberStackedSnackbarHostState(
        maxStack = 5,
        animation = StackedSnackbarAnimation.Bounce
    )

    LaunchedEffect(Unit, block = {
        if (reviews.value.isEmpty()) {
            therapistPresenter.getTherapistReviews(3)
        }
    })

    Scaffold(
        snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
        topBar = {},
        content = {
            if (uiState.value.isLoading) {
                // Content Loading
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    IndeterminateCircularProgressBar()
                }
            } else if (uiState.value.isFailed) {

                // Error Occurred display reload

            } else if (uiState.value.isSuccess) {
                   Column(
                        Modifier
                            .fillMaxHeight(0.30f)
                            .fillMaxWidth()
                            .background(color = Color.White)
                    ) {

                        TherapistReviewScreen(reviews.value)

                    }
             }
        })


}