package presentation.Bookings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import domain.Models.AppointmentItem
import domain.Models.UnsavedAppointment
import presentation.components.ButtonComponent
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.NewAppointmentWidget
import theme.styles.Colors
import utils.getUnSavedAppointmentViewHeight

@Composable
 fun BookingOverview(mainViewModel: MainViewModel, uiStateViewModel: UIStateViewModel,
                     bookingViewModel: BookingViewModel,
                     bookingPresenter: BookingPresenter, onAddMoreServiceClicked:() -> Unit) {


       val currentBooking: UnsavedAppointment = bookingViewModel.currentAppointmentBooking.value
       val unsavedAppointments: ArrayList<UnsavedAppointment> = mainViewModel.unSavedAppointments.value
   // println("current"+currentBooking.bookingId)
   // println("Unsaved"+unsavedAppointments)

    if(unsavedAppointments.isNotEmpty()) {
       val initialBooking =  unsavedAppointments.find {
           it.bookingId == currentBooking.bookingId
       }
        if (initialBooking != null){
            println("Am not null")
            val initialIndex = unsavedAppointments.indexOf(initialBooking)
            println(initialIndex)
            unsavedAppointments.removeAt(initialIndex)
            unsavedAppointments.add(currentBooking)
            mainViewModel.setCurrentUnsavedAppointments(unsavedAppointments)
        }
        else{
            println("Am null")
            if(currentBooking.serviceId != -1 && currentBooking.serviceTypeId != -1) {
                unsavedAppointments.add(currentBooking)
                mainViewModel.setCurrentUnsavedAppointments(unsavedAppointments)
            }
        }
     }
    else{
        println("Am empty")
           if(currentBooking.serviceId != -1 && currentBooking.serviceTypeId != -1) {
               unsavedAppointments.add(currentBooking)
               mainViewModel.setCurrentUnsavedAppointments(unsavedAppointments)
           }
       }


        val columnModifier = Modifier
            .padding(top = 5.dp, bottom = 30.dp)
            .fillMaxHeight()
            .fillMaxWidth()


        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val appointmentList = ArrayList<AppointmentItem>()
            val appointmentItem1 = AppointmentItem(appointmentType = 1)
            val appointmentItem2 = AppointmentItem(appointmentType = 2)

            appointmentList.add(appointmentItem1)
            appointmentList.add(appointmentItem2)

            PopulateAppointmentScreen(
                appointmentList = mainViewModel.unSavedAppointments.value
            )

            val buttonStyle = Modifier
                .fillMaxWidth(0.90f)
                .height(50.dp)
                .padding(top = 10.dp)

            ButtonComponent(modifier = buttonStyle, buttonText = "Add More Services", borderStroke = BorderStroke(1.dp, Colors.primaryColor), colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 16, shape = RoundedCornerShape(10.dp), textColor = Colors.primaryColor, style = TextStyle()){
                bookingViewModel.clearCurrentBooking()
                onAddMoreServiceClicked()
            }

        }

    }
    @Composable
    fun PopulateAppointmentScreen(appointmentList: List<UnsavedAppointment>) {
        LazyColumn(modifier = Modifier.fillMaxWidth().height(getUnSavedAppointmentViewHeight(appointmentList).dp), userScrollEnabled = true) {
            items(appointmentList) {item ->
                NewAppointmentWidget()
            }
        }
    }



