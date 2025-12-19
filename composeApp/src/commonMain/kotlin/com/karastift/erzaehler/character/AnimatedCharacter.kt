package com.karastift.erzaehler.character

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.karastift.erzaehler.domain.model.valueobjects.CharacterId
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.imageResource


@Composable
fun AnimatedCharacter(
    characterId: CharacterId,
    modifier: Modifier = Modifier,
    scale: Float = 10f, // Canvas scale
    characterScale: Float = 1.6f, // Character relative scaler (to adjust it to canvas, 1.6 worked good in simulator)
    frameDurationMs: Long = 200L,
    isPlaying: Boolean = true
) {
    val frames = CharacterAssets.getCharacter(characterId).idleFrames
    var frameIndex by remember { mutableStateOf(0) }

    // Only animate when isPlaying
    LaunchedEffect(isPlaying) {
        if (!isPlaying) return@LaunchedEffect
        while (true) {
            delay(frameDurationMs)
            frameIndex = (frameIndex + 1) % frames.size
        }
    }

    val image = imageResource(frames[frameIndex])

    val canvasWidthDp = (image.width * scale).dp
    val canvasHeightDp = (image.height * scale).dp

    Canvas(
        modifier = modifier
            .size(canvasWidthDp, canvasHeightDp)
//            .background(Color.LightGray) // Just to debug canvas size
    ) {
        val drawWidth = size.width * characterScale
        val drawHeight = size.height * characterScale

        // Anchor is bottom center
        val offsetX = (size.width - drawWidth) / 2f
        val offsetY = size.height - drawHeight

        drawImage(
            image = image,
            dstOffset = IntOffset(
                offsetX.toInt(),
                offsetY.toInt()
            ),
            dstSize = IntSize(
                drawWidth.toInt(),
                drawHeight.toInt()
            ),
            filterQuality = FilterQuality.None
        )
    }
}
