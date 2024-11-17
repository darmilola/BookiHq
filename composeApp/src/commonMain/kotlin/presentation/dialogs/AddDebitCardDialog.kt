package presentation.dialogs

import StackedSnackbarHost
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import domain.Models.PaymentCard
import domain.Models.PlatformNavigator
import kotlinx.coroutines.launch
import presentation.components.ButtonComponent
import presentation.widgets.ButtonContent
import presentation.widgets.TitleWidget
import presentations.widgets.InputWidget
import rememberStackedSnackbarHostState
import theme.styles.Colors

@Composable
fun AddDebitCardDialog(databaseBuilder: RoomDatabase.Builder<AppDatabase>?, onDismissRequest: () -> Unit, onConfirmation: () -> Unit) {
    Dialog( properties = DialogProperties(usePlatformDefaultWidth = false), onDismissRequest = { onDismissRequest() }) {


        val pattern = remember { Regex("^\\d*\$") }
        val scope = rememberCoroutineScope()

        val cardNumber = remember { mutableStateOf("") }
        val cvv = remember { mutableStateOf("") }
        val expiryMonth = remember { mutableStateOf("") }
        val expiryYear = remember { mutableStateOf("") }
        val isSavedClicked = remember { mutableStateOf(false) }

        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Colors.lighterPrimaryColor,
            modifier = Modifier.fillMaxWidth(0.90f)
        ) {
            Card(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                shape = RoundedCornerShape(10.dp),
                elevation = 15.dp, border = BorderStroke((0.5).dp, color = Colors.primaryColor)
            ) {
                Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(70.dp)
                            .background(color = Colors.primaryColor),
                        contentAlignment = Alignment.Center
                    ) {
                        TitleWidget(title = "Add Debit Card", textColor = Color.White)
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        InputWidget(
                            iconRes = "drawable/address.png",
                            placeholderText = "Card Number",
                            iconSize = 28,
                            text = cardNumber.value,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isPasswordField = false,
                            onSaveClicked = isSavedClicked.value,
                            isSingleLine = true,
                            maxLines = 1,
                            maxLength = 25
                        ) {
                            if (it.matches(pattern)) {
                                cardNumber.value = it
                            }
                        }
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        InputWidget(
                            iconRes = "drawable/card_icon.png",
                            placeholderText = "Expiry Month(MM)",
                            iconSize = 28,
                            onSaveClicked = isSavedClicked.value,
                            text = expiryMonth.value,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isPasswordField = false,
                            isSingleLine = true,
                            maxLines = 1,
                            maxLength = 3
                        ) {
                            if (it.matches(pattern) && it.length <= 2) {
                                expiryMonth.value = it
                            }
                        }
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        InputWidget(
                            iconRes = "drawable/card_icon.png",
                            placeholderText = "Expiry Year(YY)",
                            iconSize = 28,
                            text = expiryYear.value,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isPasswordField = false,
                            onSaveClicked = isSavedClicked.value,
                            isSingleLine = true,
                            maxLines = 1,
                            maxLength = 3
                        ) {
                            if (it.matches(pattern) && it.length <= 2) {
                                expiryYear.value = it
                            }
                        }
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        InputWidget(
                            iconRes = "drawable/address.png",
                            placeholderText = "CVV",
                            iconSize = 28,
                            text = cvv.value,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isPasswordField = false,
                            onSaveClicked = isSavedClicked.value,
                            isSingleLine = true,
                            maxLines = 1,
                            maxLength = 4
                        ) {
                            if (it.matches(pattern) && it.length <= 3) {
                                cvv.value = it
                            }
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 20.dp)) {
                        ButtonContent(onDismissRequest = {
                            onDismissRequest()
                        }, onConfirmation = {
                            scope.launch {
                               if (cardNumber.value.isNotEmpty() && expiryMonth.value.isNotEmpty() &&
                                   expiryYear.value.isNotEmpty() && cvv.value.isNotEmpty()) {
                                   val paymentDao = databaseBuilder!!.build().getPaymentCardDao()
                                   paymentDao.insert(
                                       paymentCard = PaymentCard(
                                           cardNumber = cardNumber.value,
                                           expiryMonth = expiryMonth.value,
                                           expiryYear = expiryYear.value,
                                           cvv = cvv.value
                                       )
                                   )
                               }
                                isSavedClicked.value = true
                                if (cardNumber.value.isNotEmpty() && cvv.value.isNotEmpty() && expiryMonth.value.isNotEmpty() && expiryYear.value.isNotEmpty()){
                                     onConfirmation()
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}

