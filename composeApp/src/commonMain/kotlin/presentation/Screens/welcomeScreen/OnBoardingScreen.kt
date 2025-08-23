package presentation.Screens.welcomeScreen

import GGSansRegular
import GGSansSemiBold
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
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
import com.assignment.moniepointtest.ui.theme.AppTheme
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Models.PlatformNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Transient
import presentation.Screens.WelcomeScreen
import presentation.Screens.auth.SignupScreen
import presentation.components.ButtonComponent
import presentation.viewmodels.MainViewModel
import presentation.widgets.onBoardingScreenView
import presentation.widgets.welcomeScreenScrollWidget
import presentation.widgets.welcomeScreenView
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors
import kotlin.math.absoluteValue

@Parcelize
data class OnBoardingScreen(val platformNavigator: PlatformNavigator) : Screen, Parcelable {

    @Transient
    private var mainViewModel: MainViewModel? = null

    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    override val key: ScreenKey = uniqueScreenKey

    fun setDatabaseBuilder(builder: RoomDatabase.Builder<AppDatabase>?) {
        this.databaseBuilder = builder
    }

    fun setMainViewModel(mainViewModel: MainViewModel? = null) {
        this.mainViewModel = mainViewModel
    }

    @Composable
    override fun Content() {
        OnBoardingScreenCompose()
    }

    @Composable
    fun OnBoardingScreenCompose() {

        AppTheme {
            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
                Box(
                    modifier = Modifier.fillMaxHeight().fillMaxWidth()
                        .background(color = theme.Colors.dashboardBackground),
                    contentAlignment = Alignment.Center
                ) {
                    OnBoardingScreenScrollWidget(platformNavigator)
                }
            }
        }

    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun OnBoardingScreenScrollWidget(platformNavigator: PlatformNavigator) {
        val navigator = LocalNavigator.currentOrThrow
        val pagerState = rememberPagerState(pageCount = { 3 })
        val userScrollNext = remember { mutableStateOf(false) }
        val titleTextList = arrayListOf<String>()
        val subtitleTextList = arrayListOf<String>()
        val imgList = arrayListOf<String>()

        titleTextList.add("Luxury Spa Experience")
        titleTextList.add("Fresh Cuts, Premium\nExperience")
        titleTextList.add("Relax, Recharge, \nRejuvenate")

        subtitleTextList.add("Relax and recharge—book your perfect spa day with expert care and premium services.")
        subtitleTextList.add("Sharp style, expert barbers, and top-tier grooming—all in one place")
        subtitleTextList.add("Book your perfect session—expert guidance, personalized treatments, lasting results")

        imgList.add("drawable/onboarding_image1.png")
        imgList.add("drawable/onboarding_image2.png")
        imgList.add("drawable/onboarding_image3.png")

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth().background(color = theme.Colors.dashboardBackground),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().height(50.dp)
                    .padding(end = 20.dp)
                    .background(color = theme.Colors.dashboardBackground), contentAlignment = Alignment.CenterEnd
            ) {
                TextComponent(
                    text = "Skip",
                    fontSize = 16,
                    textStyle = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                    textColor = Colors.primaryColor,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 25,
                    textModifier = Modifier.wrapContentWidth().clickable {
                        val welcomeScreen = WelcomeScreen(platformNavigator = platformNavigator)
                        welcomeScreen.setDatabaseBuilder(databaseBuilder = databaseBuilder!!)
                        welcomeScreen.setMainViewModel(mainViewModel = mainViewModel!!)
                        navigator.replaceAll(welcomeScreen)
                        userScrollNext.value = false
                    }
                )
            }
            val isDraggedState = pagerState.interactionSource.collectIsDraggedAsState()
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = true,
                flingBehavior = PagerDefaults.flingBehavior(
                    state = pagerState,
                    snapPositionalThreshold = 0.2f
                ),
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.80f)
            ) { currentPage ->
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        .pagerFadeTransition(pagerState.currentPage, pagerState)
                        .background(color = Color.Transparent)
                ) {
                    onBoardingScreenView(
                        subtitleTextList[currentPage],
                        titleTextList[currentPage],
                        imgList[currentPage]
                    )
                }
            }
            // Start auto-scroll effect

            if (userScrollNext.value) {
                LaunchedEffect(isDraggedState) {
                    if (pagerState.currentPage == pagerState.pageCount - 1) {
                        val welcomeScreen = WelcomeScreen(platformNavigator = platformNavigator)
                        welcomeScreen.setDatabaseBuilder(databaseBuilder = databaseBuilder!!)
                        welcomeScreen.setMainViewModel(mainViewModel = mainViewModel!!)
                        navigator.replaceAll(welcomeScreen)
                        userScrollNext.value = false
                    } else {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage + 1,
                            animationSpec = tween(
                                easing = LinearEasing,
                                durationMillis = 1000
                            )
                        )
                        userScrollNext.value = false
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.15f),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    Modifier
                        .height(10.dp)
                        .fillMaxWidth(0.20f)
                        .background(color = Color.Transparent),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(3) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) Colors.darkPrimary else Colors.darkPrimary.copy(
                                alpha = 0.5f
                            )
                        Box(
                            modifier = Modifier
                                .padding(start = 3.dp, end = 3.dp)
                                .clip(CircleShape)
                                .background(color)
                                .height(3.dp)
                                .weight(1f, fill = true)
                        )
                    }

                }

            }
            Box(
                modifier = Modifier.fillMaxHeight().fillMaxWidth()
                    .background(color = theme.Colors.dashboardBackground),
                contentAlignment = Alignment.TopCenter
            ) {

                val buttonStyle = Modifier
                    .padding(top = 30.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .height(50.dp)

                ButtonComponent(
                    modifier = buttonStyle,
                    buttonText = "Next",
                    borderStroke = null,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Colors.darkPrimary),
                    fontSize = 18,
                    shape = RoundedCornerShape(15.dp),
                    textColor = Color.White,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                ) {
                    userScrollNext.value = true
                }
            }

        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    fun Modifier.pagerFadeTransition(page: Int, pagerState: PagerState) =
        graphicsLayer {
            val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
            translationX = pageOffset * size.width
            alpha = 1 - pageOffset.absoluteValue
        }

    @OptIn(ExperimentalFoundationApi::class)
    fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
        return (currentPage - page) + currentPageOffsetFraction
    }

}
