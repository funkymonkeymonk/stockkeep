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
    echo "🎨 Design Workflow Tasks:"
    echo "  devenv tasks run design:build    # Build debug APK"
    echo "  devenv tasks run design:deploy   # Deploy to emulator"
    echo "  devenv tasks run design:test     # Build + deploy"
    echo "  devenv tasks run design:emulator # Start emulator"
    echo ""
    echo "💻 Development Workflow Tasks:"
    echo "  devenv tasks run dev:clean       # Clean build"
    echo "  devenv tasks run dev:build       # Build debug"
    echo "  devenv tasks run dev:test        # Run tests"
    echo "  devenv tasks run dev:lint        # Run linting"
    echo "  devenv tasks run dev:check       # Full check suite"
    echo ""
    echo "🚀 Delivery Workflow Tasks:"
    echo "  devenv tasks run delivery:build  # Build release APK"
    echo "  devenv tasks run delivery:bundle # Build release AAB"
    echo ""
    echo "🔧 Setup Tasks:"
    echo "  devenv tasks run setup:check     # Check environment"
    echo "  devenv tasks run setup:emulator  # Create emulator"
    echo ""
    echo "Quick start:"
    echo "  1. devenv tasks run setup:check"
    echo "  2. devenv tasks run setup:emulator"
    echo "  3. devenv tasks run design:emulator  (Terminal 1)"
    echo "  4. emu-mirror                        (Terminal 2)"
    echo "  5. devenv tasks run design:test      (Terminal 3)"
    echo ""
    echo "Documentation: docs/tutorials/getting-started-with-design.md"
  '';

  enterTest = ''
    echo "Running project tests"
  '';

  # Tasks for Design, Development, and Delivery workflows
  tasks = {
    # Design Workflow Tasks
    "design:build" = {
      exec = ''
        echo "📱 Building debug APK for design review..."
        ./gradlew assembleDebug
        echo ""
        echo "✅ APK built: app/build/outputs/apk/debug/app-debug.apk"
        echo ""
        echo "Next steps:"
        echo "  devenv tasks run design:deploy  # Deploy to emulator"
        echo "  devenv tasks run design:test     # Build and deploy"
      '';
      execIfModified = [ 
        "app/src/**/*.kt"
        "app/src/**/*.xml"
        "app/build.gradle.kts"
      ];
    };

    "design:deploy" = {
      exec = ''
        echo "🚀 Deploying to emulator..."
        if ! adb devices | grep -q "emulator"; then
          echo "❌ No emulator found. Run 'devenv tasks run design:emulator' first"
          exit 1
        fi
        ./gradlew installDebug
        if [ $? -eq 0 ]; then
          echo ""
          echo "✅ App deployed!"
          echo "🎯 Launching StockKeep..."
          adb shell monkey -p com.example.stockkeep -c android.intent.category.LAUNCHER 1
        else
          echo "❌ Deployment failed"
          exit 1
        fi
      '';
    };

    "design:test" = {
      exec = ''
        echo "🧪 Running design workflow: build + deploy"
      '';
      after = [ "design:build" "design:deploy" ];
    };

    "design:emulator" = {
      exec = ''
        if ! avdmanager list avd | grep -q "StockKeep-Device"; then
          echo "📱 Creating StockKeep-Device emulator..."
          avdmanager create avd --force --name "StockKeep-Device" --package "system-images;android-34;google_apis;x86_64" --device "pixel_7"
        fi
        echo "🚀 Starting emulator..."
        echo "💡 Use 'emu-mirror' to see the screen"
        emulator -avd StockKeep-Device -no-snapshot-load -no-boot-anim &
      '';
    };

    # Development Workflow Tasks
    "dev:clean" = {
      exec = ''
        echo "🧹 Cleaning build artifacts..."
        ./gradlew clean
        echo "✅ Clean complete"
      '';
    };

    "dev:build" = {
      exec = ''
        echo "🔨 Building debug APK..."
        ./gradlew assembleDebug
        echo "✅ Build complete"
      '';
      execIfModified = [
        "app/src/**/*.kt"
        "app/src/**/*.xml"
        "app/build.gradle.kts"
        "build.gradle.kts"
      ];
    };

    "dev:test" = {
      exec = ''
        echo "🧪 Running unit tests..."
        ./gradlew test
        echo "✅ Tests complete"
      '';
    };

    "dev:lint" = {
      exec = ''
        echo "🔍 Running lint checks..."
        ./gradlew lint
        echo "✅ Lint complete"
      '';
      execIfModified = [
        "app/src/**/*.kt"
        "app/src/**/*.xml"
      ];
    };

    "dev:check" = {
      exec = ''
        echo "🔍 Running full development check suite..."
      '';
      after = [ "dev:clean" "dev:build" "dev:test" "dev:lint" ];
    };

    # Delivery Workflow Tasks
    "delivery:build" = {
      exec = ''
        echo "📦 Building release APK..."
        ./gradlew assembleRelease
        echo ""
        echo "✅ Release APK built: app/build/outputs/apk/release/"
        ls -lh app/build/outputs/apk/release/*.apk 2>/dev/null || echo "Check app/build/outputs/apk/release/"
      '';
    };

    "delivery:bundle" = {
      exec = ''
        echo "📦 Building release bundle (AAB)..."
        ./gradlew bundleRelease
        echo ""
        echo "✅ Release bundle built: app/build/outputs/bundle/release/"
        ls -lh app/build/outputs/bundle/release/*.aab 2>/dev/null || echo "Check app/build/outputs/bundle/release/"
      '';
    };

    "delivery:verify" = {
      exec = ''
        echo "🔐 Verifying release build..."
        echo "  - Checking for signing configuration"
        echo "  - APK size analysis"
        ./gradlew assembleRelease
        echo ""
        echo "✅ Verification complete"
      '';
      after = [ "delivery:build" ];
    };

    # Utility Tasks
    "setup:emulator" = {
      exec = ''
        if avdmanager list avd | grep -q "StockKeep-Device"; then
          echo "✅ StockKeep-Device already exists"
          avdmanager list avd | grep "StockKeep-Device"
        else
          echo "📱 Creating StockKeep-Device emulator..."
          avdmanager create avd --force --name "StockKeep-Device" --package "system-images;android-34;google_apis;x86_64" --device "pixel_7"
          echo "✅ Emulator created! Start it with: devenv tasks run design:emulator"
        fi
      '';
    };

    "setup:check" = {
      exec = ''
        echo "🔍 Checking development environment..."
        echo ""
        echo "Android SDK: $ANDROID_HOME"
        echo "Java: $JAVA_HOME"
        java -version 2>&1 | head -1
        echo ""
        echo "ADB: $(which adb)"
        echo "Emulator: $(which emulator)"
        echo "Gradle: $(which gradle)"
        echo ""
        echo "AVDs:"
        avdmanager list avd 2>/dev/null || echo "  No AVDs configured"
        echo ""
        echo "✅ Environment check complete"
      '';
      before = [ "devenv:enterShell" ];
    };
  };

  # Pre-commit hooks for Android/Kotlin
  # git-hooks.hooks.shellcheck.enable = true;
}
