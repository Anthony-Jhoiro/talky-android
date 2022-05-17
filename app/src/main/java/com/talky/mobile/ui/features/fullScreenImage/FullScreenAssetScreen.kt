package com.talky.mobile.ui.features.fullScreenImage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.talky.mobile.ui.commons.Asset
import com.talky.mobile.ui.commons.AssetComposable
import kotlin.math.roundToInt


@Composable
fun FullScreenImageScreen(
        state: FullScreenAssetContract.State,
        onPressBack: () -> Unit
) {
    val scale = remember { mutableStateOf(1f) }
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    val size = remember { mutableStateOf(IntSize.Zero) }

    Scaffold(
            topBar = {
                TopAppBar(
                        navigationIcon = {
                            Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    modifier = Modifier
                                            .padding(horizontal = 12.dp)
                                            .clickable { onPressBack() },
                                    contentDescription = "Go back"
                            )
                        },

                        title = { Text("Close") },
                        backgroundColor = MaterialTheme.colors.background
                )
            }
    ) {
        Column() {
            Column(

                    Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectTransformGestures { _, pan, zoom, _ ->
                                    scale.value *= zoom
                                    val x = pan.x * zoom
                                    val y = pan.y * zoom
                                    offsetX.value += x
                                    offsetY.value += y
                                }
                            }
                            .onSizeChanged {
                                size.value = it
                            }
                            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
                            .graphicsLayer {
                                scaleX = maxOf(1f, minOf(10f, scale.value))
                                scaleY = maxOf(1f, minOf(10f, scale.value))
                            }
            ) {
                if (state.url != null) {
                    AssetComposable(Asset("IMAGE", state.url), {}, false)
                }
            }
        }

    }

}