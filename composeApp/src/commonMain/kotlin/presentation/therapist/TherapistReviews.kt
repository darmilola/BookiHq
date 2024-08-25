package presentation.therapist

import StackedSnackbarHost
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.Models.TherapistInfo
import domain.Models.TherapistReviews
import presentation.DomainViewHandler.TherapistHandler
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.TherapistViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.widgets.TherapistReviewScreen
import presentation.widgets.TitleWidget
import rememberStackedSnackbarHostState
import theme.styles.Colors

@Composable
fun TherapistReviews(therapistInfo: TherapistInfo, therapistPresenter: TherapistPresenter,
                     loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel, performedActionUIStateViewModel: PerformedActionUIStateViewModel){

    val therapistReviews = remember { mutableStateOf(listOf<TherapistReviews>()) }
    val isAvailableForBooking = remember { mutableStateOf(therapistInfo.isAvailable) }
    val isMobileServiceAvailable = remember { mutableStateOf(therapistInfo.isMobileServiceAvailable) }
    val handler = TherapistHandler(therapistPresenter,
        loadingScreenUiStateViewModel = loadingScreenUiStateViewModel,
        performedActionUIStateViewModel,
        onReviewsReady = {
          it -> therapistReviews.value = it
        },
        onMeetingTokenReady = {},
        null)
    handler.init()

    val uiState = loadingScreenUiStateViewModel.uiStateInfo.collectAsState()

    val stackedSnackBarHostState = rememberStackedSnackbarHostState(
        maxStack = 5,
        animation = StackedSnackbarAnimation.Bounce
    )

    LaunchedEffect(Unit, block = {
            therapistPresenter.getTherapistReviews(therapistInfo.id!!)
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
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .background(color = Color.White)
                    ) {
                       Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)) {
                           TherapistReviewScreen(therapistReviews.value)
                       }
                       Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(top = 50.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
                             TitleWidget(textColor = Colors.primaryColor, title = "Settings")

                             Row(modifier = Modifier.fillMaxWidth().height(50.dp), horizontalArrangement = Arrangement.Center) {
                                 Switch(checked = isAvailableForBooking.value!!, onCheckedChange = {
                                     isAvailableForBooking.value = it
                                     therapistPresenter.updateAvailability(therapistInfo.id!!, isMobileServiceAvailable = isMobileServiceAvailable.value!!, isAvailable = isAvailableForBooking.value!!)
                                 })
                                 TitleWidget(textColor = Colors.primaryColor, title = "Is Available for booking")
                             }
                           Row(modifier = Modifier.fillMaxWidth().height(50.dp), horizontalArrangement = Arrangement.Center) {
                               Switch(checked = isMobileServiceAvailable.value!!, onCheckedChange = {
                                   isMobileServiceAvailable.value = it
                                   therapistPresenter.updateAvailability(therapistInfo.id!!, isMobileServiceAvailable = isMobileServiceAvailable.value!!, isAvailable = isAvailableForBooking.value!!)
                               })
                               TitleWidget(textColor = Colors.primaryColor, title = "Mobile Service Available")
                           }
                         }

                    }
             }
        })


}