package screens.main

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ButtonComponent
import components.GradientButton
import components.ImageComponent
import components.TextComponent
import kotlinx.coroutines.launch
import screens.SplashScreenCompose
import screens.authentication.AuthenticationComposeScreen
import widgets.AttachImageStacks
import kotlin.math.roundToInt

class ConsultTab(private val mainViewModel: MainViewModel) : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Consult"
            val icon = painterResource("video_icon.png")

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

        mainViewModel.setTitle("Consultation")
        val columnModifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 70.dp)
            .fillMaxHeight()
            .fillMaxWidth()

        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = columnModifier
            ) {
                ConsultDesc()
                ConsultTherapistImages()
                AttachActionButtons()
            }
        }

    }

    @Composable
    fun ConsultDesc() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            TextComponent(
                text = "Make Online And Live Consultation Easily with",
                fontSize = 35,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                lineHeight = 45,
                textModifier = Modifier
                    .fillMaxWidth()
            )
            TherapistText()
        }

    }


    @Composable
    fun TherapistText() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 10.dp)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {

                TextComponent(
                    text = "Top Therapist",
                    fontSize = 35,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 40
                )
                GradientLock()
            }

            AttachImageStacks()

        }

    }

    @Composable
    fun GradientLock() {
        Box(
            modifier = Modifier
                .width(210.dp)
                .height(3.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(color = 0xFFF43569),
                            Color(color = 0xFFFF823E)
                        )
                    )
                )
        ) {
        }
    }


    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    @Composable
    fun AttachActionButtons() {
        val buttonStyle2 = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .height(50.dp)

        val gradientButtonStyle = Modifier
            .fillMaxWidth()
            .height(50.dp)

        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

            ButtonComponent(
                modifier = buttonStyle2,
                buttonText = "Get Started",
                borderStroke = BorderStroke(1.dp, Color(color = 0xFFF43569)),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF3F3F3)),
                fontSize = 18,
                shape = RoundedCornerShape(10.dp),
                textColor = Color(color = 0xFFF43569),
                style = MaterialTheme.typography.h4
            ) {
                mainViewModel.setId(2)
            }
        }

    }



    @Composable
    fun ConsultTherapistImages(){
        Box(
           modifier =  Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(color = Color(0xFBFBFB), shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ){
            Box(
                Modifier
                    .padding(start = 40.dp, bottom = 25.dp)
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(color = Color(0xFBFBFB), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.CenterStart
            ) {
                val modifier = Modifier
                    .rotate(-25f)
                    .width(120.dp)
                    .clip(shape = RoundedCornerShape(17.dp))
                    .height(130.dp)
                ImageComponent(imageModifier = modifier, imageRes = "therap1.jpg")
            }

            Box(
                Modifier
                    .padding(bottom = 80.dp)
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(color = Color(0xFBFBFB), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                val modifier = Modifier
                    .width(125.dp)
                    .clip(shape = RoundedCornerShape(17.dp))
                    .height(130.dp)
                ImageComponent(imageModifier = modifier, imageRes = "doctor.jpg")
            }

            Box(
                Modifier
                    .padding(end = 40.dp)
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(color = Color(0xFBFBFB), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.CenterEnd
            ) {
                val modifier = Modifier
                    .rotate(20f)
                    .width(120.dp)
                    .clip(shape = RoundedCornerShape(17.dp))
                    .height(130.dp)
                ImageComponent(imageModifier = modifier, imageRes = "1.jpg")
            }
            AttachCircle()
        }

    }
    @Composable
    fun AttachCircle() {
        Box(
            Modifier
                .graphicsLayer {
                    this.transformOrigin = TransformOrigin(0.5f, 0.5f)
                    this.rotationX = 105f
                    this.rotationY = 150f
                    this.rotationZ = 0f
                }
                .padding(start = 40.dp, end = 40.dp)
                .height(210.dp)
                .fillMaxWidth()
                .drawWithContent {
                    clipRect(top = 180f) {
                        this@drawWithContent.drawContent()
                    }
                }
        ) {
            Box(
                Modifier
                    .border(width = (0.5).dp, color = Color(color = 0xFFFA2D65), shape = RoundedCornerShape(50))
                    .height(210.dp)
                    .padding(start = 40.dp, end = 40.dp)
                    .fillMaxWidth()) {}
        }

    }
}