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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import domain.Models.UnsavedAppointment
import presentation.components.ButtonComponent
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.viewmodels.UIStates
import presentation.widgets.UnsavedAppointmentWidget
import theme.styles.Colors
import utils.getUnSavedAppointmentViewHeight

@Composable
 fun BookingOverview(mainViewModel: MainViewModel, uiStateViewModel: UIStateViewModel,
                     bookingViewModel: BookingViewModel,
                     bookingPresenter: BookingPresenter, onAddMoreServiceClicked:() -> Unit, onLastItemRemoved: () -> Unit) {

       val currentBooking: UnsavedAppointment = bookingViewModel.currentAppointmentBooking.value
       var unsavedAppointments = remember {  mainViewModel.unSavedAppointments.value }


    LaunchedEffect(Unit, block = {
        if (unsavedAppointments.isNotEmpty()) {
            val editedBooking = unsavedAppointments.find {
                it.bookingId == currentBooking.bookingId
            }
            if (editedBooking != null) {
                val editedIndex = unsavedAppointments.indexOf(editedBooking)
                unsavedAppointments.removeAt(editedIndex)
                unsavedAppointments.add(currentBooking)
                mainViewModel.setCurrentUnsavedAppointments(unsavedAppointments)
            } else {
                if (currentBooking.serviceId != -1 && currentBooking.serviceTypeId != -1) {
                    unsavedAppointments.add(currentBooking)
                    mainViewModel.setCurrentUnsavedAppointments(unsavedAppointments)
                }
            }
        } else {
            if (currentBooking.serviceId != -1 && currentBooking.serviceTypeId != -1) {
                unsavedAppointments.add(currentBooking)
                mainViewModel.setCurrentUnsavedAppointments(unsavedAppointments)
            }
        }
        bookingPresenter.getUnSavedAppointment()
        unsavedAppointments = mainViewModel.unSavedAppointments.value
    })



 /*   // View Contract Handler Initialisation
    val handler = BookingScreenHandler(
        bookingViewModel, bookingPresenter,
        onPageLoading = {},
        onShowUnsavedAppointment = {
            unsavedAppointments = mainViewModel.unSavedAppointments.value
            bookingViewModel.setCurrentBooking(unsavedAppointments.last())
          },
         onContentVisible = {}, onErrorVisible = {})
         handler.init()
*/



    val columnModifier = Modifier
            .padding(top = 5.dp, bottom = 30.dp)
            .fillMaxHeight()
            .fillMaxWidth()

       Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PopulateAppointmentScreen(
                appointmentList = unsavedAppointments, onRemoveItem = { it ->
                        unsavedAppointments.remove(it)
                        mainViewModel.setCurrentUnsavedAppointments(unsavedAppointments)
                     if (unsavedAppointments.size == 0){
                            onLastItemRemoved()
                        }
                    else{
                         bookingViewModel.setCurrentBooking(unsavedAppointments.last())
                         bookingViewModel.setSelectedServiceType(unsavedAppointments.last().serviceTypeItem!!)
                         bookingViewModel.setSelectedDate(unsavedAppointments.last().appointmentDate!!)
                         mainViewModel.setSelectedService(unsavedAppointments.last().services!!)
                         bookingPresenter.getUnSavedAppointment()
                    }
                }
            )

            val buttonStyle = Modifier
                .fillMaxWidth(0.90f)
                .height(50.dp)
                .padding(top = 10.dp)

            ButtonComponent(modifier = buttonStyle, buttonText = "Add More Services", borderStroke = BorderStroke(1.dp, Colors.primaryColor), colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 16, shape = RoundedCornerShape(10.dp), textColor = Colors.primaryColor, style = TextStyle()){
                bookingViewModel.clearCurrentBooking()
                mainViewModel.clearVendorRecommendation()
                onAddMoreServiceClicked()
            }

        }
     }
    @Composable
    fun PopulateAppointmentScreen(appointmentList: MutableList<UnsavedAppointment>, onRemoveItem: (UnsavedAppointment) -> Unit) {
        LazyColumn(modifier = Modifier.fillMaxWidth().height(getUnSavedAppointmentViewHeight(appointmentList).dp), userScrollEnabled = true) {
            items(key = {
                     it -> it.bookingId
            }, items = appointmentList) {item ->
                UnsavedAppointmentWidget(unsavedAppointment = item, onRemoveItem = {
                    onRemoveItem(it)
                })
            }
        }
    }


