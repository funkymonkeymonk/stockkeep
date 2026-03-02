# How to Customize the App Theme

This guide shows you how to customize StockKeep's visual appearance by modifying colors, typography, and shapes.

## Overview

StockKeep uses Material Design 3 with a custom theme. The theme is defined in three files:
- `Color.kt` - Color palette
- `Type.kt` - Typography styles  
- `Theme.kt` - Theme configuration

## Prerequisites

- Understanding of Compose Previews (see [How-to: Use Compose Previews](./use-compose-previews.md))
- Familiarity with Material Design 3 concepts

## Changing Colors

### Step 1: Define New Colors

Open `app/src/main/java/com/example/stockkeep/ui/theme/Color.kt`:

```kotlin
// Add your custom colors
val Blue40 = Color(0xFF1976D2)
val Blue80 = Color(0xFF90CAF9)
val Teal40 = Color(0xFF00796B)
val Teal80 = Color(0xFF80CBC4)
```

### Step 2: Apply Colors to Theme

Open `app/src/main/java/com/example/stockkeep/ui/theme/Theme.kt`:

```kotlin
private val LightColorScheme = lightColorScheme(
    primary = Blue40,           // Was Purple40
    secondary = Teal40,         // Was PurpleGrey40
    tertiary = Pink40,          // Keep or change
    // Add more color mappings
)

private val DarkColorScheme = darkColorScheme(
    primary = Blue80,           // Was Purple80
    secondary = Teal80,         // Was PurpleGrey80
    tertiary = Pink80,          // Keep or change
)
```

### Step 3: Preview the Changes

Add a preview to see both themes:

```kotlin
@Preview(name = "Light Theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ThemePreview() {
    StockKeepTheme {
        Surface {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Primary Color",
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    "Secondary Color", 
                    color = MaterialTheme.colorScheme.secondary
                )
                Button(onClick = {}) {
                    Text("Primary Button")
                }
            }
        }
    }
}
```

## Complete Color Scheme

### Material 3 Color Roles

```kotlin
private val LightColorScheme = lightColorScheme(
    primary = Blue40,
    onPrimary = Color.White,
    primaryContainer = Blue80,
    onPrimaryContainer = Blue40,
    secondary = Teal40,
    onSecondary = Color.White,
    secondaryContainer = Teal80,
    onSecondaryContainer = Teal40,
    tertiary = Pink40,
    onTertiary = Color.White,
    tertiaryContainer = Pink80,
    onTertiaryContainer = Pink40,
    error = Color(0xFFB3261E),
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    // ... and more
)
```

## Customizing Typography

### Step 1: Define Custom Type

Open `app/src/main/java/com/example/stockkeep/ui/theme/Type.kt`:

```kotlin
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
)
```

### Step 2: Use in Components

```kotlin
Text(
    text = "Inventory",
    style = MaterialTheme.typography.headlineLarge
)

Text(
    text = "5 items in stock",
    style = MaterialTheme.typography.bodyLarge
)
```

## Custom Shapes

### Step 1: Create Shape Theme

Add to `Theme.kt`:

```kotlin
val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)
```

### Step 2: Apply to Theme

```kotlin
@Composable
fun StockKeepTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // ... color scheme logic
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,  // Add this
        content = content
    )
}
```

## Dynamic Colors (Android 12+)

Enable dynamic colors for Android 12+ devices:

```kotlin
@Composable
fun StockKeepTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,  // Set to true to enable
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) 
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    // ...
}
```

To test dynamic colors in Preview:

```kotlin
@Preview(name = "Dynamic Color Light")
@Composable
fun DynamicColorPreview() {
    StockKeepTheme(dynamicColor = true) {
        InventoryScreen()
    }
}
```

## Creating Design Tokens

### Step 1: Create Spacing Object

Create `app/src/main/java/com/example/stockkeep/ui/theme/Dimension.kt`:

```kotlin
object Spacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 16.dp
    val lg = 24.dp
    val xl = 32.dp
    val xxl = 48.dp
}

object Elevation {
    val none = 0.dp
    val low = 2.dp
    val medium = 4.dp
    val high = 8.dp
}

object IconSize {
    val small = 16.dp
    val medium = 24.dp
    val large = 32.dp
}
```

### Step 2: Use Tokens in UI

```kotlin
Card(
    modifier = Modifier
        .fillMaxWidth()
        .padding(Spacing.md),
    elevation = CardDefaults.cardElevation(
        defaultElevation = Elevation.low
    )
) {
    Row(
        modifier = Modifier.padding(Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Icon(
            imageVector = Icons.Default.Inventory,
            contentDescription = null,
            modifier = Modifier.size(IconSize.medium)
        )
        Text("Item Name")
    }
}
```

## Testing Theme Changes

### Preview All Components

Create a comprehensive theme preview:

```kotlin
@Preview(name = "Theme Components")
@Composable
fun ThemeComponentsPreview() {
    StockKeepTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Colors
                Text("Colors", style = MaterialTheme.typography.headlineSmall)
                ColorSample("Primary", MaterialTheme.colorScheme.primary)
                ColorSample("Secondary", MaterialTheme.colorScheme.secondary)
                
                // Typography
                Text("Typography", style = MaterialTheme.typography.headlineSmall)
                Text("Headline Large", style = MaterialTheme.typography.headlineLarge)
                Text("Body Large", style = MaterialTheme.typography.bodyLarge)
                
                // Components
                Text("Components", style = MaterialTheme.typography.headlineSmall)
                Button(onClick = {}) { Text("Primary Button") }
                OutlinedButton(onClick = {}) { Text("Secondary Button") }
                Card {
                    Text("Card", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Composable
private fun ColorSample(name: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(name)
    }
}
```

## Common Customizations

### Custom Button Style

```kotlin
Button(
    onClick = { },
    colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary
    ),
    shape = RoundedCornerShape(8.dp)
) {
    Text("Custom Button")
}
```

### Custom Card Style

```kotlin
Card(
    colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ),
    elevation = CardDefaults.cardElevation(
        defaultElevation = 4.dp
    )
) {
    // Card content
}
```

## Best Practices

1. **Use semantic color names** - `primary`, `secondary`, not `blue`, `green`
2. **Test in both themes** - Always preview light and dark
3. **Use Material 3 tokens** - Leverage built-in typography and shapes
4. **Create reusable tokens** - Spacing, elevations as constants
5. **Document custom colors** - Add comments explaining usage

## Related Documentation

- [Getting Started with Visual Design](../tutorials/getting-started-with-design.md) - Tutorial
- [How-to: Use Compose Previews](./use-compose-previews.md) - Preview guide
- [Design Tools Reference](../reference/design-tools.md) - Reference
- [Visual Design Workflow](../explanation/visual-design-workflow.md) - Philosophy
