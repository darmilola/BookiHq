package presentation.profile

import StackedSnackbarHost
import UIStates.AppUIStates
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import domain.Enums.CustomerMovementEnum
import domain.Models.PlatformNavigator
import domain.Models.Vendor
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.ProfileHandler
import presentation.Screens.SplashScreen
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.widgets.BusinessInfoContent
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentation.widgets.VendorDetailsTitle
import presentations.components.ImageComponent
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.Colors
import utils.ParcelableScreen

@Parcelize
@OptIn(ExperimentalVoyagerApi::class)
class JoinSpaDetails(val platformNavigator: PlatformNavigator) : ParcelableScreen, KoinComponent,
    ScreenTransition {
    @Transient
    private var performedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient
    private val profilePresenter: ProfilePresenter by inject()
    @Transient
    private val preferenceSettings: Settings = Settings()
    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    override val key: ScreenKey = uniqueScreenKey

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        if (performedActionUIStateViewModel == null) {
            performedActionUIStateViewModel= kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        val profileHandler = ProfileHandler(profilePresenter,
            onUserLocationReady = {},
            onVendorInfoReady = { it -> },
            onUserProfileDeleted = {},
            performedActionUIStateViewModel!!)
        profileHandler.init()

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }


        val userInfo = mainViewModel!!.currentUserInfo.value
        val joinSpaUiState = performedActionUIStateViewModel!!.uiStateInfo.collectAsState()


        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )
        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                VendorDetailsTitle(onBackPressed = {
                    mainViewModel!!.setOnBackPressed(true)
                })
            },
            backgroundColor = Colors.dashboardBackground,
            content = {
                val joinVendor = mainViewModel!!.joinSpaVendor.value
                if (joinSpaUiState.value.isLoading) {
                    LoadingDialog("Joining Business")
                } else if (joinSpaUiState.value.isSuccess) {
                    performedActionUIStateViewModel!!.switchActionUIState(AppUIStates(isDefault = true))
                    val splashScreen = SplashScreen(platformNavigator)
                    splashScreen.setDatabaseBuilder(databaseBuilder!!)
                    navigator.replaceAll(splashScreen)

                } else if (joinSpaUiState.value.isFailed) {
                    ErrorDialog(dialogTitle = joinSpaUiState.value.errorMessage, actionTitle = "Retry"){}
                }

                BusinessInfoContent(joinVendor)
            },
            floatingActionButton = {
                val joinVendor = mainViewModel!!.joinSpaVendor.value
                FloatingActionButton(
                    modifier = Modifier.wrapContentSize(),
                    contentColor = Colors.darkPrimary,
                    containerColor = Colors.darkPrimary,
                    shape = RoundedCornerShape(15.dp),
                    onClick = {
                        profilePresenter.joinSpa(therapistId = userInfo.userId!!, vendorId = joinVendor.vendorId!!)
                    }) {
                    floatingButton()
                }
            }
        )
    }

    @Composable
    fun floatingButton(){
        Row(
            modifier = Modifier.height(45.dp).width(150.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
        ) {

            TextComponent(
                textModifier = Modifier.wrapContentWidth()
                    .padding(start = 5.dp),
                text = "Join",
                fontSize = 16,
                textStyle = MaterialTheme.typography.titleMedium,
                textColor = Color.White,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 23,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            val modifier = Modifier
                .padding(start = 10.dp)
                .size(30.dp)
            ImageComponent(imageModifier = modifier, imageRes = "drawable/forward_arrow.png", colorFilter = ColorFilter.tint(color = Color.White))



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
