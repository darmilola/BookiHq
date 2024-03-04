package presentation.authentication

import ProxyNavigator
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import presentation.viewmodels.AuthenticationViewModel

@Composable
fun AuthenticationScreenCompose(currentPosition: Int = 0, viewType: Int = -1, proxyNavigator: ProxyNavigator) {

    val viewModel = AuthenticationViewModel()
    viewModel.changeScreen(currentPosition)
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
                SignUpLogin(1, proxyNavigator)
            }
            1 -> {
                SignUpLogin(0, proxyNavigator)
                 }
            2 -> {
                ContinueWithPhone(proxyNavigator)
            }
            3 -> {
                ContinueWithEmail(proxyNavigator)
            }
            4 -> {
                if(viewType == 0){
                    VerifyOTP(viewType = 0,proxyNavigator = proxyNavigator)
                }
                else{
                    VerifyOTP(viewType = 1,proxyNavigator = proxyNavigator)
                }
            }

            5 -> {
                CompleteProfile()

            }

        }

    }



}

class AuthenticationScreen(currentScreen: Int = 0, private val viewType: Int = -1,val  proxyNavigator: ProxyNavigator) : Screen {

    private val sc = currentScreen

    @Composable
    override fun Content() {
        AuthenticationScreenCompose(currentPosition = sc, viewType = viewType, proxyNavigator = proxyNavigator)
    }
}

