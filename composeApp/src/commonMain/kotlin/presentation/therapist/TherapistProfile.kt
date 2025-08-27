package presentation.therapist

import GGSansBold
import GGSansRegular
import StackedSnackbarHost
import UIStates.AppUIStates
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.assignment.moniepointtest.ui.theme.AppTheme
import domain.Models.AppointmentReview
import domain.Models.TherapistInfo
import presentation.widgets.ErrorOccurredWidget
import presentation.DomainViewHandler.TherapistProfileHandler
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.widgets.TherapistReviewScreen
import presentation.widgets.EmptyContentWidget
import presentation.widgets.TitleWidget
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.styles.Colors

@Composable
fun TherapistProfile(therapistInfo: TherapistInfo, therapistPresenter: TherapistPresenter, performedActionUIStateViewModel: PerformedActionUIStateViewModel, loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel, onUpdateSuccess: () -> Unit){

    val isAvailableForBooking = remember { mutableStateOf(therapistInfo.isAvailable) }
    val isMobileServiceAvailable = remember { mutableStateOf(therapistInfo.isMobileServiceAvailable) }

    val updateUiState = performedActionUIStateViewModel.uiStateInfo.collectAsState()
    val appointmentReviews = remember { mutableStateOf(listOf<AppointmentReview>()) }

    val handler = TherapistProfileHandler(therapistPresenter, performedActionUIStateViewModel,
        onReviewsReady = {
              appointmentReviews.value = it
        },
        loadingScreenUiStateViewModel = loadingScreenUiStateViewModel)
    handler.init()

    LaunchedEffect(true) {
        therapistPresenter.getTherapistReviews(therapistInfo.id!!)
    }

    val stackedSnackBarHostState = rememberStackedSnackbarHostState(
        maxStack = 5,
        animation = StackedSnackbarAnimation.Bounce
    )

    if (updateUiState.value.isLoading){
        Box(modifier = Modifier.fillMaxWidth()) {
            LoadingDialog(updateUiState.value.loadingMessage)
        }
    }

    else if (updateUiState.value.isSuccess) {
         performedActionUIStateViewModel.switchActionUIState(AppUIStates(isDefault = true))
         onUpdateSuccess()
    }
    else if (updateUiState.value.isFailed){
        Box(modifier = Modifier.fillMaxWidth()) {
            ErrorDialog(updateUiState.value.loadingMessage, actionTitle = "Close", onConfirmation = {})
        }
    }

    val uiState = loadingScreenUiStateViewModel.uiStateInfo.collectAsState()


    AppTheme {
        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {},
            backgroundColor = theme.Colors.dashboardBackground,
            content = {
                Column(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = theme.Colors.dashboardBackground)
                ) {

                    if (uiState.value.isLoading) {
                        Box(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.50f),
                            contentAlignment = Alignment.Center
                        ) {
                            IndeterminateCircularProgressBar()
                        }
                    } else if (uiState.value.isFailed) {
                        Column(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.50f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth().height(400.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                ErrorOccurredWidget(uiState.value.errorMessage, onRetryClicked = {
                                    therapistPresenter.getTherapistReviews(therapistInfo.id!!)
                                })
                            }
                        }
                    } else if (uiState.value.isEmpty) {
                        Column(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.50f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                EmptyContentWidget(emptyText = "No Reviews Available")
                            }
                        }
                    } else if (uiState.value.isSuccess) {
                        Column(
                            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            TextComponent(
                                text = "Reviews",
                                fontSize = 20,
                                textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                                textColor = Colors.darkPrimary,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 30,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                textModifier = Modifier.fillMaxWidth().padding(start = 20.dp)
                            )

                            TherapistReviewScreen(appointmentReviews.value)
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight()
                            .padding(top = 30.dp, start = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {

                        TextComponent(
                            text = "Account Settings",
                            fontSize = 20,
                            textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                            textColor = Colors.darkPrimary,
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 30,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            textModifier = Modifier.fillMaxWidth()
                        )

                        Column(
                            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                .verticalScroll(rememberScrollState())
                                .padding(start = 15.dp, end = 10.dp, bottom = 50.dp)) {
                            AvailableForBooking(isAvailableForBooking = isAvailableForBooking.value!!, onUpdate = {
                                isAvailableForBooking.value = !isAvailableForBooking.value!!
                            })

                            HomeServiceAvailable(isHomeServiceAvailable = isMobileServiceAvailable.value!!, onUpdate = {
                                isMobileServiceAvailable.value = !isMobileServiceAvailable.value!!
                            })

                            val buttonStyle = Modifier
                                .padding(top = 10.dp, end = 20.dp)
                                .fillMaxWidth()
                                .background(color = Color.Transparent)
                                .height(40.dp)
                            ButtonComponent(modifier = buttonStyle, buttonText = "Save Settings", borderStroke = BorderStroke((1).dp, color = Colors.primaryColor), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 16, shape = RoundedCornerShape(12.dp), textColor =  Colors.primaryColor, style = androidx.compose.material3.MaterialTheme.typography.titleMedium){
                                therapistPresenter.updateAvailability(therapistInfo.id!!, isMobileServiceAvailable = isMobileServiceAvailable.value!!, isAvailable = isAvailableForBooking.value!!)
                            }
                        }
                    }
                }
            })

    }

}

