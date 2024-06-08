package presentation.bookings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import domain.Models.UnsavedAppointment
import presentation.components.ButtonComponent
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.ScreenUIStateViewModel
import presentation.widgets.UnsavedAppointmentWidget
import theme.styles.Colors
import utils.getUnSavedAppointmentViewHeight

@OptIn(ExperimentalFoundationApi::class)
@Composable
 fun BookingOverview(mainViewModel: MainViewModel, screenUiStateViewModel: ScreenUIStateViewModel,
                     bookingViewModel: BookingViewModel,
                     bookingPresenter: BookingPresenter, onAddMoreServiceClicked:() -> Unit, onLastItemRemoved: () -> Unit) {

       val currentBooking: UnsavedAppointment = bookingViewModel.currentAppointmentBooking.value
       var unsavedAppointments = remember {  mutableStateOf(mainViewModel.unSavedAppointments.value) }



        if (unsavedAppointments.value.isNotEmpty()) {
            val editedBooking = unsavedAppointments.value.find {
                it.bookingId == currentBooking.bookingId
            }
            if (editedBooking != null) {
                val editedIndex = unsavedAppointments.value.indexOf(editedBooking)
                unsavedAppointments.value.removeAt(editedIndex)
                unsavedAppointments.value.add(currentBooking)
                mainViewModel.setCurrentUnsavedAppointments(unsavedAppointments.value)
            } else {
                if (currentBooking.serviceId != -1 && currentBooking.serviceTypeId != -1) {
                    unsavedAppointments.value.add(currentBooking)
                    mainViewModel.setCurrentUnsavedAppointments(unsavedAppointments.value)
                }
            }
        } else {
            if (currentBooking.serviceId != -1 && currentBooking.serviceTypeId != -1) {
                unsavedAppointments.value.add(currentBooking)
                mainViewModel.setCurrentUnsavedAppointments(unsavedAppointments.value)
            }
        }
        bookingPresenter.getUnSavedAppointment()
        unsavedAppointments.value = mainViewModel.unSavedAppointments.value

        println("Unsaved $unsavedAppointments")


    val columnModifier = Modifier
            .padding(top = 5.dp, bottom = 30.dp)
            .fillMaxHeight()
            .fillMaxWidth()

       Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

           Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.90f), contentAlignment = Alignment.TopStart) {
               PopulateAppointmentScreen(
                   appointmentList = unsavedAppointments.value, onRemoveItem = { it ->
                       unsavedAppointments.value.remove(it)
                       mainViewModel.setCurrentUnsavedAppointments(unsavedAppointments.value)
                       if (unsavedAppointments.value.size == 0){
                           onLastItemRemoved()
                       }
                       else{
                           bookingViewModel.setCurrentBooking(unsavedAppointments.value.last())
                           bookingViewModel.setSelectedServiceType(unsavedAppointments.value.last().serviceTypeItem!!)
                           bookingViewModel.setSelectedDay(unsavedAppointments.value.last().day)
                           bookingViewModel.setSelectedMonth(unsavedAppointments.value.last().month)
                           bookingViewModel.setSelectedYear(unsavedAppointments.value.last().year)
                           mainViewModel.setSelectedService(unsavedAppointments.value.last().services!!)
                           bookingPresenter.getUnSavedAppointment()
                       }
                   }
               )
           }

           Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center) {

               val buttonStyle = Modifier
                   .fillMaxWidth(0.90f)
                   .height(50.dp)
                   .padding(top = 10.dp)

               ButtonComponent(
                   modifier = buttonStyle,
                   buttonText = "Add More Services",
                   borderStroke = BorderStroke(1.dp, Colors.primaryColor),
                   colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                   fontSize = 16,
                   shape = RoundedCornerShape(10.dp),
                   textColor = Colors.primaryColor,
                   style = TextStyle()
               ) {
                   bookingViewModel.clearCurrentBooking()
                   mainViewModel.clearVendorRecommendation()
                   onAddMoreServiceClicked()
               }
           }

        }
     }
    @Composable
    fun PopulateAppointmentScreen(appointmentList: MutableList<UnsavedAppointment>, onRemoveItem: (UnsavedAppointment) -> Unit) {
        LazyColumn(modifier = Modifier.fillMaxWidth().height(getUnSavedAppointmentViewHeight(appointmentList).dp), userScrollEnabled = true) {
            items(key = { it -> it.bookingId
            }, items = appointmentList) {item ->
                UnsavedAppointmentWidget(unsavedAppointment = item, onRemoveItem = {
                    onRemoveItem(it)
                })
            }
        }
    }


