package presentation.widgets

import GGSansBold
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import presentation.components.ButtonComponent
import presentation.viewmodels.MainViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentPaymentMethodBottomSheet(mainViewModel: MainViewModel, onCardPaymentSelected:() -> Unit, onCashSelected:() -> Unit,
                                     onDismiss: () -> Unit) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true))
    val isCardPaymentMethod = remember { mutableStateOf(true) }

    val showBottomSheet = mainViewModel.showPaymentMethodBottomSheet.collectAsState()
    scope.launch {
        scaffoldState.bottomSheetState.hide()
    }
    if (showBottomSheet.value){
        scope.launch {
            scaffoldState.bottomSheetState.show()
        }
    }
    else{
        scope.launch {
            scaffoldState.bottomSheetState.hide()
            onDismiss()
        }
    }

    ModalBottomSheet(
        modifier = Modifier.padding(top = 20.dp).height(375.dp),
        onDismissRequest = {
            onDismiss()
        },
        sheetState = scaffoldState.bottomSheetState,
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        containerColor = Color(0xFFF3F3F3),
        dragHandle = {},
    ) {
        Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
            Row(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.80f)
                ) {
                    TextComponent(
                        text = "Choose Payment Method",
                        fontSize = 18,
                        fontFamily = GGSansBold,
                        textStyle = TextStyle(),
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 20,
                        textColor = Colors.primaryColor,
                        maxLines = 1,
                        textModifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
                    val modifier = Modifier
                        .padding(top = 2.dp)
                        .clickable {
                            scope.launch {
                                scaffoldState.bottomSheetState.hide()
                                onDismiss()
                            }
                        }
                        .size(18.dp)
                    ImageComponent(
                        imageModifier = modifier,
                        imageRes = "drawable/cancel_icon.png",
                        colorFilter = ColorFilter.tint(color = Colors.primaryColor)
                    )

                }
            }
            PaymentMethodWidget(onCashSelectedListener = {
                isCardPaymentMethod.value = false
            }, onCardPaymentSelectedListener = {
                isCardPaymentMethod.value = true
            })

            ButtonComponent(
                modifier = Modifier
                    .padding(bottom = 10.dp, start = 10.dp, end = 10.dp, top = 4.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                buttonText = "Continue",
                colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                fontSize = 16,
                shape = CircleShape,
                textColor = Color(color = 0xFFFFFFFF),
                style = TextStyle(),
                borderStroke = null
            ) {
                if (isCardPaymentMethod.value){
                    onCardPaymentSelected()
                }
                else{
                    onCashSelected()
                }
                scope.launch {
                    scaffoldState.bottomSheetState.hide()
                }
            }

        }
    }

}
