package presentation.welcomeScreen

import GGSansBold
import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import di.initKoin
import domain.Enums.AuthType
import domain.Enums.SharedPreferenceEnum
import domain.Enums.getDisplayCurrency
import domain.Models.PlatformNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.AuthenticationScreenHandler
import presentation.Screens.MainScreen
import presentation.Screens.SplashScreenCompose
import presentation.Screens.WelcomeScreen
import presentation.authentication.AuthenticationPresenter
import presentation.components.ButtonComponent
import presentation.components.SplashScreenBackground
import presentation.connectVendor.ConnectVendor
import presentation.viewmodels.MainViewModel
import presentation.widgets.AttachTextContent
import presentation.widgets.SplashScreenWidget
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors

@Parcelize
class LandingScreen() : Screen, KoinComponent, Parcelable {

    @Transient
    private val authenticationPresenter: AuthenticationPresenter by inject()
    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    override val key: ScreenKey = uniqueScreenKey

    fun setDatabaseBuilder(builder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = builder
    }

    fun setMainViewModel(mainViewModel: MainViewModel? = null){
        this.mainViewModel = mainViewModel
    }

    @Composable
    override fun Content() {
        LandingScreenCompose()
    }

    @Composable
    fun LandingScreenCompose() {
        Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
            ImageComponent(
                imageModifier = Modifier.fillMaxHeight().fillMaxWidth(),
                imageRes = "drawable/landing_image.jpg", contentScale = ContentScale.FillBounds
            )

            Box(modifier = Modifier.fillMaxSize().background(color = Colors.transparentPrimaryColor)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp, bottom = 50.dp, start = 20.dp, end = 20.dp)
                        .border(
                            border = BorderStroke(1.dp, Color(0XFFA9D8E3)),
                            shape = RoundedCornerShape(15.dp)
                        )
                ) {

                    Box(
                        modifier = Modifier.fillMaxHeight(0.50f).fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {

                        ImageComponent(
                            imageModifier = Modifier.width(170.dp).height(80.dp),
                            imageRes = "drawable/carevida.png",
                            colorFilter = ColorFilter.tint(color = Color.White)
                        )


                    }

                    Box(
                        modifier = Modifier.fillMaxHeight(0.40f).fillMaxWidth()
                            .padding(start = 50.dp, end = 100.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {

                        TextComponent(
                            text = "Easily book your services with your trusted salon and spa provider",
                            fontSize = 23,
                            fontFamily = GGSansSemiBold,
                            textStyle = TextStyle(),
                            textColor = Color.White,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 30
                        )

                    }

                    Row(
                        modifier = Modifier.fillMaxHeight().fillMaxWidth()
                            .padding(bottom = 40.dp, start = 20.dp, end = 20.dp),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Start
                    ) {

                        val buttonStyle = Modifier
                            .padding(top = 30.dp, start = 20.dp, end = 20.dp)
                            .fillMaxWidth(0.70f)
                            .height(50.dp)

                        ButtonComponent(
                            modifier = buttonStyle,
                            buttonText = "Next",
                            borderStroke = null,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            fontSize = 18,
                            shape = RoundedCornerShape(15.dp),
                            textColor = Color.Black,
                            style = MaterialTheme.typography.h4
                        ) {}

                        Box(
                            modifier = Modifier
                                .padding(top = 30.dp, start = 20.dp, end = 20.dp)
                                .fillMaxWidth()
                                .height(50.dp), contentAlignment = Alignment.Center
                        ) {

                            ImageComponent(
                                imageModifier = Modifier.size(30.dp),
                                imageRes = "drawable/forward_arrow.png",
                                colorFilter = ColorFilter.tint(color = Color.White)
                            )
                        }

                    }

                }
            }

        }
    }

}