@Composable
fun AvailableForBooking(isAvailableForBooking: Boolean = false, onUpdate: () -> Unit){
    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 20.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(0.90f).wrapContentHeight()
        ) {

            TextComponent(
                textModifier = Modifier.wrapContentWidth()
                    .padding(bottom = 5.dp),
                text = "Available For Booking",
                fontSize = 16,
                textStyle = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                textColor = Color(0xFFb0afaf),
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 23,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            TextComponent(
                textModifier = Modifier.wrapContentWidth(),
                text = if (isAvailableForBooking) "Available" else "Not Available",
                fontSize = 16,
                textStyle = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                textColor = if (isAvailableForBooking) theme.Colors.greenColor else theme.Colors.redColor,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 23,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
        Box(modifier = Modifier.fillMaxWidth().wrapContentHeight(), contentAlignment = Alignment.Center){
            Switch(checked = isAvailableForBooking, onCheckedChange = {
                onUpdate()
            }, modifier = Modifier.size(50.dp), colors = SwitchDefaults.colors(
                checkedThumbColor = if (isAvailableForBooking) theme.Colors.greenColor else theme.Colors.redColor,
                checkedTrackColor = if (isAvailableForBooking) theme.Colors.greenColor else theme.Colors.redColor,
            ))
        }
    }
}

@Composable
fun HomeServiceAvailable(isHomeServiceAvailable: Boolean = false, onUpdate: () -> Unit){
    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 20.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(0.90f).wrapContentHeight()
        ) {

            TextComponent(
                textModifier = Modifier.wrapContentWidth()
                    .padding(bottom = 5.dp),
                text = "Home Service Available",
                fontSize = 16,
                textStyle = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                textColor = Color(0xFFb0afaf),
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 23,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            TextComponent(
                textModifier = Modifier.wrapContentWidth(),
                text = if (isHomeServiceAvailable) "Available" else "Not Available",
                fontSize = 16,
                textStyle = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                textColor = if (isHomeServiceAvailable) theme.Colors.greenColor else theme.Colors.redColor,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 23,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
        Box(modifier = Modifier.fillMaxWidth().wrapContentHeight(), contentAlignment = Alignment.Center){
            Switch(checked = isHomeServiceAvailable, onCheckedChange = {
                onUpdate()
            }, modifier = Modifier.size(50.dp), colors = SwitchDefaults.colors(
                checkedThumbColor = if (isHomeServiceAvailable) theme.Colors.greenColor else theme.Colors.redColor,
                checkedTrackColor = if (isHomeServiceAvailable) theme.Colors.greenColor else theme.Colors.redColor,
            ))
        }
    }
}