package presentation.Screens

import StackedSnackbarHost
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Models.PaymentCard
import domain.Models.PlatformNavigator
import kotlinx.coroutines.launch
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import presentation.components.ButtonComponent
import presentation.consultation.rightTopBarItem
import presentation.widgets.PageBackNavWidget
import presentation.widgets.TitleWidget
import presentations.widgets.InputWidget
import rememberStackedSnackbarHostState
import theme.styles.Colors
import utils.ParcelableScreen


@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class AddDebitCardScreen(val platformNavigator: PlatformNavigator? = null) : KoinComponent,
    ParcelableScreen, ScreenTransition {

    override val key: ScreenKey = uniqueScreenKey

    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }


    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val pattern = remember { Regex("^\\d*\$") }
        val scope = rememberCoroutineScope()

        val rootModifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .verticalScroll(rememberScrollState())
                .background(color = Color.White)


        val topLayoutModifier =
            Modifier
                .padding(top = 20.dp, start = 5.dp, end = 5.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.White)

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 1,
            animation = StackedSnackbarAnimation.Bounce
        )

        val cardNumber = remember { mutableStateOf("") }
        val cvv = remember { mutableStateOf("") }
        val expiryMonth = remember { mutableStateOf("") }
        val expiryYear = remember { mutableStateOf("") }
        val isSavedClicked = remember { mutableStateOf(false) }

        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState)  }
        ) {

            Column(modifier = rootModifier) {
                Column(modifier = topLayoutModifier) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(modifier =  Modifier.weight(1.0f)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                            contentAlignment = Alignment.CenterStart) {
                            leftTopBarItem(onBackPressed = {
                                navigator.pop()
                            })
                        }

                        Box(modifier =  Modifier.weight(3.0f)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                            contentAlignment = Alignment.Center) {
                            PageTitle()
                        }

                        Box(modifier =  Modifier.weight(1.0f)
                            .fillMaxWidth(0.20f)
                            .fillMaxHeight(),
                            contentAlignment = Alignment.Center) {
                            rightTopBarItem()
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
                        placeholderText = "Card Number",
                        iconSize = 28,
                        text = cardNumber.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isPasswordField = false,
                        onSaveClicked = isSavedClicked.value,
                        isSingleLine = true,
                        maxLines = 1,
                        maxLength = 16
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
                            maxLength = 2
                        ) {
                            if (it.matches(pattern) && it.length <= 2) {
                                expiryMonth.value = it
                            }
                        }
                    }
                    Box(modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                        contentAlignment = Alignment.Center) {
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
                            maxLength = 2
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
                        maxLength = 3
                    ) {
                        if (it.matches(pattern) && it.length <= 3) {
                            cvv.value = it
                        }
                    }
                }

                Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                    val buttonStyle = Modifier
                        .padding(end = 10.dp, top = 30.dp, start = 10.dp)
                        .fillMaxWidth()
                        .height(45.dp)

                    ButtonComponent(
                        modifier = buttonStyle,
                        buttonText = "Save",
                        colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                        fontSize = 18,
                        shape = CircleShape,
                        textColor = Color(color = 0xFFFFFFFF),
                        style = TextStyle(),
                        borderStroke = null
                    ) {
                        scope.launch {
                            val paymentDao = databaseBuilder!!.build().getPaymentCardDao()
                            paymentDao.insert(paymentCard = PaymentCard(cardNumber = cardNumber.value, expiryMonth = expiryMonth.value, expiryYear = expiryYear.value, cvv = cvv.value))
                            navigator.pop()
                        }
                    }
                }
            }
        }
    }

    override fun enter(lastEvent: StackEvent): EnterTransition {
        return slideIn { size ->
            val x = if (lastEvent == StackEvent.Pop) -size.width else size.width
            IntOffset(x = x, y = 0)
        }
    }

    override fun exit(lastEvent: StackEvent): ExitTransition {
        return slideOut { size ->
            val x = if (lastEvent == StackEvent.Pop) size.width else -size.width
            IntOffset(x = x, y = 0)
        }
    }

}

@Composable
fun leftTopBarItem(onBackPressed: () -> Unit) {
    PageBackNavWidget {
        onBackPressed()
    }
}



@Composable
fun PageTitle() {
    val rowModifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = rowModifier
    ) {
        TitleWidget(title = "Add Debit Card", textColor = Colors.primaryColor)
    }
}