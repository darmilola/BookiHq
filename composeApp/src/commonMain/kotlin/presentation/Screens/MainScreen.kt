package presentation.Screens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.russhwolf.settings.Settings
import dev.icerock.moko.parcelize.Parcelize
import domain.Enums.Screens
import domain.Models.PlatformNavigator
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.Orders.Orders
import presentation.Products.CartTab
import presentation.account.JoinASpa
import presentation.authentication.AuthenticationPresenter
import presentation.bookings.BookingScreenTab
import presentation.bookings.PendingAppointmentsTab
import presentation.connectVendor.ConnectVendorTab
import presentation.connectVendor.SwitchVendorDetailsTab
import presentation.consultation.ConsultationScreenTab
import presentation.consultation.VirtualConsultationRoom
import presentation.home.ViewConnectedVendorDetailsTab
import presentation.main.MainTab
import presentation.profile.EditProfileTab
import presentation.profile.JoinDetailsTab
import presentation.profile.TalkWithATherapist
import presentation.therapist.TherapistDashboardTab
import presentation.viewmodels.MainViewModel
import presentation.widgets.PageBackNavWidget
import utils.ParcelableScreen

@Parcelize
class MainScreen(val platformNavigator: PlatformNavigator) : ParcelableScreen, KoinComponent {

     @Transient private var mainViewModel: MainViewModel? = null
     @Transient private val preferenceSettings: Settings = Settings()
     private var userId: Long = 0L
     private var mainTab: MainTab? = null
     private var joinASpa: JoinASpa? = null
     @Transient
     private val  authenticationPresenter: AuthenticationPresenter by inject()
     @Transient
     private var screenNav: State<Pair<Int, Int>>? = null

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    override val key: ScreenKey
        get() = "mainScreen"

