{ pkgs, ... }:

{
  android = {
    enable = true;
    platforms.version = [ "34" ];
    systemImageTypes = [ "google_apis" ];
    abis = [ "x86_64" ];
    buildTools.version = [ "34.0.0" ];
    cmdLineTools.version = "11.0";
    emulator = {
      enable = true;
    };
    systemImages.enable = true;
    extras = [ ];
  };

  packages = with pkgs; [
    git
    openjdk17
    scrcpy
  ];


  env.ANDROID_EMULATOR_HOME = "$HOME/.android/avd";
  env.ANDROID_AVD_HOME = "$HOME/.android/avd";

  # Build helper script
  scripts.design-build.exec = ''
    echo "Building debug APK for design review..."
    ./gradlew assembleDebug
    echo "APK location: app/build/outputs/apk/debug/app-debug.apk"
    echo ""
    echo "To test on emulator:"
    echo "  1. emu-start    (if not running)"
    echo "  2. emu-mirror   (to see the screen)"
    echo "  3. ./gradlew installDebug"
  '';

  # Emulator helper scripts
  scripts.emu-list.exec = ''
    echo "Available Android Virtual Devices:"
    avdmanager list avd 2>/dev/null || echo "No AVDs found. Run 'emu-create' to create one."
  '';

  scripts.emu-create.exec = ''
    echo "Creating new Android Virtual Device..."
    echo "This will create a Pixel 7 API 34 device"
    
    avdmanager create avd --force --name "StockKeep-Device" --package "system-images;android-34;google_apis;x86_64" --device "pixel_7"
    
    if [ $? -eq 0 ]; then
      echo ""
      echo "AVD 'StockKeep-Device' created successfully!"
      echo "Run 'emu-start' to launch the emulator"
    else
      echo ""
      echo "AVD creation failed. You may need to install system images first:"
      echo "  sdkmanager 'system-images;android-34;google_apis;x86_64'"
    fi
  '';

  scripts.emu-start.exec = ''
    echo "Starting Android Emulator..."
    
    # Check if emulator exists
    if ! avdmanager list avd | grep -q "StockKeep-Device"; then
      echo "No StockKeep-Device found. Run 'emu-create' first."
      exit 1
    fi
    
    echo "Waiting for device to boot (this may take a minute)..."
    
    # Start emulator in background
    emulator -avd StockKeep-Device -no-snapshot-load -no-boot-anim &
    EMU_PID=$!
    
    # Wait for boot
    adb wait-for-device
    
    echo ""
    echo "Emulator started successfully!"
    echo "Device ID: $(adb devices | grep emulator | cut -f1)"
    echo ""
    echo "Next steps:"
    echo "  1. Run 'emu-mirror' to see the screen (scrcpy)"
    echo "  2. Run './gradlew installDebug' to deploy the app"
    echo "  3. Run 'emu-stop' when done to shut down"
    echo ""
    echo "Emulator running in background (PID: $EMU_PID)"
  '';

  scripts.emu-mirror.exec = ''
    if ! adb devices | grep -q "emulator"; then
      echo "No emulator found. Run 'emu-start' first."
      exit 1
    fi
    
    echo "Starting screen mirroring with scrcpy..."
    echo "Use Ctrl+C to stop mirroring (emulator keeps running)"
    echo ""
    scrcpy --turn-screen-off --stay-awake --window-title "StockKeep Preview"
  '';

  scripts.emu-stop.exec = ''
    echo "Stopping emulator..."
    adb emu kill 2>/dev/null || echo "Emulator not running or already stopped"
    echo "Emulator stopped"
  '';

  scripts.emu-deploy.exec = ''
    echo "Building and deploying to emulator..."
    ./gradlew installDebug
    
    if [ $? -eq 0 ]; then
      echo ""
      echo "App deployed successfully!"
      echo "Launching StockKeep..."
      adb shell monkey -p com.example.stockkeep -c android.intent.category.LAUNCHER 1
    else
      echo "Deployment failed"
      exit 1
    fi
  '';

  enterShell = ''
    echo "Android SDK: $ANDROID_HOME"
    echo "Java: $JAVA_HOME"
    java -version 2>&1 | head -1
    echo ""
    echo "Design commands available:"
    echo "  design-build    - Build debug APK for design testing"
    echo ""
    echo "Emulator commands (no IDE needed!):"
    echo "  emu-list        - List available Android Virtual Devices"
    echo "  emu-create      - Create a new AVD (Pixel 7 API 34)"
    echo "  emu-start       - Start the emulator"
    echo "  emu-mirror      - Mirror emulator screen (scrcpy)"
    echo "  emu-deploy      - Build and deploy to emulator"
    echo "  emu-stop        - Stop the emulator"
    echo ""
    echo "Quick start: emu-create && emu-start && emu-mirror"
    echo "Documentation: docs/tutorials/getting-started-with-design.md"
  '';

  enterTest = ''
    echo "Running project tests"
  '';

  # Pre-commit hooks for Android/Kotlin
  # git-hooks.hooks.shellcheck.enable = true;
}
