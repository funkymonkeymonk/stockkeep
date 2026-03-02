{ pkgs, lib, config, ... }:

{
  env.GREET = "Welcome to StockKeep Android Development";

  # Enable shell scripts
  packages = with pkgs; [
    git
    openjdk17
    android-sdk
    android-sdkCommandLineToolsOnly
  ];

  env.ANDROID_HOME = "${config.android-sdk-sdkcmdline-tools-only}/libexec/android-sdk";
  env.ANDROID_SDK_ROOT = "${config.android-sdk-sdkcmdline-tools-only}/libexec/android-sdk";
  env.JAVA_HOME = "${lib.mkDefault pkgs.openjdk17}";

  # Design workflow helper scripts
  scripts.design-preview.exec = ''
    echo "Starting Compose Preview workflow..."
    echo "Open Android Studio and use @Preview annotations"
    echo "See docs/tutorials/getting-started-with-design.md for details"
  '';

  scripts.design-build.exec = ''
    echo "Building debug APK for design review..."
    ./gradlew assembleDebug
    echo "APK location: app/build/outputs/apk/debug/app-debug.apk"
  '';

  enterShell = ''
    echo "Android SDK: $ANDROID_HOME"
    java -version
    echo ""
    echo "Design commands available:"
    echo "  design-preview  - Show Compose Preview workflow info"
    echo "  design-build    - Build debug APK for design testing"
    echo ""
    echo "Documentation: docs/tutorials/getting-started-with-design.md"
  '';

  enterTest = ''
    echo "Running project tests"
  '';

  # Pre-commit hooks for Android/Kotlin
  # git-hooks.hooks.shellcheck.enable = true;
}
