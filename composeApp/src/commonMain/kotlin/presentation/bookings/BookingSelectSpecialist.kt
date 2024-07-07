package presentation.bookings

import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.dp
import domain.Models.PlatformTime
import domain.Models.ServiceTypeTherapists
import domain.Models.ServiceTypeTherapistUIModel
import domain.Models.AvailableTime
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.AttachTherapistWidget
import presentation.widgets.TherapistReviewScreen
import presentation.widgets.TimeGrid
import presentations.components.TextComponent

@Composable
fun BookingSelectTherapists(mainViewModel: MainViewModel, uiStateViewModel: UIStateViewModel,
                            bookingViewModel: BookingViewModel,
                            bookingPresenter: BookingPresenter) {

    val therapists = bookingViewModel.serviceTherapists.collectAsState()
    LaunchedEffect(Unit, block = {
        if (therapists.value.isEmpty()
            || (bookingViewModel.currentBookingId.value != bookingViewModel.currentAppointmentBooking.value.bookingId)) {
            bookingPresenter.getServiceTherapists(
                bookingViewModel.selectedServiceType.value.categoryId,
                day = bookingViewModel.day.value,
                month = bookingViewModel.month.value,
                year = bookingViewModel.year.value
            )
        }
    })

    val uiStates = uiStateViewModel.uiStateInfo.collectAsState()
    val currentBooking = bookingViewModel.currentAppointmentBooking.value
    val selectedTherapist = remember { mutableStateOf(ServiceTypeTherapists()) }
    if (currentBooking.serviceTypeTherapists != null) {
        selectedTherapist.value = currentBooking.serviceTypeTherapists!!
    }


    if (uiStates.value.loadingVisible) {
        // Content Loading
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            IndeterminateCircularProgressBar()
        }
    } else if (uiStates.value.errorOccurred) {

      // error occurred, refresh

    } else if (uiStates.value.contentVisible) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
             TherapistContent(bookingViewModel,therapists.value, onTherapistSelected = {
                 selectedTherapist.value = it
                 currentBooking.serviceTypeTherapists = it
                 bookingViewModel.setCurrentBooking(currentBooking)
                 bookingViewModel.setServiceTimes(it.therapistInfo?.availableTimes!!)
             })
            if(selectedTherapist.value.id != null) {
                currentBooking.appointmentTime = null
                AvailableTimeContent(selectedTherapist.value,bookingViewModel, onWorkHourClickListener = {
                     currentBooking.appointmentTime = it
                     bookingViewModel.setCurrentBooking(currentBooking)
                })
                if (selectedTherapist.value.therapistInfo?.therapistReviews?.isNotEmpty() == true) {
                    AttachServiceReviews(selectedTherapist.value)
                }
            }
          }

      }
}






@Composable
fun AvailableTimeContent(serviceTypeTherapists: ServiceTypeTherapists, bookingViewModel: BookingViewModel, onWorkHourClickListener: (AvailableTime) -> Unit) {

    val unSavedTime = bookingViewModel.currentAppointmentBooking.value.appointmentTime
    val availableWorkHour = serviceTypeTherapists.therapistInfo?.availableTimes
    val normalisedBookedTimes = arrayListOf<PlatformTime>()
    val normalisedBookingTimes = arrayListOf<AvailableTime>()
    val displayTimes = remember { mutableStateOf(arrayListOf<AvailableTime>()) }


    for (item in serviceTypeTherapists.therapistInfo?.bookedTimes!!) {
        normalisedBookedTimes.add(item.platformTime!!)
    }




    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 10.dp, top = 15.dp, bottom = 10.dp)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Service Times",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth().padding(start = 10.dp, bottom = 20.dp))

        availableWorkHour!!.map { it ->
            if (it.vendorTime?.platformTime in normalisedBookedTimes){
                val normalisedTime =  availableWorkHour.find { it2 ->
                    it.id == it2.id
                }?.copy(isAvailable = false)
                normalisedBookingTimes.add(normalisedTime!!)
            }
            else{
                val isAvailableTime =  it.copy(isAvailable = true)
                normalisedBookingTimes.add(isAvailableTime)
            }
        }
         displayTimes.value = normalisedBookingTimes
         if (displayTimes.value.isNotEmpty()) {
             TimeGrid(displayTimes.value, selectedTime = unSavedTime, onWorkHourClickListener = {
                 onWorkHourClickListener(it)
             })
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
         println(selectedTherapistUIModel.toString())
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
fun AttachServiceReviews(serviceTypeTherapists: ServiceTypeTherapists){
    val therapistReviews = serviceTypeTherapists.therapistInfo?.therapistReviews
    TextComponent(
        text = serviceTypeTherapists.therapistInfo?.profileInfo?.firstname+"'s Reviews",
        fontSize = 18,
        fontFamily = GGSansSemiBold,
        textStyle = TextStyle(),
        textColor = Colors.darkPrimary,
        textAlign = TextAlign.Left,
        fontWeight = FontWeight.Black,
        lineHeight = 30,
        textModifier = Modifier
            .padding(bottom = 5.dp, start = 15.dp, top = 10.dp)
            .fillMaxWidth())

   TherapistReviewScreen(therapistReviews!!)

}


