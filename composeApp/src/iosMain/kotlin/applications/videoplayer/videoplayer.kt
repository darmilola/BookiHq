package applications.videoplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import domain.Models.VideoStatusViewMeta
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.flow.collectLatest
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerLayer
import platform.AVFoundation.AVPlayerStatus
import platform.AVFoundation.AVPlayerTimeControlStatus
import platform.AVFoundation.AVPlayerTimeControlStatusPlaying
import platform.AVFoundation.currentItem
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.reasonForWaitingToPlay
import platform.AVFoundation.seekToTime
import platform.AVFoundation.timeControlStatus
import platform.AVKit.AVPlayerViewController
import platform.CoreGraphics.CGColorRef
import platform.CoreGraphics.CGRect
import platform.CoreMedia.CMTime
import platform.Foundation.NSKeyValueChangeNewKey
import platform.Foundation.NSKeyValueObservingOptionNew
import platform.Foundation.NSURL
import platform.Foundation.addObserver
import platform.Foundation.removeObserver
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIView
import platform.darwin.NSObject
import platform.foundation.NSKeyValueObservingProtocol

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun VideoPlayer(modifier: Modifier, url: String, videoStatusViewMeta: VideoStatusViewMeta) {

   /* val player = AVPlayer(uRL = NSURL.URLWithString(url)!!)
    val playerLayer = AVPlayerLayer()
    val avPlayerViewController = AVPlayerViewController()

    avPlayerViewController.player = player
    avPlayerViewController.showsPlaybackControls = false
    val isShowingMediaBufferingLayer = remember { mutableStateOf(true) }
    playerLayer.player = player


    val timeControlObserver: NSObject = object : NSObject(), NSKeyValueObservingProtocol {

        override fun observeValueForKeyPath(
            keyPath: String?,
            ofObject: Any?,
            change: Map<Any?, *>?,
            context: COpaquePointer?
        ) {

            when (change!![NSKeyValueChangeNewKey]!!) {
                0L -> {
                    isShowingMediaBufferingLayer.value = false
                }
                1L -> {
                    isShowingMediaBufferingLayer.value = true
                }
                2L -> {
                    isShowingMediaBufferingLayer.value = false
                }
            }
        }
    }

    player.addObserver(
        observer = timeControlObserver,
        forKeyPath = "timeControlStatus",
        options = NSKeyValueObservingOptionNew,
        context = null
    )


    if (videoStatusViewMeta.currentPage != videoStatusViewMeta.settledPage){
        player.pause()
    }

    if (!player.isPlaying){
        isShowingMediaBufferingLayer.value = true
    }





    UIKitView(
        factory = {
            // Create a UIView to hold the AVPlayerLayer
            val playerContainer = UIView()
            playerContainer.addSubview(avPlayerViewController.view)
            // Return the playerContainer as the root UIView
            playerContainer
        },
        onResize = { view: UIView, rect: CValue<CGRect> ->
            CATransaction.begin()
            CATransaction.setValue(true, kCATransactionDisableActions)
            view.layer.setFrame(rect)
            playerLayer.setFrame(rect)
            avPlayerViewController.view.layer.frame = rect
            CATransaction.commit()
        },
        update = { view ->
            print("Meta $videoStatusViewMeta")
            if (videoStatusViewMeta.currentPage != videoStatusViewMeta.settledPage){
                println("Pause Me Here")
                if (player.isPlaying) {
                    println("Is Playing")
                    player.pause()
                    avPlayerViewController.player!!.pause()
                }
            }
        },
        modifier = modifier)
        val bgColor = if (isShowingMediaBufferingLayer.value) Color(0x80ffffff) else  Color.Transparent
        val modifier = Modifier
        .background(color = bgColor)
        .fillMaxSize()*/

    val interactionSource = remember { MutableInteractionSource() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collectLatest { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    /*if (!player.isPlaying) {
                        player.play()
                    }
                    else{
                      player.pause()
                    }*/
                }
            }
        }
    }

    Box(
        modifier = modifier.clickable(interactionSource = interactionSource, indication = null, onClick = {}),
        contentAlignment = Alignment.Center
    ) {
       // if (isShowingMediaBufferingLayer.value) {
            CircularProgressIndicator(
                modifier = Modifier.size(80.dp),
                color = Color(color = 0x90004659),
                strokeWidth = 4.dp)
       // }
    }
}

private val AVPlayer.isPlaying: Boolean
    get() = timeControlStatus == AVPlayerTimeControlStatusPlaying


