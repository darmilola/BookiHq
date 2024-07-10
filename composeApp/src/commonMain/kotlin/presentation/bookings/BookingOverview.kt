package presentation.bookings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import domain.Models.CurrentAppointmentBooking
import presentation.components.ButtonComponent
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.UnsavedAppointmentWidget
import theme.styles.Colors

@Composable
 fun BookingOverview(mainViewModel: MainViewModel, uiStateViewModel: UIStateViewModel,
                     bookingViewModel: BookingViewModel,
                     bookingPresenter: BookingPresenter, onAddMoreServiceClicked:() -> Unit, onLastItemRemoved: () -> Unit) {

   val currentAppointmentsBooking = bookingViewModel.currentAppointmentBooking.value

    println(currentAppointmentsBooking)

    val columnModifier = Modifier
        .padding(top = 5.dp, bottom = 30.dp)
        .fillMaxHeight()
        .fillMaxWidth()

    Column(
        modifier = columnModifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.90f),
            contentAlignment = Alignment.Center
        ) {
            PopulateAppointmentScreen(
                appointment = currentAppointmentsBooking, onRemoveItem = { it ->
                     onLastItemRemoved()
                    })
                }
          }

        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {

            val buttonStyle = Modifier
                .fillMaxWidth(0.90f)
                .height(50.dp)
                .padding(top = 10.dp)
        }

    }

    @Composable
    fun PopulateAppointmentScreen(
        appointment: CurrentAppointmentBooking,
        onRemoveItem: (CurrentAppointmentBooking) -> Unit
    ) {
        Column(modifier = Modifier.fillMaxWidth().height(190.dp)) {
            UnsavedAppointmentWidget(currentAppointmentBooking = appointment, onRemoveItem = {
                onRemoveItem(it)
            })
        }
    }




