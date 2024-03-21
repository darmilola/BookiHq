package presentation.appointments

import androidx.compose.foundation.BorderStroke
import domain.Models.AppointmentItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Models.Appointment
import domain.Models.AppointmentItemUIModel
import domain.Models.Product
import domain.Models.ProductCategory
import domain.Models.ProductItemUIModel
import domain.Models.ResourceListEnvelope
import domain.Models.User
import domain.Models.Vendor
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.Products.ProductContract
import presentation.Products.ProductPresenter
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.main.ShopCategoryHandler
import presentation.main.ShopProductsHandler
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.ProductViewModel
import presentation.viewmodels.ResourceListEnvelopeViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.viewmodels.UIStates
import utils.getAppointmentViewHeight
import presentation.widgets.AppointmentWidget
import presentation.widgets.NewAppointmentWidget
import theme.Colors

class AppointmentsTab(private val mainViewModel: MainViewModel) : Tab, KoinComponent {

    private val appointmentPresenter: AppointmentPresenter by inject()
    private var uiStateViewModel: UIStateViewModel? = null
    private var currentUser: User? = null
    private var currentVendor: Vendor? = null
    private var appointmentResourceListEnvelopeViewModel: ResourceListEnvelopeViewModel<Appointment>? =
        null


    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Bookings"
            val icon = painterResource("calender_icon_semi.png")

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

        currentUser = mainViewModel.currentUserInfo.value
        currentVendor = mainViewModel.connectedVendor.value

        if (uiStateViewModel == null) {
            uiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    UIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )

        }


        if (appointmentResourceListEnvelopeViewModel == null) {
            appointmentResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                })
            appointmentPresenter.getUserAppointments(currentUser?.userId!!)
        }

        val handler = AppointmentsHandler(
            appointmentResourceListEnvelopeViewModel!!,
            uiStateViewModel!!, appointmentPresenter
        )
        handler.init()


        val loadMoreState = appointmentResourceListEnvelopeViewModel?.isLoadingMore?.collectAsState()
        val appointmentList = appointmentResourceListEnvelopeViewModel?.resources?.collectAsState()
        val totalAppointmentsCount = appointmentResourceListEnvelopeViewModel?.totalItemCount?.collectAsState()
        val displayedAppointmentsCount = appointmentResourceListEnvelopeViewModel?.displayedItemCount?.collectAsState()
        val uiState = uiStateViewModel!!.uiData.collectAsState()
        val lastIndex = appointmentList?.value?.size?.minus(1)
        val selectedAppointment = remember { mutableStateOf(Appointment()) }


        var appointmentUIModel by remember { mutableStateOf(AppointmentItemUIModel(selectedAppointment.value, appointmentList?.value!!)) }

        if(!loadMoreState?.value!!) {
            appointmentUIModel = appointmentUIModel.copy(selectedAppointment = selectedAppointment.value,
                appointmentList = appointmentResourceListEnvelopeViewModel!!.resources.value.map { it2 ->
                    it2.copy(
                        isSelected = it2.appointmentId == selectedAppointment.value.appointmentId
                    )
                })
        }


        if (uiState.value.loadingVisible) {
            //Content Loading
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                IndeterminateCircularProgressBar()
            }
        } else if (uiState.value.errorOccurred) {

            //Error Occurred display reload

        } else if (uiState.value.contentVisible) {

            val columnModifier = Modifier
                .padding(top = 5.dp)
                .fillMaxHeight()
                .fillMaxWidth()

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = columnModifier
            ) {
                Column(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = Color.White)
                ) {

                    LazyColumn(modifier = Modifier.fillMaxWidth().height(getAppointmentViewHeight(appointmentUIModel.appointmentList).dp), userScrollEnabled = true) {
                        items(appointmentUIModel.appointmentList.size) {it ->
                            NewAppointmentWidget(appointmentUIModel.appointmentList[it])
                            if (it == lastIndex && loadMoreState.value) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().height(60.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    IndeterminateCircularProgressBar()
                                }
                            }
                            else if (it == lastIndex && (displayedAppointmentsCount?.value!! < totalAppointmentsCount?.value!!)) {
                                val buttonStyle = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)

                                ButtonComponent(
                                    modifier = buttonStyle,
                                    buttonText = "Show More",
                                    borderStroke = BorderStroke(1.dp, Colors.primaryColor),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                                    fontSize = 16,
                                    shape = CircleShape,
                                    textColor = Colors.primaryColor,
                                    style = TextStyle()
                                ) {
                                    if (appointmentResourceListEnvelopeViewModel!!.nextPageUrl.value.isNotEmpty()) {
                                        appointmentPresenter.getMoreAppointments(currentUser?.userId!!,
                                            nextPage = appointmentResourceListEnvelopeViewModel!!.currentPage.value + 1)
                                    }
                                }
                            }
                        }

                    }

                }

            }
        }
    }
}


class AppointmentsHandler(
    private val appointmentResourceListEnvelopeViewModel: ResourceListEnvelopeViewModel<Appointment>,
    private val uiStateViewModel: UIStateViewModel,
    private val appointmentPresenter: AppointmentPresenter,
) : AppointmentContract.View {
    fun init() {
        appointmentPresenter.registerUIContract(this)
    }

    override fun showLce(uiState: UIStates, message: String) {
        uiStateViewModel.switchState(uiState)
    }

    override fun showAppointments(appointments: ResourceListEnvelope<Appointment>?) {
        if (appointmentResourceListEnvelopeViewModel.resources.value.isNotEmpty()) {
            val appointmentList = appointmentResourceListEnvelopeViewModel.resources.value
            appointmentList.addAll(appointments?.resources!!)
            appointmentResourceListEnvelopeViewModel.setResources(appointmentList)
            appointments.prevPageUrl?.let { appointmentResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            appointments.nextPageUrl?.let { appointmentResourceListEnvelopeViewModel.setNextPageUrl(it) }
            appointments.currentPage?.let { appointmentResourceListEnvelopeViewModel.setCurrentPage(it) }
            appointments.totalItemCount?.let { appointmentResourceListEnvelopeViewModel.setTotalItemCount(it) }
            appointments.displayedItemCount?.let { appointmentResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        } else {
            appointmentResourceListEnvelopeViewModel.setResources(appointments?.resources)
            appointments?.prevPageUrl?.let { appointmentResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            appointments?.nextPageUrl?.let { appointmentResourceListEnvelopeViewModel.setNextPageUrl(it) }
            appointments?.currentPage?.let { appointmentResourceListEnvelopeViewModel.setCurrentPage(it) }
            appointments?.totalItemCount?.let { appointmentResourceListEnvelopeViewModel.setTotalItemCount(it) }
            appointments?.displayedItemCount?.let { appointmentResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
    }

    override fun onLoadMoreAppointmentStarted(isSuccess: Boolean) {
        appointmentResourceListEnvelopeViewModel.setLoadingMore(true)
    }

    override fun onLoadMoreAppointmentEnded(isSuccess: Boolean) {
        appointmentResourceListEnvelopeViewModel.setLoadingMore(false)
    }

}