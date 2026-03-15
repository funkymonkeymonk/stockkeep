# StockKeep Documentation

Welcome to the StockKeep documentation. This documentation follows the [Diataxis framework](https://diataxis.fr/), organizing content into four distinct types based on user needs.

**Important:** This is a terminal-based workflow - no IDE required! All design and development is done from the command line using the Android emulator and scrcpy for screen mirroring.

## Documentation Structure

```
docs/
├── tutorials/          # Learning-oriented ("Teach me...")
├── how-to/             # Goal-oriented ("How do I...?")
├── reference/          # Information-oriented ("What is...?")
├── explanation/        # Understanding-oriented ("Why...?")
└── workflow-examples/  # Practical examples
```

## Quick Start

**New to StockKeep?** Start here:
1. [Tutorial: Getting Started with Visual Design](tutorials/getting-started-with-design.md)
2. [How-to: Use the Android Emulator from Terminal](how-to/use-emulator-terminal.md)
3. [Example: Card Redesign](workflow-examples/card-redesign-example.md)

**Need specific information?**
- [Reference: Design Tools](reference/design-tools.md) - Technical details
- [How-to: Customize App Theme](how-to/customize-app-theme.md) - Theme customization
- [Explanation: Visual Design Workflow](explanation/visual-design-workflow.md) - Philosophy

## Documentation Types

### Tutorials (Learning-oriented)

Tutorials take you through a learning experience. They are designed for users who want to learn by doing.

**Question answered:** "Can you teach me to...?"

- [Getting Started with Visual Design](tutorials/getting-started-with-design.md) - Learn terminal-based design workflow

### How-to Guides (Goal-oriented)

How-to guides help you accomplish a specific task. They assume you know what you want to achieve.

**Question answered:** "How do I...?"

- [Use the Android Emulator from Terminal](how-to/use-emulator-terminal.md) - Run emulator without Android Studio
- [Customize App Theme](how-to/customize-app-theme.md) - Change colors, typography, and shapes

### Reference (Information-oriented)

Reference documents describe the machinery. They are factual and complete.

**Question answered:** "What is...?" / "What does X do?"

- [Design Tools Reference](reference/design-tools.md) - Complete technical reference

### Explanation (Understanding-oriented)

Explanations clarify and illuminate. They provide context and background.

**Question answered:** "Why...?" / "Can you explain...?"

- [Visual Design Workflow](explanation/visual-design-workflow.md) - Philosophy and rationale

## Workflow Examples

Practical, end-to-end examples of design workflows:

- [Card Redesign Example](workflow-examples/card-redesign-example.md) - Complete card redesign workflow using terminal
- [Complete Theme Example](workflow-examples/complete-theme-example.md) - Building a custom theme from scratch

## Available Commands

When in the devenv shell, the following commands are available:

```bash
# Building
design-build                    # Build debug APK

# Emulator Management
emu-list                        # List Android Virtual Devices
emu-create                      # Create new AVD (Pixel 7 API 34)
emu-start                       # Start the emulator
emu-mirror                      # Mirror screen with scrcpy
emu-deploy                      # Build and deploy to emulator
emu-stop                        # Stop the emulator

# Direct Gradle
./gradlew assembleDebug         # Build APK
./gradlew installDebug          # Install on emulator/device
```

## Quick Workflow

```bash
# Terminal 1: Start emulator and mirror screen
devenv shell
emu-start        # Start emulator (keep running)
# In another terminal:
emu-mirror       # See the screen

# Terminal 2: Edit and deploy
devenv shell
vim app/src/main/java/com/example/stockkeep/ui/theme/Color.kt
./gradlew installDebug
adb shell monkey -p com.example.stockkeep -c android.intent.category.LAUNCHER 1
# See changes in scrcpy window!
```

## Navigation Guide

| I want to... | Go to... |
|-------------|----------|
| Learn the basics | [Getting Started Tutorial](tutorials/getting-started-with-design.md) |
| Run the emulator | [How-to: Use Emulator from Terminal](how-to/use-emulator-terminal.md) |
| Change app colors | [How-to: Customize App Theme](how-to/customize-app-theme.md) |
| Look up commands | [Design Tools Reference](reference/design-tools.md) |
| Understand the workflow | [Visual Design Workflow](explanation/visual-design-workflow.md) |
| See a complete example | [Workflow Examples](workflow-examples/) |

## Why Terminal-Based?

StockKeep uses a **terminal-first approach** for several reasons:

- **No IDE required** - Edit with vim, helix, or any editor you prefer
- **Lightweight** - No heavy Android Studio installation
- **Fast iteration** - Build and deploy from the command line
- **Screen mirroring** - Use scrcpy for a responsive, high-quality preview
- **Version control friendly** - Everything is text and scripts

The trade-off is you don't get Compose Previews (which require an IDE), but you get a faster, lighter workflow that many developers prefer.

## What About Compose Previews?

Compose Previews are a feature of Android Studio/IntelliJ IDEA that show UI instantly as you code. Since we're avoiding IDEs:

- **Instead of Previews:** We use the emulator + scrcpy
- **Iteration speed:** 10-15 seconds (build + deploy) vs instant
- **Trade-off:** Slightly slower but much lighter toolchain

If you later decide you want Previews, you can install IntelliJ IDEA Community Edition and add @Preview annotations alongside the terminal workflow.

## Requirements

- macOS or Linux
- Terminal (ghostty, kitty, alacritty, etc.)
- devenv (installed via Nix)
- Text editor (vim, helix, emacs, etc.)

**Not required:**
- Android Studio
- IntelliJ IDEA
- Any IDE

## Contributing to Documentation

When adding new documentation:

1. **Choose the right type** - Tutorial, How-to, Reference, or Explanation
2. **Keep it focused** - One document = one purpose
3. **Cross-reference** - Link to related documents
4. **Use examples** - Show, don't just tell
5. **Follow existing style** - Match tone and format

## Related Resources

- [Material Design 3 Guidelines](https://m3.material.io/)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [scrcpy Documentation](https://github.com/Genymobile/scrcpy)
- [ADB Documentation](https://developer.android.com/studio/command-line/adb)
- [Diataxis Framework](https://diataxis.fr/)

## Questions?

If you can't find what you need:
1. Check the [Design Tools Reference](reference/design-tools.md) for technical details
2. Review the [workflow examples](workflow-examples/) for practical patterns
3. Read the [explanation](explanation/visual-design-workflow.md) for context
