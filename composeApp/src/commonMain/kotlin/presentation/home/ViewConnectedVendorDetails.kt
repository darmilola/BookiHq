package presentation.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import presentation.viewmodels.MainViewModel
import presentation.widgets.BusinessInfoContent
import presentation.widgets.BusinessInfoTitle

@Parcelize
class ViewConnectedVendorDetailsTab() : Tab, KoinComponent, Parcelable {
    @Transient
    private var mainViewModel: MainViewModel? = null
    override val options: TabOptions
        @Composable
        get() {
            val title = "Vendor"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title
                )
            }
        }

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    @Composable
    override fun Content() {
        Scaffold(
            topBar = {
                BusinessInfoTitle(mainViewModel = mainViewModel)
            },
            content = {
                BusinessInfoContent(mainViewModel!!.connectedVendor.value, isViewOnly = true){}
            },
            backgroundColor = Color.Transparent
        )
    }
}
