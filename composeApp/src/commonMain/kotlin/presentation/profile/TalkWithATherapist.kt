package presentation.profile

import GGSansSemiBold
import StackedSnackbarHost
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import dev.jordond.compass.Place
import domain.Models.PlatformNavigator
import domain.Models.Screens
import domain.Models.VendorTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.BookingCalendar
import presentation.widgets.MultilineInputWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentation.widgets.TimeGrid
import presentation.widgets.TitleWidget
import presentation.widgets.VendorTimeGrid
import presentations.components.TextComponent
import presentations.widgets.InputWidget
import rememberStackedSnackbarHostState
import theme.styles.Colors

class TalkWithATherapist(private val mainViewModel: MainViewModel) : Tab,
    KoinComponent {
    private val profilePresenter: ProfilePresenter by inject()
    private var uiStateViewModel: UIStateViewModel? = null
    override val options: TabOptions
        @Composable
        get() {
            val title = "Talk With A Therapist"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title
                )
            }
        }

    @Composable
    override fun Content() {

        val isLoading = remember { mutableStateOf(false) }
        val isDone = remember { mutableStateOf(false) }
        val isSuccess = remember { mutableStateOf(false) }
        val availableTimes = remember { mutableStateOf(listOf<VendorTime>()) }


        val handler = TalkWithTherapistHandler(profilePresenter,
            isLoading = {
                isLoading.value = true
                isSuccess.value = false
                isDone.value = false
            }, isDone = {
                isLoading.value = false
                isDone.value = true
                isSuccess.value = true
            }, isSuccess = {
                isLoading.value = false
                isSuccess.value = true
                isDone.value = true
            },
            onAvailableTimesReady = {
                availableTimes.value = it
            })
        handler.init()


        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 1,
            animation = StackedSnackbarAnimation.Bounce
        )

        profilePresenter.getVendorAvailability(mainViewModel.vendorId.value)

        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                Box(modifier = Modifier.fillMaxWidth().height(60.dp)) {
                    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(start = 10.dp), contentAlignment = Alignment.CenterStart) {
                        AttachBackIcon(mainViewModel)
                    }
                    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(), contentAlignment = Alignment.Center) {
                        PageTitle()
                    }
                }
            },
            content = {

                if (isLoading.value) {
                    //Content Loading
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        IndeterminateCircularProgressBar()
                    }
                } else if (isDone.value && !isSuccess.value) {

                    //Error Occurred display reload

                } else if (isDone.value && isSuccess.value) {

                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(top = 70.dp, start = 10.dp, end = 10.dp)
                    ) {

                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                        ) {

                            TextComponent(
                                text = "Select Date",
                                fontSize = 16,
                                fontFamily = GGSansSemiBold,
                                textStyle = TextStyle(),
                                textColor = Colors.darkPrimary,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Black,
                                lineHeight = 30,
                                textModifier = Modifier
                                    .fillMaxWidth().padding(start = 10.dp)
                            )

                            BookingCalendar(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 10.dp)) {}
                        }


                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 40.dp, start = 10.dp, end = 10.dp)
                        ) {

                            TextComponent(
                                text = "Select Time",
                                fontSize = 16,
                                fontFamily = GGSansSemiBold,
                                textStyle = TextStyle(),
                                textColor = Colors.darkPrimary,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Black,
                                lineHeight = 30,
                                textModifier = Modifier
                                    .fillMaxWidth().padding(start = 10.dp)
                            )

                            VendorTimeGrid(availableTimes.value, onWorkHourClickListener = {})
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                        ) {

                            TextComponent(
                                text = "Reason for consultation?",
                                fontSize = 16,
                                fontFamily = GGSansSemiBold,
                                textStyle = TextStyle(),
                                textColor = Colors.darkPrimary,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Black,
                                lineHeight = 30,
                                textModifier = Modifier
                                    .fillMaxWidth().padding(start = 10.dp)
                            )

                            MultilineInputWidget(viewHeight = 200){}
                        }
                    }
                }


            },
            bottomBar = {
                val buttonStyle = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth()
                    .height(50.dp)

                ButtonComponent(
                    modifier = buttonStyle,
                    buttonText = "Proceed",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                    fontSize = 18,
                    shape = CircleShape,
                    textColor = Color(color = 0xFFFFFFFF),
                    style = TextStyle(),
                    borderStroke = null
                ) {

                }
            })
    }

    @Composable
    private fun AttachBackIcon(mainViewModel: MainViewModel) {
        PageBackNavWidget {
            when (mainViewModel.screenNav.value.first) {
                Screens.MAIN_TAB.toPath() -> {
                    mainViewModel.setScreenNav(Pair(Screens.TALK_WITH_A_THERAPIST.toPath(), Screens.MAIN_TAB.toPath()))
                }
            }
        }
    }



    @Composable
    fun PageTitle(){
        val rowModifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {
            TitleWidget(title = "Talk With A Therapist", textColor = Colors.primaryColor)
        }
    }

}

class TalkWithTherapistHandler(
    private val profilePresenter: ProfilePresenter,
    private val onAvailableTimesReady:(List<VendorTime>)-> Unit,
    private val isLoading: () -> Unit,
    private val isDone: () -> Unit,
    private val isSuccess: () -> Unit,
) : ProfileContract.VideoView {
    fun init() {
        profilePresenter.registerTalkWithTherapistContract(this)
    }

    override fun showLce(asyncUIStates: AsyncUIStates, message: String) {
        asyncUIStates.let {
            when{
                it.isLoading -> {
                    isLoading()
                }

                it.isDone -> {
                    isDone()
                }

                it.isSuccess -> {
                    isSuccess()
                }
            }
        }
    }

    override fun showAvailability(availableTimes: List<VendorTime>) {
        onAvailableTimesReady(availableTimes)
    }
}


