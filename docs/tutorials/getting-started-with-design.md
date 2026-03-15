# Getting Started with Visual Design

In this tutorial, you will learn how to use Compose Previews to iterate on StockKeep's visual design. By the end, you'll have a working understanding of how to make visual changes and see them instantly without deploying to a device.

## What You'll Learn

- How to use `@Preview` annotations to see your UI in real-time
- How to preview different themes (light and dark mode)
- How to iterate on colors, typography, and layouts
- How to capture design states for review

## Prerequisites

- Android Studio installed
- StockKeep project opened in Android Studio
- devenv environment activated (`devenv shell`)

## Step 1: Open the Theme Files

First, let's examine the current design system:

1. In Android Studio, open `app/src/main/java/com/example/stockkeep/ui/theme/Color.kt`
2. Notice the current color palette:
   - Purple40, PurpleGrey40, Pink40 (light theme)
   - Purple80, PurpleGrey80, Pink80 (dark theme)

These are the colors you'll customize.

## Step 2: Add Your First Preview

Open `app/src/main/java/com/example/stockkeep/ui/screens/InventoryScreen.kt`.

At the bottom of the file (after the last function), add this preview:

```kotlin
@Preview(
    name = "Inventory Screen - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun InventoryScreenPreview() {
    StockKeepTheme {
        InventoryScreen()
    }
}
```

## Step 3: See the Preview

1. Look for the split panel on the right side of Android Studio
2. Click "Split" or "Design" to see the preview
3. You should see the Inventory screen rendered

**Try this:** Change one of the colors in `Color.kt` and watch the preview update instantly.

## Step 4: Preview Multiple States

Add more previews to see different states:

```kotlin
@Preview(
    name = "Inventory Screen - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun InventoryScreenDarkPreview() {
    StockKeepTheme(darkTheme = true) {
        InventoryScreen()
    }
}
```

Now you can see both light and dark versions side by side.

## Step 5: Preview Individual Components

Create a preview for a specific UI component. Add this to `InventoryScreen.kt`:

```kotlin
@Preview(name = "Item Card")
@Composable
fun InventoryItemCardPreview() {
    StockKeepTheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Sample Item",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "5 in stock • $10.00",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
```

This helps you focus on one component at a time.

## Step 6: Change Colors and See Results

Let's customize the theme colors:

1. Open `Color.kt`
2. Change `Purple40` to a different color, for example:
   ```kotlin
   val Purple40 = Color(0xFF2196F3)  // Blue
   ```
3. Watch all previews update immediately

## Step 7: Export Preview Images

To save a preview for sharing or documentation:

1. Right-click on any preview
2. Select "Copy Image" or "Save Image"
3. Save to your project folder

## What You've Learned

You now know how to:
- Create Compose Previews
- Preview in light and dark modes
- Focus on individual components
- Iterate on colors and see instant results
- Export preview images

## Next Steps

- Learn more advanced preview options: [How-to: Use Compose Previews](../how-to/use-compose-previews.md)
- Customize the full app theme: [How-to: Customize App Theme](../how-to/customize-app-theme.md)
- Understand the workflow philosophy: [Explanation: Visual Design Workflow](../explanation/visual-design-workflow.md)

## Troubleshooting

**Preview not showing?**
- Make sure the file compiles without errors
- Try Build → Clean Project, then Build → Rebuild Project
- Check that you're using `@Preview` from `androidx.compose.ui.tooling.preview`

**Preview shows "Rendering Problems"?**
- Some Preview features require a device/emulator to be connected
- Try using `showSystemUi = false` in the Preview annotation
