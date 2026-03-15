# Example: Creating a Complete Theme

This example walks through creating a complete custom theme for StockKeep from scratch using Compose Previews.

## Goal

Create a professional blue-green theme for StockKeep that:
- Reflects inventory/business aesthetics
- Provides excellent readability
- Works beautifully in light and dark modes
- Follows Material 3 guidelines

## Phase 1: Define the Color Strategy

### Step 1: Choose Base Colors

Based on inventory/business app needs:
- **Primary**: Trustworthy blue (#1976D2)
- **Secondary**: Growth/positive green (#388E3C)
- **Tertiary**: Accent orange (#F57C00)
- **Error**: Standard red (#D32F2F)

### Step 2: Create Color Scale

Define light and dark variants:

```kotlin
// Primary Blue Scale
val Blue10 = Color(0xFF001D36)
val Blue20 = Color(0xFF003258)
val Blue30 = Color(0xFF00497D)
val Blue40 = Color(0xFF1976D2)  // Main primary
val Blue80 = Color(0xFF90CAF9)  // Dark theme primary
val Blue90 = Color(0xFFD3E4FF)  // Primary container (light)
val Blue95 = Color(0xFFECF4FF)  // Background variant

// Secondary Green Scale
val Green10 = Color(0xFF002106)
val Green20 = Color(0xFF00390F)
val Green30 = Color(0xFF005318)
val Green40 = Color(0xFF388E3C)  // Main secondary
val Green80 = Color(0xFF80E090)  // Dark theme secondary
val Green90 = Color(0xFF9BF6A7)  // Secondary container

// Tertiary Orange Scale
val Orange30 = Color(0xFF723600)
val Orange40 = Color(0xFFF57C00)  // Main tertiary
val Orange80 = Color(0xFFFFB781)  // Dark theme tertiary
val Orange90 = Color(0xFFFFDBC8)  // Tertiary container

// Neutral Scale
val Gray10 = Color(0xFF191C1E)
val Gray90 = Color(0xFFE1E2E4)    // Background
val Gray95 = Color(0xFFEFF1F2)    // Surface
val Gray99 = Color(0xFFFCFCFC)    // Background (light)
```

### Step 3: Create Preview Test

```kotlin
@Preview(name = "Color Palette Test", widthDp = 400)
@Composable
fun ColorPalettePreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Primary Blue Scale", style = MaterialTheme.typography.titleMedium)
        ColorRow(listOf(Blue10, Blue20, Blue30, Blue40, Blue80, Blue90, Blue95))
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Secondary Green Scale", style = MaterialTheme.typography.titleMedium)
        ColorRow(listOf(Green10, Green20, Green30, Green40, Green80, Green90))
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Tertiary Orange Scale", style = MaterialTheme.typography.titleMedium)
        ColorRow(listOf(Orange30, Orange40, Orange80, Orange90))
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Neutral Scale", style = MaterialTheme.typography.titleMedium)
        ColorRow(listOf(Gray10, Gray90, Gray95, Gray99))
    }
}

@Composable
private fun ColorRow(colors: List<Color>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(color)
            )
        }
    }
}
```

Verify the color scales look harmonious.

## Phase 2: Build the Theme

### Step 4: Create Light Color Scheme

```kotlin
private val LightColorScheme = lightColorScheme(
    // Primary
    primary = Blue40,
    onPrimary = Color.White,
    primaryContainer = Blue90,
    onPrimaryContainer = Blue10,
    
    // Secondary
    secondary = Green40,
    onSecondary = Color.White,
    secondaryContainer = Green90,
    onSecondaryContainer = Green10,
    
    // Tertiary
    tertiary = Orange40,
    onTertiary = Color.White,
    tertiaryContainer = Orange90,
    onTertiaryContainer = Orange30,
    
    // Error
    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    
    // Background/Surface
    background = Gray99,
    onBackground = Gray10,
    surface = Gray95,
    onSurface = Gray10,
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    
    // Outline
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0),
    
    // Inverse
    inverseSurface = Gray10,
    inverseOnSurface = Gray90,
    inversePrimary = Blue80,
    
    // Scrim
    scrim = Color.Black,
    
    // Surface containers
    surfaceContainerHighest = Color(0xFFE6E0E9),
    surfaceContainerHigh = Color(0xFFECE6F0),
    surfaceContainer = Color(0xFFF3EDF7),
    surfaceContainerLow = Color(0xFFF7F2FA),
    surfaceContainerLowest = Color.White,
    surfaceBright = Color(0xFFFDF8FD),
    surfaceDim = Color(0xFFDED8E1),
)
```

### Step 5: Create Dark Color Scheme

```kotlin
private val DarkColorScheme = darkColorScheme(
    // Primary
    primary = Blue80,
    onPrimary = Blue20,
    primaryContainer = Blue30,
    onPrimaryContainer = Blue90,
    
    // Secondary
    secondary = Green80,
    onSecondary = Green20,
    secondaryContainer = Green30,
    onSecondaryContainer = Green90,
    
    // Tertiary
    tertiary = Orange80,
    onTertiary = Orange30,
    tertiaryContainer = Orange30,
    onTertiaryContainer = Orange90,
    
    // Error (dark variants)
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC),
    
    // Background/Surface (dark)
    background = Gray10,
    onBackground = Gray90,
    surface = Color(0xFF141218),
    onSurface = Gray90,
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    
    // Outline (dark)
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F),
    
    // Inverse (dark)
    inverseSurface = Gray90,
    inverseOnSurface = Gray10,
    inversePrimary = Blue40,
    
    // Scrim
    scrim = Color.Black,
    
    // Surface containers (dark)
    surfaceContainerHighest = Color(0xFF36343B),
    surfaceContainerHigh = Color(0xFF2B2930),
    surfaceContainer = Color(0xFF211F26),
    surfaceContainerLow = Color(0xFF1D1B20),
    surfaceContainerLowest = Color(0xFF0F0D13),
    surfaceBright = Color(0xFF3B383E),
    surfaceDim = Color(0xFF141218),
)
```

## Phase 3: Test the Theme

### Step 6: Create Comprehensive Theme Preview

```kotlin
@Preview(name = "Theme - Light")
@Preview(name = "Theme - Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CompleteThemePreview() {
    StockKeepTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    "StockKeep Theme",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Color roles section
                Text("Color Roles", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                
                ColorRoleSample("Primary", MaterialTheme.colorScheme.primary)
                ColorRoleSample("Secondary", MaterialTheme.colorScheme.secondary)
                ColorRoleSample("Tertiary", MaterialTheme.colorScheme.tertiary)
                ColorRoleSample("Error", MaterialTheme.colorScheme.error)
                ColorRoleSample("Surface", MaterialTheme.colorScheme.surface)
                ColorRoleSample("Background", MaterialTheme.colorScheme.background)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Components section
                Text("Components", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(onClick = { }) {
                    Text("Primary Button")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedButton(onClick = { }) {
                    Text("Secondary Button")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                FilledTonalButton(onClick = { }) {
                    Text("Tonal Button")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                ElevatedButton(onClick = { }) {
                    Text("Elevated Button")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                TextButton(onClick = { }) {
                    Text("Text Button")
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Card Component",
                        modifier = Modifier.padding(16.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = "Sample text",
                    onValueChange = { },
                    label = { Text("Input Field") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Typography section
                Text("Typography", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                
                Text("Headline Large", style = MaterialTheme.typography.headlineLarge)
                Text("Headline Medium", style = MaterialTheme.typography.headlineMedium)
                Text("Headline Small", style = MaterialTheme.typography.headlineSmall)
                Text("Title Large", style = MaterialTheme.typography.titleLarge)
                Text("Title Medium", style = MaterialTheme.typography.titleMedium)
                Text("Title Small", style = MaterialTheme.typography.titleSmall)
                Text("Body Large", style = MaterialTheme.typography.bodyLarge)
                Text("Body Medium", style = MaterialTheme.typography.bodyMedium)
                Text("Body Small", style = MaterialTheme.typography.bodySmall)
                Text("Label Large", style = MaterialTheme.typography.labelLarge)
                Text("Label Medium", style = MaterialTheme.typography.labelMedium)
                Text("Label Small", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
private fun ColorRoleSample(name: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color, MaterialTheme.shapes.small)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(name)
    }
}
```

### Step 7: Test with Real Screens

```kotlin
@Preview(name = "Inventory Screen - Light")
@Preview(name = "Inventory Screen - Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun InventoryScreenThemeTest() {
    StockKeepTheme {
        InventoryScreen()
    }
}

@Preview(name = "Scan Screen - Light")
@Preview(name = "Scan Screen - Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ScanScreenThemeTest() {
    StockKeepTheme {
        ScanScreen()
    }
}
```

## Phase 4: Document the Theme

### Step 8: Create Theme Documentation

```markdown
# StockKeep Blue-Green Theme

## Color Philosophy

- **Blue Primary**: Represents trust, professionalism, reliability
- **Green Secondary**: Represents growth, positive inventory status, success
- **Orange Tertiary**: Represents alerts, attention, warm accents

## Usage Guidelines

### Primary (Blue)
- Main actions and buttons
- App bar and navigation
- Key interactive elements
- Links and highlights

### Secondary (Green)
- Positive states (in stock, successful scan)
- Confirm actions
- Growth indicators
- Success messages

### Tertiary (Orange)
- Attention items (low stock warnings)
- Promotional highlights
- Accent icons
- Caution states

### Error (Red)
- Error messages
- Delete actions
- Invalid inputs
- Critical alerts

## Accessibility

- All color combinations meet WCAG AA contrast requirements
- Color is not the only indicator (icons + text always used)
- Dark theme maintains readability
- Tested with TalkBack screen reader
```

## Phase 5: Apply to App

### Step 9: Update Theme.kt

```kotlin
@Composable
fun StockKeepTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is disabled for this custom theme
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
```

### Step 10: Verify Application

1. Run `design-build` to create APK
2. Install on device
3. Test in both light and dark modes
4. Verify all screens look consistent
5. Check accessibility (font scaling, contrast)

## Time Breakdown

- Color strategy: 30 minutes
- Building color schemes: 1 hour
- Testing with previews: 30 minutes
- Documentation: 30 minutes
- Application and verification: 30 minutes
- **Total: 3 hours**

## Result

You now have:
- ✅ Complete custom theme
- ✅ Comprehensive color system
- ✅ Tested in both light and dark modes
- ✅ Documented usage guidelines
- ✅ Real-time preview workflow
- ✅ Accessible by default

## Next Steps

1. Apply theme to all app components
2. Create component library with theme applied
3. Add animations that respect theme colors
4. Create design tokens for spacing/sizing
5. Export theme documentation for stakeholders
