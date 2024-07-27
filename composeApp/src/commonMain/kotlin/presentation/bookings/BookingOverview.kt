package presentation.bookings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import domain.Enums.AppointmentType
import domain.Enums.BookingStatus
import domain.Enums.PaymentMethod
import domain.Enums.ServiceLocationEnum
import domain.Models.Appointment
import domain.Models.OrderItem
import domain.Models.OrderItemUIModel
import domain.Models.UserAppointmentsData
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.dialogs.SuccessDialog
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.AppointmentWidget
import presentation.widgets.MeetingAppointmentWidget
import presentation.widgets.PendingAppointmentWidget
import theme.styles.Colors
import utils.getAppointmentViewHeight


@Composable
fun BookingOverview(mainViewModel: MainViewModel, actionUIStateViewModel: ActionUIStateViewModel, bookingPresenter: BookingPresenter, bookingViewModel: BookingViewModel,uiStateViewModel: UIStateViewModel,onAddMoreServiceClicked:() -> Unit, onLastItemRemoved: () -> Unit, onEditItem: (UserAppointmentsData) -> Unit) {


    val pendingAppointments = bookingViewModel.pendingAppointments.collectAsState()
    val uiState = uiStateViewModel.uiStateInfo.collectAsState()
    val actionState = actionUIStateViewModel.uiStateInfo.collectAsState()


    if (actionState.value.isLoading){
        LoadingDialog("deleting Appointment")
    }
    else if (actionState.value.isSuccess){
        SuccessDialog("success", actionTitle = "", onConfirmation = {
            bookingPresenter.getPendingAppointment(mainViewModel.currentUserInfo.value.userId!!)
        })
    }
    else if (actionState.value.isFailed){
        ErrorDialog("success", actionTitle = "", onConfirmation = {})
    }



    if (uiState.value.loadingVisible) {
        // Content Loading
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            IndeterminateCircularProgressBar()
        }
    } else if (uiState.value.errorOccurred) {
        // error occurred, refresh
    } else if (uiState.value.contentVisible) {


        val columnModifier = Modifier
            .padding(top = 5.dp, bottom = 30.dp)
            .fillMaxHeight()
            .fillMaxWidth()

        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.90f),
                contentAlignment = Alignment.TopStart
            ) {
                PopulateAppointmentScreen(appointmentList = pendingAppointments.value,
                    onEditItem = {
                        onEditItem(it)
                    }, onDeleteItem = {
                        bookingPresenter.deletePendingAppointment(it.resources!!.appointmentId!!)
                    })
            }



            Box(
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
            }
        }
    }

 }

@Composable
fun PopulateAppointmentScreen(appointmentList: List<UserAppointmentsData>, onDeleteItem: (UserAppointmentsData) -> Unit, onEditItem: (UserAppointmentsData) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
            .height(getAppointmentViewHeight(appointmentList.size).dp),
        userScrollEnabled = true
    ) {
        itemsIndexed(items = appointmentList) { it, item ->
                PendingAppointmentWidget(
                    item!!,
                    onEditAppointment = {
                        onEditItem(it)
                    },
                    onDeleteAppointment = {
                        onDeleteItem(it)
                    })
            }

        }
    }





