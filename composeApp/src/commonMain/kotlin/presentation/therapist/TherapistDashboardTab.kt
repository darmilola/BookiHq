package presentation.therapist

import GGSansSemiBold
import StackedSnackbarHost
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Models.Appointment
import domain.Models.Product
import domain.Models.ProductCategory
import domain.Models.ServiceTime
import domain.Models.TimeOffs
import domain.Models.User
import domain.Models.Vendor
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.Products.CategoryScreen
import presentation.Products.ProductPresenter
import presentation.Products.SearchBar
import presentation.appointments.AppointmentPresenter
import presentation.components.IndeterminateCircularProgressBar
import presentation.main.SearchContent
import presentation.viewmodels.AsyncUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PostponementViewModel
import presentation.viewmodels.ResourceListEnvelopeViewModel
import presentation.viewmodels.TherapistViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.viewmodels.UIStates
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.styles.Colors

class TherapistDashboardTab(private val mainViewModel: MainViewModel) : Tab, KoinComponent {

    private val appointmentPresenter: AppointmentPresenter by inject()
    private val therapistPresenter: TherapistPresenter by inject()
    private var uiStateViewModel: UIStateViewModel? = null
    private var asyncUiStateViewModel: AsyncUIStateViewModel? = null
    private var therapistViewModel: TherapistViewModel? = null
    private var postponementViewModel: PostponementViewModel? = null
    private var appointmentResourceListEnvelopeViewModel: ResourceListEnvelopeViewModel<Appointment>? = null

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Therapist Dashboard"
            val icon = painterResource("drawable/dashboard_icon.png")

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon

                )
            }
        }

    @Composable
    override fun Content() {

        if (uiStateViewModel == null) {
            uiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    UIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )

        }

        if (asyncUiStateViewModel == null) {
            asyncUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    AsyncUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )

        }

        if (postponementViewModel == null) {
            postponementViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PostponementViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (therapistViewModel == null) {
            therapistViewModel = kmpViewModel(
                factory = viewModelFactory {
                    TherapistViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        if (appointmentResourceListEnvelopeViewModel == null) {
            appointmentResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                })
        }

        val handler = TherapistHandler(therapistPresenter,
            onReviewsReady = {
                therapistViewModel!!.setTherapistReviews(it)
             },
            onErrorVisible = {
                uiStateViewModel!!.switchState(UIStates(errorOccurred = true))
            },
            onContentVisible = {
                uiStateViewModel!!.switchState(UIStates(contentVisible = true))
            }, onPageLoading = {
                uiStateViewModel!!.switchState(UIStates(loadingVisible = true))
            }, onTherapistAvailabilityReady = {
                 availableTimes: List<ServiceTime>,
                    bookedTimes: List<Appointment>,
                    timeOffs: List<TimeOffs> ->
                therapistViewModel!!.setTherapistAvailableTimes(availableTimes)
                therapistViewModel!!.setTherapistTimeOffs(timeOffs)
            },
            onAsyncLoading = {
                asyncUiStateViewModel!!.switchState(UIStates(loadingVisible = true))
            },
            onAsyncSuccess = {
                asyncUiStateViewModel!!.switchState(UIStates(contentVisible = true))
            },
            onAsyncFailed = {
                asyncUiStateViewModel!!.switchState(UIStates(errorOccurred = true))
            })
        handler.init()


        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )
        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                TherapistDashboardTopBar(mainViewModel)
            },
            content = {
                 TabScreen()
            },
            backgroundColor = Color.White,
            floatingActionButton = {}
        )
    }


    @Composable
    fun TabScreen() {
        val tabItems: ArrayList<String> = arrayListOf()
        tabItems.add("Appointments")
        tabItems.add("Availability")
        tabItems.add("Reviews")
        tabItems.add("Vendors")
        var tabIndex by remember { mutableStateOf(0) }
        Column(modifier = Modifier.fillMaxSize()) {
            ScrollableTabRow(selectedTabIndex = tabIndex,
                modifier = Modifier.height(40.dp),
                backgroundColor = Color.Transparent,
                indicator = { tabPositions ->
                    Box(modifier = Modifier
                            .tabIndicatorOffset(tabPositions[tabIndex])
                            .height(3.dp)
                            .padding(start = 30.dp, end = 30.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(color = Colors.primaryColor))
                     },
                divider = {}) {
                tabItems.forEachIndexed { index, title ->
                    Tab(text =
                    {
                        TextComponent(
                            text = tabItems[index],
                            fontSize = 16,
                            fontFamily = GGSansSemiBold,
                            textStyle = MaterialTheme.typography.h6,
                            textColor = Colors.darkPrimary,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 30
                        )
                    }, selected = tabIndex == index,
                        onClick = {
                            tabIndex = index
                        }

                    )
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(bottom = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                when(tabIndex){
                    0 -> TherapistAppointment(mainViewModel, uiStateViewModel!!, postponementViewModel!!, appointmentResourceListEnvelopeViewModel, appointmentPresenter)
                    1 -> TherapistAvailability(mainViewModel, therapistPresenter, therapistViewModel!!, uiStateViewModel!!, asyncUiStateViewModel!!)
                    2 -> TherapistReviews(mainViewModel, therapistPresenter, therapistViewModel!!, uiStateViewModel!!)
                    3 -> TherapistVendors()
                }

            }
        }
    }


}