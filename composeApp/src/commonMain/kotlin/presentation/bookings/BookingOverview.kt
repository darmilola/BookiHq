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
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import domain.Models.CurrentAppointmentBooking
import domain.Models.CurrentAppointmentBookingItemUIModel
import domain.Models.OrderItem
import domain.Models.OrderItemUIModel
import presentation.components.ButtonComponent
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.UnsavedAppointmentWidget
import theme.styles.Colors
import utils.getUnSavedAppointmentViewHeight


@Composable
fun BookingOverview(mainViewModel: MainViewModel, bookingViewModel: BookingViewModel, onAddMoreServiceClicked:() -> Unit, onLastItemRemoved: () -> Unit) {

    val currentBooking = bookingViewModel.currentAppointmentBooking.value
    val currentKeys = arrayListOf<Int>()
    var unsavedAppointments = remember {  mutableStateOf(mainViewModel.unSavedAppointments.value) }
    unsavedAppointments.value.map {
        currentKeys.add(it.bookingKey)
    }
    println(currentKeys)
    println(currentBooking.bookingKey)

    println(unsavedAppointments.value.size)

    if (unsavedAppointments.value.isNotEmpty()) {
        println("Here 2")
       if(!currentKeys.contains(currentBooking.bookingKey)){
           println("My size is ${unsavedAppointments.value.size}")
           val editedIndex = unsavedAppointments.value.find {
               it.bookingKey == currentBooking.bookingKey
           }
           unsavedAppointments.value.remove(editedIndex)
           unsavedAppointments.value.add(currentBooking)
           mainViewModel.setCurrentUnsavedAppointment(unsavedAppointments.value)
       }
    } else {
        println("Here 4")
        if (currentBooking.bookingKey != -1) {
            unsavedAppointments.value.add(currentBooking)
            mainViewModel.setCurrentUnsavedAppointment(unsavedAppointments.value)
        }
    }


    var currentAppointmentBookingItemUIModel by remember {
        mutableStateOf(
            CurrentAppointmentBookingItemUIModel(
                currentBooking,
                mainViewModel.unSavedAppointments.value
            )
        )
    }



    println("Unsaved $unsavedAppointments")

    val columnModifier = Modifier
        .padding(top = 5.dp, bottom = 30.dp)
        .fillMaxHeight()
        .fillMaxWidth()

    Column(
        modifier = columnModifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.90f), contentAlignment = Alignment.TopStart) {
            PopulateAppointmentScreen(
                currentAppointmentBookingItemUIModel.itemList, onRemoveItem = { it ->
                    val index = unsavedAppointments.value.indexOf(it)
                    currentAppointmentBookingItemUIModel.itemList.remove(it)
                    currentAppointmentBookingItemUIModel = currentAppointmentBookingItemUIModel.copy(
                        selectedItem = CurrentAppointmentBooking(),
                        itemList = currentAppointmentBookingItemUIModel.itemList
                    )
                    mainViewModel.setCurrentUnsavedAppointment(currentAppointmentBookingItemUIModel.itemList)
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
                    }
                }
            )
        }



      /*  Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            contentAlignment = Alignment.BottomCenter
        ) {

            val buttonStyle = Modifier
                .fillMaxWidth(0.90f)
                .height(50.dp)
                .padding(top = 10.dp)

            ButtonComponent(
                modifier = buttonStyle,
                buttonText = "Add More Service",
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                fontSize = 16,
                shape = RoundedCornerShape(10.dp),
                textColor = Colors.primaryColor,
                borderStroke = BorderStroke(width = 1.dp, color = Colors.primaryColor),
                style = MaterialTheme.typography.h6
            ) {
                onAddMoreServiceClicked()
            }
        }*/
    }

    }

@Composable
fun PopulateAppointmentScreen(itemList: MutableList<CurrentAppointmentBooking>, onRemoveItem: (CurrentAppointmentBooking) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxWidth().height(getUnSavedAppointmentViewHeight(itemList).dp), userScrollEnabled = true) {
        items(key = { it -> it.bookingKey
        }, items = itemList) {item ->
            UnsavedAppointmentWidget(currentAppointmentBooking = item, onRemoveItem = {
                onRemoveItem(it)
            })
        }
    }
}




