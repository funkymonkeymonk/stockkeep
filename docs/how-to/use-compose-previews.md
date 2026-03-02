# How to Use Compose Previews

This guide shows you how to use Jetpack Compose Previews effectively for visual design work in StockKeep.

## Overview

Compose Previews let you see your UI without running the app. They update instantly when you change code, making them perfect for visual design iteration.

## Prerequisites

- Android Studio Arctic Fox or newer
- StockKeep project open
- Familiarity with basic Compose syntax

## Basic Preview

To create a preview, add the `@Preview` annotation to a Composable function:

```kotlin
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun MyPreview() {
    StockKeepTheme {
        // Your UI here
        InventoryItemCard()
    }
}
```

## Preview Configuration Options

### Show Background

Display a background color matching the theme:

```kotlin
@Preview(showBackground = true)
@Composable
fun WithBackgroundPreview() {
    StockKeepTheme {
        InventoryScreen()
    }
}
```

### Show System UI

Include status bar and navigation:

```kotlin
@Preview(showSystemUi = true)
@Composable
fun FullScreenPreview() {
    StockKeepTheme {
        InventoryScreen()
    }
}
```

### Preview Name

Give your preview a descriptive label:

```kotlin
@Preview(name = "Empty State - Light Theme")
@Composable
fun EmptyStatePreview() {
    StockKeepTheme {
        EmptyInventoryView()
    }
}
```

### Device Configuration

Preview at specific sizes:

```kotlin
@Preview(
    name = "Small Phone",
    widthDp = 360,
    heightDp = 640
)
@Composable
fun SmallPhonePreview() {
    StockKeepTheme {
        InventoryScreen()
    }
}
```

## Theme Previews

### Light and Dark Mode

See both themes simultaneously:

```kotlin
@Preview(
    name = "Light Theme",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Theme", 
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ThemeComparisonPreview() {
    StockKeepTheme {
        InventoryScreen()
    }
}
```

### Force Dark Theme

Explicitly set dark theme in the theme wrapper:

```kotlin
@Preview(name = "Dark Theme - Forced")
@Composable
fun DarkThemeForcedPreview() {
    StockKeepTheme(darkTheme = true) {
        InventoryScreen()
    }
}
```

## Locale Previews

Test different languages:

```kotlin
@Preview(
    name = "English",
    locale = "en"
)
@Preview(
    name = "Spanish",
    locale = "es"
)
@Composable
fun LocalizationPreview() {
    StockKeepTheme {
        InventoryScreen()
    }
}
```

## Font Scale Previews

Test accessibility with different font sizes:

```kotlin
@Preview(
    name = "Normal Font",
    fontScale = 1.0f
)
@Preview(
    name = "Large Font",
    fontScale = 1.5f
)
@Preview(
    name = "Extra Large Font",
    fontScale = 2.0f
)
@Composable
fun FontScalePreview() {
    StockKeepTheme {
        InventoryItemCard()
    }
}
```

## Grouping Related Previews

Use `group` parameter to organize:

```kotlin
@Preview(
    name = "Card - Light",
    group = "Components",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Card - Dark",
    group = "Components",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CardComponentPreview() {
    StockKeepTheme {
        InventoryItemCard()
    }
}
```

## Preview with Sample Data

Create previews with realistic content:

```kotlin
@Preview(name = "Item with Data")
@Composable
fun ItemWithDataPreview() {
    StockKeepTheme {
        val sampleItem = InventoryItem(
            id = 1,
            name = "Wireless Mouse",
            barcode = "123456789",
            quantity = 5,
            price = 29.99
        )
        InventoryItemCard(item = sampleItem)
    }
}
```

## Interactive Previews

Enable interaction (Android Studio Electric Eel+):

```kotlin
@Preview(
    name = "Interactive",
    showSystemUi = true
)
@Composable
fun InteractivePreview() {
    StockKeepTheme {
        InventoryScreen()
    }
}
```

After building, click the "Start Interactive Mode" button in the preview panel.

## Preview Annotations

Create reusable preview configurations:

```kotlin
@Preview(
    name = "Phone",
    widthDp = 360,
    heightDp = 640,
    showSystemUi = true
)
annotation class PhonePreview

@Preview(
    name = "Tablet",
    widthDp = 800,
    heightDp = 600,
    showSystemUi = true
)
annotation class TabletPreview

// Usage:
@PhonePreview
@TabletPreview
@Composable
fun ResponsivePreview() {
    StockKeepTheme {
        InventoryScreen()
    }
}
```

## Best Practices

1. **Name your previews descriptively** - Include theme, state, and component
2. **Create focused previews** - One component per preview for clarity
3. **Preview edge cases** - Empty states, error states, long text
4. **Use sample data** - Previews should look realistic
5. **Group related previews** - Use the `group` parameter

## Common Patterns for StockKeep

### Screen Previews

```kotlin
@Preview(
    name = "Inventory Screen",
    showSystemUi = true
)
@Composable
fun InventoryScreenPreview() {
    StockKeepTheme {
        InventoryScreen()
    }
}
```

### Component Previews

```kotlin
@Preview(
    name = "Scan Button",
    group = "Components"
)
@Composable
fun ScanButtonPreview() {
    StockKeepTheme {
        FloatingActionButton(onClick = {}) {
            Icon(Icons.Default.Add, "Scan")
        }
    }
}
```

### State Previews

```kotlin
@Preview(name = "Empty Inventory")
@Composable
fun EmptyInventoryPreview() {
    StockKeepTheme {
        EmptyInventoryView(onAddClick = {})
    }
}

@Preview(name = "Populated Inventory")
@Composable
fun PopulatedInventoryPreview() {
    StockKeepTheme {
        InventoryList(items = sampleItems)
    }
}
```

## Troubleshooting

### Previews Not Updating

1. Build → Clean Project
2. Build → Rebuild Project
3. File → Invalidate Caches / Restart

### Preview Shows Error

- Check for compilation errors in the file
- Ensure all imports are correct
- Verify preview function is `@Composable`

### Interactive Mode Not Working

- Requires Android Studio Electric Eel or newer
- Must build project first (Ctrl+F9 / Cmd+F9)

## Related Documentation

- [Getting Started with Visual Design](../tutorials/getting-started-with-design.md) - Tutorial
- [Customize App Theme](./customize-app-theme.md) - How-to
- [Design Tools Reference](../reference/design-tools.md) - Reference
