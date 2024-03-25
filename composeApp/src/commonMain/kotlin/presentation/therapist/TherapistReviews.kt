package presentation.therapist

import StackedSnackbarHost
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import domain.Models.Appointment
import domain.Models.ServiceTime
import domain.Models.SpecialistReviews
import domain.Models.TimeOffs
import presentation.appointments.AppointmentPresenter
import presentation.appointments.AppointmentsHandler
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PostponementViewModel
import presentation.viewmodels.ResourceListEnvelopeViewModel
import presentation.viewmodels.TherapistViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentation.widgets.SpecialistReviewScreen
import presentation.widgets.TherapistAppointmentWidget
import rememberStackedSnackbarHostState
import theme.Colors
import utils.getAppointmentViewHeight

@Composable
fun TherapistReviews(mainViewModel: MainViewModel, therapistPresenter: TherapistPresenter, therapistViewModel: TherapistViewModel,
                     uiStateViewModel: UIStateViewModel){
    val uiState = uiStateViewModel.uiData.collectAsState()
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
            if (uiState.value.loadingVisible) {
                // Content Loading
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    IndeterminateCircularProgressBar()
                }
            } else if (uiState.value.errorOccurred) {

                // Error Occurred display reload

            } else if (uiState.value.contentVisible) {
                   Column(
                        Modifier
                            .fillMaxHeight(0.30f)
                            .fillMaxWidth()
                            .background(color = Color.White)
                    ) {

                        SpecialistReviewScreen(reviews.value)

                    }
             }
        })


}