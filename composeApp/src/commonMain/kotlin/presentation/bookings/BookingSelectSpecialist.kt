package presentation.bookings

import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.ExperimentalFoundationApi
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
import domain.Models.ServiceTypeSpecialist
import domain.Models.ServiceTypeTherapistUIModel
import domain.Models.ServiceTime
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.AttachTherapistWidget
import presentation.widgets.SpecialistReviewScreen
import presentation.widgets.TimeGrid
import presentations.components.TextComponent

@Composable
fun BookingSelectSpecialist(mainViewModel: MainViewModel, uiStateViewModel: UIStateViewModel,
                            bookingViewModel: BookingViewModel,
                            bookingPresenter: BookingPresenter) {

    val therapists = bookingViewModel.serviceSpecialists.collectAsState()
    val serviceTimes = bookingViewModel.serviceTime.collectAsState()
    LaunchedEffect(Unit, block = {
        if (therapists.value.isEmpty() || serviceTimes.value.isEmpty()
            || (bookingViewModel.currentBookingId.value != bookingViewModel.currentAppointmentBooking.value.bookingId)) {
            bookingPresenter.getServiceTherapists(
                bookingViewModel.selectedServiceType.value.categoryId,
                day = bookingViewModel.day.value,
                month = bookingViewModel.month.value,
                year = bookingViewModel.year.value
            )
        }
    })

    val uiStates = uiStateViewModel.uiData.collectAsState()
    val currentBooking = bookingViewModel.currentAppointmentBooking.value
    val selectedTherapist = remember { mutableStateOf(ServiceTypeSpecialist()) }
    if (currentBooking.serviceTypeSpecialist != null) {
        selectedTherapist.value = currentBooking.serviceTypeSpecialist!!
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
                 currentBooking.serviceTypeSpecialist = it
                 bookingViewModel.setCurrentBooking(currentBooking)
             })
            if(selectedTherapist.value.id != null) {
                currentBooking.appointmentTime = null
                AvailableTimeContent(selectedTherapist.value,bookingViewModel, onWorkHourClickListener = {
                     currentBooking.appointmentTime = it
                     bookingViewModel.setCurrentBooking(currentBooking)
                })
                if (selectedTherapist.value.specialistInfo?.specialistReviews?.isNotEmpty() == true) {
                    AttachServiceReviews(selectedTherapist.value)
                }
            }
          }

      }
}






@Composable
fun AvailableTimeContent(serviceTypeSpecialist: ServiceTypeSpecialist, bookingViewModel: BookingViewModel, onWorkHourClickListener: (ServiceTime) -> Unit) {

    val unSavedTime = bookingViewModel.currentAppointmentBooking.value.appointmentTime
    val serviceTime = bookingViewModel.serviceTime.value
    val timeOffs = serviceTypeSpecialist.timeOffs
    val normalisedBookedTimes = arrayListOf<ServiceTime>()
    val normalisedTimeOffTimes = arrayListOf<ServiceTime>()
    val bookedTimes = arrayListOf<ServiceTime>()
    val displayTimes = remember { mutableStateOf(arrayListOf<ServiceTime>()) }
    for (item in serviceTypeSpecialist.specialistInfo?.bookedTimes!!){
        normalisedBookedTimes.add(item.serviceTime!!)
    }
    for (item in timeOffs!!){
        normalisedTimeOffTimes.add(item.timeOffTime!!)
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

        serviceTime.map { it ->
            if (it in normalisedBookedTimes || it in normalisedTimeOffTimes){
                val bookedTime =  serviceTime.find { it2 ->
                    it.id == it2.id
                }?.copy(isAvailable = false)
                bookedTimes.add(bookedTime!!)
            }
            else{
                bookedTimes.add(it)
            }
        }
         displayTimes.value = bookedTimes

         TimeGrid(displayTimes.value,selectedTime = unSavedTime, onWorkHourClickListener = {
             onWorkHourClickListener(it)
         })


    }
}

@Composable
fun TherapistContent(bookingViewModel: BookingViewModel, specialists: List<ServiceTypeSpecialist>, onTherapistSelected: (ServiceTypeSpecialist) -> Unit) {

    val unsavedAppointmentTherapist = bookingViewModel.currentAppointmentBooking.value.serviceTypeSpecialist
    var selectedTherapistUIModel = remember { mutableStateOf(ServiceTypeTherapistUIModel(selectedTherapist = ServiceTypeSpecialist(), specialists))}

    if (unsavedAppointmentTherapist != null) {
         selectedTherapistUIModel = remember { mutableStateOf(ServiceTypeTherapistUIModel(selectedTherapist = unsavedAppointmentTherapist,  visibleTherapist = selectedTherapistUIModel.value.visibleTherapist.map { it2 ->
             it2.copy(
                 isSelected = it2.specialistId == unsavedAppointmentTherapist.specialistId
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
            text = "Choose Specialist",
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
                            isSelected = it2.specialistId == it.specialistId
                        )
                    })
                    onTherapistSelected(it)
                })
            }
        }
    }
}

@Composable
fun AttachServiceReviews(serviceTypeSpecialist: ServiceTypeSpecialist){
    val specialistReviews = serviceTypeSpecialist.specialistInfo?.specialistReviews
    TextComponent(
        text = serviceTypeSpecialist.specialistInfo?.profileInfo?.firstname+"'s Reviews",
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

   SpecialistReviewScreen(specialistReviews!!)

}


