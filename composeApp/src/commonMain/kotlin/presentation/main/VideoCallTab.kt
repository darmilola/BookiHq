package presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.russhwolf.settings.Settings
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.main.home.HomepagePresenter
import presentation.viewmodels.HomePageViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.LiveVideoActionWidget
import presentation.widgets.RemoteVideoWidget
import presentations.components.ImageComponent
import theme.styles.Colors

class VideoCallTab() : Screen,
    KoinComponent {

    private var uiStateViewModel: UIStateViewModel? = null
    private val homepagePresenter: HomepagePresenter by inject()
    private var userEmail: String = ""
    private val preferenceSettings: Settings = Settings()


    @Composable
    override fun Content() {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            ImageComponent(imageModifier = Modifier.fillMaxSize(), imageRes = "drawable/woman_in_jeans.jpeg")
            RemoteVideoWidget()
            LiveVideoActionWidget()
        }

    }

}