    @Composable
    override fun Content() {
        userId = preferenceSettings.getLong("profileId", 0L)

        platformNavigator!!.startNotificationService {
            println("Token is here $it")
            authenticationPresenter.updateFcmToken(userId = userId, fcmToken = it)
        }


        screenNav = mainViewModel?.screenNav?.collectAsState()
        val restartApp = mainViewModel!!.restartApp.collectAsState()
        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()

        if (restartApp.value) {
            mainViewModel!!.setRestartApp(false)
            val navigator = LocalNavigator.currentOrThrow
            navigator.replaceAll(SplashScreen(platformNavigator, mainViewModel!!))
        }


        if (onBackPressed.value) {
              mainViewModel!!.setOnBackPressed(false)
              when (screenNav?.value?.second) {
                 Screens.BOOKING.toPath() -> {
                     mainViewModel!!.setScreenNav(Pair(Screens.BOOKING.toPath(), Screens.MAIN_TAB.toPath()))
                }
                Screens.CART.toPath() -> {
                    mainViewModel!!.setScreenNav(Pair(Screens.CART.toPath(), Screens.MAIN_TAB.toPath()))
                }

                Screens.CONNECT_VENDOR_TAB.toPath() -> {
                    mainViewModel!!.setScreenNav(Pair(Screens.CONNECT_VENDOR_TAB.toPath(), Screens.MAIN_TAB.toPath()))
                }

                Screens.EDIT_PROFILE.toPath() -> {
                    when (mainViewModel?.screenNav?.value?.first) {
                Screens.MAIN_TAB.toPath() -> {
                mainViewModel!!.setScreenNav(
                    Pair(
                        Screens.EDIT_PROFILE.toPath(),
                        Screens.MAIN_TAB.toPath()
                    )
                )
            }

            Screens.CART.toPath() -> {
                mainViewModel!!.setScreenNav(
                    Pair(
                        Screens.EDIT_PROFILE.toPath(),
                        Screens.CART.toPath()
                    )
                )
            }

                        Screens.BOOKING.toPath() -> {
                            mainViewModel!!.setScreenNav(
                                Pair(
                                    Screens.EDIT_PROFILE.toPath(),
                                    Screens.BOOKING.toPath()
                                )
                            )
                        }

            else -> {}
        }
                }

                  Screens.ORDERS.toPath() -> {
                      mainViewModel!!.setScreenNav(Pair(Screens.ORDERS.toPath(), Screens.MAIN_TAB.toPath()))
                  }

                  Screens.ORDER_DETAILS.toPath() -> {
                      mainViewModel!!.setScreenNav(Pair(Screens.ORDER_DETAILS.toPath(), Screens.ORDERS.toPath()))
                  }

                  Screens.THERAPIST_DASHBOARD.toPath() -> {
                   mainViewModel!!.setScreenNav(Pair(Screens.THERAPIST_DASHBOARD.toPath(), Screens.MAIN_TAB.toPath()))
                }

                Screens.JOIN_SPA.toPath() -> {
                    mainViewModel!!.setScreenNav(Pair(Screens.JOIN_SPA.toPath(), Screens.MAIN_TAB.toPath()))
                }

                Screens.TALK_WITH_A_THERAPIST.toPath() -> {
                    mainViewModel!!.setScreenNav(Pair(Screens.TALK_WITH_A_THERAPIST.toPath(), Screens.MAIN_TAB.toPath()))
                }

                Screens.CONNECTED_VENDOR_DETAILS.toPath() -> {
                    when (screenNav?.value?.first) {
                        Screens.MAIN_TAB.toPath() -> {
                            mainViewModel!!.setScreenNav(Pair(Screens.CONNECTED_VENDOR_DETAILS.toPath(), Screens.MAIN_TAB.toPath()))
                        }
                        else -> {}
                    }
                  }
                  Screens.VENDOR_INFO.toPath() -> {
                      when (screenNav?.value?.first) {
                          Screens.CART.toPath() -> {
                              mainViewModel!!.setScreenNav(Pair(Screens.VENDOR_INFO.toPath(), Screens.CART.toPath()))
                          }
                          Screens.JOIN_SPA.toPath() -> {
                              mainViewModel!!.setScreenNav(Pair(Screens.VENDOR_INFO.toPath(), Screens.JOIN_SPA.toPath()))
                          }
                          Screens.CONNECT_VENDOR_TAB.toPath() -> {
                              mainViewModel!!.setScreenNav(Pair(Screens.VENDOR_INFO.toPath(), Screens.CONNECT_VENDOR_TAB.toPath()))
                          }
                          else -> {}
                      }
                  }

                Screens.JOIN_SPA_INFO.toPath() -> {
                    mainViewModel!!.setScreenNav(Pair(Screens.JOIN_SPA_INFO.toPath(), Screens.JOIN_SPA.toPath()))
                }
            }

    }

        TabNavigator(showDefaultTab(mainViewModel!!)) {
            when (screenNav?.value?.second) {
                Screens.MAIN_TAB.toPath() -> {
                    mainTab =  MainTab(platformNavigator)
                    mainTab!!.setMainViewModel(mainViewModel!!)
                    it.current = mainTab!!
                }
                Screens.BOOKING.toPath() -> {
                    val bookingTab = BookingScreenTab(platformNavigator)
                    bookingTab.setMainViewModel(mainViewModel!!)
                    it.current = bookingTab
                }
                Screens.CONSULTATION.toPath() -> {
                    val consultTab = ConsultationScreenTab()
                    consultTab.setMainViewModel(mainViewModel!!)
                    it.current = consultTab
                }
                Screens.CART.toPath() -> {
                    val cart = CartTab(platformNavigator!!)
                    cart.setMainViewModel(mainViewModel!!)
                    it.current = cart
                }
                Screens.ORDERS.toPath() -> {
                    val orders = Orders()
                    orders.setMainViewModel(mainViewModel!!)
                    it.current = orders
                }
                Screens.CONNECT_VENDOR_TAB.toPath() -> {
                    val connectVendorTab = ConnectVendorTab(platformNavigator)
                    connectVendorTab.setMainViewModel(mainViewModel!!)
                    it.current = connectVendorTab
                }
                Screens.CONSULTATION_ROOM.toPath() -> {
                    val consultation = VirtualConsultationRoom()
                    consultation.setMainViewModel(mainViewModel!!)
                    it.current = consultation
                }
                Screens.EDIT_PROFILE.toPath() -> {
                    val editProfileTab = EditProfileTab(platformNavigator)
                    editProfileTab.setMainViewModel(mainViewModel!!)
                    it.current = editProfileTab
                }
                Screens.VENDOR_INFO.toPath() -> {
                    val switchVendor = SwitchVendorDetailsTab(platformNavigator)
                    switchVendor.setMainViewModel(mainViewModel!!)
                    it.current = switchVendor
                }
                Screens.PENDING_APPOINTMENT.toPath() -> {
                    val pendingAppointmentsTab = PendingAppointmentsTab()
                    pendingAppointmentsTab.setMainViewModel(mainViewModel!!)
                    it.current = pendingAppointmentsTab
                }
                Screens.THERAPIST_DASHBOARD.toPath() -> {
                    val dashboard = TherapistDashboardTab()
                    dashboard.setMainViewModel(mainViewModel!!)
                    it.current = dashboard
                }
                Screens.JOIN_SPA.toPath() -> {
                    joinASpa = JoinASpa(platformNavigator!!)
                    joinASpa!!.setMainViewModel(mainViewModel!!)
                    it.current = joinASpa!!
                }
                Screens.TALK_WITH_A_THERAPIST.toPath() -> {
                    val talkWithTherapist = TalkWithATherapist(platformNavigator!!)
                    talkWithTherapist.setMainViewModel(mainViewModel!!)
                    it.current = talkWithTherapist
                }
                Screens.CONNECTED_VENDOR_DETAILS.toPath() -> {
                    val details = ViewConnectedVendorDetailsTab()
                    details.setMainViewModel(mainViewModel!!)
                    it.current = details
                }
                Screens.JOIN_SPA_INFO.toPath() -> {
                    val details = JoinDetailsTab(platformNavigator)
                    details.setMainViewModel(mainViewModel!!)
                    it.current = details
                }
            }

            Scaffold(
                content = { CurrentTab() })
        }
    }

    private fun showDefaultTab(mainViewModel: MainViewModel): MainTab {
        mainTab =  MainTab(platformNavigator!!)
        mainTab!!.setMainViewModel(mainViewModel)
        return mainTab!!
    }


}
