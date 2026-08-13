package app.ammar.watefinder.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val LightColorScheme = lightColorScheme(
    primary = BlueSlatePrimary,
    onPrimary = BlueSlateOnPrimary,
    primaryContainer = BlueSlatePrimaryContainer,
    onPrimaryContainer = BlueSlateOnPrimaryContainer,
    secondary = BlueSlateSecondary,
    onSecondary = BlueSlateOnSecondary,
    background = BlueSlateBackground,
    onBackground = BlueSlateOnBackground,
    surface = BlueSlateSurface,
    onSurface = BlueSlateOnSurface,
    surfaceVariant = BlueSlateSurfaceVariant,
    onSurfaceVariant = BlueSlateOnSurfaceVariant,
    error = AppError,
    onError = AppOnError,
)

private val DarkColorScheme = darkColorScheme(
    primary = CharcoalBluePrimary,
    onPrimary = CharcoalBlueOnPrimary,
    primaryContainer = CharcoalBluePrimaryContainer,
    onPrimaryContainer = CharcoalBlueOnPrimaryContainer,
    secondary = CharcoalBlueSecondary,
    onSecondary = CharcoalBlueOnSecondary,
    background = CharcoalBlueBackground,
    onBackground = CharcoalBlueOnBackground,
    surface = CharcoalBlueSurface,
    onSurface = CharcoalBlueOnSurface,
    surfaceVariant = CharcoalBlueSurfaceVariant,
    onSurfaceVariant = CharcoalBlueOnSurfaceVariant,
    error = AppError,
    onError = AppOnError,
)


@Composable
fun WaTeFinderTheme(
    darkTheme: Boolean = false, // isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}