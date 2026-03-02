# Design Tools Reference

Reference documentation for visual design tools and commands in the StockKeep project.

## devenv Commands

The following commands are available in the devenv shell for design workflows:

### `design-preview`

Displays information about the Compose Preview workflow.

```bash
$ design-preview
Starting Compose Preview workflow...
Open Android Studio and use @Preview annotations
See docs/tutorials/getting-started-with-design.md for details
```

### `design-build`

Builds a debug APK for design testing on devices.

```bash
$ design-build
Building debug APK for design review...
APK location: app/build/outputs/apk/debug/app-debug.apk
```

## Project Structure

### Theme Files

| File | Purpose | Key Content |
|------|---------|-------------|
| `app/src/main/java/.../ui/theme/Color.kt` | Color definitions | Light and dark theme color palettes |
| `app/src/main/java/.../ui/theme/Theme.kt` | Theme configuration | Color scheme selection, dynamic colors |
| `app/src/main/java/.../ui/theme/Type.kt` | Typography | Text styles and font configurations |

### Screen Files

| File | Purpose | Design Notes |
|------|---------|--------------|
| `app/src/main/java/.../ui/screens/InventoryScreen.kt` | Main inventory list | Cards, lists, floating action button |
| `app/src/main/java/.../ui/screens/ScanScreen.kt` | Barcode scanner | Camera preview, scanning overlay |

## Compose Preview Annotations

### @Preview Parameters

| Parameter | Type | Description | Example |
|-----------|------|-------------|---------|
| `name` | String | Display name in preview panel | `"Light Theme"` |
| `group` | String | Group related previews | `"Components"` |
| `showBackground` | Boolean | Show theme background color | `true` |
| `showSystemUi` | Boolean | Include status/navigation bars | `true` |
| `widthDp` | Int | Preview width in dp | `360` |
| `heightDp` | Int | Preview height in dp | `640` |
| `fontScale` | Float | Text scaling factor | `1.5f` |
| `uiMode` | Int | UI mode flags | `Configuration.UI_MODE_NIGHT_YES` |
| `locale` | String | Locale for preview | `"es"` |

### UI Mode Constants

```kotlin
Configuration.UI_MODE_NIGHT_NO      // Light theme
Configuration.UI_MODE_NIGHT_YES     // Dark theme
Configuration.UI_MODE_TYPE_NORMAL   // Normal mode
```

## Material 3 Color Scheme

### Light Theme Colors

| Token | Default Value | Usage |
|-------|--------------|-------|
| `primary` | Purple40 | Main brand color, buttons |
| `onPrimary` | White | Text on primary color |
| `primaryContainer` | Purple90 | Containers with primary accent |
| `secondary` | PurpleGrey40 | Secondary actions |
| `tertiary` | Pink40 | Accent highlights |
| `background` | Light surface | App background |
| `surface` | Light surface | Cards, sheets |
| `error` | Red | Error states |

### Dark Theme Colors

| Token | Default Value | Usage |
|-------|--------------|-------|
| `primary` | Purple80 | Main brand color (dark) |
| `secondary` | PurpleGrey80 | Secondary actions (dark) |
| `tertiary` | Pink80 | Accent highlights (dark) |
| `background` | Dark surface | App background (dark) |
| `surface` | Dark surface | Cards, sheets (dark) |

## Typography Scale

### Material 3 Type Roles

| Style | Default Size | Usage |
|-------|-------------|-------|
| `displayLarge` | 57sp | Hero text, splash screens |
| `displayMedium` | 45sp | Large headlines |
| `displaySmall` | 36sp | Medium headlines |
| `headlineLarge` | 32sp | Screen titles |
| `headlineMedium` | 28sp | Section headers |
| `headlineSmall` | 24sp | Card titles |
| `titleLarge` | 22sp | App bar titles |
| `titleMedium` | 16sp | List items, emphasis |
| `titleSmall` | 14sp | Small labels |
| `bodyLarge` | 16sp | Primary body text |
| `bodyMedium` | 14sp | Secondary body text |
| `bodySmall` | 12sp | Captions, hints |
| `labelLarge` | 14sp | Buttons, tabs |
| `labelMedium` | 12sp | Small buttons |
| `labelSmall` | 11sp | Overlines, badges |

