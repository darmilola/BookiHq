package presentation.therapist

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.Models.SpecialistReviews
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.MainViewModel
import presentation.widgets.BookingCalendar
import presentation.widgets.SpecialistReviewScreen
import rememberStackedSnackbarHostState

@Composable
fun TherapistAvailability(mainViewModel: MainViewModel, therapistPresenter: TherapistPresenter){

    val isLoading = remember { mutableStateOf(false) }
    val isContentVisible = remember { mutableStateOf(false) }
    val isErrorOccurred = remember { mutableStateOf(false) }


    val stackedSnackBarHostState = rememberStackedSnackbarHostState(
        maxStack = 5,
        animation = StackedSnackbarAnimation.Bounce
    )


                Column(
                    Modifier
                        .fillMaxHeight(0.30f)
                        .fillMaxWidth()
                        .background(color = Color.White)
                ) {

                    BookingCalendar(bookingViewModel = null) {

                    }

                }






}