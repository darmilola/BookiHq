package presentation.consultation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.russhwolf.settings.Settings
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.home.HomepagePresenter
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.LiveVideoActionWidget
import presentation.widgets.RemoteVideoWidget
import presentations.components.ImageComponent

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