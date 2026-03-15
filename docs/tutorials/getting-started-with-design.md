# Getting Started with Visual Design

In this tutorial, you will learn how to iterate on StockKeep's visual design using the Android emulator and terminal-based tools. By the end, you'll have a working understanding of how to make visual changes and test them on a running emulator without using Android Studio.

## What You'll Learn

- How to run the Android emulator from the terminal
- How to mirror the emulator screen to your desktop
- How to deploy and test your app instantly
- How to iterate on colors, typography, and layouts

## Prerequisites

- StockKeep project cloned
- devenv environment activated (`devenv shell`)
- Terminal with ghostty (or similar)

## Two Approaches to Visual Design

StockKeep supports two workflows for visual design:

1. **Terminal + Emulator** (this tutorial) - Use command-line tools and emulator
2. **IDE + Compose Previews** - Use Android Studio or IntelliJ IDEA with @Preview annotations

This tutorial focuses on the **terminal-based approach** which doesn't require a heavy IDE.

## Step 1: Create an Android Virtual Device (AVD)

First, you need to create a virtual Android device to run your app:

```bash
devenv tasks run setup:emulator
```

Or use the helper script:
```bash
emu-create
```

This creates a Pixel 7 device with API 34.

**Note:** If this fails, you may need to download system images manually using `sdkmanager`.

## Step 2: Start the Emulator

Launch the emulator in the background:

```bash
devenv tasks run design:emulator
```

Or use the helper script:
```bash
emu-start
```

You'll see output like:
```
Emulator started successfully!
Device ID: emulator-5554
```