## Shapes

### Material 3 Shape Roles

| Shape | Default | Usage |
|-------|---------|-------|
| `extraSmall` | 4dp | Chips, small buttons |
| `small` | 8dp | Buttons, text fields |
| `medium` | 12dp | Cards, dialogs |
| `large` | 16dp | Large cards, bottom sheets |
| `extraLarge` | 28dp | Full-screen dialogs |

## Elevation Values

### Standard Elevations

| Token | Value | Usage |
|-------|-------|-------|
| `Level0` | 0dp | Flat elements |
| `Level1` | 1dp | Resting cards |
| `Level2` | 3dp | Raised cards |
| `Level3` | 6dp | Navigation drawer |
| `Level4` | 8dp | Modal bottom sheet |
| `Level5` | 12dp | Dialogs |

## Design Token Values

### Spacing (if using Dimension.kt)

| Token | Value | Usage |
|-------|-------|-------|
| `xs` | 4dp | Tight spacing |
| `sm` | 8dp | Default padding |
| `md` | 16dp | Section padding |
| `lg` | 24dp | Large gaps |
| `xl` | 32dp | Screen margins |
| `xxl` | 48dp | Major separations |

### Icon Sizes

| Token | Value | Usage |
|-------|-------|-------|
| `small` | 16dp | Inline icons |
| `medium` | 24dp | Standard icons |
| `large` | 32dp | Feature icons |

## Environment Variables

Variables set by devenv for Android development:

| Variable | Value | Purpose |
|----------|-------|---------|
| `ANDROID_HOME` | SDK path | Android SDK location |
| `ANDROID_SDK_ROOT` | SDK path | Android SDK root |
| `JAVA_HOME` | JDK path | Java home directory |
| `GREET` | Welcome message | Shell greeting |

## Gradle Tasks for Design

### Build Tasks

```bash
./gradlew assembleDebug          # Build debug APK
./gradlew assembleRelease        # Build release APK
./gradlew installDebug           # Install on connected device
```

### Verification Tasks

```bash
./gradlew lint                   # Run lint checks
./gradlew ktlintCheck            # Check Kotlin style
./gradlew detekt                 # Run static analysis
```

### Clean Tasks

```bash
./gradlew clean                  # Clean build artifacts
./gradlew cleanBuildCache        # Clean build cache
```

## Android Studio Shortcuts

### Preview Shortcuts

| Shortcut | Action |
|----------|--------|
| `Ctrl+F9` / `Cmd+F9` | Build project |
| `Shift+F10` / `Ctrl+R` | Run app |
| `Shift+F9` / `Ctrl+D` | Debug app |
| `Alt+Enter` | Quick fix |
| `Ctrl+Shift+A` / `Cmd+Shift+A` | Find action |

### Design Mode

| Feature | Location |
|---------|----------|
| Split View | Right panel "Split" button |
| Design View | Right panel "Design" button |
| Code View | Right panel "Code" button |
| Interactive Preview | Preview panel play button |

## File Locations

### Resources

| Resource Type | Location |
|--------------|----------|
| Drawables | `app/src/main/res/drawable/` |
| Mipmaps | `app/src/main/res/mipmap-*/` |
| Values | `app/src/main/res/values/` |
| Fonts | `app/src/main/res/font/` (create if needed) |

### Source Code

| Component | Location |
|-----------|----------|
| Themes | `app/src/main/java/com/example/stockkeep/ui/theme/` |
| Screens | `app/src/main/java/com/example/stockkeep/ui/screens/` |
| Components | `app/src/main/java/com/example/stockkeep/ui/components/` |

## Related Documentation

- [Getting Started with Visual Design](../tutorials/getting-started-with-design.md) - Tutorial
- [How-to: Use Compose Previews](../how-to/use-compose-previews.md) - Preview guide
- [How-to: Customize App Theme](../how-to/customize-app-theme.md) - Theme customization
- [Visual Design Workflow](../explanation/visual-design-workflow.md) - Philosophy
