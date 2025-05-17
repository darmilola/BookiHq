package presentation.appointmentBookings

import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import domain.Models.AppointmentReview
import domain.Models.PlatformTime
import domain.Models.ServiceTypeTherapists
import domain.Models.ServiceTypeTherapistUIModel
import presentation.widgets.ErrorOccurredWidget
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.widgets.AttachTherapistWidget
import presentation.widgets.TherapistReviewScreen
import presentation.widgets.EmptyContentWidget
import presentation.widgets.TimeGridDisplay
import presentations.components.TextComponent
import utils.calculateTherapistServiceTimes

@Composable
fun BookingSelectTherapists(mainViewModel: MainViewModel, performedActionUIStateViewModel: PerformedActionUIStateViewModel,
                            bookingViewModel: BookingViewModel,
                            bookingPresenter: BookingPresenter) {

    val therapists = bookingViewModel.serviceTherapists.collectAsState()
    val vendorTimes = bookingViewModel.vendorTimes.collectAsState()
    val platformTimes = bookingViewModel.platformTimes.collectAsState()
    val isMobileService = bookingViewModel.isMobileService.collectAsState()

    LaunchedEffect(Unit, block = {
        if (therapists.value.isEmpty()) {
            if (isMobileService.value) {
                bookingPresenter.getMobileServiceTherapists(
                    bookingViewModel.selectedServiceType.value.serviceTypeId,
                    mainViewModel.connectedVendor.value.vendorId!!,
                    day = bookingViewModel.day.value,
                    month = bookingViewModel.month.value,
                    year = bookingViewModel.year.value
                )
            } else {
                bookingPresenter.getServiceTherapists(
                    bookingViewModel.selectedServiceType.value.serviceTypeId,
                    mainViewModel.connectedVendor.value.vendorId!!,
                    day = bookingViewModel.day.value,
                    month = bookingViewModel.month.value,
                    year = bookingViewModel.year.value
                )
            }
        }
    })

    val getTherapistActionUiStates = performedActionUIStateViewModel.getTherapistUiState.collectAsState()
    val currentBooking = bookingViewModel.currentAppointmentBooking.value
    val selectedTherapist = remember { mutableStateOf(ServiceTypeTherapists()) }
    if (currentBooking.serviceTypeTherapists != null) {
        selectedTherapist.value = currentBooking.serviceTypeTherapists!!
    }
    currentBooking.serviceTypeItem = bookingViewModel.selectedServiceType.value
    currentBooking.isMobileService = bookingViewModel.isMobileService.value
    currentBooking.appointmentDay = bookingViewModel.day.value
    currentBooking.appointmentMonth = bookingViewModel.month.value
    currentBooking.appointmentYear = bookingViewModel.year.value


    if (getTherapistActionUiStates.value.isLoading) {
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            IndeterminateCircularProgressBar()
        }
    }
    else if (getTherapistActionUiStates.value.isFailed) {
        Box(modifier = Modifier .fillMaxWidth().height(400.dp), contentAlignment = Alignment.Center) {
            ErrorOccurredWidget(getTherapistActionUiStates.value.errorMessage, onRetryClicked = {
                bookingPresenter.getServiceTherapists(bookingViewModel.selectedServiceType.value.serviceTypeId,
                    mainViewModel.connectedVendor.value.vendorId!!, day = bookingViewModel.day.value, month = bookingViewModel.month.value, year = bookingViewModel.year.value)
            })
        }
    }
    else if (getTherapistActionUiStates.value.isEmpty) {
        Box(modifier = Modifier .fillMaxWidth().height(400.dp), contentAlignment = Alignment.Center) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                EmptyContentWidget(emptyText = getTherapistActionUiStates.value.emptyMessage)
            }
        }
    }
    else if (getTherapistActionUiStates.value.isSuccess) {
        Column(
            Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
             TherapistContent(bookingViewModel,therapists.value, onTherapistSelected = {
                 selectedTherapist.value = it
                 currentBooking.serviceTypeTherapists = it
                 bookingViewModel.setCurrentAppointmentBooking(currentBooking)
             })
            if(selectedTherapist.value.id != null) {
                currentBooking.appointmentTime = null

                val workHours = calculateTherapistServiceTimes(platformTimes = platformTimes.value,
                    vendorTimes = vendorTimes.value,
                    bookedAppointment = selectedTherapist.value.therapistInfo?.bookedTimes!!)

                Column(
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 10.dp)
                        .fillMaxWidth()
                        .height(400.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment  = Alignment.CenterHorizontally,
                ) {
                    AvailableTimeContent(workHours, onWorkHourClickListener = {
                        currentBooking.pendingTime = it
                        bookingViewModel.setCurrentAppointmentBooking(currentBooking)
                    })
                }
                if (selectedTherapist.value.id != null) {
                    val therapistName = selectedTherapist.value.therapistInfo!!.profileInfo!!.firstname
                    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                        TextComponent(
                            text = "$therapistName's Service Reviews",
                            fontSize = 18,
                            fontFamily = GGSansSemiBold,
                            textStyle = TextStyle(),
                            textColor = Colors.darkPrimary,
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Black,
                            lineHeight = 30,
                            textModifier = Modifier
                                .fillMaxWidth().padding(start = 10.dp, bottom = 20.dp))

                        AttachServiceReviews(selectedTherapist.value.therapistInfo!!.reviews!!)
                    }
                }
            }
          }
     }
}






