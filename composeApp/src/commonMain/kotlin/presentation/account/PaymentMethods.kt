package presentation.account

import GGSansBold
import GGSansRegular
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import domain.Enums.SharedPreferenceEnum
import domain.Models.PaymentCard
import domain.Models.PaymentCardUIModel
import domain.Models.PlatformNavigator
import domain.Models.Vendor
import domain.Models.VendorItemUIModel
import domain.Models.getVendorListItemViewHeight
import drawable.ErrorOccurredWidget
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.ConnectPageHandler
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.connectVendor.ConnectVendorPresenter
import presentation.connectVendor.SwitchVendorBusinessItemComponent
import presentation.connectVendor.SwitchVendorDetails
import presentation.dialogs.AddDebitCardDialog
import presentation.viewmodels.ConnectPageViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.VendorsResourceListEnvelopeViewModel
import presentation.widgets.ConnectBusinessDescription
import presentation.widgets.ConnectBusinessTitle
import presentation.widgets.EmptyContentWidget
import presentation.widgets.PaymentCardItem
import presentation.widgets.SearchBar
import presentation.widgets.SwitchVendorBottomSheet
import presentation.widgets.SwitchVendorHeader
import presentation.widgets.TitleWidget
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors
import utils.ParcelableScreen
import utils.getPaymentCardsViewHeight

@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class PaymentMethods(val platformNavigator: PlatformNavigator) : ParcelableScreen, KoinComponent,
    ScreenTransition {

    @Transient
    private val preferenceSettings: Settings = Settings()
    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    override val key: ScreenKey = uniqueScreenKey
    @Transient var cardList = listOf<PaymentCard>()

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        runBlocking {
            cardList = databaseBuilder!!.build().getPaymentCardDao().getAllPaymentCards()
        }

        val paymentMethods = remember { mutableStateOf(cardList) }
        val cardsHeight = getPaymentCardsViewHeight(paymentMethods.value)
        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }

        val openAddDebitCardDialog = remember { mutableStateOf(false) }

        when {
            openAddDebitCardDialog.value -> {
                AddDebitCardDialog(databaseBuilder, onDismissRequest = {
                    openAddDebitCardDialog.value = false
                }, onConfirmation = {
                    runBlocking {
                        cardList = databaseBuilder!!.build().getPaymentCardDao().getAllPaymentCards()
                    }
                    paymentMethods.value = cardList
                    openAddDebitCardDialog.value = false
                })
            }
        }

        Scaffold(
            topBar = {
                Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    val rowModifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)

                    val colModifier = Modifier
                        .padding(top = 20.dp, end = 0.dp)
                        .fillMaxWidth()
                        .height(40.dp)

                    Column(modifier = colModifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(modifier = rowModifier,
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically) {

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
                                TitleWidget(title = "Payment Methods", textColor = Colors.primaryColor)
                            }

                            Box(modifier =  Modifier.weight(1.0f)
                                .fillMaxWidth(0.20f)
                                .fillMaxHeight(),
                                contentAlignment = Alignment.Center) {
                            }
                        }
                    }
                    TextComponent(
                        text = "Securely save your card details for hassle-free payments",
                        fontSize = 16,
                        fontFamily = GGSansRegular,
                        textStyle = TextStyle(),
                        textColor = Color.DarkGray,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 20,
                        textModifier = Modifier.fillMaxWidth().wrapContentHeight().padding(start = 10.dp, end = 10.dp, top = 20.dp))
                }

            },
            content = {
                Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().height(cardsHeight.dp).padding(top = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        userScrollEnabled = true
                    ) {
                        items(paymentMethods.value.size) {
                            Box(
                                modifier = Modifier.fillMaxWidth().height(100.dp)
                                    .padding(start = 20.dp, end = 20.dp)
                                    .background(color = Color.White, shape = RoundedCornerShape(15.dp)),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                PaymentCardItem(paymentMethods.value[it], onPaymentCardSelected = {},
                                    onPaymentCardRemoved = {
                                     runBlocking {
                                       databaseBuilder!!.build().getPaymentCardDao().deletePaymentCardByById(it.id)
                                       cardList = databaseBuilder!!.build().getPaymentCardDao().getAllPaymentCards()
                                     }
                                     paymentMethods.value = cardList
                                    })
                            }
                        }
                    }
                    Row(modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Box(modifier = Modifier.size(20.dp), contentAlignment = Alignment.CenterEnd) {
                                val modifier = Modifier
                                    .padding(top = 2.dp)
                                    .size(18.dp)
                                ImageComponent(
                                    imageModifier = modifier,
                                    imageRes = "drawable/add_icon.png",
                                    colorFilter = ColorFilter.tint(color = Colors.primaryColor)
                                )
                            }
                            Box(modifier = Modifier.wrapContentSize().clickable {
                                   openAddDebitCardDialog.value = true
                            }) {
                                TextComponent(
                                    text = "Add New Card",
                                    fontSize = 16,
                                    fontFamily = GGSansBold,
                                    textStyle = TextStyle(),
                                    textAlign = TextAlign.Left,
                                    fontWeight = FontWeight.ExtraBold,
                                    lineHeight = 20,
                                    textColor = Colors.primaryColor,
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    ButtonComponent(
                        modifier = Modifier
                            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp, top = 4.dp)
                            .fillMaxWidth()
                            .height(50.dp),
                        buttonText = "Done",
                        colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                        fontSize = 16,
                        shape = CircleShape,
                        textColor = Color(color = 0xFFFFFFFF),
                        style = TextStyle(),
                        borderStroke = null
                    ) {

                    }
                }
            },
            backgroundColor = Color.White,
        )

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