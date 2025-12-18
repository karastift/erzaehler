package com.karastift.erzaehler.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ErzaehlerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            background = Color(0xFFFFFFFF),
            surface = Color(0xFFFFFFFF),
            primary = Color(0xFF232323),
            primaryContainer = Color(0xFF232323),
            secondary = Color(0xFF444444),
            secondaryContainer = Color(0xFF212121),
            onBackground = Color(0xFF1F2937),
            onSurface = Color(0xFF1F2937),
        ),
        typography = luaTypography(),
        content = content
    )
}
