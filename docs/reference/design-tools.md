# Design Tools Reference

Reference documentation for visual design tools and commands in the StockKeep project.

**Note:** This is a terminal-based workflow. All commands run from the shell - no IDE required.

## devenv Commands

The following commands are available in the devenv shell for design workflows:

### Build Commands

#### `design-build`

Builds a debug APK for design testing.

```bash
$ design-build
Building debug APK for design review...
APK location: app/build/outputs/apk/debug/app-debug.apk

To test on emulator:
  1. emu-start    (if not running)
  2. emu-mirror   (to see the screen)
  3. ./gradlew installDebug
```

### Emulator Commands

#### `emu-list`

List available Android Virtual Devices.

```bash
$ emu-list
Available Android Virtual Devices:
StockKeep-Device
```

#### `emu-create`

Create a new Android Virtual Device.

```bash
$ emu-create
Creating new Android Virtual Device...
This will create a Pixel 7 API 34 device

AVD 'StockKeep-Device' created
Run 'emu-start' to launch the emulator
```

#### `emu-start`

Start the Android emulator.

```bash
$ emu-start
Starting Android Emulator...
Waiting for device to boot (this may take a minute)...

Emulator started successfully!
Device ID: emulator-5554
```

**Options used:**
- `-no-snapshot-load` - Fresh boot
- `-no-boot-anim` - Skip boot animation

**Manual start:**
```bash
emulator -avd StockKeep-Device -gpu swiftshader_indirect  # Software rendering
emulator -avd StockKeep-Device -wipe-data                 # Factory reset
```

#### `emu-mirror`

Mirror emulator screen to desktop using scrcpy.

```bash
$ emu-mirror
Starting screen mirroring with scrcpy...
Use Ctrl+C to stop mirroring (emulator keeps running)
```

**Default options:**
- `--turn-screen-off` - Turn off device screen while mirroring
- `--stay-awake` - Keep device awake
- `--window-title "StockKeep Preview"`

**Manual scrcpy:**
```bash
scrcpy --max-size 1024                    # Smaller window
scrcpy --fullscreen                       # Fullscreen mode
scrcpy --record demo.mp4                  # Record screen
scrcpy --bit-rate 4M                      # Lower quality/faster
```

#### `emu-deploy`

Build and deploy to emulator.

```bash
$ emu-deploy
Building and deploying to emulator...

App deployed successfully!
Launching StockKeep...
```

#### `emu-stop`

Stop the emulator.

```bash
$ emu-stop
Stopping emulator...
Emulator stopped
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

## ADB Commands

Since you have the emulator running, you can use adb directly:

### Device Management

```bash
adb devices                          # List connected devices
adb get-state                        # Check device state
adb shell                            # Open shell on device
adb push local.txt /sdcard/          # Copy file to device
adb pull /sdcard/remote.txt ./       # Copy file from device
```

### App Management

```bash
adb install app.apk                  # Install APK
adb install -r app.apk               # Reinstall (keep data)
adb uninstall com.example.stockkeep  # Uninstall
adb shell pm clear com.example.stockkeep  # Clear app data
adb shell am force-stop com.example.stockkeep  # Force stop
```

### Debugging

```bash
adb logcat                           # View all logs
adb logcat -s StockKeep:D            # Filter by tag
adb logcat -c                        # Clear logs
adb bugreport                        # Full bug report
```

### UI Automation

```bash
adb shell input tap 500 500          # Tap screen
adb shell input swipe 100 500 100 100  # Swipe
adb shell input text "Hello"         # Type text
adb shell input keyevent 4           # Press back button
adb shell input keyevent 3           # Press home button
```

### System Settings

```bash
adb shell cmd uimode night yes       # Enable dark mode
adb shell cmd uimode night no        # Disable dark mode
adb shell wm density 320             # Change screen density
adb shell wm density reset           # Reset density
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

Variables set by devenv:

| Variable | Value | Purpose |
|----------|-------|---------|
| `ANDROID_HOME` | SDK path | Android SDK location |
| `ANDROID_SDK_ROOT` | SDK path | Android SDK root |
| `ANDROID_EMULATOR_HOME` | `~/.android/avd` | AVD storage location |
| `ANDROID_AVD_HOME` | `~/.android/avd` | AVD storage location |
| `JAVA_HOME` | JDK path | Java home directory |
| `GREET` | Welcome message | Shell greeting |

## Gradle Tasks for Design

### Build Tasks

```bash
./gradlew assembleDebug          # Build debug APK
./gradlew assembleRelease        # Build release APK
./gradlew installDebug           # Install on connected device
./gradlew bundleRelease          # Create Play Store bundle
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

### Development Tasks

```bash
./gradlew build                  # Full build
./gradlew check                  # Run all checks
./gradlew tasks                  # List all available tasks
```

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
- [How-to: Use the Android Emulator from Terminal](../how-to/use-emulator-terminal.md) - Emulator guide
- [How-to: Customize App Theme](../how-to/customize-app-theme.md) - Theme customization
- [Visual Design Workflow](../explanation/visual-design-workflow.md) - Philosophy
