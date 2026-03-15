# Visual Design Workflow

This document explains the philosophy and rationale behind the terminal-based visual design workflow for StockKeep.

## Why Terminal-Based Design?

StockKeep uses a **terminal-first approach** to visual design, avoiding heavy IDEs while maintaining rapid iteration capabilities.

### The Traditional Problem

Most Android development requires:
1. **Android Studio** - Heavy IDE (several GB download)
2. **Compose Previews** - IDE-only feature
3. **Resource overhead** - Significant RAM/CPU usage
4. **Context switching** - Between editor, IDE, and emulator

### The Terminal Solution

StockKeep's workflow:
1. **Editor of choice** - vim, helix, emacs, or any text editor
2. **Terminal emulator** - Run Android emulator from command line
3. **Screen mirroring** - scrcpy for high-quality display
4. **Rapid deployment** - One command to build and install

This keeps you in your preferred environment while providing full visual feedback.

## The Code-As-Design Approach

### Single Source of Truth

Your design IS your implementation:

- **Colors** defined in `Color.kt` are the actual app colors
- **Typography** in `Type.kt` is the actual text rendering
- **Layouts** in screen files are the actual UI structure
- **Components** tested on emulator are production components

This eliminates the "design handoff" problem where designs differ from implementation.

### Feedback Loop

Terminal-based workflow:
```
Change color in code → Build (10s) → Deploy (2s) → See on emulator → Adjust → Repeat
```

Total iteration time: **~15 seconds**

While slower than IDE previews (instant), this is:
- Much faster than traditional design tools
- Lighter than running a full IDE
- More flexible (use any editor)
- Fully version controlled

### Tools in the Loop

```
┌─────────────┐    ┌──────────────┐    ┌─────────────┐    ┌─────────────┐
│   Editor    │───→│ Gradle Build │───→│   ADB       │───→│  Emulator   │
│ (vim/helix) │    │ (./gradlew)  │    │ (install)   │    │ (scrcpy)    │
└─────────────┘    └──────────────┘    └─────────────┘    └─────────────┘
      ↑                                                      │
      └────────────────── Iterate ──────────────────────────┘
```

## Benefits for StockKeep

### 1. Inventory App Specifics

Inventory apps have repetitive UI patterns:
- List items with consistent cards
- Form inputs with similar styling
- Scan interfaces with overlays
- Data-dense displays

The terminal workflow lets you:
- Perfect one card design, then replicate
- Test with realistic inventory data
- Verify readability with long product names
- Ensure scanning UI works in both themes
- Test on actual Android runtime (not preview simulation)

### 2. Editor Freedom

Use your preferred editor:
- **vim/neovim** - Modal editing, powerful macros
- **helix** - Post-modal editor with built-in LSP
- **emacs** - Extensible, customizable environment
- **VS Code** (terminal) - If you prefer GUI editors
- **Any editor** - No lock-in to a specific IDE

### 3. Lightweight Environment

Compare resource usage:

| Component | Android Studio | Terminal + Emulator |
|-----------|----------------|---------------------|
| **Memory** | 4-8 GB | 2-4 GB |
| **Disk** | 5-10 GB | 1-2 GB |
| **Startup** | Slow | Fast |
| **Updates** | Frequent, large | Minimal |

### 4. Version Control Integration

All design iterations are:
- Plain text (Kotlin code)
- Git-tracked
- Diff-able
- Reviewable in PRs
- Revertible

## Trade-offs and Limitations

### What You Gain

- **Lightweight**: No heavy IDE to run
- **Flexibility**: Use any editor
- **Accuracy**: Test on real Android runtime
- **Versioning**: Design changes tracked in Git
- **Speed**: Faster than traditional design tools
- **Automation**: Easy to script and automate

### What You Lose

