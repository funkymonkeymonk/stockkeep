# How to Use the Android Emulator from Terminal

This guide shows you how to run and control the Android emulator entirely from the terminal without Android Studio.

## Overview

StockKeep includes helper scripts in devenv to manage the Android emulator:
- Create and manage virtual devices
- Launch emulator with one command
- Mirror screen to desktop using scrcpy
- Deploy apps instantly

All from your terminal - no IDE required!

## Prerequisites

- StockKeep repository cloned
- devenv environment: `devenv shell`
- Terminal (ghostty, kitty, alacritty, etc.)

## Quick Start

```bash
# 1. Create a virtual device (one-time setup)
emu-create

# 2. Start the emulator
emu-start

# 3. In another terminal, mirror the screen
emu-mirror

# 4. Deploy and test StockKeep
emu-deploy
```

## Commands Reference

### emu-list

List all available Android Virtual Devices (AVDs).

```bash
$ emu-list
Available Android Virtual Devices:
StockKeep-Device
Pixel_3a_API_34
```

### emu-create

Create a new Android Virtual Device with sensible defaults.

```bash
$ emu-create
Creating new Android Virtual Device...
This will create a Pixel 7 API 34 device

AVD 'StockKeep-Device' created
Run 'emu-start' to launch the emulator
```

**What it creates:**
- Device: Pixel 7
- API Level: 34 (Android 14)
- System Image: google_apis/x86_64
- Name: StockKeep-Device

**Manual creation** (if automatic fails):
```bash
sdkmanager "system-images;android-34;google_apis;x86_64"
avdmanager create avd -n "StockKeep-Device" -d "pixel_7" -k "system-images;android-34;google_apis;x86_64"
```

### emu-start

Launch the emulator in the background.

```bash
$ emu-start
Starting Android Emulator...
Waiting for device to boot (this may take a minute)...

Emulator started successfully!
Device ID: emulator-5554

Next steps:
  1. Run 'emu-mirror' to see the screen (scrcpy)
  2. Run './gradlew installDebug' to deploy the app
  3. Run 'emu-stop' when done to shut down

Emulator running in background (PID: 12345)
```

**Options:**

The script uses these emulator flags:
- `-no-snapshot-load` - Fresh boot (faster when developing)
- `-no-boot-anim` - Skip boot animation (faster startup)

**Manual start with options:**
```bash
# With GPU acceleration (default)
emulator -avd StockKeep-Device

# Software rendering (if GPU issues)
emulator -avd StockKeep-Device -gpu swiftshader_indirect

# Cold boot (ignore snapshots)
emulator -avd StockKeep-Device -no-snapshot

# Wipe data (factory reset)
emulator -avd StockKeep-Device -wipe-data
```

### emu-mirror

Mirror the emulator screen to your desktop using scrcpy.

```bash
$ emu-mirror
Starting screen mirroring with scrcpy...
Use Ctrl+C to stop mirroring (emulator keeps running)
```

**What scrcpy provides:**
- High-performance screen mirroring
- Keyboard input forwarding
- Mouse/touch interaction
- File drag-and-drop
- Copy-paste sync
- Screen recording

**scrcpy options:**
```bash
# Different window size
scrcpy --max-size 1024

# Fullscreen
scrcpy --fullscreen

# Record screen
scrcpy --record stockkeep-demo.mp4

# Turn off device screen while mirroring
scrcpy --turn-screen-off

# Keep device awake
scrcpy --stay-awake

# All combined
scrcpy --turn-screen-off --stay-awake --max-size 1024 --window-title "StockKeep"
```

### emu-deploy

Build and deploy StockKeep to the emulator in one command.

```bash
$ emu-deploy
Building and deploying to emulator...
> Task :app:assembleDebug

App deployed successfully!
Launching StockKeep...
```

**What it does:**
1. Runs `./gradlew installDebug`
2. Launches the app: `adb shell monkey -p com.example.stockkeep ...`

**Manual deployment:**
```bash
./gradlew installDebug
adb shell monkey -p com.example.stockkeep -c android.intent.category.LAUNCHER 1
```

### emu-stop

Stop the running emulator.

```bash
$ emu-stop
Stopping emulator...
Emulator stopped
```

## Complete Workflow Example

### Setup (One-time)

```bash
cd /Users/monkey/repos/stockkeep
devenv shell
emu-create  # Creates StockKeep-Device
```

### Development Session

**Terminal 1** (Editor + Deploy):
```bash
devenv shell

# Edit theme
evim app/src/main/java/com/example/stockkeep/ui/theme/Color.kt

# Deploy and test
emu-deploy
```

**Terminal 2** (Screen Mirroring):
```bash
devenv shell

# Start emulator (if not running)
emu-start

# Mirror screen
emu-mirror

# Keep this terminal open - scrcpy runs here
```

## Advanced ADB Commands

Since you have the emulator running, you can use adb directly:

### Device Management

