package presentation.therapist

import GGSansSemiBold
import StackedSnackbarHost
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
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
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import dev.icerock.moko.parcelize.Parcelize
import domain.Enums.SharedPreferenceEnum
import domain.Models.PlatformNavigator
import domain.Models.TherapistInfo
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.Screens.SplashScreen
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.TherapistAppointmentResourceListEnvelopeViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.styles.Colors
import utils.ParcelableScreen

@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class TherapistDashboard(val platformNavigator: PlatformNavigator? = null) : ParcelableScreen, KoinComponent, ScreenTransition{

    @Transient
    private val therapistPresenter: TherapistPresenter by inject()
    @Transient
    private var loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null
    @Transient
    private var performedActionUiStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient
    private var appointmentResourceListEnvelopeViewModel: TherapistAppointmentResourceListEnvelopeViewModel? = null
    @Transient
    private var availabilityPerformedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    private var therapistInfo: TherapistInfo? = null
    val preferenceSettings = Settings()
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    override val key: ScreenKey = uniqueScreenKey

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    fun setTherapistInfo(therapistInfo: TherapistInfo){
        this.therapistInfo = therapistInfo
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        val userId = preferenceSettings[SharedPreferenceEnum.USER_ID.toPath(),-1L]

        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }

        if (loadingScreenUiStateViewModel == null) {
            loadingScreenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    LoadingScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        if (availabilityPerformedActionUIStateViewModel == null) {
            availabilityPerformedActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        if (loadingScreenUiStateViewModel == null) {
            loadingScreenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    LoadingScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )

        }

        if (performedActionUiStateViewModel == null) {
            performedActionUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )

        }


        if (appointmentResourceListEnvelopeViewModel == null) {
            appointmentResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    TherapistAppointmentResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                })
        }




        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce)


        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                TherapistDashboardTopBar(onBackPressed = {
                    navigator.pop()
                })
            },
            content = {
                 TabScreen(onUpdateSuccess = {
                     val splashScreen = SplashScreen(platformNavigator!!)
                     splashScreen.setDatabaseBuilder(databaseBuilder!!)
                     navigator.replaceAll(splashScreen)
                 })
            },
            backgroundColor = theme.Colors.dashboardBackground,
            floatingActionButton = {}
        )
    }


    @Composable
    fun TabScreen(onUpdateSuccess: () -> Unit) {
        val tabItems: ArrayList<String> = arrayListOf()
        tabItems.add("Appointments")
        tabItems.add("Profile")
        var tabIndex by remember { mutableStateOf(0) }
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                ScrollableTabRow(selectedTabIndex = tabIndex,
                    modifier = Modifier.height(40.dp).fillMaxWidth(0.80f),
                    backgroundColor = Color.Transparent,
                    indicator = { tabPositions ->
                        Box(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[tabIndex])
                                .height(3.dp)
                                .padding(start = 30.dp, end = 30.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(color = Colors.primaryColor)
                        )
                    },
                    divider = {}) {
                    tabItems.forEachIndexed { index, title ->
                        Tab(text =
                        {
                            TextComponent(
                                text = tabItems[index],
                                fontSize = 16,
                                textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge,
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
            }
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(bottom = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                when(tabIndex){
                    0 -> TherapistAppointment(mainViewModel!!, loadingScreenUiStateViewModel!!, appointmentResourceListEnvelopeViewModel, therapistPresenter,therapistInfo!!,performedActionUiStateViewModel!!)
                    1 -> TherapistProfile(therapistInfo!!, therapistPresenter, performedActionUiStateViewModel!!,loadingScreenUiStateViewModel!!,onUpdateSuccess = {
                          onUpdateSuccess()
                       })
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