- **Instant previews**: 15s iteration vs instant (IDE)
- **Compose Previews**: @Preview annotations don't work without IDE
- **Visual debugger**: No IDE debugging tools
- **Vector editing**: No pen tool or bezier curves
- **Asset creation**: Still need tools for complex icons
- **Stakeholder review**: Non-technical stakeholders may prefer design tool links

### When to Consider an IDE

Even with this workflow, you might want an IDE for:
- **Debugging complex issues** - Visual debugger is helpful
- **Compose Previews** - If you need instant visual feedback
- **Refactoring** - IDE refactoring tools are powerful
- **Profiling** - Memory/CPU analysis

**Recommendation:** Start with terminal workflow. Add IDE later if needed.

### When to Use External Tools

Even with the terminal workflow, you might still want:

1. **Penpot or Figma**: For initial concept exploration, stakeholder presentations, or complex illustrations
2. **Vector editors**: For custom icons that require complex shapes (Inkscape)
3. **Image editors**: For photo manipulation or raster assets

For StockKeep specifically:
- Use terminal + emulator for 90% of design work (screens, components, themes)
- Use external tools only for the app icon and any complex illustrations

## The Iterative Design Process

### Phase 1: Theme Foundation (Day 1)

Establish the visual foundation:
1. Define color palette in `Color.kt`
2. Set typography scale in `Type.kt`
3. Build and deploy to emulator
4. Test in both light and dark modes

**Commands:**
```bash
vim app/src/main/java/com/example/stockkeep/ui/theme/Color.kt
emu-deploy
# Check on emulator via scrcpy
```

### Phase 2: Component Library (Day 2-3)

Build reusable components and test on emulator:
1. Inventory item card
2. Scan button and overlay
3. Form inputs (text fields, dropdowns)
4. Empty states and loading indicators

Each component gets:
- Tested with different data (normal, empty, error states)
- Tested in both light and dark themes
- Edge case testing (long text, zero quantity)

**Commands:**
```bash
# Edit component
vim app/src/main/java/com/example/stockkeep/ui/screens/InventoryScreen.kt

# Deploy and test
emu-deploy

# Test different states by editing test data in code
```

### Phase 3: Screen Assembly (Day 4-5)

Compose screens from components:
1. Inventory list screen
2. Scan screen with camera overlay
3. Add/Edit item dialogs
4. Settings/preferences screen

Each screen gets:
- Full testing on emulator
- Different device sizes (change emulator settings)
- Real interaction testing

### Phase 4: Polish (Ongoing)

Continuous refinement:
1. Animation tuning (test on emulator)
2. Accessibility testing (font scaling, contrast)
3. Edge case handling
4. Performance optimization

## Collaboration with Terminal Workflow

### Designer + Developer Workflow

**Traditional:**
- Designer works in Figma
- Hands off designs to developer
- Developer implements
- Designer reviews build
- Iterate

**With Terminal Workflow:**
- Designer checks out code
- Edits theme files with preferred editor
- Runs `emu-deploy` to see changes
- Takes screenshots with scrcpy
- Submits PR with screenshots
- Developer reviews code
- Merge

### Sharing Design Progress

Capture screenshots for stakeholders:
1. Use scrcpy's built-in screenshot tool
2. Or use `adb shell screencap` command
3. Commit images to `docs/design-screenshots/`
4. Reference in PR descriptions
5. Include in documentation

**Commands:**
```bash
# Screenshot via adb
adb shell screencap -p /sdcard/screen.png
adb pull /sdcard/screen.png docs/design-screenshots/

# Or use scrcpy's screenshot button (camera icon)
```

This creates a visual changelog of design evolution.

## Best Practices for This Workflow

### 1. Emulator-Driven Development

Always test on emulator:
```bash
# After any visual change
./gradlew installDebug
adb shell monkey -p com.example.stockkeep -c android.intent.category.LAUNCHER 1
```

### 2. Two-Terminal Setup

**Terminal 1** (Editor):
```bash
devenv shell
vim app/src/.../Color.kt  # Edit
emu-deploy               # Deploy
```