@Composable
fun AvailableTimeContent(availableHours: Triple<ArrayList<PlatformTime>, ArrayList<PlatformTime>, ArrayList<PlatformTime>>, onWorkHourClickListener: (PlatformTime) -> Unit) {

    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 10.dp)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Availability",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth().padding(start = 10.dp, bottom = 20.dp))
        Row(modifier = Modifier.fillMaxWidth(0.90f).wrapContentHeight()) {
            TextComponent(
                text = "Morning",
                fontSize = 16,
                textStyle = TextStyle(),
                textColor = theme.Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                lineHeight = 25,
                textModifier = Modifier.weight(1f).padding(bottom = 15.dp, top = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
            TextComponent(
                text = "Afternoon",
                fontSize = 16,
                textStyle = TextStyle(),
                textColor = theme.Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                lineHeight = 25,
                textModifier = Modifier.weight(1f).padding(bottom = 15.dp, top = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
            TextComponent(
                text = "Evening",
                fontSize = 16,
                textStyle = TextStyle(),
                textColor = theme.Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                lineHeight = 25,
                textModifier = Modifier.weight(1f).padding(bottom = 15.dp, top = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
        }

        Row(modifier = Modifier.fillMaxWidth().height(400.dp)) {
            Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                TimeGridDisplay(platformTimes = availableHours, onWorkHourClickListener = {
                    onWorkHourClickListener(it)
                })
            }
        }
    }
}

@Composable
fun TherapistContent(bookingViewModel: BookingViewModel, therapists: List<ServiceTypeTherapists>, onTherapistSelected: (ServiceTypeTherapists) -> Unit) {

    val unsavedAppointmentTherapist = bookingViewModel.currentAppointmentBooking.value.serviceTypeTherapists
    var selectedTherapistUIModel = remember { mutableStateOf(ServiceTypeTherapistUIModel(selectedTherapist = ServiceTypeTherapists(), therapists))}

    if (unsavedAppointmentTherapist != null) {
         selectedTherapistUIModel = remember { mutableStateOf(ServiceTypeTherapistUIModel(selectedTherapist = unsavedAppointmentTherapist,  visibleTherapist = selectedTherapistUIModel.value.visibleTherapist.map { it2 ->
             it2.copy(
                 isSelected = it2.therapistId == unsavedAppointmentTherapist.therapistId
             )
         }))}
    }

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 10.dp, top = 5.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Choose Therapist",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth().padding(start = 10.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(230.dp),
            contentPadding = PaddingValues(6.dp)
        ) {
            items(selectedTherapistUIModel.value.visibleTherapist.size) { i ->
                AttachTherapistWidget(selectedTherapistUIModel.value.visibleTherapist[i], onTherapistSelectedListener = {
                        it -> selectedTherapistUIModel.value = selectedTherapistUIModel.value.copy(
                    selectedTherapist = it,
                    visibleTherapist = selectedTherapistUIModel.value.visibleTherapist.map { it2 ->
                        it2.copy(
                            isSelected = it2.therapistId == it.therapistId
                        )
                    })
                    onTherapistSelected(it)
                })
            }
        }
    }
}

@Composable
fun AttachServiceReviews(appointmentReview: List<AppointmentReview>){
    TherapistReviewScreen(appointmentReview)
}


