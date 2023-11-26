package screens.authentication

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class, ExperimentalAnimationApi::class)
@Composable
fun AuthenticationScreenCompose(currentPosition: Int = 0) {

    val viewModel: AuthenticationViewModel = AuthenticationViewModel()
    viewModel.changeScreen(currentPosition)
    val authenticationScreenData = viewModel.authenticationScreenData ?: return
    val state = viewModel.authenticationScreenData!!.screenType


    AnimatedContent(targetState = state,
        transitionSpec = {

            if (state == 0) {
                // Going forwards in the survey: Set the initial offset to start
                // at the size of the content so it slides in from right to left, and
                // slides out from the left of the screen to -fullWidth
                slideInHorizontally(
                    animationSpec = tween(500),
                    initialOffsetX = { fullWidth -> fullWidth }
                ) togetherWith
                        slideOutHorizontally(
                            animationSpec = tween(500),
                            targetOffsetX = { fullWidth -> -fullWidth }
                        )
            }
            if(state == 2){
                (fadeIn()).togetherWith(
                    fadeOut(
                        animationSpec = tween(800)
                    )
                )
            }
            else {
                // Going back to the previous question in the set, we do the same
                // transition as above, but with different offsets - the inverse of
                // above, negative fullWidth to enter, and fullWidth to exit.
                slideInHorizontally(
                    animationSpec = tween(500),
                    initialOffsetX = { fullWidth -> -fullWidth }
                ) togetherWith
                        slideOutHorizontally(
                            animationSpec = tween(500),
                            targetOffsetX = { fullWidth -> fullWidth }
                        )
            }
        }
    ) { targetState ->
        // It's important to use targetState and not state, as its critical to ensure
        // a successful lookup of all the incoming and outgoing content during
        // content transform.
        when (targetState) {
            0 -> {
                SignUpLoginCompose(1)

            }
            1 -> {

                SignUpLoginCompose(0)

                }
            2 -> {

                 ContinueWithPhoneCompose()

            }

            else -> {

            }


            }
        }



}


class AuthenticationScreen(currentScreen: Int = 0) : Screen {

    private val sc = currentScreen
    @Composable
    override fun Content() {
        AuthenticationScreenCompose(currentPosition = sc)
    }
}