**Terminal 2** (Emulator):
```bash
devenv shell
emu-start                # Start once
emu-mirror               # Keep running
```

Then implement the actual component.

### 2. State Coverage

Preview all states:
- Loading
- Empty
- Error
- Success with data
- Interaction states

### 3. Theme Testing

Always check both themes on the emulator:
```bash
# Enable dark mode
adb shell cmd uimode night yes

# Disable dark mode  
adb shell cmd uimode night no
```

### 4. Realistic Data

Use sample data that resembles production:
```kotlin
val sampleItem = InventoryItem(
    name = "Wireless Bluetooth Headphones",
    quantity = 42,
    price = 79.99
)
```

### 5. Document Decisions

When you make design choices, document why:
```kotlin
// Using secondary color for delete actions
// to indicate destructive operations
IconButton(
    onClick = { deleteItem() },
    colors = IconButtonDefaults.iconButtonColors(
        contentColor = MaterialTheme.colorScheme.error
    )
)
```

## Comparison with Alternatives

### Terminal + Emulator vs. IDE (Android Studio)

| Aspect | Terminal + Emulator | IDE (Android Studio) |
|--------|---------------------|---------------------|
| **Iteration speed** | ~15 seconds | Instant (with Preview) |
| **Resource usage** | Light (2-4 GB RAM) | Heavy (4-8 GB RAM) |
| **Editor choice** | Any (vim, helix, emacs) | Fixed (IDE built-in) |
| **Setup time** | Fast | Slow (large download) |
| **Debugging** | CLI tools | Visual debugger |
| **Compose Previews** | Not available | Available |
| **Cost** | Free | Free |

### Terminal + Emulator vs. Figma/Sketch

| Aspect | Terminal + Emulator | Figma |
|--------|---------------------|-------|
| **Implementation** | Code is design | Separate handoff |
| **Iteration speed** | ~15 seconds | Fast (visual tool) |
| **Real data** | Native (Android runtime) | Simulated |
| **Developer learning** | Requires code | Visual only |
| **Asset export** | Not needed | Required |
| **Cost** | Free | Freemium/Paid |

## Future Possibilities

### Compose Multiplatform

If StockKeep expands to other platforms:
- Share previews across Android, Desktop, iOS
- Single design system for all platforms
- Consistent user experience

### Design System Export

Tools exist to:
- Export Compose themes to Figma
- Generate Compose from design tokens
- Sync design systems bidirectionally

### AI-Assisted Design

Emerging capabilities:
- Generate previews from text descriptions
- Auto-suggest color palettes
- Convert screenshots to Compose code

## Conclusion

The terminal-based workflow provides a **lightweight, flexible approach** to Android UI design. It leverages StockKeep's **Compose-native architecture** while avoiding the overhead of a heavy IDE:

- **Lightweight** - No IDE required, use any editor
- **Accurate** - Test on real Android runtime (emulator)
- **Fast enough** - ~15 second iteration cycle
- **Maintainable** - Everything version controlled
- **Flexible** - Easy to automate and script

For a data-driven inventory app with consistent UI patterns, this approach strikes the right balance between iteration speed and tool simplicity. It keeps the focus on user experience while respecting developer preferences for lightweight tooling.

**The bottom line:** You get 90% of the benefit of IDE previews with 10% of the resource cost.

## Related Documentation

- [Getting Started with Visual Design](../tutorials/getting-started-with-design.md) - Tutorial
- [How-to: Use the Android Emulator from Terminal](../how-to/use-emulator-terminal.md) - Emulator guide
- [How-to: Customize App Theme](../how-to/customize-app-theme.md) - Theme customization
- [Design Tools Reference](../reference/design-tools.md) - Technical reference
- [How-to: Use Compose Previews (IDE-Only)](../how-to/use-compose-previews-ide-only.md) - If you later decide to use an IDE
