package presentation.Packages

import GGSansRegular
import StackedSnackbarHost
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import domain.Enums.SharedPreferenceEnum
import kotlinx.serialization.Transient
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.home.HomepagePresenter
import presentation.viewmodels.MainViewModel
import presentation.widgets.MultiLineTextWidget
import presentations.components.ImageComponent
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.styles.Colors

@Parcelize
class Packages : Tab, KoinComponent, Parcelable {

    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    private val packagePresenter: PackagePresenter by inject()
    @Transient
    val preferenceSettings = Settings()

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Packages"
            val icon = painterResource("drawable/shop_icon.png")

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon

                )
            }
        }

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    @Composable
    override fun Content() {
        val vendorId: Long = preferenceSettings[SharedPreferenceEnum.VENDOR_ID.toPath(),-1L]

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        Scaffold(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .background(color = Color.White),
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {

            },
            content = {
                Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {



                    }
            },
            backgroundColor = Color.Transparent,
        )
    }
}