```bash
# List connected devices
adb devices

# Check device state
adb get-state

# Open shell on device
adb shell

# Copy file to device
adb push local.txt /sdcard/

# Copy file from device
adb pull /sdcard/remote.txt ./
```

### App Management

```bash
# Install APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Reinstall (keep data)
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Uninstall
adb uninstall com.example.stockkeep

# Clear app data
adb shell pm clear com.example.stockkeep

# Force stop
adb shell am force-stop com.example.stockkeep

# Start app
adb shell monkey -p com.example.stockkeep -c android.intent.category.LAUNCHER 1
```

### Debugging

```bash
# View logs
adb logcat

# Filter logs
adb logcat -s StockKeep:D

# Clear logs
adb logcat -c

# Bug report
adb bugreport
```

### UI Automation

```bash
# Tap on screen (x y coordinates)
adb shell input tap 500 500

# Swipe
adb shell input swipe 100 500 100 100

# Text input
adb shell input text "Hello World"

# Key press
adb shell input keyevent 4  # Back button
adb shell input keyevent 3  # Home button
```

### System Settings

```bash
# Enable/disable dark mode
adb shell cmd uimode night yes
adb shell cmd uimode night no

# Change screen density
adb shell wm density 320

# Reset density
adb shell wm density reset

# Screen size
adb shell wm size 1080x1920
adb shell wm size reset
```

## Troubleshooting

### Emulator Issues

**"Emulator: command not found"**
```bash
# Check if emulator is in PATH
echo $ANDROID_HOME
cd $ANDROID_HOME/emulator
./emulator -list-avds
```

**"AVD not found"**
```bash
# List available AVDs
emulator -list-avds

# Check AVD location
ls ~/.android/avd/
```

**Black screen / Won't boot**
```bash
# Wipe data and cold boot
emulator -avd StockKeep-Device -wipe-data -no-snapshot

# Or try software rendering
emulator -avd StockKeep-Device -gpu swiftshader_indirect
```

**Slow performance**
```bash
# Check if HAXM/Hypervisor installed
emulator -accel-check

# Use hardware acceleration
emulator -avd StockKeep-Device -gpu host

# Reduce memory
# Edit ~/.android/avd/StockKeep-Device.avd/config.ini
# hw.ramSize=2048
```

### scrcpy Issues

**"No devices detected"**
```bash
# Check ADB
adb devices

# Restart ADB server
adb kill-server
adb start-server

# Reconnect
adb connect localhost:5555
```

**"Could not open video stream"**
```bash
# Try different encoder
scrcpy --encoder OMX.google.h264.encoder

# Or software encoding
scrcpy --render-driver software
```

**Laggy performance**
```bash
# Lower resolution
scrcpy --max-size 800

# Lower bitrate
scrcpy --bit-rate 4M

# Disable buffering
scrcpy --display-buffer=0
```

### ADB Issues

**"Device unauthorized"**
1. Check emulator screen for authorization dialog
2. Click "Always allow from this computer"
3. Restart ADB: `adb kill-server && adb start-server`

**"More than one device/emulator"**
```bash
# Specify device
adb -s emulator-5554 install app.apk

# Or disconnect other devices
adb disconnect
```

## Performance Tips

### Speed Up Emulator

1. **Enable virtualization** in BIOS (Intel VT-x / AMD-V)
2. **Use HAXM** (Intel) or **Hypervisor** (AMD/Apple Silicon)
3. **Allocate more RAM** in AVD settings
4. **Use host GPU** for rendering
5. **Disable boot animation**: `-no-boot-anim`

### Speed Up Builds

```bash
# Gradle daemon
./gradlew --daemon

# Parallel builds
export GRADLE_OPTS="-Xmx4096m -XX:MaxMetaspaceSize=512m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8"

# Configuration cache
./gradlew assembleDebug --configuration-cache
```

### Workflow Optimization

1. **Keep emulator running** between sessions
2. **Use incremental builds** (don't `clean` unless necessary)
3. **Hot reload** with Compose: some changes don't need reinstall
4. **scrcpy persistent**: Keep mirroring window open

## Comparison: Terminal vs IDE

| Task | Terminal | IDE (Android Studio) |
|------|----------|---------------------|
| **Start emulator** | `emu-start` | Click button |
| **View screen** | `emu-mirror` | Built-in panel |
| **Deploy app** | `emu-deploy` | Click run button |
| **Edit code** | vim/emacs/helix | Built-in editor |
| **Debug** | `adb logcat` | Visual debugger |
| **Memory/CPU** | CLI tools | Visual profiler |
| **Compose Preview** | Not available | @Preview annotations |

**Recommendation:** Use terminal for rapid iteration, IDE for debugging and previews.

## Related Documentation

- [Getting Started with Visual Design](../tutorials/getting-started-with-design.md) - Tutorial
- [Design Tools Reference](../reference/design-tools.md) - Technical reference
- [Customize App Theme](./customize-app-theme.md) - Theme customization
