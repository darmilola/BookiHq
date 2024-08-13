package presentation.bookings

import UIStates.ActionUIStates
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.Enums.AppointmentType
import domain.Enums.BookingStatus
import domain.Enums.PaymentMethod
import domain.Enums.ServiceLocationEnum
import domain.Enums.ServiceStatusEnum
import domain.Models.UserAppointment
import presentation.components.IndeterminateCircularProgressBar
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.dialogs.SuccessDialog
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.widgets.PendingAppointmentWidget
import utils.getAppointmentViewHeight


@Composable
fun BookingCheckOut(mainViewModel: MainViewModel, bookingPresenter: BookingPresenter, bookingViewModel: BookingViewModel, actionUIStateViewModel: ActionUIStateViewModel, onLastItemRemoved: () -> Unit) {

    val userId = mainViewModel.currentUserInfo.value.userId
    val vendorId = mainViewModel.connectedVendor.value.vendorId
    val currentAppointmentBooking = bookingViewModel.currentAppointmentBooking.value

    val isPendingAppointmentLoaded = bookingViewModel.isPendingAppointmentLoaded.collectAsState()
    val customerPendingBookingAppointments = bookingViewModel.pendingAppointments.collectAsState()

    val deleteActionUiState = actionUIStateViewModel.deletePendingAppointmentUiState.collectAsState()
    val loadingPendingActionUiState = actionUIStateViewModel.loadPendingAppointmentUiState.collectAsState()

    if (!isPendingAppointmentLoaded.value) {
        bookingPresenter.createPendingBookingAppointment(
            userId = userId!!,
            vendorId = vendorId!!,
            serviceId = currentAppointmentBooking.serviceId,
            serviceTypeId = currentAppointmentBooking.serviceTypeId!!,
            therapistId = currentAppointmentBooking.serviceTypeTherapists?.therapistInfo?.therapistId!!,
            appointmentTime = currentAppointmentBooking.pendingTime?.id!!,
            day = currentAppointmentBooking.appointmentDay!!,
            month = currentAppointmentBooking.appointmentMonth!!,
            year = currentAppointmentBooking.appointmentYear!!,
            serviceLocation = if (currentAppointmentBooking.isMobileService) ServiceLocationEnum.MOBILE.toPath() else ServiceLocationEnum.SPA.toPath(),
            serviceStatus = ServiceStatusEnum.BOOKING.toPath(),
            appointmentType = AppointmentType.SERVICE.toPath(),
            paymentAmount = currentAppointmentBooking.serviceTypeItem!!.price.toDouble(),
            paymentMethod = PaymentMethod.CARD_PAYMENT.toPath(),
            bookingStatus = BookingStatus.PENDING.toPath()
        )
        bookingViewModel.setPendingAppointmentLoaded(isLoaded = true)
    }


    if (deleteActionUiState.value.isLoading) {
        LoadingDialog("Deleting Appointment")
    }
    else if (deleteActionUiState.value.isSuccess) {
        SuccessDialog("success", actionTitle = "", onConfirmation = {
            actionUIStateViewModel.switchDeletePendingAppointmentUiState(ActionUIStates(isDefault = true))
            bookingPresenter.getPendingBookingAppointment(
                mainViewModel.currentUserInfo.value.userId!!,
                bookingStatus = BookingStatus.PENDING.toPath()
            )
        })
    }
    else if (deleteActionUiState.value.isFailed) {
        ErrorDialog("success", actionTitle = "", onConfirmation = {})
    }



    if (loadingPendingActionUiState.value.isLoading) {
        // Content Loading
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            IndeterminateCircularProgressBar()
        }
    } else if (loadingPendingActionUiState.value.isFailed) {
        // error occurred, refresh
    } else if (loadingPendingActionUiState.value.isSuccess) {
        if (bookingViewModel.pendingAppointments.value.isEmpty()) {
            actionUIStateViewModel.switchActionLoadPendingAppointmentUiState(ActionUIStates(isDefault = true))
            onLastItemRemoved()
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                contentAlignment = Alignment.TopStart
            ) {
                PopulateBookingAppointmentScreen(appointmentList = customerPendingBookingAppointments.value,
                    onDeleteItem = {
                        bookingPresenter.deletePendingBookingAppointment(it.resources!!.appointmentId!!)
                    })
            }
        }
    }
}



@Composable
fun PopulateBookingAppointmentScreen(appointmentList: List<UserAppointment>, onDeleteItem: (UserAppointment) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
            .height(getAppointmentViewHeight(appointmentList.size).dp),
        userScrollEnabled = true
    ) {
        itemsIndexed(items = appointmentList) { it, item ->
                PendingAppointmentWidget(
                    item,
                    onDeleteAppointment = {
                        onDeleteItem(it)
                    })
              }

        }
    }