The emulator window will open (but we're going to use scrcpy for a better experience).

## Step 3: Mirror the Screen with scrcpy

In a **new terminal** (also with `devenv shell`):

```bash
emu-mirror
```

This opens a window showing your emulator screen with:
- Better performance than the emulator window
- Keyboard and mouse support
- The emulator screen turns off to save resources

**Tip:** Keep this running while you work. Use Ctrl+C to stop mirroring (emulator keeps running).

## Step 4: Deploy StockKeep

Build and deploy the app to the running emulator:

```bash
# Using devenv tasks (recommended)
devenv tasks run design:test

# Or use the helper script
emu-deploy
```

This:
1. Builds the debug APK
2. Installs it on the emulator
3. Launches the app automatically

You should see StockKeep open in the scrcpy window!

## Step 5: Make Visual Changes

Now let's customize the app appearance:

### Change Colors

Open `app/src/main/java/com/example/stockkeep/ui/theme/Color.kt` in your editor (vim, nano, helix, etc.):

```bash
# Example with vim
vim app/src/main/java/com/example/stockkeep/ui/theme/Color.kt
```

Change the primary color:
```kotlin
// From:
val Purple40 = Color(0xFF6650a4)

// To:
val Purple40 = Color(0xFF1976D2)  // Blue
```

Save the file.

### Redeploy and See Changes

```bash
# Using devenv tasks (with file watching - skips if no changes)
devenv tasks run design:test

# Or use the helper script
emu-deploy
```

The app will rebuild and relaunch with your new color!

## Step 6: Iteration Workflow

Here's your rapid iteration loop:

1. **Edit** - Change colors, typography, or layouts in your editor
2. **Deploy** - Run `devenv tasks run design:test` to build and install
3. **View** - See changes instantly in the scrcpy window
4. **Repeat** - Keep iterating

**Time per iteration:** ~10-15 seconds

**💡 Tip:** The `design:test` task uses file watching, so if you haven't changed any source files, it will skip the build and just deploy!

## Step 7: Testing Different States

Create different preview scenarios by modifying your code:

### Test with Different Data

Edit `InventoryScreen.kt` to show different test data:

```kotlin
// Add test items to see how UI handles them
val testItems = listOf(
    InventoryItem(1, "Wireless Mouse", "123", 5, 29.99),
    InventoryItem(2, "Long Product Name That Might Overflow", "456", 999, 199.99),
    InventoryItem(3, "Keyboard", "789", 0, 79.99)  // Out of stock
)
```

### Test Dark Mode

Enable dark mode on the emulator:
1. Open Settings app in the emulator
2. Go to Display → Dark theme
3. Or use adb: `adb shell cmd uimode night yes`

## Step 8: Stop the Emulator

When you're done:

```bash
emu-stop
```

Or simply close the scrcpy window and emulator window.

## What You've Learned

You now know how to:
- Create and manage Android Virtual Devices from the terminal
- Run the emulator and mirror its screen
- Deploy apps rapidly without an IDE
- Iterate on visual design quickly
- Test different themes and states

## Available Commands

### Using devenv Tasks (Recommended)

devenv tasks provide intelligent workflow automation with file watching:

```bash
# Design Workflow
devenv tasks run design:build    # Build debug APK (skips if no changes)
devenv tasks run design:deploy   # Deploy to emulator
devenv tasks run design:test     # Build + deploy together
devenv tasks run design:emulator # Start emulator

# Development Workflow
devenv tasks run dev:clean       # Clean build artifacts
devenv tasks run dev:build       # Build debug APK
devenv tasks run dev:test        # Run unit tests
devenv tasks run dev:lint        # Run linting
devenv tasks run dev:check       # Full check suite (clean, build, test, lint)

# Delivery Workflow
devenv tasks run delivery:build  # Build release APK
devenv tasks run delivery:bundle # Build release AAB

# Setup
devenv tasks run setup:emulator  # Create emulator
devenv tasks run setup:check     # Check environment
```

### Helper Scripts (Alternative)

For quick access without typing full task commands:

```bash
emu-list        # List available Android Virtual Devices
emu-create      # Create a new AVD
emu-start       # Start the emulator
emu-mirror      # Mirror emulator screen (scrcpy)
emu-deploy      # Build and deploy to emulator
emu-stop        # Stop the emulator
design-build    # Build debug APK only
```

## Next Steps

- Customize the app theme: [How-to: Customize App Theme](../how-to/customize-app-theme.md)
- Learn about Compose Previews (IDE-based): [How-to: Use Compose Previews](../how-to/use-compose-previews.md)
- Understand the design workflow: [Explanation: Visual Design Workflow](../explanation/visual-design-workflow.md)

## Using an IDE Instead

If you later want Compose Previews with instant visual feedback:

1. Install **IntelliJ IDEA Community Edition** (free, lighter than Android Studio)
2. Open the StockKeep project
3. Add `@Preview` annotations to your Composables
4. See instant previews in the IDE

The emulator workflow you learned here still works alongside the IDE!

## Troubleshooting

**Emulator won't start?**
- Check if virtualization is enabled in BIOS (Intel VT-x or AMD-V)
- Try: `emulator -avd StockKeep-Device -gpu swiftshader_indirect` (software rendering)

**scrcpy won't connect?**
- Make sure emulator is running: `adb devices` should show the device
- Try disconnecting/reconnecting: `adb kill-server && adb start-server`

**App won't install?**
- Check build output: `./gradlew assembleDebug`
- Uninstall first: `adb uninstall com.example.stockkeep`
- Check for errors: `adb logcat | grep AndroidRuntime`

**Changes not showing?**
- Make sure you're saving files
- Do a clean build: `./gradlew clean`
- Check that you're editing the right file

## Pro Tips

1. **Use two terminals**: One for editing, one for deploying
2. **Keep scrcpy running**: No need to restart it between deploys
3. **Use adb logcat**: Watch logs in real-time: `adb logcat -s StockKeep:D`
4. **Screenshot**: scrcpy supports screenshots: click the camera icon
5. **Record video**: scrcpy can record: `scrcpy --record file.mp4`

## Alternative: Physical Device

If you have an Android phone:

```bash
# Enable USB debugging on your phone first
adb devices  # Should show your device
./gradlew installDebug
adb shell monkey -p com.example.stockkeep -c android.intent.category.LAUNCHER 1
scrcpy  # Mirror your physical device
```

This is even faster than the emulator!
