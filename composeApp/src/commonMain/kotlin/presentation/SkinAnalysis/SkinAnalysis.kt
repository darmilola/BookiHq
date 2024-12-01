package presentation.SkinAnalysis

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
import presentation.viewmodels.MainViewModel
import presentation.widgets.MultiLineTextWidget
import presentations.components.ImageComponent
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.styles.Colors

@Parcelize
class SkinAnalysisTab : Tab, KoinComponent, Parcelable {

    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    val preferenceSettings = Settings()

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Products"
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
                      Box(modifier = Modifier.fillMaxWidth().weight(1f).padding(bottom = 10.dp), contentAlignment = Alignment.BottomCenter) {
                          TextComponent(
                              text = "UNLOCK YOUR",
                              fontSize = 35,
                              fontFamily = GGSansRegular,
                              textStyle = MaterialTheme.typography.h6,
                              textColor = Colors.darkPrimary,
                              textAlign = TextAlign.Left,
                              fontWeight = FontWeight.ExtraBold,
                              lineHeight = 30,
                              maxLines = 1,
                              letterSpacing = 2,
                              textModifier = Modifier.wrapContentSize())
                      }
                     Box(modifier = Modifier.fillMaxWidth().weight(7f).padding(start = 20.dp, end = 20.dp)) {
                         imageDisplayView(displayText = "", imageRes = "drawable/woman_skin.jpg")
                         Box(modifier = Modifier.fillMaxSize().padding(top = 10.dp), contentAlignment = Alignment.TopCenter){
                                 MultiLineTextWidget(
                                     text = "SKIN'S BEAUTY\n POTENTIAL",
                                     fontSize = 35,
                                     textColor = Color.White,
                                     lineHeight = 40,
                                     textAlign = TextAlign.Center
                                 )
                             }
                         Box(modifier = Modifier.fillMaxSize().padding(top = 10.dp), contentAlignment = Alignment.BottomCenter){
                             Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(bottom = 10.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

                                 val modifier = Modifier
                                     .padding(bottom = 10.dp)
                                     .size(24.dp)
                                 ImageComponent(imageModifier = modifier, imageRes = "drawable/ai_logo.png", colorFilter = ColorFilter.tint(color = Color.White))

                                 MultiLineTextWidget(
                                     text = "Use our AI skin analysis tool to discover any skin issue you might be having",
                                     fontSize = 16,
                                     textColor = Color.White,
                                     lineHeight = 25,
                                     textAlign = TextAlign.Center
                                 )
                             }
                         }
                     }
                     Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(15.dp).background(color = Colors.darkPrimary), contentAlignment = Alignment.Center){
                            TextComponent(
                                text = "Start Analysis Now",
                                fontSize = 20,
                                fontFamily = GGSansRegular,
                                textStyle = MaterialTheme.typography.h6,
                                textColor = Color.White,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.ExtraBold,
                                lineHeight = 30,
                                maxLines = 1,
                                textModifier = Modifier.wrapContentSize())
                        }
                     }
                    Box(modifier = Modifier.fillMaxWidth().weight(1f)) {}
                  }
                      },
            backgroundColor = Color.Transparent,
        )
    }

    @Composable
    fun imageDisplayView(displayText: String, imageRes: String) {
        val bgStyle = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(color = 0x60000000),
                        Color(color = 0x60000000),
                        Color(color = 0x60000000))
                )
            )
        Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
            ImageComponent(imageModifier = Modifier.fillMaxHeight().fillMaxWidth(),
                imageRes = imageRes, contentScale = ContentScale.FillHeight)
            Box(modifier = bgStyle)
            //AttachTextContent(displayText = displayText)
        }
    }
}