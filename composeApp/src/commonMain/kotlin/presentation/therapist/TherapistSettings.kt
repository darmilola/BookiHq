package presentation.therapist

import GGSansRegular
import StackedSnackbarHost
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import domain.Models.TherapistInfo
import presentation.DomainViewHandler.TherapistSettingsHandler
import presentation.Screens.SplashScreen
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.widgets.TitleWidget
import rememberStackedSnackbarHostState
import theme.styles.Colors

@Composable
fun TherapistSettings(therapistInfo: TherapistInfo, therapistPresenter: TherapistPresenter, performedActionUIStateViewModel: PerformedActionUIStateViewModel, onUpdateSuccess: () -> Unit){

    val isAvailableForBooking = remember { mutableStateOf(therapistInfo.isAvailable) }
    val isMobileServiceAvailable = remember { mutableStateOf(therapistInfo.isMobileServiceAvailable) }

    val updateUiState = performedActionUIStateViewModel.uiStateInfo.collectAsState()

    val handler = TherapistSettingsHandler(therapistPresenter, performedActionUIStateViewModel)
    handler.init()

    val stackedSnackBarHostState = rememberStackedSnackbarHostState(
        maxStack = 5,
        animation = StackedSnackbarAnimation.Bounce
    )

    if (updateUiState.value.isLoading){
        println("LOADING")
        Box(modifier = Modifier.fillMaxWidth()) {
            LoadingDialog(updateUiState.value.loadingMessage)
        }
    }

    else if (updateUiState.value.isSuccess) {
        println("Success")
         onUpdateSuccess()
    }
    else if (updateUiState.value.isFailed){
        println("Failed")
        Box(modifier = Modifier.fillMaxWidth()) {
            ErrorDialog(updateUiState.value.loadingMessage, actionTitle = "Close", onConfirmation = {})
        }
    }


    Scaffold(
        snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
        topBar = {},
        content = {
                   Column(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .background(color = Color.White)
                    ) {
                       Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(top = 50.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {

                           Row(modifier = Modifier.fillMaxWidth().height(50.dp), horizontalArrangement = Arrangement.Center) {
                                 Switch(checked = isAvailableForBooking.value!!, onCheckedChange = {
                                     isAvailableForBooking.value = it
                                 })
                                 TitleWidget(textColor = Colors.primaryColor, title = "Is Available for booking")
                             }
                           Row(modifier = Modifier.fillMaxWidth().height(50.dp), horizontalArrangement = Arrangement.Center) {
                               Switch(checked = isMobileServiceAvailable.value!!, onCheckedChange = {
                                   isMobileServiceAvailable.value = it
                               })
                               TitleWidget(textColor = Colors.primaryColor, title = "Mobile Service Available")
                           }

                           val buttonStyle = Modifier
                               .padding(top = 10.dp)
                               .fillMaxWidth(0.80f)
                               .background(color = Color.Transparent)
                               .height(40.dp)
                           ButtonComponent(modifier = buttonStyle, buttonText = "Save Settings", borderStroke = BorderStroke((1).dp, color = Colors.primaryColor), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 16, shape = RoundedCornerShape(12.dp), textColor =  Colors.primaryColor, style = TextStyle(fontFamily = GGSansRegular)){
                               therapistPresenter.updateAvailability(therapistInfo.id!!, isMobileServiceAvailable = isMobileServiceAvailable.value!!, isAvailable = isAvailableForBooking.value!!)
                           }
                         }
             }
        })


}